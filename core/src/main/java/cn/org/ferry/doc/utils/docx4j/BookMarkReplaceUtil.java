package cn.org.ferry.doc.utils.docx4j;

import cn.org.ferry.system.annotations.NotNull;
import cn.org.ferry.system.exception.FileException;
import cn.org.ferry.system.utils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.dml.picture.Pic;
import org.docx4j.dml.wordprocessingDrawing.Anchor;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.org.apache.poi.util.IOUtils;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.CTMarkupRange;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private static final String CX = "cx";
    private static final String CY = "cy";

    private BookMarkReplaceUtil(){}

    /**
     * 书签替换
     * @param mlPackage 包
     * @param bookmark 书签
     * @param value 替换的图片，可以是一个路径，也可以是一个字节数组
     * @param bookMarkType 书签类型
     * @throws FileException 若出现错误，统一抛出该异常
     */
    public static void replace(WordprocessingMLPackage mlPackage, @NotNull CTBookmark bookmark, @NotNull Object value,
                               @NotNull BookMarkType bookMarkType) throws FileException {
        Objects.requireNonNull(bookmark,"书签不能为空");
        Objects.requireNonNull(value,"图片资源不能为空");
        Objects.requireNonNull(bookMarkType,"书签类型不能为空");
        switch (bookMarkType){
            case TEXT:
                replaceText(bookmark, value);
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
                    throw new FileException("图片类型非法，当前类型为："+value.getClass().getName());
                }
                replaceImage(mlPackage, bookmark, bs);
                break;
            case GRID:
                if(!(value instanceof List)){
                    throw new FileException("表格类型非法，当前类型为："+value.getClass().getName());
                }
                replaceGrid(bookmark, (List)value);
                break;
        }
    }

    /**
     * 书签替换文本
     * @param bookmark 书签
     * @param value 替换的内容
     */
    public static void replaceText(CTBookmark bookmark, Object value) {
        String content = BeanUtils.ifnull(value, BeanUtils.EMPTY).toString();
        List<Object> bookMarkParentList = TraversalUtil.getChildrenImpl(bookmark.getParent());
        List<CTMarkupRange> markupRangeList = Docx4jCommonUtil.getAllElementByChildren(bookmark.getParent(), CTMarkupRange.class);
        int rangeStart = -1;
        int rangeEnd = -1;
        for (int i = 0; i < bookMarkParentList.size(); i++) {
            Object o = XmlUtils.unwrap(bookMarkParentList.get(i));
            if(o.equals(bookmark)){
                rangeStart = i;
            }
            for (CTMarkupRange ctMarkupRange : markupRangeList) {
                if(o.equals(ctMarkupRange)){
                    rangeEnd = i;
                }
            }
        }
        Text text = null;
        for (int i = rangeStart+1; i < rangeEnd; i++) {
            List<Text> textList = Docx4jCommonUtil.getAllElementByChildren(bookMarkParentList.get(i), Text.class);
            if(CollectionUtils.isNotEmpty(textList)){
                text = textList.get(0);
                text.setValue(content);
                break;
            }
        }
        if(text == null){
            R r = Docx4jCommonUtil.initR();
            RPr rPr = null;
            if(rangeStart != 0){
                for (int i = rangeStart-1; i >= 0; i--) {
                    List<R> rList = Docx4jCommonUtil.getAllElementByChildren(bookMarkParentList.get(i), R.class);
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
    @SuppressWarnings("all")
    public static void replaceImage(WordprocessingMLPackage mlPackage, CTBookmark bookmark, byte[] bs) throws FileException {
        // 模版中的图片
        List<Drawing> drawList = Docx4jCommonUtil.getAllElementByChildren(bookmark.getParent(), Drawing.class);
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
            templateInline.getExtent().setCx(map.get(CX));
            templateInline.getExtent().setCy(map.get(CY));
        }else{
            long templateCx = templateAnchor.getExtent().getCx();
            long templateCy = templateAnchor.getExtent().getCy();
            Pic pic = templateAnchor.getGraphic().getGraphicData().getPic();
            // 替换
            pic.getBlipFill().getBlip().setEmbed(embed);
            Map<String,Long> map = dealCxy(templateCx, templateCy, replaceCx, replaceCy);
            templateAnchor.getExtent().setCx(map.get(CX));
            templateAnchor.getExtent().setCy(map.get(CY));
        }
    }

    /**
     * 书签替换表格
     * @param bookmark 书签
     * @param list 替换的内容
     */
    public static void replaceGrid(CTBookmark bookmark, List list) throws FileException {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        Tbl table = Docx4jCommonUtil.getFirstElementByParent(bookmark.getParent(), Tbl.class);
        Tr templateTr = dealAndReturnTemplateTr(table);
        for (Object o : list) {
            if(!(o instanceof LinkedHashMap)){
                throw new FileException("行记录请使用LinkedHashMap集合存储！");
            }
            LinkedHashMap map = (LinkedHashMap)o;
            Tr tr = XmlUtils.deepCopy(templateTr);
            List<Tc> tcs = Docx4jCommonUtil.getAllElementByChildren(tr, Tc.class);
            int i = 0;
            for(Object mapEntry : map.entrySet()){
                Map.Entry entry = (Map.Entry)mapEntry;
                String value = BeanUtils.ifnull(entry.getValue(), BeanUtils.EMPTY).toString();

                P p = Docx4jCommonUtil.getAllElementByChildren(tcs.get(i), P.class).get(0);
                R r = Docx4jCommonUtil.getAllElementByChildren(p, R.class).get(0);
                List<Text> textList = Docx4jCommonUtil.getAllElementByChildren(r, Text.class);
                if(CollectionUtils.isNotEmpty(textList)){
                    textList.get(0).setValue(value);
                }else{
                    r.getContent().add(Docx4jCommonUtil.initText(value));
                }
                i++;
            }
            table.getContent().add(tr);
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
        map.put(CX,cx);
        map.put(CY,cy);
        return map;
    }

    /**
     * 获取处理后的模版行
     * 总是依据表格的最后一行的样式作为模版行
     * 在获取模版行之后将模版行删除
     */
    private static Tr dealAndReturnTemplateTr(Tbl table){
        List<Tr> trs = Docx4jCommonUtil.getAllElementByChildren(table, Tr.class);
        Tr templateTr = trs.get(trs.size()-1);
        int remove = -1;
        for (int i = 0; i < table.getContent().size(); i++) {
            if(XmlUtils.unwrap(table.getContent().get(i)).equals(templateTr)){
                remove = i;
            }
        }
        table.getContent().remove(remove);
        // 对模版行进行深拷贝
//        Tr templateTr = XmlUtils.deepCopy(tr);
        // 获取行中所有的单元格
        List<Tc> tcs = Docx4jCommonUtil.getAllElementByChildren(templateTr, Tc.class);
        for (int i = 0; i < tcs.size(); i++) {
            List<P> ps = Docx4jCommonUtil.getAllElementByChildren(tcs.get(i), P.class);
            // 如果单元格中的段落不止一个，则取第一个段落作为模版，其他的段落删除
            P p = ps.get(0);
            if(ps.size()>1){
                for (int j = 1; j < ps.size(); j++) {
                    int removeIndex = -1;
                    for (Object tcsContent : tcs.get(i).getContent()) {
                        if(XmlUtils.unwrap(tcsContent).equals(ps.get(j))){
                            removeIndex = tcs.get(i).getContent().indexOf(tcsContent);
                        }
                    }
                    tcs.get(i).getContent().remove(removeIndex);
                }
            }
            // 获取段落中所有样式串，若没有样式串，则创建默认的样式的样式串；若大于一个，则取最后一个样式串作为模版，其余的样式串删除。
            List<R> rs = Docx4jCommonUtil.getAllElementByChildren(p, R.class);
            if(CollectionUtils.isEmpty(rs)){
                RFonts rFonts = Docx4jCommonUtil.initRFonts();
                RPr rPr = Docx4jCommonUtil.initRPr();
                rPr.setRFonts(rFonts);
                R r = Docx4jCommonUtil.initR();
                r.setRPr(rPr);
                p.getContent().add(r);
            }else if(rs.size() > 1){
                for(int j = 0; j < rs.size(); j++){
                    if(j != rs.size()-1){
                        int removeIndex = -1;
                        for (Object obj : p.getContent()) {
                            if(XmlUtils.unwrap(obj).equals(rs.get(j))){
                                removeIndex = p.getContent().indexOf(obj);
                            }
                        }
                        p.getContent().remove(removeIndex);
                    }
                }
            }
        }
        return templateTr;
    }
}
