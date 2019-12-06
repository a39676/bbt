package demo.clawing.movie.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.clawing.movie.pojo.po.MovieMagnetUrl;
import demo.clawing.movie.pojo.po.MovieMagnetUrlExample;

public interface MovieMagnetUrlMapper {
    long countByExample(MovieMagnetUrlExample example);

    int deleteByExample(MovieMagnetUrlExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MovieMagnetUrl record);

    int insertSelective(MovieMagnetUrl record);

    List<MovieMagnetUrl> selectByExample(MovieMagnetUrlExample example);

    MovieMagnetUrl selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MovieMagnetUrl record, @Param("example") MovieMagnetUrlExample example);

    int updateByExample(@Param("record") MovieMagnetUrl record, @Param("example") MovieMagnetUrlExample example);

    int updateByPrimaryKeySelective(MovieMagnetUrl record);

    int updateByPrimaryKey(MovieMagnetUrl record);
}