package cn.org.ferry.doc.utils.docx4j;

import cn.org.ferry.system.annotations.NotNull;
import cn.org.ferry.system.exception.FileException;
import cn.org.ferry.system.utils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.TraversalUtil;
import org.docx4j.dml.picture.Pic;
import org.docx4j.dml.wordprocessingDrawing.Anchor;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.org.apache.poi.util.IOUtils;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>docx4j替换书签工具类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/08/25 12:21
 */

public final class BookMarkReplaceUtil {
    private static final Logger logger = LoggerFactory.getLogger(BookMarkReplaceUtil.class);

    private BookMarkReplaceUtil(){}

    /**
     * 书签替换
     * @param mlPackage 包
     * @param bookmark 书签
     * @param rangeStart 书签开始位置
     * @param rangeEnd 书签结束位置
     * @param value 替换的图片，可以是一个路径，也可以是一个字节数组
     * @param bookMarkType 书签类型
     * @throws FileException 若出现错误，统一抛出该异常
     */
    public static void replace(WordprocessingMLPackage mlPackage, @NotNull CTBookmark bookmark,
                               int rangeStart, int rangeEnd, @NotNull Object value,
                               @NotNull BookMarkType bookMarkType) throws FileException {
        Objects.requireNonNull(bookmark,"书签不能为空");
        Objects.requireNonNull(value,"图片资源不能为空");
        Objects.requireNonNull(bookMarkType,"书签类型不能为空");
        switch (bookMarkType){
            case TEXT:
                replaceText(bookmark, rangeStart, rangeEnd, value);
                break;
            case IMAGE:
                byte[] bs;
                if(value instanceof String){
                    try {
                        bs = IOUtils.toByteArray(new FileInputStream((String) value));
                    } catch (IOException e) {
                        FileException fileException = new FileException("can not load image "+value);
                        fileException.initCause(e);
                        throw fileException;
                    }
                }else if(value instanceof byte[]){
                    bs = (byte[]) value;
                }else{
                    throw new FileException("替换图片，值必须为字节数组类型，当前类型为："+value.getClass().getName());
                }
                replaceImage(mlPackage, bookmark, bs);
                break;
            case GRID:
                break;
        }
    }

    /**
     * 书签替换文本
     * @param bookmark 书签
     * @param rangeStart 书签开始位置
     * @param rangeEnd 书签结束位置
     * @param value 替换的内容
     * @throws FileException 若出现错误，统一抛出该异常
     */
    public static void replaceText(CTBookmark bookmark, int rangeStart, int rangeEnd, Object value) throws FileException {
        String content = BeanUtils.ifnull(value, BeanUtils.EMPTY).toString();
        List<Object> bookMarkParentList = TraversalUtil.getChildrenImpl(bookmark.getParent());
        Text text = null;
        for (int i = rangeStart+1; i < rangeEnd; i++) {
            text = getText(bookMarkParentList.get(i));
            if(text != null){
                text.setValue(content);
                break;
            }
        }
        if(text == null){
            R r = Docx4jCommonUtil.initR();
            RPr rPr = null;
            if(rangeStart != 0){
                for (int i = rangeStart-1; i >= 0; i--) {
                    List<R> rList = Docx4jCommonUtil.getAllElement(bookMarkParentList.get(i), R.class);
                    if(CollectionUtils.isNotEmpty(rList)){
                        rPr = rList.get(rList.size()-1).getRPr();
                        break;
                    }
                }
            }
            if(rPr == null){
                rPr = Docx4jCommonUtil.initRPr();
                rPr.setRFonts(Docx4jCommonUtil.initRFonts());
            }
            text = Docx4jCommonUtil.initText(content);
            r.getContent().add(rPr);
            r.getContent().add(text);
            bookMarkParentList.add(rangeEnd+1, r);
        }
    }

