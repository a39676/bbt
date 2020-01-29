package demo.clawing.lottery.mapper;

import demo.clawing.lottery.pojo.po.LotterySixSoldDetail;
import demo.clawing.lottery.pojo.po.LotterySixSoldDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LotterySixSoldDetailMapper {
    long countByExample(LotterySixSoldDetailExample example);

    int deleteByExample(LotterySixSoldDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LotterySixSoldDetail record);

    int insertSelective(LotterySixSoldDetail record);

    List<LotterySixSoldDetail> selectByExample(LotterySixSoldDetailExample example);

    LotterySixSoldDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LotterySixSoldDetail record, @Param("example") LotterySixSoldDetailExample example);

    int updateByExample(@Param("record") LotterySixSoldDetail record, @Param("example") LotterySixSoldDetailExample example);

    int updateByPrimaryKeySelective(LotterySixSoldDetail record);

    int updateByPrimaryKey(LotterySixSoldDetail record);
}