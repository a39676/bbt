package demo.movie.mapper;

import demo.movie.pojo.po.MovieUrl;
import demo.movie.pojo.po.MovieUrlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MovieUrlMapper {
    long countByExample(MovieUrlExample example);

    int deleteByExample(MovieUrlExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MovieUrl record);

    int insertSelective(MovieUrl record);

    List<MovieUrl> selectByExample(MovieUrlExample example);

    MovieUrl selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MovieUrl record, @Param("example") MovieUrlExample example);

    int updateByExample(@Param("record") MovieUrl record, @Param("example") MovieUrlExample example);

    int updateByPrimaryKeySelective(MovieUrl record);

    int updateByPrimaryKey(MovieUrl record);
}