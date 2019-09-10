package demo.clawing.mapper;

import demo.clawing.pojo.po.MeizituGroupRecord;
import demo.clawing.pojo.po.MeizituGroupRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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