package demo.base.user.mapper;

import demo.base.user.pojo.po.AuthRole;
import demo.base.user.pojo.po.AuthRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthRoleMapper {
    long countByExample(AuthRoleExample example);

    int deleteByExample(AuthRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AuthRole record);

    int insertSelective(AuthRole record);

    List<AuthRole> selectByExample(AuthRoleExample example);

    AuthRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AuthRole record, @Param("example") AuthRoleExample example);

    int updateByExample(@Param("record") AuthRole record, @Param("example") AuthRoleExample example);

    int updateByPrimaryKeySelective(AuthRole record);

    int updateByPrimaryKey(AuthRole record);
}