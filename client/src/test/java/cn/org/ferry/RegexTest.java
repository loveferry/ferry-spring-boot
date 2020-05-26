package cn.org.ferry;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>正则表达式测试类
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/03/31 17:27
 */

public class RegexTest {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(RegexTest.class);

    @Test
    public void testPattern(){
        Pattern pattern = Pattern.compile("r+");
        logger.info(pattern.pattern());
    }

    @Test
    public void testSplit(){
        Pattern pattern = Pattern.compile("\\.+?");

        logger.info(Arrays.toString(pattern.split("https://ferry.org.cn")));
        logger.info(Arrays.toString(pattern.split("https://ferry.org.cn", 4)));
        logger.info(Arrays.toString(pattern.split("https://ferry.org.cn", 3)));
        logger.info(Arrays.toString(pattern.split("https://ferry.org.cn", 2)));
        logger.info(Arrays.toString(pattern.split("https://ferry.org.cn", 1)));
        logger.info(Arrays.toString(pattern.split("https://ferry.org.cn", 0)));
        logger.info(Arrays.toString(pattern.split("https://ferry.org.cn", -1)));
    }

    @Test
    public void testMatches(){
        logger.info("matches result: {}", "hello".matches("^hello$"));
        logger.info("-------------------------------------------");
        logger.info("matches result: {}", "hello".matches("\\bhello\\b"));
    }

    @Test
    public void testMatcher(){
        /*Pattern pattern = Pattern.compile(".+");
        Matcher matcher = pattern.matcher("https://ferry.org.cn");
        if(matcher.matches()){
            logger.info("matches start: {}", matcher.start());
            logger.info("matches end: {}", matcher.end());
        }*/

        /*Pattern pattern = Pattern.compile("^http(s)?");
        Matcher matcher = pattern.matcher("https://ferry.org.cn");
        if(matcher.lookingAt()){
            logger.info("lookingAt start: {}", matcher.start());
            logger.info("lookingAt end: {}", matcher.end());
        }*/

        Pattern pattern = Pattern.compile("\\.+?");
        Matcher matcher = pattern.matcher("https://ferry.org.cn");
        int count = 0;
        while (matcher.find()){
            logger.info("find {} start: {}", ++count, matcher.start());
            logger.info("find {} end: {}", count, matcher.end());
        }

    }

    @Test
    public void testReplace(){
        Pattern pattern = Pattern.compile("/+?");
        Matcher matcher = pattern.matcher("ferry/org/cn");
        logger.info("replaceFirst: {}", matcher.replaceFirst("."));
        logger.info("replaceAll: {}", matcher.replaceAll("."));

    }

    @Test
    public void testGroup(){
        /*Pattern pattern = Pattern.compile("(\\w+)\\.+?(\\w+)");
        Matcher matcher = pattern.matcher("ferrysy.com");
        if(matcher.find()){
            logger.info("group count: {}", matcher.groupCount());
            logger.info("group 0: {}", matcher.group(0));
            int i = 0;
            do{
                logger.info("group {}: {}", ++i, matcher.group(i));
            }while (i < matcher.groupCount());
        }*/

        /*Pattern pattern = Pattern.compile("\\b(\\w+)\\b\\s\\1");
        Matcher matcher = pattern.matcher("ferry ferry");
        logger.info("{}", matcher.groupCount());
        if(matcher.find()){
            logger.info(matcher.group(1));
        }*/

        Pattern pattern = Pattern.compile("\\b(?<name>\\w+)\\b\\s\\1");
        Matcher matcher = pattern.matcher("ferry ferry");
        logger.info("{}", matcher.groupCount());
        if(matcher.find()){
            logger.info(matcher.group(1));
            logger.info(matcher.group("name"));
        }


    }

//    域名由多个标签(各级域名)组成，每个标签长度限制63，域名总长度253；
//    各个标签之间用`.`连接；
//    标签可以由英文字母，阿拉伯数字和中横线组成
//    中横线不能连续出现、不能单独注册，也不能放在开头和结尾。

    @Test
    public void testDomainName(){
        String msg = "ferry.org.cn";
        boolean b = msg.matches("^(?=^.{3,253}$)[a-zA-Z0-9]$");
        logger.info("{}", b);
    }

