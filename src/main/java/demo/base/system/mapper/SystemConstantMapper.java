package demo.base.system.mapper;

import java.util.List;

import demo.base.system.pojo.bo.SystemConstant;

public interface SystemConstantMapper {
    int insert(SystemConstant record);

    int insertSelective(SystemConstant record);
    
    SystemConstant getValByName(String constantName);
    
    List<SystemConstant> getValsByName(List<String> constantNames);
}