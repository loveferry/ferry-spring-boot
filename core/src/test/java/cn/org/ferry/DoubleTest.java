package cn.org.ferry;

import org.junit.Test;

public class DoubleTest {
    @Test
    public void csdvds(){
        Double a = 2.0;
        Double b = a;
        b = 1.0;
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void dscvb(){
        Double a = 2.0;
        Double b = new Double(a);
        b = 1.0;
        System.out.println(a);
        System.out.println(b);

    }

    @Test
    public void cnkjsvd(){
        Double a = new Double(2.0);
        Double b = a;
        a = 1.0;
        System.out.println(a);
        System.out.println(b);

    }
}
