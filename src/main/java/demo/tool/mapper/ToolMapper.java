package demo.tool.mapper;

import org.apache.ibatis.annotations.Param;

public interface ToolMapper {

	int complexInsert(@Param("tableName")String tableName, @Param("values")String values);
}
