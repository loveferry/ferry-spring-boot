package cn.org.ferry.doc.utils.docx4j;

import org.apache.commons.collections4.CollectionUtils;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STHint;
import org.docx4j.wml.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>通用方法工具类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/10 17:34
 */

public class Docx4jCommonUtil {
    private static final ObjectFactory factory = Context.getWmlObjectFactory();
    /**
     * 得到指定类型的元素
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getAllElement(Object obj, Class<T> toSearch) {
        Objects.requireNonNull(obj);

        List<T> list = new ArrayList<>();
        Object o = XmlUtils.unwrap(obj);
        if(o.getClass().equals(toSearch)){
            list.add((T)o);
        }
        List<Object> childrenImpl = TraversalUtil.getChildrenImpl(o);
        if(CollectionUtils.isNotEmpty(childrenImpl)){
            for (Object child : childrenImpl) {
                list.addAll(getAllElement(XmlUtils.unwrap(child), toSearch));
            }
        }
        return list;
    }

    /**
     * 初始化一个Text文本对象
     * @param value 值
     * @return 返回初始化的对象
     */
    public static Text initText(String value){
        Text text = factory.createText();
        text.setValue(value);
        return text;
    }

    /**
     * 初始化一个R对象
     * @return 返回初始化的对象
     */
    public static R initR(){
        R r = factory.createR();
        return r;
    }

    /**
     * 初始化一个RPr对象
     * @return 返回初始化的对象
     */
    public static RPr initRPr(){
        RPr rPr = factory.createRPr();
        return rPr;
    }

    /**
     * 初始化一个RFonts对象
     * @return 返回初始化的对象
     */
    public static RFonts initRFonts(){
        RFonts rFonts = factory.createRFonts();
        rFonts.setAscii("宋体");
        rFonts.setEastAsia("宋体");
        rFonts.setHAnsi("宋体");
        rFonts.setHint(STHint.EAST_ASIA);
        return rFonts;
    }
}
