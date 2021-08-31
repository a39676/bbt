package demo.scriptCore.medicine.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.scriptCore.medicine.pojo.po.MedicineInfoError;
import demo.scriptCore.medicine.pojo.po.MedicineInfoErrorExample;

public interface MedicineInfoErrorMapper {
    long countByExample(MedicineInfoErrorExample example);

    int deleteByExample(MedicineInfoErrorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MedicineInfoError record);

    int insertSelective(MedicineInfoError record);

    List<MedicineInfoError> selectByExample(MedicineInfoErrorExample example);

    MedicineInfoError selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MedicineInfoError record, @Param("example") MedicineInfoErrorExample example);

    int updateByExample(@Param("record") MedicineInfoError record, @Param("example") MedicineInfoErrorExample example);

    int updateByPrimaryKeySelective(MedicineInfoError record);

    int updateByPrimaryKey(MedicineInfoError record);
}