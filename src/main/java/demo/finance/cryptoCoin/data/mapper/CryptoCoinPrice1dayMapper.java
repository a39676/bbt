package demo.finance.cryptoCoin.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;

public interface CryptoCoinPrice1dayMapper {
	long countByExample(CryptoCoinPrice1dayExample example);

	int deleteByExample(CryptoCoinPrice1dayExample example);

	int deleteByPrimaryKey(Long id);

	int insert(CryptoCoinPrice1day row);

	int insertSelective(CryptoCoinPrice1day row);

	List<CryptoCoinPrice1day> selectByExampleWithRowbounds(CryptoCoinPrice1dayExample example, RowBounds rowBounds);

	List<CryptoCoinPrice1day> selectByExample(CryptoCoinPrice1dayExample example);

	CryptoCoinPrice1day selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("row") CryptoCoinPrice1day row,
			@Param("example") CryptoCoinPrice1dayExample example);

	int updateByExample(@Param("row") CryptoCoinPrice1day row, @Param("example") CryptoCoinPrice1dayExample example);

	int updateByPrimaryKeySelective(CryptoCoinPrice1day row);

	int updateByPrimaryKey(CryptoCoinPrice1day row);

	CryptoCoinPrice1day selectLastDataByCoinTypeAndCurrencyType(@Param("coinType") Long coinType,
			@Param("currencyType") Long currencyType);
}