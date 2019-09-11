package cn.org.ferry;

import cn.org.ferry.system.annotations.NotNull;
import org.junit.Test;

import java.lang.annotation.*;

public class AnnotationTest {
    @Ferry(name = "love")
    class User{
    }
    @Ferry
    class Employee{
    }

    @Test
    public void annotationExistsTest(){
        System.out.println(User.class.isAnnotationPresent(Ferry.class));
    }

    @Test
    public void hjsvbj(){
        Ferry ferry = User.class.getAnnotation(Ferry.class);
        System.out.println(ferry.name());
        Ferry f = Employee.class.getAnnotation(Ferry.class);
        System.out.println(f.name());
        System.out.println(ferry.equals(f));
    }

    @Test
    public void dscslkndv(){
        Ferry ferry = User.class.getAnnotation(Ferry.class);
        System.out.println(ferry.toString());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Look{
        String value();
    }
    @Look("see")
    @Test
    public void cbsiflk(){
        try {
            Look look = this.getClass().getMethod("cbsiflk").getAnnotation(Look.class);
            System.out.println(look);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Inherited
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface X {}

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Y {}

    @X
    class A {}

    class B extends A {}

    @Y
    class C {}

    class D extends C {}

    @Test
    public void skpcfsojv(){
        System.out.println("使用了@Inherited注解: "+B.class.isAnnotationPresent(X.class));
        System.out.println("未使用@Inherited注解: "+D.class.isAnnotationPresent(Y.class));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Paths{
        Path[] value();
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Repeatable(Paths.class)
    @interface Path{
        String value();
    }

    @Path("ferry/downloads")
    @Path("ferry/music")
    class AA{

    }

    @Test
    public void dsbucn(){
        Path[] ps = AA.class.getAnnotationsByType(Path.class);
        for (Path p : ps) {
            System.out.println(p.value());
        }
    }


    private String run(@NotNull String value){
        return value;
    }

    @Test
    public void dhsbuh(){
        System.out.println(run(null));

    }

}
