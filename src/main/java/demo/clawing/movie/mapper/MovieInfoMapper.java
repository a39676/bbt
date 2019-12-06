package demo.clawing.movie.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.clawing.movie.pojo.dto.FindMovieListByConditionDTO;
import demo.clawing.movie.pojo.po.MovieInfo;
import demo.clawing.movie.pojo.po.MovieInfoExample;

public interface MovieInfoMapper {
    long countByExample(MovieInfoExample example);

    int deleteByExample(MovieInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MovieInfo record);

    int insertSelective(MovieInfo record);

    List<MovieInfo> selectByExample(MovieInfoExample example);

    MovieInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MovieInfo record, @Param("example") MovieInfoExample example);

    int updateByExample(@Param("record") MovieInfo record, @Param("example") MovieInfoExample example);

    int updateByPrimaryKeySelective(MovieInfo record);

    int updateByPrimaryKey(MovieInfo record);
    
    List<MovieInfo> findListByCondition(FindMovieListByConditionDTO dto);
}