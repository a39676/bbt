package demo.selenium.mapper;

import demo.selenium.pojo.po.TestEvent;
import demo.selenium.pojo.po.TestEventExample;
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
}