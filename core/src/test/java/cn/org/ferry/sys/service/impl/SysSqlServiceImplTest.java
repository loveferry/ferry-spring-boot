package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.service.SysSqlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/14
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysSqlServiceImplTest {
    @Autowired
    private SysSqlService sysSqlService;

    @Test
    public void executeSqlTest() throws Exception{
        String sql = "select ";
//        sysSqlService.execute(sql, new HashMap<>());
    }
}
