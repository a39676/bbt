package demo.clawing.mapper;

import demo.clawing.pojo.po.MedicineDocument;
import demo.clawing.pojo.po.MedicineDocumentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MedicineDocumentMapper {
    long countByExample(MedicineDocumentExample example);

    int deleteByExample(MedicineDocumentExample example);

    int deleteByPrimaryKey(Long medicineId);

    int insert(MedicineDocument record);

    int insertSelective(MedicineDocument record);

    List<MedicineDocument> selectByExample(MedicineDocumentExample example);

    MedicineDocument selectByPrimaryKey(Long medicineId);

    int updateByExampleSelective(@Param("record") MedicineDocument record, @Param("example") MedicineDocumentExample example);

    int updateByExample(@Param("record") MedicineDocument record, @Param("example") MedicineDocumentExample example);

    int updateByPrimaryKeySelective(MedicineDocument record);

    int updateByPrimaryKey(MedicineDocument record);
}