package demo.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.base.user.pojo.po.UserAuth;
import demo.base.user.pojo.po.UserAuthExample;

public interface UserAuthMapper {

	long countByExample(UserAuthExample example);

    int deleteByExample(UserAuthExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserAuth record);

    int insertSelective(UserAuth record);

    List<UserAuth> selectByExample(UserAuthExample example);

    UserAuth selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserAuth record, @Param("example") UserAuthExample example);

    int updateByExample(@Param("record") UserAuth record, @Param("example") UserAuthExample example);

    int updateByPrimaryKeySelective(UserAuth record);

    int updateByPrimaryKey(UserAuth record);
    
}