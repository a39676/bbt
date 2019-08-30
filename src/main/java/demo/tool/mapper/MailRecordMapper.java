package demo.tool.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import demo.tool.pojo.MailRecord;
import demo.tool.pojo.param.InsertNewMailRecordParam;

public interface MailRecordMapper {
    int insert(MailRecord record);

    int insertSelective(MailRecord record);
    
    MailRecord findMailByMailKeyMailType(@Param("mailKey")String mailKey, @Param("mailType")Integer mailType);
    
    int updateWasUsed(Integer mailId);
    
    MailRecord findUnusedByUserId(@Param("userId")Long userId, @Param("mailType")Integer mailType);
    
    int updateResend(String mailKey);
    
    int updateResend(@Param("mailKey")String mailKey, @Param("newValidTime")String newValidTime);
    
    int cleanMailRecord(@Param("validTime")Date validTime);
    
    int insertNewMailRecord(InsertNewMailRecordParam param);
    
    int hasMailTask();
}