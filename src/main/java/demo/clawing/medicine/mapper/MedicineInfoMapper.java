package demo.clawing.medicine.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.clawing.medicine.pojo.po.MedicineInfo;
import demo.clawing.medicine.pojo.po.MedicineInfoExample;

public interface MedicineInfoMapper {
    long countByExample(MedicineInfoExample example);

    int deleteByExample(MedicineInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MedicineInfo record);

    int insertSelective(MedicineInfo record);

    List<MedicineInfo> selectByExample(MedicineInfoExample example);

    MedicineInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MedicineInfo record, @Param("example") MedicineInfoExample example);

    int updateByExample(@Param("record") MedicineInfo record, @Param("example") MedicineInfoExample example);

    int updateByPrimaryKeySelective(MedicineInfo record);

    int updateByPrimaryKey(MedicineInfo record);
}