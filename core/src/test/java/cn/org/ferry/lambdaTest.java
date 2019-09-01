package cn.org.ferry;

import org.junit.Test;

import java.util.Comparator;

/**
 * <p>
 * description
 * </p>
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/08/18 10:44
 */

public class lambdaTest {
    @Test
    public void lambda(){
        LengthInterface lengthInterface = msg -> msg.length()*10;
        LengthInterface lengthInterface2 = String::length;
        System.out.println(lengthInterface.renderLength("ferry"));
        System.out.println(lengthInterface2.renderLength("ferry"));
    }

    @Test
    public void innerClass(){
        System.out.println(new LengthInterface() {
            @Override
            public int renderLength(String msg) {
                return msg.length()*10;
            }
        }.renderLength("ferry"));
    }

    @FunctionalInterface
    public interface LengthInterface{
        int renderLength(String msg);
    }

    @Test
    public void method(){
        int a = 10,b = 12;
        Comparator<Integer> comparator = Integer::compare;
        System.out.println(comparator.compare(a, b));
        String msg = "ferry";
        String str = "ferry";
    }
}
