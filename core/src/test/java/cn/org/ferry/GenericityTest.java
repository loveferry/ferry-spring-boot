package cn.org.ferry;


import javafx.beans.binding.ObjectExpression;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenericityTest {
	@Data
	class Person{
		Integer id;
		String name;
	}

	@Data
	class Student extends Person{
		String grade;

		@Override
		public String toString() {
			return "Student{" +
					"id=" + id +
					", name='" + name + '\'' +
					", grade='" + grade + '\'' +
					'}';
		}
	}

	@Test
	public void chgjk(){
		List<? extends Person> list = get();
		System.out.println(list);
	}

	/**
	 * 泛型通配符,子类型限定的通配符用于访问器读取数据
	 */
	private List<? extends Person> get(){
		Student student = new Student();
		student.setId(101);
		List<Student> list = new ArrayList<>(1);
		list.add(student);
		return list;
	}

	@Test
	public void cdsvds(){
		List<Object> list = new ArrayList<>(1);
		set(list);
		System.out.println(list);
	}

	/**
	 * 泛型通配符,超类型限定的通配符用于更改器写入数据
	 */
	private void set(List<? super Person> list){
		Student student = new Student();
		student.setName("ferry");
		list.add(student);
	}
}

