package demo.clawing.mapper;

import demo.clawing.pojo.po.ImageStore;
import demo.clawing.pojo.po.ImageStoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageStoreMapper {
    long countByExample(ImageStoreExample example);

    int deleteByExample(ImageStoreExample example);

    int insert(ImageStore record);

    int insertSelective(ImageStore record);

    List<ImageStore> selectByExample(ImageStoreExample example);

    int updateByExampleSelective(@Param("record") ImageStore record, @Param("example") ImageStoreExample example);

    int updateByExample(@Param("record") ImageStore record, @Param("example") ImageStoreExample example);
}