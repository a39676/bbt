package demo.selenium.mapper;

import demo.selenium.pojo.po.Screenshot;
import demo.selenium.pojo.po.ScreenshotExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScreenshotMapper {
    long countByExample(ScreenshotExample example);

    int deleteByExample(ScreenshotExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Screenshot record);

    int insertSelective(Screenshot record);

    List<Screenshot> selectByExample(ScreenshotExample example);

    Screenshot selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Screenshot record, @Param("example") ScreenshotExample example);

    int updateByExample(@Param("record") Screenshot record, @Param("example") ScreenshotExample example);

    int updateByPrimaryKeySelective(Screenshot record);

    int updateByPrimaryKey(Screenshot record);
}