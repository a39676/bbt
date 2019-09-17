package demo.movie.mapper;

import demo.movie.pojo.po.MovieInfo;
import demo.movie.pojo.po.MovieInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
}