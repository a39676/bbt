package demo.movie.mapper;

import demo.movie.pojo.po.MovieImage;
import demo.movie.pojo.po.MovieImageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MovieImageMapper {
    long countByExample(MovieImageExample example);

    int deleteByExample(MovieImageExample example);

    int deleteByPrimaryKey(Long imageId);

    int insert(MovieImage record);

    int insertSelective(MovieImage record);

    List<MovieImage> selectByExample(MovieImageExample example);

    MovieImage selectByPrimaryKey(Long imageId);

    int updateByExampleSelective(@Param("record") MovieImage record, @Param("example") MovieImageExample example);

    int updateByExample(@Param("record") MovieImage record, @Param("example") MovieImageExample example);

    int updateByPrimaryKeySelective(MovieImage record);

    int updateByPrimaryKey(MovieImage record);
}