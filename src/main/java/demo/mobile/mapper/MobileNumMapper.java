package demo.mobile.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.mobile.pojo.param.mapperParam.MobileNumBatchInsertParam;
import demo.mobile.pojo.po.MobileNum;
import demo.mobile.pojo.po.MobileNumExample;

public interface MobileNumMapper {
    long countByExample(MobileNumExample example);

	int deleteByExample(MobileNumExample example);

	int insert(MobileNum record);

	int insertSelective(MobileNum record);

	List<MobileNum> selectByExample(MobileNumExample example);

	int updateByExampleSelective(@Param("record") MobileNum record, @Param("example") MobileNumExample example);

	int updateByExample(@Param("record") MobileNum record, @Param("example") MobileNumExample example);

	int insertIgnoreSelective(MobileNum record);
    
    Integer insertIgnoreSelectiveMultiple(MobileNumBatchInsertParam param);
}