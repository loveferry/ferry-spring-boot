import org.junit.Test;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/01/08 08:59
 */

public class EnumTest {
    public enum Apple{
        apple("apple");

        private String apple2;

        Apple(String apple2){
            this.apple2 = apple2;
        }

        public String getApple2() {
            return apple2;
        }
    }
    @Test
    public void xisnc(){
        System.out.println(Apple.valueOf("pear"));

    }

    @Test
    public void snc(){
        String msg = "F3";

        System.out.println(msg.substring(0,1).charAt(0)-64);

    }
}
