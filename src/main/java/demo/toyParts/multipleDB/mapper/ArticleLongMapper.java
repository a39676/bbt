package demo.toyParts.multipleDB.mapper;

import demo.toyParts.multipleDB.pojo.po.ArticleLong;
import demo.toyParts.multipleDB.pojo.po.ArticleLongExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleLongMapper {
    long countByExample(ArticleLongExample example);

    int deleteByExample(ArticleLongExample example);

    int deleteByPrimaryKey(Long articleId);

    int insert(ArticleLong record);

    int insertSelective(ArticleLong record);

    List<ArticleLong> selectByExample(ArticleLongExample example);

    ArticleLong selectByPrimaryKey(Long articleId);

    int updateByExampleSelective(@Param("record") ArticleLong record, @Param("example") ArticleLongExample example);

    int updateByExample(@Param("record") ArticleLong record, @Param("example") ArticleLongExample example);

    int updateByPrimaryKeySelective(ArticleLong record);

    int updateByPrimaryKey(ArticleLong record);
}