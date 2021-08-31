package demo.scriptCore.medicine.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.scriptCore.medicine.pojo.po.ClawingMedicineConstant;
import demo.scriptCore.medicine.pojo.po.ClawingMedicineConstantExample;

public interface ClawingMedicineConstantMapper {
    long countByExample(ClawingMedicineConstantExample example);

    int deleteByExample(ClawingMedicineConstantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClawingMedicineConstant record);

    int insertSelective(ClawingMedicineConstant record);

    List<ClawingMedicineConstant> selectByExample(ClawingMedicineConstantExample example);

    ClawingMedicineConstant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClawingMedicineConstant record, @Param("example") ClawingMedicineConstantExample example);

    int updateByExample(@Param("record") ClawingMedicineConstant record, @Param("example") ClawingMedicineConstantExample example);

    int updateByPrimaryKeySelective(ClawingMedicineConstant record);

    int updateByPrimaryKey(ClawingMedicineConstant record);
}