package demo.movie.mapper;

import demo.movie.pojo.po.MovieAssociationCrew;
import demo.movie.pojo.po.MovieAssociationCrewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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