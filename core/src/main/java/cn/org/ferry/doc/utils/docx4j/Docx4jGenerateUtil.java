package cn.org.ferry.doc.utils.docx4j;

import cn.org.ferry.system.exception.FileException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.finders.RangeFinder;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STHint;
import org.docx4j.wml.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 使用 docx4j 生成文档的工具类
 * </p>
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/08/17 20:02
 */

public final class Docx4jGenerateUtil {
    private static final Logger logger = LoggerFactory.getLogger(Docx4jGenerateUtil.class);

    private Docx4jGenerateUtil() {
    }

    public static File generateDocx() throws FileException {
        WordprocessingMLPackage mlPackage;
        try {
            mlPackage = WordprocessingMLPackage.createPackage();
        } catch (InvalidFormatException e) {
            FileException fileException = new FileException("load .docx file error.");
            fileException.initCause(e);
            throw fileException;
        }
        MainDocumentPart mainDocumentPart = mlPackage.getMainDocumentPart();
        List<Object> body = mainDocumentPart.getContent();
        ObjectFactory factory = Context.getWmlObjectFactory();

        P p = factory.createP();
        body.add(p);

        R r = factory.createR();
        p.getContent().add(r);

        RPr rPr = factory.createRPr();
        r.getContent().add(rPr);

        RFonts rFonts = factory.createRFonts();
        rFonts.setAscii("宋体");
        rFonts.setEastAsia("宋体");
        rFonts.setHAnsi("宋体");
        rFonts.setHint(STHint.EAST_ASIA);
        rPr.setRFonts(rFonts);

        Text text = factory.createText();
        text.setValue("hello word");
        r.getContent().add(text);

        File f = new File("/Users/ferry/Downloads/ferry2.docx");
        if(f.exists() && !f.delete()){
            FileException fileException = new FileException("can not delete old file: "+f.getAbsolutePath());
            throw fileException;
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            FileException fileException = new FileException("can not create new file: "+f.getAbsolutePath());
            fileException.initCause(e);
            throw fileException;
        }
        try {
            mlPackage.save(f);
        } catch (Docx4JException e) {
            FileException fileException = new FileException("save .docx to "+f.getAbsolutePath()+" error");
            fileException.initCause(e);
            throw fileException;
        }
        return f;
    }

    /**
     * 根据模版替换标签生成word文档
     * @param bookMarkMap 书签集合，key为书签名，value为书签对应要替换的值
     * @param sourcePath 模版路径
     * @param targetPath 生成文档的目标路径
     * @throws FileException 统一抛出此异常
     */
    public static void generateDocxWithReplaceBookMark(Map<String, Object> bookMarkMap, String sourcePath, String targetPath) throws FileException {
        generateDocxWithReplaceBookMark(bookMarkMap, sourcePath, targetPath, false);
    }

    /**
     * 根据模版替换标签生成word文档
     * @param bookMarkMap 书签集合，key为书签名，value为书签对应要替换的值
     * @param sourcePath 模版路径
     * @param targetPath 生成文档的目标路径
     * @param isReplaceTarget 目标路径存在时是否替换
     * @throws FileException 统一抛出此异常
     */
    public static void generateDocxWithReplaceBookMark(Map<String, Object> bookMarkMap, String sourcePath, String targetPath, boolean isReplaceTarget) throws FileException {
        generateDocxWithReplaceBookMark(bookMarkMap, new File(sourcePath), new File(targetPath), isReplaceTarget);
    }

    /**
     * 根据模版替换标签生成word文档
     * @param bookMarkMap 书签集合，key为书签名，value为书签对应要替换的值
     * @param sourceFile 模版文件
     * @param targetFile 生成文档的目标文件
     * @throws FileException 统一抛出此异常
     */
    public static void generateDocxWithReplaceBookMark(Map<String, Object> bookMarkMap, File sourceFile, File targetFile) throws FileException {
        generateDocxWithReplaceBookMark(bookMarkMap, sourceFile, targetFile, false);
    }

