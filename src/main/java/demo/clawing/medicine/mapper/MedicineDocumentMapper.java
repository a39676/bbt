package demo.clawing.medicine.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.clawing.medicine.pojo.po.MedicineDocument;
import demo.clawing.medicine.pojo.po.MedicineDocumentExample;

public interface MedicineDocumentMapper {
    long countByExample(MedicineDocumentExample example);

    int deleteByExample(MedicineDocumentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MedicineDocument record);

    int insertSelective(MedicineDocument record);

    List<MedicineDocument> selectByExample(MedicineDocumentExample example);

    MedicineDocument selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MedicineDocument record, @Param("example") MedicineDocumentExample example);

    int updateByExample(@Param("record") MedicineDocument record, @Param("example") MedicineDocumentExample example);

    int updateByPrimaryKeySelective(MedicineDocument record);

    int updateByPrimaryKey(MedicineDocument record);
}