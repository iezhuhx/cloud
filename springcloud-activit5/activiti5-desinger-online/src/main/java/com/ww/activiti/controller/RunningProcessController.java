package com.ww.activiti.controller;

import com.ww.activiti.vo.RuExecution;
import com.ww.activiti.vo.RuTask;
import com.ww.common.RestServiceController;
import com.ww.util.ToWeb;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行中的流程
 *
 * @Auther: Ace Lee
 * @Date: 2019/3/5 15:04
 */
@RestController
@RequestMapping("runs")
public class RunningProcessController implements RestServiceController<RuExecution, String> {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Override
    public Object getOne(String s) {
        return null;
    }

    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "update/{state}/{processInstanceId}")
    public Object updateState(@PathVariable("state") String state, @PathVariable("processInstanceId") String processInstanceId) {
        if (state.equals("active")) {
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println("已激活ID为[" + processInstanceId + "]的流程实例。");
        } else if (state.equals("suspend")) {
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println("已挂起ID为[" + processInstanceId + "]的流程实例。");
        }
        return ToWeb.buildResult().refresh();
    }

    /**
     * 获取任务列表
     *
     * @param rowSize
     * @param page
     * @return
     */
    @RequestMapping(value = "tasks")
    public Object tasks(@RequestParam(value = "rowSize", defaultValue = "1000", required = false) Integer rowSize, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        List<Task> tasks = taskService.createTaskQuery().listPage(rowSize * (page - 1), rowSize);
        long count = runtimeService.createExecutionQuery().count();
        List<RuTask> list = new ArrayList<>();
        for(Task task: tasks){
            list.add(new RuTask(task));
        }
        return ToWeb.buildResult().setRows(
                ToWeb.Rows.buildRows()
                        .setRowSize(rowSize)
                        .setTotalPages((int) (count / rowSize + 1))
                        .setTotalRows(count)
                        .setList(list)
                        .setCurrent(page)
        );
    }


    @Override
    public Object getList(@RequestParam(value = "rowSize", defaultValue = "1000", required = false) Integer rowSize, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        List<Execution> executions = runtimeService.createExecutionQuery().listPage(rowSize * (page - 1), rowSize);
        long count = runtimeService.createExecutionQuery().count();
        List<RuExecution> list = new ArrayList<>();
        for(Execution execution: executions){
            list.add(new RuExecution(execution));
        }
        return ToWeb.buildResult().setRows(
                ToWeb.Rows.buildRows()
                        .setRowSize(rowSize)
                        .setTotalPages((int) (count / rowSize + 1))
                        .setTotalRows(count)
                        .setList(list)
                        .setCurrent(page)
        );
    }

    @Override
    public Object postOne(RuExecution entity) {
        return null;
    }

    @Override
    public Object putOne(String s, RuExecution entity) {
        return null;
    }

    @Override
    public Object patchOne(String s, RuExecution entity) {
        return null;
    }

    @Override
    public Object deleteOne(String s) {
        return null;
    }


}
