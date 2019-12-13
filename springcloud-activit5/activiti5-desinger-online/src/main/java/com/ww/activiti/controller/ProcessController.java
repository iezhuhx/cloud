package com.ww.activiti.controller;

import com.ww.common.Result;
import com.ww.service.ProcessInfoService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 流程管理
 *
 * @Auther: Ace Lee
 * @Date: 2019/3/7 15:17
 */
@Slf4j
@RestController
@RequestMapping("process")
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessInfoService processInfoService;

    /**
     * 查看流程图
     *
     * @auther: Ace Lee
     * @date: 2019/3/5 15:24
     */
    @GetMapping("show")
    public void show(@RequestParam("did")String did, @RequestParam("ext")String ext, HttpServletResponse httpServletResponse) throws IOException {
        if (StringUtils.isEmpty(did) || StringUtils.isEmpty(ext)){
            return;
        }
        InputStream in = null;
        ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(did).singleResult();
        if (".png".equalsIgnoreCase(ext)){
            in = repositoryService.getProcessDiagram(processDefinition.getId());
        }else if (".bpmn".equalsIgnoreCase(ext)){
            in = repositoryService.getResourceAsStream(did, processDefinition.getResourceName());
        }
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 启动一个流程
     *
     * 参数：
     *      key:  流程定义KEY
     *
     *      bussId：  业务对象序号
     *      bussName：业务对象名称
     *      bussType：业务对象类型
     *      startUserId: 发起人id
     *      startUnitId: 发起人单位id
     *
     * @auther: Ace Lee
     * @date: 2019/3/5 15:24
     */
    @PostMapping("run/{key}")
    public Result<String> run(@PathVariable String key, @RequestBody Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, variables);
        //log.info("启动一个流程实例，id为：{}",processInstance.getId());
        return new Result<>(processInstance.getId());
    }

    /**
     *
     * 获取所有流程
     *
     * @auther: Ace Lee
     * @date: 2019/3/7 16:27
     */
    @GetMapping("")
    public Result<List<Map<String,Object>>> list() {
        List<Map<String,Object>> list = processInfoService.process();
        return new Result<>(list);
    }


    /**
     *
     * 获取流程节点
     *
     * @auther: Ace Lee
     * @date: 2019/3/7 16:27
     */
    @GetMapping("/{proDefId}/elements")
    public Result<List<Map<String,Object>>> listActivities(@PathVariable String proDefId) {
        List<Map<String,Object>> list = new ArrayList<>();
        BpmnModel model = repositoryService.getBpmnModel(proDefId);
        if(model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for(FlowElement e : flowElements) {
                //log.info("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());
                if (e.getClass().toString().equalsIgnoreCase("class org.activiti.bpmn.model.StartEvent") ||
                        e.getClass().toString().equalsIgnoreCase("class org.activiti.bpmn.model.UserTask")){
                    Map<String,Object> element = new HashMap<>();
                    element.put(e.getId(),e.getName());
                    list.add(element);
                }
            }
        }
        return new Result<>(list);
    }

}
