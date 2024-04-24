package demo.finance.cryptoCoin.data.mapper;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinCatalogMapper {
    long countByExample(CryptoCoinCatalogExample example);

    int deleteByExample(CryptoCoinCatalogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CryptoCoinCatalog row);

    int insertSelective(CryptoCoinCatalog row);

    List<CryptoCoinCatalog> selectByExampleWithRowbounds(CryptoCoinCatalogExample example, RowBounds rowBounds);

    List<CryptoCoinCatalog> selectByExample(CryptoCoinCatalogExample example);

    CryptoCoinCatalog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") CryptoCoinCatalog row, @Param("example") CryptoCoinCatalogExample example);

    int updateByExample(@Param("row") CryptoCoinCatalog row, @Param("example") CryptoCoinCatalogExample example);

    int updateByPrimaryKeySelective(CryptoCoinCatalog row);

    int updateByPrimaryKey(CryptoCoinCatalog row);
}