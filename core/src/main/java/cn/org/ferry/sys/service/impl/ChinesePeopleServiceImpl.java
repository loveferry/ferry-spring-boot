package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.ChinesePeople;
import cn.org.ferry.sys.mapper.ChinesePeopleMapper;
import cn.org.ferry.sys.service.ChinesePeopleService;
import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import cn.org.ferry.system.sysenum.Sex;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Slf4j
@Service
public class ChinesePeopleServiceImpl extends BaseServiceImpl<ChinesePeople> implements ChinesePeopleService {
    private static final String FIRST_NAME = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯昝管卢莫经房裘缪干解应宗丁宣贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄麴家封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘景詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲邰从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍舄璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庾终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查後荆红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于单于太叔申屠公孙仲孙轩辕令狐钟离宇文长孙慕容鲜于闾丘司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓跋夹谷宰父谷梁晋楚闫法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福";

    private static final String LAST_NAME = "一厂八儿二几九了力七人入十又广才大飞干个工及己口马么门万千三山上土习下小也已义于与之子办比不长车从斗队反方分风公化火计见今开历六内区片气切认日少什手书水太天王为文无五心以引元月支中专毛白半包北本必边布出处打代石电东对发号记加叫节且可立龙们民目平去生史世市示术四他它头外务写业议用由正只主安百并产场成传此次存达当导地动多而合各红共关观光过行好后许华划回会机级价件江交阶她决军老列论米名那年农全权任如色设式收同团问西先向压约因有在再则争至众自把报别步层但低改更还何花即极系技际间角进近究局克快况来劳里利连两没每你求却社身声时识体条听完位我县形严应员运张这证志住状走作备变表采参单到的定法放非府该构规国果和话或其建金经京具空矿拉例林明命青取实使始事受所些图往委物细现线性学易油育者知织直制治质周转组按保便标查持种重除带点度段复革给很活济将结界看科适类律美面南派品前亲思信省是说统相响型须选研养要音院战政指总般被部称党调都高格根海候积家较离料流能难起热容素速特铁通消效验样原圆造展真值准资常得第断基教接据理领率清情商深维象眼着族做程道等提期集强就联量确然属斯温越装最感解路群数想新意照置满管精酸算需题影增器整";

