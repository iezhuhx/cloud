package com.ww.service;
/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年5月25日 下午12:47:00
 */
	import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ww.activiti.config.CustomProcessDiagramGeneratorImpl;
import com.ww.activiti.config.WorkflowConstants;
import com.ww.activiti.controller.R;

	@Service
	public class ActivitProcessServiceImpl  {
		Log log = LogFactory.getLog(getClass());
		@Autowired
		protected RepositoryService repositoryService;

		@Autowired
		protected RuntimeService runtimeService;

		@Autowired
		protected TaskService taskService;

		@Autowired
		protected ProcessEngine processEngine;

		@Autowired
		protected HistoryService historyService;

		@Autowired
		protected FormService formService;
		
		
		
		/*@Autowired
		Activiti5Mapper activitiMapper;*/

		/**
		 * 获取流程活动的任务id
		 * 
		 * @param procId
		 *            流程号
		 * @return
		 */
		public List<String> getActivityIds(String procId) {
			// 得到流程执行对象
			List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(procId).list();
			// 得到正在执行的Activity的Id
			List<String> activityIds = new ArrayList<String>();
			for (Execution exe : executions) {
				List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
				activityIds.addAll(ids);
			}
			return activityIds;
		}
		
		public Set<String> getActivitySetIds(String procId) {
			// 得到流程执行对象
			List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(procId).list();
			// 得到正在执行的Activity的Id
			Set<String> activityIds = new HashSet<String>();
			for (Execution exe : executions) {
				List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
				activityIds.addAll(ids);
			}
			return activityIds;
		}

		/**
		 * 获取流程图的流信息
		 * 
		 * @param proDefId
		 * @param procId
		 * @return
		 */
		public InputStream getProcessImage(String proDefId, String procId) {
			/**/
			List<String> activityIds = getActivityIds(procId);
			BpmnModel bpmnModel = repositoryService.getBpmnModel(proDefId);
			InputStream in = new DefaultProcessDiagramGenerator()
					.generateDiagram(bpmnModel, "png", activityIds,
					new ArrayList<String>(0), "宋体", "宋体", "宋体", null, 1.0);
			return in;
		}

		@Autowired
		CustomProcessDiagramGeneratorImpl cpdgi;
		
		public InputStream getProcessImageV2(String proDefId, String procId) {
			/**/
			List<String> activityIds = getActivityIds(procId);
			Set<String> ids = new HashSet<String>();
			ids.addAll(activityIds);
			BpmnModel bpmnModel = repositoryService.getBpmnModel(proDefId);
			/*InputStream imageStream = cpdgi.generateDiagram(bpmnModel, "png", activityIds,
					new ArrayList<String>(0), "宋体", "宋体", "宋体", null, 1.0, 
					new Color[] { WorkflowConstants.COLOR_NORMAL, 
								  WorkflowConstants.COLOR_CURRENT },
					ids);*/
			InputStream imageStream = cpdgi.generateDiagram(bpmnModel, "png", activityIds,
					getHighLightedFlows(procId), "宋体", "宋体", "宋体", null, 1.0, 
					new Color[] { WorkflowConstants.COLOR_NORMAL, 
								  WorkflowConstants.COLOR_CURRENT },
					ids);
	        // 输出资源内容到相应对象
			return imageStream;
		}
		
		

		public Deployment deploye(String bpmnName) {
			Deployment deployment = repositoryService
					.createDeployment()
					.addClasspathResource("/processes/"+bpmnName + ".bpmn")
					.deploy();
			return deployment;
		}
		
		
		public Deployment deployeInputStream(String bpmnName) throws FileNotFoundException{
				File file = ResourceUtils.getFile("classpath:"+bpmnName);
				Deployment deployment = repositoryService.createDeployment()
						.name("二次开户1.0")
						.addInputStream(bpmnName, new FileInputStream(file))
						.deploy();
				return deployment;
		}
		
		public Deployment deployeInputStreamAbs(String bpmnNamePath) throws FileNotFoundException{
			File file = new File(bpmnNamePath);
			Deployment deployment = repositoryService.createDeployment()
					.name("二次开户1.0")
					.addInputStream(file.getName(), new FileInputStream(file))
					.deploy();
			return deployment;
	   }
		public Deployment deployeInputStreamAbs(MultipartFile file) throws FileNotFoundException{
			Deployment deployment = null;
			try {
				deployment = repositoryService.createDeployment()
						.name("二次开户1.0")
						.addInputStream(file.getOriginalFilename(), file.getInputStream())
						.deploy();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return deployment;
	   }
		
		public void deleteDeploye(String deployeId,Boolean cascade) throws FileNotFoundException{
			repositoryService.deleteDeployment(deployeId, cascade);
		}

		public ProcessDefinition getProcessDefine(String deployId) {
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.deploymentId(deployId)
					.singleResult();
			return processDefinition;
		}
		//同一个部署id可以对应一组不同的定义信息
		public List<ProcessDefinition> getProcessDefineList(String deployId) {
			List<ProcessDefinition> processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.deploymentId(deployId)
					.orderByProcessDefinitionVersion().asc()
					.list();
			return processDefinition;
		}
		//根据部署id和流程key查询所有版本的流程定义文件
		public List<ProcessDefinition> getProcessDefineWithKeyList(String deployId,String processDefinitionKey) {
			List<ProcessDefinition> processDefinitions = repositoryService
					.createProcessDefinitionQuery()
					.deploymentId(deployId)
					.processDefinitionKey(processDefinitionKey)
					.orderByProcessDefinitionVersion().desc()
					.list();
			return processDefinitions;
		}
		
		  /*public ProcessDefinition getMaxVersionProcessDefine(String deployId,String procId){
			  ProcessInstance pi = this.getProcessInstance(procId);
			  return getMaxVersionProcessDefine(deployId,pi);
		  }
		  //获取最新版本
		  public ProcessDefinition getMaxVersionProcessDefine(String deployId,ProcessInstance pi){
			  List<ProcessDefinition> list = getProcessDefineList(deployId);
			  ProcessDefinition curPd=null;
			  int version = 1;
			  if(!CollectionUtils.isEmpty(list)){
				  curPd = list.get(0);
				  version = list.get(0).getVersion();
			  }
			  for(ProcessDefinition pd:list){
				  if(pd.getKey().equals(pi.getProcessDefinitionKey())){
					 if(pd.getVersion()>=version){
						 version = pd.getVersion();
						 curPd = pd;
					 }
				  }
			  }
			  return curPd;
		  }
		  public ProcessDefinition getMinVersionProcessDefine(String deployId,String procId){
			  ProcessInstance pi = this.getProcessInstance(procId);
			  return getMinVersionProcessDefine(deployId,pi);
		  }
		  public ProcessDefinition getMinVersionProcessDefine(String deployId,ProcessInstance pi){
			  List<ProcessDefinition> list = getProcessDefineList(deployId);
			  ProcessDefinition curPd=null;
			  int version = 1;
			  if(!CollectionUtils.isEmpty(list)){
				  curPd = list.get(0);
				  version = list.get(0).getVersion();
			  }
			  for(ProcessDefinition pd:list){
				  if(pd.getKey().equals(pi.getProcessDefinitionKey())){
					 if(pd.getVersion()<=version){
						 version = pd.getVersion();
						 curPd = pd;
					 }
				  }
			  }
			  return curPd;
		  }*/
	  

		public ProcessDefinitionEntity getProcessDefineEntity(String proDefId) {
			ProcessDefinitionEntity pde = (ProcessDefinitionEntity) repositoryService.createProcessDefinitionQuery()
					.processDefinitionId(proDefId).singleResult();
			return pde;
		}

		public ProcessInstance startProcessInstanceById(String proDefId) {
			return runtimeService.startProcessInstanceById(proDefId);
		}
		public boolean isFinished(String proId) {
			if(StringUtils.isEmpty(proId)) return true;
			return getProcessInstance(proId)==null?true:false;
		}

		/*
		 * org.activiti.engine.ActivitiException: Query return 2 results instead of
		 * max 1 返回记录如果大于1，则报错，不友好！
		 */
		public Task getSingleTask(String processId) {
			return taskService.createTaskQuery().processInstanceId(processId).singleResult();
		}

		/**
		 * 多人会签时，同一个进程号会有多个任务同时进行，比如wangba和wangjiu
		 * 
		 * @param processId
		 * @return 通用的获取任务方法，获取一个或者多个任务 并行网关有问题：应该获取当前任务的下一个任务，而不是其他任务的下一个任务！！！！
		 */
		public Task getTask(String processId) {
			try {
				 return getSingleTask(processId);
				/*List<Task> tasks = taskService.createTaskQuery().processInstanceId(processId).list();
				if (CollectionUtils.isEmpty(tasks)) {
					return null;
				}
				return tasks.get(0);*/
			} catch (Exception e) {
				return null;
			}
		}
		/**
		 * 查询某一个流程的task列表
		 * @param processId
		 * @return
		 */
		public List<Task> getTaskList(String processId) {
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(processId).list();
			return tasks;
		}
	    /**
	     * 根据任务id获取任务
	     * @param taskId
	     * @return
	     */
		public Task getTaskById(String taskId) {
			try{
				return taskService.createTaskQuery().taskId(taskId).singleResult();
			}catch(Exception e){
				return null;
			}
		}

		public void complete(String taskId) {
			taskService.complete(taskId);
		}

		public void complete(String taskId, Map<String, Object> variables) {
			taskService.setVariables(taskId, variables);
			taskService.complete(taskId, variables);
		}

		
		public void setValuesByExeId(String exeId,Map<String, Object> variables){
			runtimeService.setVariables(exeId, variables);
		}
		
		public Map<String, Object> getValuesByExeId(String exeId){
			return runtimeService.getVariables(exeId);
		}
		public void completeByProcId(String processId, Map<String, Object> variables) {
			Task task = getTask(processId);
			/*taskService.setVariables(task.getId(), variables);
			taskService.setVariable(task.getId(), "setSingle", "回家养病，大概一周把！");//绑定变量 任务结束了 么有用了
*/			setVariableByTaskId(task.getId(), true, variables);
			taskService.complete(task.getId());
			/*Task task1= getTask(processId);
			taskService.setVariables(task1.getId(), variables);
			taskService.setVariable(task1.getId(), "setSingle", "回家养病，大概一周把！");//绑定变量 任务结束了 么有用了
*/		}

		public void completeByProcId(String processId) {
			Task task = getTask(processId);
			taskService.complete(task.getId());
		}

		public List<Task> getTaskListByCandidateUser(String candidateUser) {// management
			List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(candidateUser).list();
			return tasks;
		}

		public List<Task> getTaskListByGroup(String group) {
			List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(group).list();
			return tasks;
		}

		public List<Task> getTaskListInGroup(List<String> candidateGroups) {
			List<Task> tasks = taskService.createTaskQuery().taskCandidateGroupIn(candidateGroups).list();
			return tasks;
		}

		/**
		 * 将指定某人的任务，或者指定候选人或者候选组的任务查询出来
		 * 
		 * @param userIdForCandidateAndAssignee
		 * @return
		 */
		public List<Task> getTaskListByAll(String userIdForCandidateAndAssignee) {
			List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(userIdForCandidateAndAssignee).list();
			return tasks;
		}

		public List<Task> getTaskListByRole(String assigne) {// management
			return taskService.createTaskQuery().taskAssignee(assigne).list();
		}

		public boolean setVariableByTaskId(String taskId, boolean isLocal, Map<String, Object> map) {
			/*
			 * taskService.setVariable(taskId, variableName, value);
			 *
			 * 设置本执行对象的变量，该作用域只在当前的executionId中 taskService.setVariableLocal(taskId,
			 * variableName, value); 可以设置对个变量，放在map中
			 */
			if (isLocal) {
				taskService.setVariablesLocal(taskId, map);
			} else {
				taskService.setVariables(taskId, map);
			}
			return true;
		}

		public Map<String, Object> getVariableByTaskId(String taskId, boolean isLocal) {
			Map<String, Object> variablesMap = new HashMap<String, Object>();
			if (isLocal) {
				variablesMap = taskService.getVariablesLocal(taskId);
			} else {
				variablesMap = taskService.getVariables(taskId);
			}
			return variablesMap;
		}

		// 也可以强制类型转换
		public Object getVariableByTaskId(String taskId, String key) {
			return getVariableByTaskId(taskId, key);
		}

		/**
		 * // 传递的是一个自定义的bean对象 SequenceFlow sequenceFlow = new SequenceFlow();
		 * sequenceFlow.setCreateLoginName("0003"); sequenceFlow.setDate(new
		 * Date()); sequenceFlow.setMessage("重要");
		 * activitiService.setVariableByTaskId(taskId, "sequenceFlow",
		 * sequenceFlow);
		 * 
		 * SequenceFlow sequenceFlow = activitiService.getVariableByTaskId(taskId,
		 * "sequenceFlow", SequenceFlow.class);
		 */

		public void addCommentByProcId(String processId, String message, String username) {
			try {
				Authentication.setAuthenticatedUserId(username);
				taskService.addComment(getTask(processId).getId(), processId, message);
			} catch (Exception e) {
				log.info("添加注释异常，忽略！");
			}
		}

		public void addComment(String taskId, String processId, String message, String curUser) {
			Authentication.setAuthenticatedUserId(curUser);
			taskService.addComment(taskId, processId, message);
		}
		public void addComment(String processId, String message, String curUser) {
			try{
				Authentication.setAuthenticatedUserId(curUser);
				taskService.addComment(getTask(processId).getId(), processId, message);
			}catch(Exception e){
				log.info("流程已经结束！");
			}
		}

		public List<Map<String, String>> findCommentByProcId(String procId) {
			List<Map<String, String>> data = new ArrayList<>();
			// 获取流程实例ID
			String processInstanceId = procId;
			// 使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
			List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()// 历史任务表查询
					.processInstanceId(processInstanceId)// 使用流程实例ID查询
					.list();
			List<Comment> list = new ArrayList<Comment>();
			// 遍历集合，获取每个任务ID
			if (htiList != null && htiList.size() > 0) {
				for (HistoricTaskInstance hti : htiList) {
					// 任务ID
					String htaskId = hti.getId();
					// 获取批注信息
					List<Comment> taskList = taskService.getTaskComments(htaskId);// 对用历史完成后的任务ID
					list.addAll(taskList);
				}
			}
			list = taskService.getProcessInstanceComments(processInstanceId);
			Map<String, String> map = null;
			for (Comment com : list) {
				map = new LinkedHashMap<>();
				map.put("ID", com.getId());
				map.put("Message", com.getFullMessage());
				map.put("TaskId", com.getTaskId());
				map.put("ProcessInstanceId:", com.getProcessInstanceId());
				map.put("UserId", com.getUserId());// 如何存储为用户名
				map.put("time", com.getTime().toString());
				data.add(map);
			}
			System.out.println(list);
			return data;
		}

		public void findCommentByTaskId(String taskId) {
			// HistoryService historyService = processEngine.getHistoryService();
			// TaskService taskService = processEngine.getTaskService();

			// 使用当前的任务ID，查询当前流程对应的历史任务ID

			// 使用当前任务ID，获取当前任务对象
			Task task = taskService.createTaskQuery()//
					.taskId(taskId)// 使用任务ID查询
					.singleResult();
			findCommentByProcId(task.getProcessInstanceId());
		}

		public void submitStartFormDataByProcDefId(String processDefinitionId, Map<String, String> properties) {
			formService.submitStartFormData(processDefinitionId, properties);
		}

		public void submitStartFormDataByTaskId(String taskId, Map<String, String> properties) {
			formService.submitStartFormData(taskId, properties);
		}

		// 用于存放业务数据的主键或者部分业务数据
		public StartFormDataImpl getProcessFormData(String processDefinitionId) {
			return (StartFormDataImpl) formService.getStartFormData(processDefinitionId);
		}

		// 用于存放上一个任务业务数据的主键或者部分业务数据
		public TaskFormDataImpl getTaskFormData(String taskId) {
			return (TaskFormDataImpl) formService.getTaskFormData(taskId);
		}

		/**
		 * 
		 * @param taskId
		 * @return
		 */
		public ProcessDefinition getProcessDefinition(String taskId) {
			Task task = taskService.createTaskQuery() // 创建任务查询
					.taskId(taskId) // 根据任务id查询
					.singleResult();
			String processDefinitionId = task.getProcessDefinitionId(); // 获取流程定义id
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery() // 创建流程定义查询
					.processDefinitionId(processDefinitionId) // 根据流程定义id查询
					.singleResult();
			return processDefinition;
		}

		public String getDeploymentIdByTaskId(String taskId) {
			return getProcessDefinition(taskId).getDeploymentId();
		}

		public String getDeploymentIdByProcId(String processId) {
			try{
				return getDeploymentIdByTaskId(getTask(processId).getId());
			}catch(Exception e){
				return null;
			}
		}

		public ProcessInstance getProcessInstance(String processInstanceId) {
			try{
				ProcessInstance pi = runtimeService.createProcessInstanceQuery() // 根据流程实例id获取流程实例
						.processInstanceId(processInstanceId).singleResult();
				
				return pi;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		
		public HistoricProcessInstance getHisProcessInstance(String processInstanceId) {
			HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId)
				.singleResult();
			return pi;
		}
		
		public ProcessInstance startProcessInstanceByKey(String name, Map<String, Object> map) {
			return runtimeService.startProcessInstanceByKey(name, map);
		}
		//在Activiti中总共有三种表单，动态表单，普通表单和外置表单。
		public ProcessInstance startProcessInstanceById(String id, Map<String, Object> map) {
			return runtimeService.startProcessInstanceById(id,map);
		}
		
		
		/*public List<String> getDeployeeIds(){
			return activitiMapper.deployeIds();
		}
		
		public List<ProcessMessage> getProcessMessage(String procId){
			List<ProcessMessage> data = activitiMapper.procMsgs(procId);
			if(data==null){
				data = new ArrayList<>();
			}
			return data;
		}*/
		
		
		public boolean isTaskFinish(String taskId){
			Object obj =  getTaskById(taskId);
			if(obj == null){
				return true;
			}
			return false;
		}
		public boolean isProcessFinish(String procId){
			Object obj = getProcessInstance(procId);
			if(obj == null){
				return true;
			}
			return false;
		}
		/**
		 * 判断当前任务是否是正常的。
		 * @param procId
		 * @param taskId
		 * @return
		 */
		public R<String> isNormalTask(String procId,String taskId){
			try{
				if(StringUtils.isEmpty(procId)||"null".equals(procId)) return new R<String>().success();//正常
				if(StringUtils.isEmpty(taskId)||"null".equals(taskId)) return new R<String>().success();//跳过
				Task curTask = getTask(procId);
				if(curTask==null){
					return new R<String>().fail("任务已经处理完成，当前操作无效！");
				}else{
					if(taskId.equals(curTask.getId())){
						return new R<String>().success();
					}else{
						return new R<String>().fail("任务已经处理完成，当前操作无效.");
					}
				}
			}catch(Exception e){
				return new R<String>("任务处理失败！").fail(); 
			}
		}
		/**
		 * 获取高亮task
		 * @param processInstanceId
		 * @return
		 */
		public List<String> highLightedFlows(String processInstanceId){
			
		
			List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery()
					.processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
			
			// 高亮线路id集合
			//List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitList);
			// 高亮环节id集合
			List<String> highLightedActivitis = new ArrayList<String>();
			for (HistoricActivityInstance tempActivity : highLightedActivitList) {
				String activityId = tempActivity.getActivityId();
				highLightedActivitis.add(activityId);
			}
			return highLightedActivitis;
		}
		
		//获取流程实例信息
		public HistoricProcessInstance getHistoricProcessInstance(String processInstanceId){
			return historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
		}
		//获取历史上活跃的节点，即办理经过的任务节点
		public List<HistoricActivityInstance> getHistoricActivityInstance(String processInstanceId){
		 return historyService.createHistoricActivityInstanceQuery()
					.processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
				
		}
		//获取历史信息，不用考虑流程是否结束！
		public ProcessDefinitionEntity getProcessDefinitionEntity(String processInstanceId){
			return (ProcessDefinitionEntity) repositoryService
					.getProcessDefinition(getHistoricProcessInstance(processInstanceId).getProcessDefinitionId());
		}
		/**
		 * 获取需要高亮的线
		 * 
		 * @param processDefinitionEntity
		 * @param historicActivityInstances
		 * @return
		 */
		public List<String> getHighLightedFlows(String processInstanceId) {
			ProcessDefinitionEntity definitionEntity = getProcessDefinitionEntity(processInstanceId);
			List<HistoricActivityInstance> historicActivityInstances = getHistoricActivityInstance(processInstanceId); 
			List<String> highFlows = new ArrayList<>();// 用以保存高亮的线flowId
			for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
				ActivityImpl activityImpl = definitionEntity
						.findActivity(historicActivityInstances.get(i).getActivityId());// 得到节点定义的详细信息
				List<ActivityImpl> sameStartTimeNodes = new ArrayList<>();// 用以保存后需开始时间相同的节点
				ActivityImpl sameActivityImpl1 = definitionEntity
						.findActivity(historicActivityInstances.get(i + 1).getActivityId());
				// 将后面第一个节点放在时间相同节点的集合里
				sameStartTimeNodes.add(sameActivityImpl1);
				for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
					HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
					HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点
					if (Math.abs(activityImpl1.getStartTime().getTime()-activityImpl2.getStartTime().getTime()) < 200) {
//	                    if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
						// 如果第一个节点和第二个节点开始时间相同保存
						ActivityImpl sameActivityImpl2 = definitionEntity
								.findActivity(activityImpl2.getActivityId());
						sameStartTimeNodes.add(sameActivityImpl2);
					} else {
						// 有不相同跳出循环
						break;
					}
				}
				List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
				for (PvmTransition pvmTransition : pvmTransitions) {
					// 对所有的线进行遍历
					ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
					// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
					if (sameStartTimeNodes.contains(pvmActivityImpl)) {
						highFlows.add(pvmTransition.getId());
					}
				}
			}
			return highFlows;
		}
		/**
		 * 流程是否已经结束
		 * 
		 * @param processInstanceId 流程实例ID
		 * @return
		 */
		public boolean isFinishedV2(String processInstanceId) {
			return historyService.createHistoricProcessInstanceQuery().finished()
					.processInstanceId(processInstanceId).count() > 0;
		}
		
		/**
		 * 流程历史表单查询
		 *@Author iechenyb<br>
		 *@Desc 说点啥<br>
		 *@CreateTime 2020年11月6日 下午3:35:25
		 *@param procId
		 *@return
		 */
		public List<org.activiti.engine.history.HistoricDetail> historicDetail(String procId) {
			List<org.activiti.engine.history.HistoricDetail> list = historyService.createHistoricDetailQuery().processInstanceId(procId).formProperties().list();
	         return list;
		}
		
		
}
