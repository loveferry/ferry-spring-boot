package cn.org.ferry;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
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
}
