package demo.movie.mapper;

import demo.movie.pojo.po.MovieClickCount;
import demo.movie.pojo.po.MovieClickCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MovieClickCountMapper {
    long countByExample(MovieClickCountExample example);

    int deleteByExample(MovieClickCountExample example);

    int deleteByPrimaryKey(Long movieId);

    int insert(MovieClickCount record);

    int insertSelective(MovieClickCount record);

    List<MovieClickCount> selectByExample(MovieClickCountExample example);

    MovieClickCount selectByPrimaryKey(Long movieId);

    int updateByExampleSelective(@Param("record") MovieClickCount record, @Param("example") MovieClickCountExample example);

    int updateByExample(@Param("record") MovieClickCount record, @Param("example") MovieClickCountExample example);

    int updateByPrimaryKeySelective(MovieClickCount record);

    int updateByPrimaryKey(MovieClickCount record);
}