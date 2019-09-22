package demo.movie.mapper;

import demo.movie.pojo.po.MovieIntroduction;
import demo.movie.pojo.po.MovieIntroductionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MovieIntroductionMapper {
    long countByExample(MovieIntroductionExample example);

    int deleteByExample(MovieIntroductionExample example);

    int deleteByPrimaryKey(Long movieId);

    int insert(MovieIntroduction record);

    int insertSelective(MovieIntroduction record);

    List<MovieIntroduction> selectByExample(MovieIntroductionExample example);

    MovieIntroduction selectByPrimaryKey(Long movieId);

    int updateByExampleSelective(@Param("record") MovieIntroduction record, @Param("example") MovieIntroductionExample example);

    int updateByExample(@Param("record") MovieIntroduction record, @Param("example") MovieIntroductionExample example);

    int updateByPrimaryKeySelective(MovieIntroduction record);

    int updateByPrimaryKey(MovieIntroduction record);
}