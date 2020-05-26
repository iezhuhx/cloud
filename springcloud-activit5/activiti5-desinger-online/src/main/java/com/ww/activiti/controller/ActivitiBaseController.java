package com.ww.activiti.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ww.dao.RuntimeMapper;
import com.ww.service.ActivitProcessServiceImpl;

/*import com.cyb.freemarker.mvc.base.BaseController;
import com.cyb.utils.response.R;
import com.ww.service.ActivitProcessServiceImpl;*/

import io.swagger.annotations.ApiOperation;
@Controller
@RequestMapping("activit5")
public class ActivitiBaseController{
	
	
	@Autowired
	ActivitProcessServiceImpl activitProcessServiceImpl;
	
	@Autowired
	TaskService taskService ;
	
	/*@Autowired
	SpringProcessEngineConfiguration dm;//获取部署管理类
	*/	
	
	@GetMapping("a0/isFinish")
	@ResponseBody
	@ApiOperation(value="判断流程或者任务是否结束",notes="type=task|proc")
	public R<String> isFinished(String id,String type){
		if("task".equals(type)){
			Boolean res = activitProcessServiceImpl.isTaskFinish(id);
			return new R<String>(res.toString());
		}else if("proc".equals(type)){
			Boolean res = activitProcessServiceImpl.isProcessFinish(id);
			return new R<String>(res.toString());
		}else{
			return new R<String>("参数有误！");
		}
	}
	
	@GetMapping("a0/deployInfo/process")
	@ResponseBody
	@ApiOperation(value="根据流程的id获取其部署信息",notes="根据流程的id获取其部署信息")
	public R<String> deployInfo(String processId){
		ProcessInstance pi = activitProcessServiceImpl.getProcessInstance(processId);
		if(pi==null){
			return new R<String>().fail("流程已结束或者未发现。");
		}else{
			String res = pi.getProcessDefinitionKey()+",verison="+pi.getProcessDefinitionVersion();
			return new R<String>(res);
		}
	}

	//流程部署详细信息查看
	@GetMapping("a1/deployListInfor")
	@ResponseBody
	@ApiOperation(value="根据流程deployeId查询所有的部署文件",notes="参数都是必填，按照版本号降序排列返回。")
	public R<List<String>> deployInfor(String deployeId){
		List<ProcessDefinition> processDefinition = activitProcessServiceImpl.getProcessDefineList(deployeId);
		List<String> info = new ArrayList<>();
		for(ProcessDefinition pd:processDefinition){
			info.add(new StringBuilder().append(pd.getId()+",").append(pd.getKey()+",").append(pd.getName()+",").append(pd.getResourceName()).toString());
		}
		return new R<List<String>>(info);
	}
	
	@GetMapping("a1/newlyProcessdeployfor")
	@ResponseBody
	@ApiOperation(value="根据流程key查询最新的部署文件",notes="参数都是必填，按照版本号降序排列返回。id,key,version,resourceName")
	public R<List<String>> newlyProcessdeployfor(String deployeId,String processKey){
		List<ProcessDefinition> processDefinition = activitProcessServiceImpl.getProcessDefineWithKeyList(deployeId,processKey);
		List<String> info = new ArrayList<>();
		for(ProcessDefinition pd:processDefinition){
			info.add(new StringBuilder().append(pd.getId()+",").append(pd.getKey()+",").append(pd.getVersion()+",").append(pd.getResourceName()).toString());
		}
		return new R<List<String>>(info);
	}
	
	
	
