package demo.autoTestBase.testEvent.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.po.TestEventExample;

public interface TestEventMapper {
	
	long countByExample(TestEventExample example);

	int deleteByExample(TestEventExample example);

	int deleteByPrimaryKey(Long id);

	int insert(TestEvent record);

	int insertSelective(TestEvent record);

	List<TestEvent> selectByExample(TestEventExample example);

	TestEvent selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") TestEvent record, @Param("example") TestEventExample example);

	int updateByExample(@Param("record") TestEvent record, @Param("example") TestEventExample example);

	int updateByPrimaryKeySelective(TestEvent record);

	int updateByPrimaryKey(TestEvent record);

	int existsRuningEvent();

	/**
	 * 2020-07-14
	 * 采用mq 之后, 准备废弃此方法
	 * @return
	 */
	int countRuningEvent();

	int countWaitingEvent();

	/**
	 * 个别异常情况�?, testEvent 数据表未记录正常结束, 会导致其�? event 无法进行, �?要手动修正状�?
	 */
	int fixMovieClawingTestEventStatus();

	/**
	 * 2020-07-14
	 * 采用mq 之后, 准备废弃此方法
	 * @return
	 */
	List<TestEvent> findTestEventNotRunYet();

	List<TestEvent> findTestEventPageByCondition(FindTestEventPageByConditionDTO dto);
}