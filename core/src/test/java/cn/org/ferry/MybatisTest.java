package cn.org.ferry;

import cn.org.ferry.sys.dto.ChinesePeople;
import cn.org.ferry.sys.mapper.ChinesePeopleMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * <p>mybatis测试类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/27 12:57
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void buildSqlSessionTest(){
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        ChinesePeople chinesePeople = new ChinesePeople();
        chinesePeople.setName("于对");
        List<ChinesePeople> list = sqlSession.getMapper(ChinesePeopleMapper.class).select(chinesePeople);
        System.out.println(sqlSession.getClass().getCanonicalName());
    }
}
