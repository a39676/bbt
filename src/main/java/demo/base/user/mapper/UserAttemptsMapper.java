package demo.base.user.mapper;

import demo.base.user.pojo.po.UserAttempts;

public interface UserAttemptsMapper {
    int insert(UserAttempts record);

    int insertSelective(UserAttempts record);
}