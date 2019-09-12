package demo.selenium.pojo.testCases;

import demo.IDGenerator;

public abstract class TestCaseBO {

	/**
	 * 请使用
	 * {@link IDGenerator}
	 * 获取生成ID, 为每个测试案例配置ID
	 */
	protected Long id;

	/**
	 * 请编写案例说明
	 */
    protected String remark;

    /**
     * 以下为参数设定
     */
    protected String param = "指定参数";
}