    /**
     * 获取工作流文件信息
     * @return
     * @throws FileNotFoundException
     */
	@GetMapping("a1/process/bpmns")
	@ApiOperation(value="返回所有的流程定义文件",notes="返回文件名列表")
	@ResponseBody
	public String[] getBpmn() throws FileNotFoundException {
		if (ResourceUtils.isUrl("classpath:")) {
			return ResourceUtils.getFile("classpath:processes").list();
		}
		return null;
	}
	
	
	@GetMapping("procview/procId/{processId}")
	@ResponseBody
	@ApiOperation(value = "流程图信息", httpMethod = "GET", notes = "查看流程图", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public R<String> showImage2(@PathVariable(value = "processId") String processId
			,HttpServletResponse response) throws Exception {
		String deployId = "";
		ProcessInstance pi = activitProcessServiceImpl.getProcessInstance(processId);
		if(pi==null){
			deployId = activitProcessServiceImpl.getHisProcessInstance(processId).getDeploymentId();
		}else{
			deployId = pi.getDeploymentId();
		}
		if(org.springframework.util.StringUtils.isEmpty(deployId)){
			return new R<String>().fail("流程图未发现！");
		}
		try {
			// 获取流程定义processDefinition.getId()
			//ProcessDefinition processDefinition = dm.getDeploymentManager().findDeployedLatestProcessDefinitionByKey(pi.getProcessDefinitionKey());
			//流程结束时，报错
			//ProcessDefinition processDefinition = activitProcessServiceImpl.getProcessDefineWithKeyList(deployId,pi.getProcessDefinitionKey()).get(0);
			//查询历史的流图信息，即使流程结束也可以正常查看办理路径。
			ProcessDefinitionEntity entity = activitProcessServiceImpl.getProcessDefinitionEntity(processId);
			InputStream in = null;
			in = activitProcessServiceImpl.getProcessImageV2(entity.getId(), processId);
			downFile(response,in,"procName.png");
			return new R<String>().success();
		} catch (Exception e) {
			e.printStackTrace();
			return new R<String>().fail("流程图展示异常！");
		}
	}

	protected void downloadFile(String filePath) throws FileNotFoundException, IOException, Exception {
		downloadFile(new File(filePath));
	}

	protected HttpServletResponse getCurrentResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	protected void downloadFile(File file) throws FileNotFoundException, IOException, Exception {
		downFile(getCurrentResponse(), file);
	}

	public static void downFile(HttpServletResponse response, File file)
			throws FileNotFoundException, IOException, Exception {
		downFile(response,new FileInputStream(file),file.getName());
	}
	public static void fileUpload(InputStream is, OutputStream os) throws Exception{
		byte[] b = new byte[1024 * 1024 * 10];
		int length = 0;
		while (true) {
			length = is.read(b);
			if (length < 0)
				break;
			os.write(b, 0, length);
		}
		is.close();
		os.close();
   }
	public static void downFile(HttpServletResponse response, InputStream input,String fileName)
			throws FileNotFoundException, IOException, Exception {
		// response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.setCharacterEncoding("utf-8");
		fileUpload(input, response.getOutputStream());
		//FileUtils.copyF
	}
	
	@GetMapping("deploye/classpath/xml")
	@ResponseBody
	@ApiOperation(value = "部署流程", httpMethod = "GET", notes = "部署上下文的流程,带后缀")
	public String deploye(String bpmnName) throws FileNotFoundException {
		Deployment deployment = activitProcessServiceImpl.deployeInputStream(bpmnName);
		return  deployment.getId();
	}
	
	@GetMapping("deploye/abso/file")
	@ResponseBody
	@ApiOperation(value = "部署流程", httpMethod = "GET", notes = "部署流程,部署指定路径文件")
	public String deployeAbsoloute(String bpmnNamePath) throws FileNotFoundException {
		Deployment deployment = activitProcessServiceImpl.deployeInputStreamAbs(bpmnNamePath);
		return  deployment.getId();
	}
	
	@PostMapping("a1/deploye/upload/file")
	@ResponseBody
	@ApiOperation(value = "部署流程", httpMethod = "POST", notes = "部署流程，上传文件")
	public String deployeupload(MultipartFile bpmnFile) throws FileNotFoundException {
		if(bpmnFile.isEmpty()){
			return "上传文件为空！";
		}
		Deployment deployment = activitProcessServiceImpl.deployeInputStreamAbs(bpmnFile);
		return  deployment.getId();
	}
	
	@GetMapping("deploye/delete")
	@ResponseBody
	@ApiOperation(value = "级联删除部署信息", httpMethod = "GET", notes = "级联删除部署信息")
	public String deleteDeploye(String deployeId,Boolean cascade) throws FileNotFoundException {
		activitProcessServiceImpl.deleteDeploye(deployeId, cascade);
		return  "删除成功！";
	}
	
	/*@GetMapping("a1/deploye/ids")
	@ResponseBody
	@ApiOperation(notes="id_||'#'||version_||'#'||deployment_id_||'#'||key_", value = "查询部署的实例信息")
	public List<String> getDeployeeIds() {
		return activitProcessServiceImpl.getDeployeeIds();
	}*/
/*	@Autowired
	SpringContextUtil context;*/
	
	/*@GetMapping("deploye/deleteAll")
	@ResponseBody
	public R<String> deleteAll() throws FileNotFoundException {
		List<String> ids = activitProcessServiceImpl.getDeployeeIds();
		if(context.isPro()){
			return new R<String>().success("没有权限操作！");
		}else{
			for(@SuppressWarnings("unused") String id:ids){
				//activitProcessServiceImpl.deleteDeploye(id, true);
			}
			return new R<String>().success("模板清除成功！");
		}
	}*/
	
	@GetMapping("a1/startByKey/process")
	@ResponseBody
	@ApiOperation(value = "启动流程", httpMethod = "GET", notes = "根据流程key启动流程。")
	public String startProcessByKey(String bpmnName) {
		//同一个key可能会对应多个部署id和版本，启动的时候应该是根据最新的版本进行启动的？
		ProcessInstance pi = activitProcessServiceImpl.startProcessInstanceByKey(bpmnName, new HashMap<String,Object>());
		return  pi.getProcessInstanceId();
	}
	
	@GetMapping("a1/startById/process")
	@ResponseBody
	@ApiOperation(value = "启动流程", httpMethod = "GET", notes = " <流程列表 >的ID。")
	public String startProcessByKey1(String lcid) {
		//同一个key可能会对应多个部署id和版本，启动的时候应该是根据最新的版本进行启动的？
		ProcessInstance pi = activitProcessServiceImpl.startProcessInstanceById(lcid, new HashMap<String,Object>());
		return  pi.getProcessInstanceId();
	}

	@GetMapping("taskVars/{taskId}")
	@ResponseBody
	public Map<String, Object> taskVars(@PathVariable(value = "taskId") String taskId) {
		return activitProcessServiceImpl.getVariableByTaskId(taskId, true);
	}

	@GetMapping("procVars/{procId}")
	@ResponseBody
	public Map<String, Object> procVars(@PathVariable(value = "procId") String procId) {
		return activitProcessServiceImpl.getVariableByTaskId(procId, true);
	}
	
	
	@GetMapping("process/{processId}")
	@ResponseBody
	public String procTaskLastly(@PathVariable(value = "processId") String processId) {
		Task task = activitProcessServiceImpl.getTask(processId);
		return task == null ? "" : task.getId();
	}

	/**
	 * 流程当前的任务id
	 * 
	 * @param processId
	 * @return
	 */
	@GetMapping("taskId/{processId}")
	@ResponseBody
	public String getTaskIdByProcessId(@PathVariable String processId) {
		Task task = activitProcessServiceImpl.getTask(processId);
		return task == null ? "" : task.getId();
	}

	/**
	 * 查询某个任务的表单数据
	 * 
	 * @param taskId
	 * @return
	 */
	@GetMapping("task/form/{taskId}")
	@ResponseBody
	public String taskForm(@PathVariable String taskId) {
		TaskFormData tfd = activitProcessServiceImpl.getTaskFormData(taskId);
		if (tfd == null)
			return null;
		List<FormProperty> a = tfd.getFormProperties();
		StringBuilder sb = new StringBuilder();
		sb.append(tfd.getDeploymentId() + ",").append(tfd.getFormKey() + ",").append(tfd.getTask().getName() + ",");
		StringBuilder ps = new StringBuilder();
		for (FormProperty p : a) {
			ps.append(
					p.getId() + "/" + p.getName() + "/" + p.getValue() + "/" + p.getClass() + "/" + p.getType() + ",");
		}
		sb.append(ps.toString());
		return sb.toString();
	}

	/**
	 * 获取指派个单个人员的任务
	 * 
	 * @param role
	 * @return
	 */
	@GetMapping("tasks/user/{user}")
	@ResponseBody
	public List<String> tasksByRole(@PathVariable String user) {
		List<Task> tasks = activitProcessServiceImpl.getTaskListByRole(user);
		return commonWrap(tasks);
	}

	/**
	 * 获取指派给候选人执行的任务
	 * 
	 * @param role
	 * @return
	 */
	@GetMapping("tasks/candidateuser/{user}")
	@ResponseBody
	public List<String> tasksByCandiUser(@PathVariable String user) {
		List<Task> tasks = activitProcessServiceImpl.getTaskListByCandidateUser(user);
		return commonWrap(tasks);
	}

	public List<String> commonWrap(List<Task> tasks) {
		List<String> tasksStr = new ArrayList<String>();
		for (Task task : tasks) {
			tasksStr.add(task.getId() + "," + task.getName() + "," + task.getProcessInstanceId() + ","
					+ task.getDescription());
		}
		return tasksStr;
	}

	// 获取流程意见
	@GetMapping("comments/{processId}")
	@ResponseBody
	public List<Map<String, String>> comments(@PathVariable(value = "processId") String processId) {
		return activitProcessServiceImpl.findCommentByProcId(processId);
	}
	/**
	 * {
		  "procId": "f2eeef41074843cda1726b70a91723ee",
		  "taskPamater": {"a":1,"b":2}
		} 挂起的流程不能执行 Cannot complete a suspended task
	 *@Author iechenyb<br>
	 *@Desc 说点啥<br>
	 *@CreateTime 2020年5月25日 下午1:12:10
	 *@param param
	 *@return
	 */
	@Autowired
	RuntimeMapper runtimeMapper;
	
	@PostMapping(value="tasks/complete/",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public R<String> executeTask(@RequestBody CommandInfo param) {
		if(activitProcessServiceImpl.isFinished(param.getProcId())) return new R<String>().fail("流程已经结束,当前操作被中断！");
		//相邻两个节点的审核人不能为同一个人校验
		Map<String,Object> comment = runtimeMapper.newlyComment(param.getProcId());
		String preUser = "";
		if(!CollectionUtils.isEmpty(comment)){
			preUser =comment.get("USER_ID_")==null?"":comment.get("USER_ID_").toString();
		}
		System.err.println(preUser+"------>"+param.getUserName()+"---"+param.getTaskPamater());
		if(preUser.equals(param.getUserName())){
			return new R<String>().fail("相邻两节点之间的审核人不能为同一人！");
		}
		//用户传值不为空时，记录日志
		if(!StringUtils.isEmpty(param.getUserName())){
			if(StringUtils.isEmpty(param.getMessage())){ return new R<String>().fail("当用户不为空时，请填写备注！");}
			activitProcessServiceImpl.addComment(param.getProcId(), param.getMessage(), param.getUserName());
		}
		if(CollectionUtils.isEmpty(param.getTaskPamater())){
			activitProcessServiceImpl.completeByProcId(param.getProcId());
		}else{
			activitProcessServiceImpl.completeByProcId(param.getProcId(),param.getTaskPamater());
		}
		return  new R<String>("执行成功");
	}

	/**
	 * 获取指派给候选人执行的任务，同时查询多个不行！
	 * 
	 * @param role
	 * @return
	 */
	@GetMapping("tasks/candidategroups/{groups}")
	@ResponseBody
	public List<String> tasksByCandiGroups(@PathVariable String groups) {
		List<String> candidateGroups = new ArrayList<>();
		if (!StringUtils.isEmpty(groups)) {
			candidateGroups = Arrays.asList(groups);
		} else {
			return candidateGroups;
		}
		List<Task> tasks = activitProcessServiceImpl.getTaskListInGroup(candidateGroups);
		return commonWrap(tasks);
	}

	/**
	 * 单个grop查询
	 * 
	 * @param group
	 * @return
	 */
	@GetMapping("tasks/candidategroup/{group}")
	@ResponseBody
	public List<String> tasksByCandiGroup(@PathVariable String group) {
		List<Task> tasks = activitProcessServiceImpl.getTaskListByGroup(group);
		return commonWrap(tasks);
	}

	@GetMapping("/showCurrentView")
	public ModelAndView showCurrentView(String taskId, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		Task task = activitProcessServiceImpl.getTaskById(taskId);
		String processDefinitionId = task.getProcessDefinitionId(); // 获取流程定义id
		ProcessDefinition processDefinition = activitProcessServiceImpl.getProcessDefinition(taskId);
		mav.addObject("deploymentId", processDefinition.getDeploymentId()); // 部署id
		mav.addObject("diagramResourceName", processDefinition.getDiagramResourceName()); // 图片资源文件名称
		ProcessDefinitionEntity processDefinitionEntity = activitProcessServiceImpl.getProcessDefineEntity(processDefinitionId);
		String processInstanceId = task.getProcessInstanceId(); // 获取流程实例id
		ProcessInstance pi = activitProcessServiceImpl.getProcessInstance(processInstanceId);
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(pi.getActivityId()); // 根据活动id获取活动实例
		mav.addObject("x", activityImpl.getX()); // x坐标
		mav.addObject("y", activityImpl.getY()); // y坐标
		mav.addObject("width", activityImpl.getWidth()); // 宽度
		mav.addObject("height", activityImpl.getHeight()); // 高度
		mav.setViewName("page/currentView");
		return mav;
	}
	
}

