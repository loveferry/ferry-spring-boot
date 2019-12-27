import cn.org.ferry.system.exception.CommonException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/25 15:03
 */

public class DemoTest {

    private String msg = "s";

    @Before
    public void beforTest(){
        msg = "b";
    }

    @Test(expected = CommonException.class)
    public void exceptionTest(){
        if(StringUtils.equals("b", msg)){
            throw new CommonException("测试");
        }
    }



    @Test
    public void mapRemoveTest(){
        Map<String, String> map = new HashMap<>(5);
        map.put("s", "s");
        map.put("a", "a");
        map.put("d", "d");
        map.put("f", "f");
        map.put("g", "g");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            if(StringUtils.equals("s", entry.getKey())){
                iterator.remove();
            }
        }
        System.out.println(MapUtils.toProperties(map).toString());
    }
}
