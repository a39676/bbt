package demo.movie.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.movie.po.dto.MovieCrewfindByConditionDTO;
import demo.movie.pojo.po.MovieCrew;
import demo.movie.pojo.po.MovieCrewExample;

public interface MovieCrewMapper {
    long countByExample(MovieCrewExample example);

    int deleteByExample(MovieCrewExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MovieCrew record);

    int insertSelective(MovieCrew record);

    List<MovieCrew> selectByExample(MovieCrewExample example);

    MovieCrew selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MovieCrew record, @Param("example") MovieCrewExample example);

    int updateByExample(@Param("record") MovieCrew record, @Param("example") MovieCrewExample example);

    int updateByPrimaryKeySelective(MovieCrew record);

    int updateByPrimaryKey(MovieCrew record);
    
    List<MovieCrew> findByCondition(MovieCrewfindByConditionDTO dto);
}