    /**
     * 书签替换图片
     * @param mlPackage 包
     * @param bookmark 书签
     * @param bs 替换的图片
     * @throws FileException 若出现错误，统一抛出该异常
     */
    public static void replaceImage(WordprocessingMLPackage mlPackage, CTBookmark bookmark, byte[] bs) throws FileException {
        // 模版中的图片
        List<Drawing> drawList = Docx4jCommonUtil.getAllElement(bookmark.getParent(), Drawing.class);
        if(CollectionUtils.isEmpty(drawList)){
            throw new FileException("图片书签未包含图片!");
        }
        List<Object> list = new ArrayList<>();
        for (Drawing drawing : drawList) {
            list.addAll(drawing.getAnchorOrInline());
        }
        Inline templateInline = null;
        Anchor templateAnchor = null;
        for (Object o : list) {
            if(o instanceof Inline){
                Inline inline = (Inline) o;
                if(StringUtils.equals(bookmark.getName(), inline.getDocPr().getDescr())){
                    templateInline = inline;
                    break;
                }
            }else if(o instanceof Anchor){
                Anchor anchor = (Anchor) o;
                if(StringUtils.equals(bookmark.getName(), anchor.getDocPr().getDescr())){
                    templateAnchor = anchor;
                    break;
                }
            }
        }
        if(templateInline == null && templateAnchor == null){
            throw new FileException("未找到模版待替换图片。图片的书签名，图片的替换文字必须保持一致！");
        }

        // 目标图片
        Inline replaceInline;
        try {
            logger.info("create inline image");
            BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(mlPackage,bs);
            replaceInline = imagePart.createImageInline(null, null, 0, 1, false);
        } catch (Exception e) {
            FileException fileException = new FileException("create image error");
            fileException.initCause(e);
            throw fileException;
        }
        long replaceCx = replaceInline.getExtent().getCx();
        long replaceCy = replaceInline.getExtent().getCy();
        String embed = replaceInline.getGraphic().getGraphicData().getPic().getBlipFill().getBlip().getEmbed();

        if(templateInline != null){
            long templateCx = templateInline.getExtent().getCx();
            long templateCy = templateInline.getExtent().getCy();
            Pic pic = templateInline.getGraphic().getGraphicData().getPic();
            // 替换
            pic.getBlipFill().getBlip().setEmbed(embed);
            Map<String,Long> map = dealCxy(templateCx, templateCy, replaceCx, replaceCy);
            templateInline.getExtent().setCx(map.get("setCx"));
            templateInline.getExtent().setCy(map.get("setCy"));
        }else{
            long templateCx = templateAnchor.getExtent().getCx();
            long templateCy = templateAnchor.getExtent().getCy();
            Pic pic = templateAnchor.getGraphic().getGraphicData().getPic();
            // 替换
            pic.getBlipFill().getBlip().setEmbed(embed);
            Map<String,Long> map = dealCxy(templateCx, templateCy, replaceCx, replaceCy);
            templateAnchor.getExtent().setCx(map.get("setCx"));
            templateAnchor.getExtent().setCy(map.get("setCy"));
        }
    }

    /**
     * 处理图片适应大小
     */
    private static Map<String, Long> dealCxy(Long templateCx, Long templateCy, Long targetCx, Long targetCy){
        Map<String, Long> map = new HashMap<>();
        Long cx;
        Long cy;

        if (targetCx > templateCx){
            if (targetCy <= templateCy){
                cx = templateCx;
                cy = targetCy / (targetCx/templateCx);
            } else {
                if ((targetCx/templateCx) > (targetCy/templateCy)){
                    cx = templateCx;
                    cy = targetCy / (targetCx/templateCx);
                } else {
                    cy = templateCy;
                    cx = targetCx / (targetCy/templateCy);
                }
            }
        } else {
            if (targetCy > templateCy) {
                cx = templateCx;
                cy = targetCy * (templateCx/targetCx);
            } else {
                if ((templateCx/targetCx) > (templateCy/targetCy)) {
                    cx = templateCx;
                    cy = targetCy * (templateCx/targetCx);
                } else {
                    cy = templateCy;
                    cx = targetCy * (templateCy/targetCy);
                }
            }
        }
        map.put("setCx",cx);
        map.put("setCy",cy);
        return map;
    }

    private static Text getText(Object o) throws FileException {
        List<Text> textList = Docx4jCommonUtil.getAllElement(o, Text.class);
        if(CollectionUtils.isEmpty(textList)){
            return null;
        }
        return textList.get(0);
    }
}
