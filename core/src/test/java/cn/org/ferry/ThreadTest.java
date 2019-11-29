package cn.org.ferry;

import org.junit.Test;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/19 20:21
 */

public class ThreadTest {
    @Test
    public void test1(){
        Runnable runnable = () -> {
            for(int i = 0; i < 100; i++) {
                System.out.println(i);
            }
        };
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }
}
