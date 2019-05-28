package cn.org.ferry;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @author ferry ferry_sy@163.com
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ferry {
    String name() default "";

    int amount() default -1;

    Class showClass() default Object.class;

    Sex showSex() default Sex.MALE;

    Honey love() default @Honey(mySex = Sex.FEMALE);

    int[] amounts() default {};

    enum Sex {
        MALE,FEMALE;
    }

    @interface Honey {
        Sex mySex() default Sex.FEMALE;
    }
}
