package demo.movie.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.movie.pojo.dto.InsertOrUpdateMovieClickCountDTO;
import demo.movie.pojo.po.MovieClickCount;
import demo.movie.pojo.po.MovieClickCountExample;
import demo.movieInteraction.pojo.dto.FindLastHotClickDTO;

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
    
    int insertOrUpdateClickCount(InsertOrUpdateMovieClickCountDTO dto);
    
    List<MovieClickCount> findLastHotClick(FindLastHotClickDTO dto);
}