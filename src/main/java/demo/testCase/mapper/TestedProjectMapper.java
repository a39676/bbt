package demo.testCase.mapper;

import demo.testCase.pojo.po.TestedProject;
import demo.testCase.pojo.po.TestedProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestedProjectMapper {
    long countByExample(TestedProjectExample example);

    int deleteByExample(TestedProjectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TestedProject record);

    int insertSelective(TestedProject record);

    List<TestedProject> selectByExample(TestedProjectExample example);

    TestedProject selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TestedProject record, @Param("example") TestedProjectExample example);

    int updateByExample(@Param("record") TestedProject record, @Param("example") TestedProjectExample example);

    int updateByPrimaryKeySelective(TestedProject record);

    int updateByPrimaryKey(TestedProject record);
    
    TestedProject findByProjectName(String findByProjectName);
}