import cn.org.ferry.system.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

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
}
