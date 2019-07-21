package cn.org.ferry;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTest {
    @Test
    public void dnksdcs(){
        String first_name = "赵\t钱\t孙\t李\t周\t吴\t郑\t王\t冯\t陈\t褚\t卫\t蒋\t沈\t韩\t杨\t朱\t秦\t尤\t许\n" +
                "何\t吕\t施\t张\t孔\t曹\t严\t华\t金\t魏\t陶\t姜\t戚\t谢\t邹\t喻\t柏\t水\t窦\t章\n" +
                "云\t苏\t潘\t葛\t奚\t范\t彭\t郎\t鲁\t韦\t昌\t马\t苗\t凤\t花\t方\t俞\t任\t袁\t柳\n" +
                "酆\t鲍\t史\t唐\t费\t廉\t岑\t薛\t雷\t贺\t倪\t汤\t滕\t殷\t罗\t毕\t郝\t邬\t安\t常\n" +
                "乐\t于\t时\t傅\t皮\t卞\t齐\t康\t伍\t余\t元\t卜\t顾\t孟\t平\t黄\t和\t穆\t萧\t尹\n" +
                "姚\t邵\t湛\t汪\t祁\t毛\t禹\t狄\t米\t贝\t明\t臧\t计\t伏\t成\t戴\t谈\t宋\t茅\t庞\n" +
                "熊\t纪\t舒\t屈\t项\t祝\t董\t梁\t杜\t阮\t蓝\t闵\t席\t季\t麻\t强\t贾\t路\t娄\t危\n" +
                "江\t童\t颜\t郭\t梅\t盛\t林\t刁\t钟\t徐\t邱\t骆\t高\t夏\t蔡\t田\t樊\t胡\t凌\t霍\n" +
                "虞\t万\t支\t柯\t昝\t管\t卢\t莫\t经\t房\t裘\t缪\t干\t解\t应\t宗\t丁\t宣\t贲\t邓\n" +
                "郁\t单\t杭\t洪\t包\t诸\t左\t石\t崔\t吉\t钮\t龚\t程\t嵇\t邢\t滑\t裴\t陆\t荣\t翁\n" +
                "荀\t羊\t於\t惠\t甄\t麴\t家\t封\t芮\t羿\t储\t靳\t汲\t邴\t糜\t松\t井\t段\t富\t巫\n" +
                "乌\t焦\t巴\t弓\t牧\t隗\t山\t谷\t车\t侯\t宓\t蓬\t全\t郗\t班\t仰\t秋\t仲\t伊\t宫\n" +
                "宁\t仇\t栾\t暴\t甘\t钭\t厉\t戎\t祖\t武\t符\t刘\t景\t詹\t束\t龙\t叶\t幸\t司\t韶\n" +
                "郜\t黎\t蓟\t薄\t印\t宿\t白\t怀\t蒲\t邰\t从\t鄂\t索\t咸\t籍\t赖\t卓\t蔺\t屠\t蒙\n" +
                "池\t乔\t阴\t郁\t胥\t能\t苍\t双\t闻\t莘\t党\t翟\t谭\t贡\t劳\t逄\t姬\t申\t扶\t堵\n" +
                "冉\t宰\t郦\t雍\t舄\t璩\t桑\t桂\t濮\t牛\t寿\t通\t边\t扈\t燕\t冀\t郏\t浦\t尚\t农\n" +
                "温\t别\t庄\t晏\t柴\t瞿\t阎\t充\t慕\t连\t茹\t习\t宦\t艾\t鱼\t容\t向\t古\t易\t慎\n" +
                "戈\t廖\t庾\t终\t暨\t居\t衡\t步\t都\t耿\t满\t弘\t匡\t国\t文\t寇\t广\t禄\t阙\t东\n" +
                "殴\t殳\t沃\t利\t蔚\t越\t夔\t隆\t师\t巩\t厍\t聂\t晁\t勾\t敖\t融\t冷\t訾\t辛\t阚\n" +
                "那\t简\t饶\t空\t曾\t毋\t沙\t乜\t养\t鞠\t须\t丰\t巢\t关\t蒯\t相\t查\t後\t荆\t红\n" +
                "游\t竺\t权\t逯\t盖\t益\t桓\t公\t万俟\t司马\t上官\t欧阳\t夏侯\t诸葛\n" +
                "闻人\t东方\t赫连\t皇甫\t尉迟\t公羊\t澹台\t公冶\t宗政\t濮阳\n" +
                "淳于\t单于\t太叔\t申屠\t公孙\t仲孙\t轩辕\t令狐\t钟离\t宇文\n" +
                "长孙\t慕容\t鲜于\t闾丘\t司徒\t司空\t亓官\t司寇\t仉\t督\t子车\n" +
                "颛孙\t端木\t巫马\t公西\t漆雕\t乐正\t壤驷\t公良\t拓跋\t夹谷\n" +
                "宰父\t谷梁\t晋\t楚\t闫\t法\t汝\t鄢\t涂\t钦\t段干\t百里\t东郭\t南门\n" +
                "呼延\t归\t海\t羊舌\t微生\t岳\t帅\t缑\t亢\t况\t后\t有\t琴\t梁丘\t左丘\n" +
                "东门\t西门\t商\t牟\t佘\t佴\t伯\t赏\t南宫\t墨\t哈\t谯\t笪\t年\t爱\t阳\t佟\n" +
                "第五\t言\t福";
        first_name = first_name.replace('\t',',');
        first_name = first_name.replace('\n',',');
        first_name = first_name.replace(",","");
        System.out.println(first_name);

    }

    @Test
    public void mdnkjsnv(){
        String msg = "asb";
        Random random = new Random();
        int a = random.nextInt(3);
        System.out.println(a);
        System.out.println(msg.substring(2,3));

    }

    @Test
    public void cnksdkv(){
        String msg = LocalDate.now().toString();
        System.out.println(msg);
        System.out.println(msg.substring(0,6));
        System.out.println(LocalDate.now().getYear());
    }

    @Test
    public void cnsnow(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("a");
        System.out.println(stringBuilder.toString());
        stringBuilder.append("c");
        System.out.println(stringBuilder.toString());
        stringBuilder.append(LocalDate.now().getMonthValue());
        System.out.println(stringBuilder.toString());


    }

    /**
     * 补足整数前置位数并转化为字符串
     */
    private String supplyingIntegerToString(int num, int len){
        String str = String.valueOf(num);
        if(str.length() > len){
            return str.substring(str.length()-len);
        }
        if(str.length() == len){
            return str;
        }
        String msg = "";
        for(int i = 0; i < len-str.length(); i++){
            msg = msg + "0";
        }
        return msg+str;
    }

    @Test
    public void nidfbij(){
        System.out.println(supplyingIntegerToString(34, 4));
    }

    @Test
    public void cndonwvd(){
        StringBuilder sb = new StringBuilder();
        sb.append("a").append("b").append(sb.toString());
        System.out.println(sb);

        sb.replace(0,1,"H");
        System.out.println(sb);

    }


    @Test
    public void vbsdjbvsj(){
        StringBuilder msg = new StringBuilder("");
        System.out.println(msg.replace(0,1,"d"));

    }


    @Test
    public void cdbksvdjs(){
        String sql = "select * from sys_user where user_name_zh = ${userNameZh} and user_name_en = ${userNameEn}";
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()){
            String key = matcher.group(1);
            System.out.println(key);
        }
        System.out.println(matcher.replaceAll("?"));
        System.out.println(matcher);
    }
}
