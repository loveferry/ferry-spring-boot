package cn.org.ferry;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Date;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/18 10:19
 */

public class ReflectTest {
    @Test
    public void sdbajbcks() throws NoSuchMethodException {
        Class<Date> cls = Date.class;
        Constructor<Date> constructor = cls.getDeclaredConstructor(long.class);
        System.out.println("hehe");
    }
}
