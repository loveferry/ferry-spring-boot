package cn.org.ferry.mybatis.enums;

/**
 * <p>书签类型枚举类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/10 10:23
 */

public enum BookMarkType {
    TEXT("文本"),
    LIST("列表"),
    IMAGE("图片");

    private String description;

    BookMarkType(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
