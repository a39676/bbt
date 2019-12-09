package demo.clawing.movie.mapper;

import demo.clawing.movie.pojo.po.MovieImage;
import demo.clawing.movie.pojo.po.MovieImageExample;
import demo.interaction.movieInteraction.pojo.dto.FindPosterIdByMovieIdListDTO;

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

	List<MovieImage> findPosterIdByMovieIdList(FindPosterIdByMovieIdListDTO dto);
}