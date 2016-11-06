package xyz.a5s7.xmt.bean;

import org.dom4j.tree.DefaultElement;

import java.util.Deque;

public class Child extends Bean1 {
	
	private int age;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@SuppressWarnings("unused")
	private void migrate1(DefaultElement dom, Deque<Integer> versions) {
	}
	
	@SuppressWarnings("unused")
	private void migrate3(DefaultElement dom, Deque<Integer> versions) {
		//it is possible to skip some versions
	}
}
