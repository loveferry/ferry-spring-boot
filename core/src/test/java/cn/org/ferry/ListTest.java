package cn.org.ferry;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
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
        System.out.println(new ArrayListTest().count);

    }
}