    private static final int MAX_PAGE_SIZE = 100000;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private static final Random random = new Random();

    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ChinesePeopleMapper chinesePeopleMapper;
    @Autowired
    private SysFileService sysFileService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SqlSession sqlSession;

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void batchGenerate(int size) throws SQLException {
        long start = System.currentTimeMillis();

        Connection conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(false);
        Statement stat = conn.createStatement();

        int page = 1;
        if(size > MAX_PAGE_SIZE){
            page = (size-1)/MAX_PAGE_SIZE+1;
        }
        try{
            for(int i = 0; i < page; i++){
                int currentPageAllMaxSize = (i+1)*MAX_PAGE_SIZE;
                int count = size >= currentPageAllMaxSize ? MAX_PAGE_SIZE : size%MAX_PAGE_SIZE;
                StringBuilder sb = new StringBuilder("insert into chinese_people(name,birth_date,sex,created_by,creation_date,last_updated_by,last_update_date) values");
                for(int j = 0; j < count; j++){
                    String birthDayStr = sdf2.format(randomGenerateBirthDate());
                    sb.append("(").
                            append('\"').append(randomGenerateName()).append('\"').append(",").
                            append('\"').append(birthDayStr).append('\"').append(",").
                            append('\"').append(randomGenerateSex()).append('\"').append(",").
                            append(1).append(",").
                            append('\"').append(birthDayStr).append('\"').append(",").
                            append(1).append(",").
                            append("SYSDATE()),");
                }
                String sql = sb.substring(0,sb.length()-1);
                stat.addBatch(sql);
                stat.executeBatch();
            }
            conn.commit();
        }catch (SQLException e){
            conn.rollback();
            e.printStackTrace();
        }finally {
            stat.close();
            conn.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("-----------------用时-----------------\n\n\n");
        System.out.println(end-start);
        System.out.println("\n\n\n-----------------用时-----------------");
    }

    @Override
    public List<ChinesePeople> queryByName(String name, int page, int pageSize) {
        /*ListOperations<String, ChinesePeople> listOperations = redisTemplate.opsForList();
        if(redisTemplate.hasKey(name)){
            log.info("从缓存中查询数据...");
            return listOperations.range(name, (page-1)*pageSize, page*pageSize-1);
        }
        List<ChinesePeople> list = chinesePeopleMapper.queryByName(name);
        if(CollectionUtils.isEmpty(list)){
            return list;
        }
        log.info("将集合插入redis缓存...");
        for (ChinesePeople chinesePeople : list) {
            listOperations.rightPush(name, chinesePeople);
        }
        return listOperations.range(name, (page-1)*pageSize, page*pageSize-1);*/

//        PageHelper.startPage(page, pageSize);

        return chinesePeopleMapper.queryByName(name);
    }

    @Override
    public List<ChinesePeople> query(ChinesePeople chinesePeople, int page, int pageSize) {
        return select(chinesePeople, page, pageSize);
    }

    @Override
    public void chinesePeopleExcelExport(HttpServletResponse response, String config, int page, int pageSize) {
        /*PageHelper.startPage(page, pageSize);
        List<ChinesePeople> list = chinesePeopleMapper.selectAll();
        config = "{header:'测试数据导出',fileName:'测试数据.xlsx',sheetSize:30000,sheetName:'测试数据',columns:[{field:'id',description:'id',width:100,type:'numeric',align:'right'},{field:'code',description:'编码',width:200,type:'string',align:'left'},{field:'name',description:'用户名',width:100,type:'String',align:'left'},{field:'birthDate',description:'出生日期',width:150,type:'numeric',align:'center'}]}";
        sysFileService.excelExport(response, list, config);*/
        Map<String, String> map = new LinkedHashMap<>(5);
        map.put("盛", "盛氏");
        map.put("陈", "陈氏");
        map.put("王", "王氏");
        map.put("赵", "赵氏");
        map.put("孙", "孙氏");
        Map<String, List<? extends BaseDTO>> data = new HashMap<>(map.size());
        for(Map.Entry<String, String> entry : map.entrySet()){
            PageHelper.startPage(page, pageSize);
            data.put(entry.getValue(), chinesePeopleMapper.queryByName(entry.getKey()));
        }
        config = "{header:'测试数据导出',fileName:'测试数据.xlsx',sheetSize:1000000,columns:[{field:'id',description:'id',width:100,type:'numeric',align:'right'},{field:'name',description:'用户名',width:100,type:'String',align:'left'},{field:'birthDate',description:'出生日期',width:150,type:'numeric',align:'center'}]}";
        sysFileService.excelExport(response, data, config);
    }

    /**
     * 随机生成性别
     */
    private Sex randomGenerateSex(){
        int i = random.nextInt(2);
        if(i == 1){
            return Sex.MALE;
        }else{
            return Sex.FEMALE;
        }
    }

    /**
     * 随机生成出生日期
     */
    private Date randomGenerateBirthDate(){
        try {
            Date start = sdf.parse("19440101");// 构造开始日期
            Date end = new Date(); // 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            long rtn = start.getTime() + (long) (Math.random() * (end.getTime() - start.getTime()));
            return new Date(rtn);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("随机日期生成出错,默认返回当前日期");
            return new Date();
        }
    }

    /**
     * 随机生成姓名
     */
    private String randomGenerateName(){
        // 姓
        int index = random.nextInt(FIRST_NAME.length());
        String name = FIRST_NAME.substring(index, index+1);
        // 名
        int lastNameLength = random.nextInt(2);
        do{
            int lastNameIndex = random.nextInt(LAST_NAME.length());
            name += LAST_NAME.substring(lastNameIndex, lastNameIndex+1);
            lastNameLength--;
        }while(lastNameLength>=0);
        return name;
    }
}
