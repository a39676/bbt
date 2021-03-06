package demo.clawing.medicine.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.clawing.medicine.pojo.po.MedicineFactory;
import demo.clawing.medicine.pojo.po.MedicineFactoryExample;

public interface MedicineFactoryMapper {
    long countByExample(MedicineFactoryExample example);

    int deleteByExample(MedicineFactoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MedicineFactory record);

    int insertSelective(MedicineFactory record);

    List<MedicineFactory> selectByExample(MedicineFactoryExample example);

    MedicineFactory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MedicineFactory record, @Param("example") MedicineFactoryExample example);

    int updateByExample(@Param("record") MedicineFactory record, @Param("example") MedicineFactoryExample example);

    int updateByPrimaryKeySelective(MedicineFactory record);

    int updateByPrimaryKey(MedicineFactory record);
}