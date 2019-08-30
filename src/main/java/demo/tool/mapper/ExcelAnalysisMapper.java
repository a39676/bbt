package demo.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.tool.pojo.po.ExcelAnalysis;
import demo.tool.pojo.po.example.ExcelAnalysisExample;

public interface ExcelAnalysisMapper {
    long countByExample(ExcelAnalysisExample example);

    int deleteByExample(ExcelAnalysisExample example);

    int insert(ExcelAnalysis record);

    int insertSelective(ExcelAnalysis record);

    List<ExcelAnalysis> selectByExample(ExcelAnalysisExample example);

    int updateByExampleSelective(@Param("record") ExcelAnalysis record, @Param("example") ExcelAnalysisExample example);

    int updateByExample(@Param("record") ExcelAnalysis record, @Param("example") ExcelAnalysisExample example);
    
    ExcelAnalysis findOne(Long id);
}