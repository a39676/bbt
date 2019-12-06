package demo.clawing.meizitu.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.clawing.meizitu.pojo.po.MeizituGroupRecord;
import demo.clawing.meizitu.pojo.po.MeizituGroupRecordExample;

public interface MeizituGroupRecordMapper {
    long countByExample(MeizituGroupRecordExample example);

    int deleteByExample(MeizituGroupRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MeizituGroupRecord record);

    int insertSelective(MeizituGroupRecord record);

    List<MeizituGroupRecord> selectByExample(MeizituGroupRecordExample example);
    MeizituGroupRecord hasClawedThisGroup(String url);

    MeizituGroupRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MeizituGroupRecord record, @Param("example") MeizituGroupRecordExample example);

    int updateByExample(@Param("record") MeizituGroupRecord record, @Param("example") MeizituGroupRecordExample example);

    int updateByPrimaryKeySelective(MeizituGroupRecord record);

    int updateByPrimaryKey(MeizituGroupRecord record);
}