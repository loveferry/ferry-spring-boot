package cn.org.ferry.system.mybatis.providers;

import org.apache.ibatis.annotations.Param;

/**
 */

public class TestSelectProvider {

    public TestSelectProvider(){
    }


    public String selectOne2(@Param("name")String str){
        return "select * from chinese_people where id = 1";
    }
}