    @Test
    public void test1(){
        // 可以匹配does不可以匹配do
        Pattern pattern = Pattern.compile("do(?=es)");
        Matcher matcher = pattern.matcher("do");
        logger.info("{}", matcher.lookingAt());
        matcher.reset("does");
        logger.info("{}", matcher.lookingAt());
    }

    @Test
    public void test2(){
        // 限制字符串长度
        Pattern pattern = Pattern.compile("(?=(^.{5}$))\\w+");
        Matcher matcher = pattern.matcher("ferry");
        logger.info("{}", matcher.matches());
    }

    @Test
    public void test3(){
        // 可以匹配where不可以匹配here
        Pattern pattern = Pattern.compile("(?<=wh)ere");
        Matcher matcher = pattern.matcher("here");
        logger.info("{}", matcher.find());
        matcher.reset("where");
        logger.info("{}", matcher.find());
    }

    @Test
    public void test4(){
        // 每五个字符后的字符不能是数字
        Pattern pattern = Pattern.compile("(.{5}(?!\\d))+");
        Matcher matcher = pattern.matcher("ferrylove2");
        logger.info("{}", matcher.matches());

    }

    @Test
    public void test5(){
        // 匹配五个下写字母且前面一个字符不是数字的
        Pattern pattern = Pattern.compile("(?<!\\d)[a-z]{5}");
        Matcher matcher = pattern.matcher("ferry");
        logger.info("{}", matcher.matches());
        matcher.reset("1ferry");
        logger.info("{}", matcher.find());
    }

    @Test
    public void test6(){
        // 贪婪匹配
        Pattern pattern = Pattern.compile("\\d.*\\d"); // 匹配两个数字，数字中间有零或多个字符
        Matcher matcher = pattern.matcher("1fer22ry9");
        while (matcher.find()){
            logger.info("{}", matcher.group());
        }
    }

    @Test
    public void test7(){
        // 懒惰匹配
        Pattern pattern = Pattern.compile("\\d.*?\\d"); // 匹配两个数字，数字中间有零或多个字符
        Matcher matcher = pattern.matcher("11fer22ry99");
        while (matcher.find()){
            logger.info("{}", matcher.group());
        }
    }

    @Test
    public void test8(){
        // 懒惰匹配
        Pattern pattern = Pattern.compile("\\d.*?\\d"); // 匹配两个数字，数字中间有零或多个字符
        Matcher matcher = pattern.matcher("1fer22ry99");
        while (matcher.find()){
            logger.info("{}", matcher.group());
        }
    }

    @Test
    public void test9(){
        // 独占模式
        Pattern pattern = Pattern.compile("s*+\\w");  // 此表达式相当于匹配零或多个s,最后一个字符不能是s结尾
        Matcher matcher = pattern.matcher("ss");
        logger.info("{}",matcher.matches());  // false
        matcher.reset("s4");
        logger.info("{}", matcher.matches());  // true
    }

    @Test
    public void test10(){
        // 平衡组
        Pattern pattern = Pattern.compile("(?<leftWord>\\<[A-Z_]*\\>)(?<content>\\S*)(?<rightWord>\\<\\/[A-Z_]*\\>)");
        Matcher matcher = pattern.matcher("<REQ_BASEINFO>" +
                                                    "<REQ_TRACE_ID>?</REQ_TRACE_ID>" +
                                                    "<REQ_SEND_TIME>?</REQ_SEND_TIME>" +
                                                    "<REQ_SRC_SYS>?</REQ_SRC_SYS>" +
                                                    "<REQ_TAR_SYS>?</REQ_TAR_SYS>" +
                                                    "<REQ_SERVER_NAME>?</REQ_SERVER_NAME>" +
                                                    "<REQ_SYN_FLAG>?</REQ_SYN_FLAG>" +
                                                    "<REQ_BSN_ID>?</REQ_BSN_ID>" +
                                                    "<REQ_RETRY_TIMES>?</REQ_RETRY_TIMES>" +
                                                    "<REQ_REPEAT_FLAG>?</REQ_REPEAT_FLAG>" +
                                                    "<REQ_REPEAT_CYCLE>?</REQ_REPEAT_CYCLE>" +
                                                "</REQ_BASEINFO>");
        logger.info("{}", matcher.groupCount());
        while (matcher.find()){
            logger.info("{}{}{}", matcher.group("leftWord"), matcher.group("content"), matcher.group("rightWord"));
        }
    }

}
