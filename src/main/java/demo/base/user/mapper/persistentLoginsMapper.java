package demo.base.user.mapper;

import demo.base.user.pojo.po.persistentLogins;

public interface persistentLoginsMapper {
    int insert(persistentLogins record);

    int insertSelective(persistentLogins record);
}