    /**
     * 根据模版替换标签生成word文档
     * @param bookMarkMap 书签集合，key为书签名，value为书签对应要替换的值
     * @param sourceFile 模版文件
     * @param targetFile 生成文档的目标文件
     * @param isReplaceTarget 目标文件存在时是否替换
     * @throws FileException 统一抛出此异常
     */
    public static void generateDocxWithReplaceBookMark(Map<String, Object> bookMarkMap, File sourceFile, File targetFile, boolean isReplaceTarget) throws FileException {
        if(targetFile.exists()){
            if(isReplaceTarget){
                if(!targetFile.delete()){
                    throw new FileException("file delete failure: " + targetFile.getAbsolutePath());
                }
                try {
                    targetFile.createNewFile();
                } catch (IOException e) {
                    FileException fileException = new FileException("can not create file: " + targetFile.getAbsolutePath());
                    fileException.initCause(e);
                    throw fileException;
                }
            }else{
                throw new FileException("file is already exists: " +targetFile.getAbsolutePath());
            }
        }
        if(sourceFile.isDirectory()){
            throw new FileException("required file but found directory: " + sourceFile.getAbsolutePath());
        }
        if(targetFile.isDirectory()){
            throw new FileException("required file but found directory: " + targetFile.getAbsolutePath());
        }
        try (InputStream is = new FileInputStream(sourceFile);
             OutputStream os = new FileOutputStream(targetFile)){
            generateDocxWithReplaceBookMark(bookMarkMap, is, os);
            os.flush();
        } catch (IOException e) {
            FileException fileException = new FileException("can not save to file");
            fileException.initCause(e);
            throw fileException;
        }
    }

    /**
     * 根据模版替换标签生成word文档
     * @param bookMarkMap 书签集合，key为书签名，value为书签对应要替换的值
     * @param is 模板输入流
     * @param os 文档输出流
     * @throws FileException 统一抛出此异常
     */
    public static void generateDocxWithReplaceBookMark(Map<String, Object> bookMarkMap, InputStream is, OutputStream os) throws FileException {
        if(null == bookMarkMap || bookMarkMap.size() == 0){
            throw new NullPointerException();
        }
        Objects.requireNonNull(is);
        Objects.requireNonNull(os);

        WordprocessingMLPackage mlPackage;
        try {
            mlPackage = WordprocessingMLPackage.load(is);
        } catch (Docx4JException e) {
            FileException fileException = new FileException("load .docx file error.");
            fileException.initCause(e);
            throw fileException;
        }
        MainDocumentPart mainDocumentPart = mlPackage.getMainDocumentPart();
        List<Object> paragraphs;
        try {
            paragraphs = mainDocumentPart.getContents().getBody().getContent();
        } catch (Docx4JException e) {
            FileException fileException = new FileException("get docx contents error");
            fileException.initCause(e);
            throw fileException;
        }
        if(CollectionUtils.isEmpty(paragraphs)){
            return ;
        }
        // 提取书签并获取书签的游标
        RangeFinder rangeFinder = new RangeFinder("CTBookmark", "CTMarkupRange");
        new TraversalUtil(paragraphs, rangeFinder);

        for (int i = 0; i < rangeFinder.getStarts().size(); i++) {
            CTBookmark bookmark = rangeFinder.getStarts().get(i);
            for(Map.Entry<String, Object> entry : bookMarkMap.entrySet()){
                if(StringUtils.equals(entry.getKey(), bookmark.getName())){
                    // 获取书签的父级标签
                    List<Object> bookMarkParentList = TraversalUtil.getChildrenImpl(bookmark.getParent());
                    int rangeStart = -1;
                    int rangeEnd = -1;
                    for (int j = 0; j < bookMarkParentList.size(); j++) {
                        Object o = XmlUtils.unwrap(bookMarkParentList.get(j));
                        if(bookmark.equals(o)){
                            rangeStart = j;
                        }
                        if(rangeFinder.getEnds().get(i).equals(o)){
                            rangeEnd = j;
                        }
                    }
                    // 书签替换
                    BookMarkReplaceUtil.replace(bookmark, rangeStart, rangeEnd, entry.getValue());
                    // 移除书签
                    bookMarkParentList.remove(rangeEnd);
                    bookMarkParentList.remove(rangeStart);
                    break;
                }
            }
        }

        try {
            mlPackage.save(os);
        } catch (Docx4JException e) {
            FileException fileException = new FileException("can not save to file");
            fileException.initCause(e);
            throw fileException;
        }
    }

    public static void main(String[] args) throws FileException {
//        generateDocx();
        Map<String, Object> bookMark = new HashMap<>();
        bookMark.put("tenant", "广州越秀");
        bookMark.put("contract_number", "YX20190901");
        String sourcePath = "/Users/ferry/Downloads/合同履行完毕及所有权转移确认书-YX.docx";
        String targetPath = "/Users/ferry/Downloads/合同履行完毕及所有权转移确认书-YX2.docx";
        generateDocxWithReplaceBookMark(bookMark, sourcePath, targetPath, true);
    }
}

