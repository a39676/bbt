package demo;

import demo.config.costom_component.SnowFlake;

public class IDGenerator {
	private final static SnowFlake s = new SnowFlake();
	
	public static long getID() {
		return s.getNextId();
	}
	
	public static void main(String[] args) {
		System.out.println(getID());
	}
}
