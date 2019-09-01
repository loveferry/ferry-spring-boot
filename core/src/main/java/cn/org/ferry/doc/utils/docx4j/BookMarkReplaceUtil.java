package cn.org.ferry.doc.utils.docx4j;

import cn.org.ferry.system.exception.FileException;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;

import java.util.List;
import java.util.Objects;

/**
 * <p>docx4j替换书签工具类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/08/25 12:21
 */

public final class BookMarkReplaceUtil {
    private BookMarkReplaceUtil(){}

    /**
     * 书签替换
     * @param bookmark 书签
     * @param rangeStart 书签开始位置
     * @param rangeEnd 书签结束位置
     * @param value 替换的内容
     * @throws FileException 若出现错误，统一抛出该异常
     */
    public static void replace(CTBookmark bookmark, int rangeStart, int rangeEnd, Object value) throws FileException {
        Objects.requireNonNull(bookmark);
        List<Object> bookMarkParentList = TraversalUtil.getChildrenImpl(bookmark.getParent());
        for (int i = rangeStart+1; i < rangeEnd; i++) {
            if(bookMarkParentList.get(i) instanceof ContentAccessor){
                List<Object> list = TraversalUtil.getChildrenImpl(bookMarkParentList.get(i));
                Text text = getText(list);
                text.setValue(value.toString());
                break;
            }
        }
    }

    private static Text getText(List<Object> list) throws FileException {
        for (Object o : list) {
            Object r = XmlUtils.unwrap(o);
            if(r instanceof R){
                return getText(((R) r).getContent());
            }else if(r instanceof Text){
                return (Text) r;
            }
        }
        throw new FileException("can not get Text by " + list);
    }
}
