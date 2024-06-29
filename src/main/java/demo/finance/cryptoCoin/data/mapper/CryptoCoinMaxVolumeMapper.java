package demo.finance.cryptoCoin.data.mapper;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinMaxVolume;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinMaxVolumeExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinMaxVolumeKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CryptoCoinMaxVolumeMapper {
    long countByExample(CryptoCoinMaxVolumeExample example);

    int deleteByExample(CryptoCoinMaxVolumeExample example);

    int deleteByPrimaryKey(CryptoCoinMaxVolumeKey key);

    int insert(CryptoCoinMaxVolume row);

    int insertSelective(CryptoCoinMaxVolume row);

    List<CryptoCoinMaxVolume> selectByExampleWithRowbounds(CryptoCoinMaxVolumeExample example, RowBounds rowBounds);

    List<CryptoCoinMaxVolume> selectByExample(CryptoCoinMaxVolumeExample example);

    CryptoCoinMaxVolume selectByPrimaryKey(CryptoCoinMaxVolumeKey key);

    int updateByExampleSelective(@Param("row") CryptoCoinMaxVolume row, @Param("example") CryptoCoinMaxVolumeExample example);

    int updateByExample(@Param("row") CryptoCoinMaxVolume row, @Param("example") CryptoCoinMaxVolumeExample example);

    int updateByPrimaryKeySelective(CryptoCoinMaxVolume row);

    int updateByPrimaryKey(CryptoCoinMaxVolume row);
}