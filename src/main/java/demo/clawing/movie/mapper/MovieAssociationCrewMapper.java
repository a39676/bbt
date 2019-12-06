package demo.clawing.movie.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import demo.clawing.movie.pojo.po.MovieAssociationCrew;
import demo.clawing.movie.pojo.po.MovieAssociationCrewExample;

public interface MovieAssociationCrewMapper {
    long countByExample(MovieAssociationCrewExample example);

    int deleteByExample(MovieAssociationCrewExample example);

    int deleteByPrimaryKey(Long movieId);

    int insert(MovieAssociationCrew record);

    int insertSelective(MovieAssociationCrew record);

    List<MovieAssociationCrew> selectByExample(MovieAssociationCrewExample example);

    MovieAssociationCrew selectByPrimaryKey(Long movieId);

    int updateByExampleSelective(@Param("record") MovieAssociationCrew record, @Param("example") MovieAssociationCrewExample example);

    int updateByExample(@Param("record") MovieAssociationCrew record, @Param("example") MovieAssociationCrewExample example);

    int updateByPrimaryKeySelective(MovieAssociationCrew record);

    int updateByPrimaryKey(MovieAssociationCrew record);
}