package demo.movie.mapper;

import demo.movie.pojo.dto.MovieRecordFindByConditionDTO;
import demo.movie.pojo.po.MovieRecord;
import demo.movie.pojo.po.MovieRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MovieRecordMapper {
    long countByExample(MovieRecordExample example);

    int deleteByExample(MovieRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MovieRecord record);

    int insertSelective(MovieRecord record);

    List<MovieRecord> selectByExample(MovieRecordExample example);

    MovieRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MovieRecord record, @Param("example") MovieRecordExample example);

    int updateByExample(@Param("record") MovieRecord record, @Param("example") MovieRecordExample example);

    int updateByPrimaryKeySelective(MovieRecord record);

    int updateByPrimaryKey(MovieRecord record);

	List<MovieRecord> findByCondition(MovieRecordFindByConditionDTO dto);
}