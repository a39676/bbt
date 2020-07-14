package demo.autoTestBase.testEvent.pojo.constant;

public class TestEventOptionConstant {
	
	public static String windowFolder = "d:\\auxiliary\\testEventReport";
	public static String linuxFolder = "/home/u2/testEventReport";
	/**
	 * 2020-07-14
	 * 采用mq之后, 准备废弃此变量
	 */
	public static boolean enableMultipleTestEvent = false;
	/**
	 * 2020-07-14
	 * 采用mq之后, 准备废弃此变量
	 */
	public static int multipleRunTestEventCount = 1;
	public static final String testEventQueue = "testEventQueue";
	
}
