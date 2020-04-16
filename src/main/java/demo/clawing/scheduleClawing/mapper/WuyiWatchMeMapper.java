package demo.clawing.scheduleClawing.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.clawing.scheduleClawing.pojo.po.WuyiWatchMe;
import demo.clawing.scheduleClawing.pojo.po.WuyiWatchMeExample;

public interface WuyiWatchMeMapper {
    long countByExample(WuyiWatchMeExample example);

    int deleteByExample(WuyiWatchMeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WuyiWatchMe record);

    int insertSelective(WuyiWatchMe record);

    List<WuyiWatchMe> selectByExample(WuyiWatchMeExample example);

    WuyiWatchMe selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WuyiWatchMe record, @Param("example") WuyiWatchMeExample example);

    int updateByExample(@Param("record") WuyiWatchMe record, @Param("example") WuyiWatchMeExample example);

    int updateByPrimaryKeySelective(WuyiWatchMe record);

    int updateByPrimaryKey(WuyiWatchMe record);
    
    WuyiWatchMe findTheLastWatch();
}