package com.ww.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HistoryMapper {

    List<Map<String, Object>> selectMyTasksCompleted(String userId);

    List<Map<String, Object>> selectMyProcessStarted(String userId);

    Map<String, Object> selectEndEventByTaskId(String taskId);

    int deleteHiEndEvent(String taskId);

    List<Map<String, Object>> selectHiTaskByTaskId(String taskId);

    List<Map<String, Object>> selectHiTaskByTaskKey(String taskKey);

    List<Map<String, Object>> selectHiVariablesByProInsId(String proInsId);

    Map<String, Object> selectIdentitylinkByTaskId(String taskId);
}