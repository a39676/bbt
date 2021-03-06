package demo.interaction.image.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.interaction.image.pojo.po.ImageStore;
import demo.interaction.image.pojo.po.ImageStoreExample;

public interface ImageStoreMapper {
    long countByExample(ImageStoreExample example);

    int deleteByExample(ImageStoreExample example);

    int insert(ImageStore record);

    int insertSelective(ImageStore record);

    List<ImageStore> selectByExample(ImageStoreExample example);

    int updateByExampleSelective(@Param("record") ImageStore record, @Param("example") ImageStoreExample example);

    int updateByExample(@Param("record") ImageStore record, @Param("example") ImageStoreExample example);
}