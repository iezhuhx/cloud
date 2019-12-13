package com.ww.activiti.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 *
 * 节点任务完成监听类
 *
 * @auther: Ace Lee
 * @date: 2019/3/8 11:57
 */
@Component
@Slf4j
public class MyTaskCompletedListener implements TaskListener {

    //监听任务的事件
    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee = delegateTask.getAssignee();
        String eventName = delegateTask.getEventName();
        String name = delegateTask.getName();
        String processInstanceId = delegateTask.getProcessInstanceId();
        Set<String> variableNames = delegateTask.getVariableNames();
        for (String key : variableNames) {
            Object value = delegateTask.getVariable(key);
            //log.info(key + " = " + value);
        }
        //log.info("一个任务["+name+"]被创建了，由["+assignee+"]负责办理");
    }
}


