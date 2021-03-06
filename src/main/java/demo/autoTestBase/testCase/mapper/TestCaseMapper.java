package demo.autoTestBase.testCase.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.autoTestBase.testCase.pojo.po.TestCase;
import demo.autoTestBase.testCase.pojo.po.TestCaseExample;

public interface TestCaseMapper {
    long countByExample(TestCaseExample example);

    int deleteByExample(TestCaseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TestCase record);

    int insertSelective(TestCase record);

    List<TestCase> selectByExample(TestCaseExample example);

    TestCase selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TestCase record, @Param("example") TestCaseExample example);

    int updateByExample(@Param("record") TestCase record, @Param("example") TestCaseExample example);

    int updateByPrimaryKeySelective(TestCase record);

    int updateByPrimaryKey(TestCase record);
    
    TestCase findByCaseCode(String caseCode);
}