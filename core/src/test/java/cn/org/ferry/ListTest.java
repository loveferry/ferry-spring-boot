package cn.org.ferry;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListTest {
    @Data
    @AllArgsConstructor
    class U{
        private int id;
        private String name;
    }
    @Test
    public void groupByListTest(){
        List<U> list = new ArrayList<>(10);
        list.add(new U(1, "ferry"));
        list.add(new U(2, "joyce"));
        list.add(new U(3, "lucy"));
        list.add(new U(1, "honey"));
        Map<Integer, List<U>> map = list.stream().collect(Collectors.groupingBy(U::getId));
        System.out.println("");
    }

    class ArrayListTest{
        int count;
    }

    @Test
    public void cjsdv(){
        List<U> list = new ArrayList<>(10);
        list.add(new U(1, "ferry"));
        list.add(new U(2, "joyce"));
        list.add(new U(3, "lucy"));
        list.add(new U(1, "honey"));
        list.forEach(item -> item.setId(10));
        System.out.println(list);
    }

    @Test
    public void dbsjabka(){
        List list = new ArrayList();
        System.out.println(list.getClass().getSuperclass().equals(Collection.class));

    }


    @Test
    public void chsiud(){
        List<String> list = new ArrayList<>(4);
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.remove(2);
        list.add(2,"2");
        System.out.println(list);

    }
}
