package demo.task.mapper;

import demo.task.pojo.param.mapperParam.UpdateIsOnParam;
import demo.task.pojo.param.mapperParam.UpdateRunResultParam;
import demo.task.pojo.po.TaskManage;

public interface TaskManageMapper {
    int insert(TaskManage record);

    int insertSelective(TaskManage record);
    
    TaskManage findTask(Long id);
    
    int updateRunResult(UpdateRunResultParam param);
    
    int updateIsOn(UpdateIsOnParam param);
}