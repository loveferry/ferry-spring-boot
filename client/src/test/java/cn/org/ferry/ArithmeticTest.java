package cn.org.ferry;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>算法测试
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/04/22 20:52
 */

public class ArithmeticTest {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ArithmeticTest.class);

    private static final int[] is = new int[100000];

    static {
        Random random = new Random();
        for (int i = 0; i < is.length; i++) {
            is[i] = random.nextInt(100000);
        }
    }

    /**
     * 先顺序排序，再去数据
     */
    @Test
    public void arraySearchTwoMin1(){
        logger.info("arrays is : {}", Arrays.toString(is));

//        int firstMin = -1, secondMin = -1;
        for (int i = 0; i < is.length-1; i++) {
            for (int j = i+1; j < is.length; j++) {
                if(is[i] > is[j]){
                    int tmp = is[i];
                    is[i] = is[j];
                    is[j] = tmp;
                }
            }
        }

        logger.info("arrays is : {}", Arrays.toString(is));


        logger.info("First min is: {}", is[0]);

        logger.info("Second min is: {}", is[1]);
    }

    /**
     * 直接取
     */
    @Test
    public void arraySearchTwoMin2(){
        logger.info("arrays is : {}", Arrays.toString(is));

        int firstMin = is[0], secondMin = is[1];
        for (int i = 2; i < is.length; i++) {
            if(is[i] < firstMin){
                secondMin = firstMin;
                firstMin = is[i];
            }else if(is[i] < secondMin){
                secondMin = is[i];
            }
        }

        logger.info("arrays is : {}", Arrays.toString(is));


        logger.info("First min is: {}", firstMin);

        logger.info("Second min is: {}", secondMin);
    }

    @Test
    public void sdabhj(){
        logger.info("arrays is : {}", Arrays.toString(is));

        int a = -1;
        for (int i = 0; i < is.length; i++) {
            a = is[i];
            for (int j = 0; j < is.length; j++) {
                if (is[i] == is[j] && i != j) {
                    a = -1;
                    break;
                }
            }
            if(a != -1){
                break;
            }
        }

        logger.info("First not equals value {}", a);



    }

    @Test
    public void dabbc(){
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        logger.info(b.encode("ferry"));
        logger.info(b.encode("admin"));
    }


    @Test
    public void scskcj(){
//        System.out.println(fib1(45));
//         System.out.println(fib2(4010));
         System.out.println(fib3(4010));
    }


    private int fib1(int n){
        if(n <= 1) return n;
        return fib1(n-1)+fib1(n-2);
    }
    private Map<Long, Long> map = new LinkedHashMap<>();
    private long fib2(long n){
        if(n <= 1){
            map.put(n, n);
            return n;
        }
        if(map.get(n-1) == null){
            map.put(n-1, fib2(n-1));
        }
        if(map.get(n-2) == null){
            map.put(n-2, fib2(n-2));
        }
        return map.get(n-1)+map.get(n-2);
    }

    private Long temp1 = null,temp2 = null;
    private long fib3(long n){
        if(n <= 1) return n;
        long temp1 = 0;
        long temp2 = 1;
        long temp3 = 1;
        for(long i = 2; i <= n; i++){
            temp3 = temp1+temp2;
            temp1 = temp2;
            temp2 = temp3;
        }
        return temp3;
    }
}
