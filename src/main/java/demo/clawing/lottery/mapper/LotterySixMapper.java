package demo.clawing.lottery.mapper;

import demo.clawing.lottery.pojo.po.LotterySix;
import demo.clawing.lottery.pojo.po.LotterySixExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LotterySixMapper {
    long countByExample(LotterySixExample example);

    int deleteByExample(LotterySixExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LotterySix record);

    int insertSelective(LotterySix record);

    List<LotterySix> selectByExample(LotterySixExample example);

    LotterySix selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LotterySix record, @Param("example") LotterySixExample example);

    int updateByExample(@Param("record") LotterySix record, @Param("example") LotterySixExample example);

    int updateByPrimaryKeySelective(LotterySix record);

    int updateByPrimaryKey(LotterySix record);
    
    LotterySix findLastRecord();
}