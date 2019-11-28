package demo.testCase.mapper;

import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.po.TestEventExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
    
    int countRuningEvent();
    
    int countWaitingEvent();
    
    /**
	 * 个别异常情况下, testEvent 数据表未记录正常结束, 会导致其他 event 无法进行, 需要手动修正状态
	 */
    int fixMovieClawingTestEventStatus();
    
    List<TestEvent> findTestEventNotRunYet();
}