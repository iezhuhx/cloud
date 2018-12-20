package com.kiiik.web.system.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 *作者 : iechenyb<br>
 *类描述: 如何考虑分页<br>
 *创建时间: 2018年10月18日
 */
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.kiiik.pub.ann.KiiikCachesParam;
import com.kiiik.pub.ann.KiiikCachesParams;
import com.kiiik.pub.bean.KiiikPage;
import com.kiiik.pub.bean.PageData;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.bean.SessionUser;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.pub.contant.RedisKeyContants;
import com.kiiik.pub.controller.BaseController;
import com.kiiik.pub.mybatis.bean.ComplexCondition;
import com.kiiik.pub.mybatis.service.GenericService;
import com.kiiik.utils.ReflectionUtils;
import com.kiiik.utils.VerifyCodeUtils;
import com.kiiik.web.employee.entity.EmployeeEntity;
import com.kiiik.web.rsa.service.RsaService;
import com.kiiik.web.system.po.User;
import com.kiiik.web.system.service.impl.UserServiceImpl;
import com.kiiik.web.system.vo.PasswordVo;
import com.kiiik.web.system.vo.SystemUser;
import com.kiiik.web.system.vo.UserRole2;
import com.kiiik.web.system.vo.UserRoleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("user")
@Api(value = "用户管理模块", description = "用户基本信息操作API", tags = "UserApi")
public class UserController extends BaseController {
	Log log = LogFactory.getLog(UserController.class);

	@Autowired
	GenericService genericService;
	
    /**
     * 
     *作者 : iechenyb<br>
     *方法描述: 分页数据查询<br>
     *创建时间: 2018年11月19日
     *@param user
     *@param page
     *@return
     *@throws Exception
     */
	@GetMapping("list")
	@ApiOperation("用户列表分页查询")
	public R<PageData<User>> listUsersPage(User user,@ModelAttribute @Validated KiiikPage page) throws Exception {
		Page<User> pageUsers = userService.getUsers(user,page);
		if(page.needAll()){//当分页参数不传时传回所有记录
		    List<User> data= pageUsers.getResult();
		    ReflectionUtils.modifyListFieldValue(data, "password", KiiikContants.BLANK);
		    setUserName(data);
		    return new R<PageData<User>>(new PageData<User>(data)).success();
	   }else{
			setUserName(pageUsers.getResult());
			if(pageUsers!=null&&pageUsers.getTotal()>0){//将密码置空
				ReflectionUtils.modifyListFieldValue(pageUsers.getResult(), "password", KiiikContants.BLANK);
			}
			return new R<PageData<User>>(new PageData<User>(pageUsers,page)).success();
	   }
	}
	
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 根据职工号获取用户信息<br>
	 *创建时间: 2018年11月23日
	 *@param users
	 */
	public void setUserName(List<User> users){
	   Map<String, String> es = userService.getUserNameEmpNoMap();
	   for(User user:users){
		   if(es.get(user.getEmpNo())!=null){
			   user.setUserName(es.get(user.getEmpNo()));
		   }
	   }
	}

	@PostMapping("add")
	@ApiOperation("新增用户信息")
	public R<String> addUser(@RequestBody User user) {
		User user_tmp = null;
		user_tmp = genericService.queryDBEntitySingleComplex(User.class,
				new ComplexCondition().and().col("empno").eq(user.getEmpNo()));
		if (user_tmp == null) {
			user.setPassword(KiiikContants.DEFAULT_PASSWORD);//设置默认密码
			genericService.insertDBEntity(user);
			EmployeeEntity emp = new EmployeeEntity();
			emp.setLoginid(user.getEmpNo());
			emp = genericService.queryDBEntitySingle(emp);
			if(emp!=null){
				user.setUserName(emp.getLastname());
			}
			return new R<String>().success("用户插入成功!");
		}
		return new R<String>().fail("用户已经存在！");
	}

	@DeleteMapping("deleteByIds")
	@ApiOperation("根据主键删除用户信息")
	public R<String> delUser(@RequestParam("ids") List<Integer> ids) throws Exception {
		return userService.deleteUsers(ids);
	}

	@PutMapping("update")
	@ApiOperation(value="更新用户信息",notes="更新用户信息,不包括更新密码,职工号和用户名信息")
	public R<String> updUser(@RequestBody User user) throws Exception {
		user.setEmpNo(null);//一旦新增不可以修改，如果传递值，则置为无效
		user.setUserName(null);//用户名不能修改！
		//本接口不能更新密码
		user.setPassword(null);//密码不可以修改
		User user_tmp = null;
		user_tmp = genericService.queryDBEntitySingleComplex(User.class, new ComplexCondition().col("id")
				.notIn(user.getId()).and(new ComplexCondition().col("empno").eq(user.getEmpNo())));
		if (user_tmp != null) {
			return new R<String>().fail("修改的用户名已经存在！");
		} else {
			genericService.updateDBEntityByKey(user);
			return new R<String>().success("更新成功！更新记录数!");
		}
	}

	
	@GetMapping("getUserRoles")
	@ApiOperation("获取用户的角色信息")
	public R<List<UserRoleVo>> getUserRole(Integer userId) {
		return new R<List<UserRoleVo>>(userService.getUserRoles(userId)).success();
	}

	@Autowired
	UserServiceImpl userService;

	/**
	 * 
	 * 作者 : iechenyb<br>
	 * 方法描述: 全覆盖式存储，不用判断有无重复记录<br>
	 * 创建时间: 2017年7月15日
	 * 
	 * @param roleIds
	 * @param userId
	 * @return
	 */
	@PostMapping("saveUserRole")
	@ApiOperation("保存用户角色")
	public R<String> saveUserRole(@RequestBody UserRole2 ur) {
		if ( StringUtils.isEmpty(ur.getUserId())) {
			return new R<String>().fail("用户Id不能为空！");
		}
		userService.saveUserRoles(ur.getRoleIds(), ur.getUserId());
		return new R<String>().success("用户角色信息保存成功！！");
	}

	// -------------------------------------用户登录相关功能-------------------------------------
	@Autowired
	AuthenticationManager authenticationManager;

	@GetMapping(value = "/getImage")
	@ApiOperation("获取验证码")
	public void authImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 存入会话session
		HttpSession session = request.getSession(true);
		// 删除以前的
		session.removeAttribute("verCode");
		session.removeAttribute("codeTime");
		session.setAttribute("verCode", verifyCode.toLowerCase());
		session.setAttribute("codeTime", System.currentTimeMillis());
		// 生成图片
		int w = 100, h = 30;
		OutputStream out = response.getOutputStream();
		VerifyCodeUtils.outputImage(w, h, out, verifyCode);
	}

	@Autowired
	RsaService rsaService;
	
	@Autowired
	Environment env;
	
	@GetMapping(value = "/login")
	@ApiOperation("用户登陆，ajax请求用")
	@ResponseBody
	@KiiikCachesParams(
	  caches = { 
		@KiiikCachesParam(cacheName=RedisKeyContants.USERINFO,clazz=UserServiceImpl.class)
	  }
	)
	public R<String> loginJson(String username, String password, String code, HttpServletRequest req) throws Exception {
		if (KiiikContants.PROD.equals(env.getProperty("spring.profiles.active"))){//生产环境校验验证码
			// 校验验证码
			Object verCode = req.getSession().getAttribute(KiiikContants.VERIFY_CODE);
			if (null == verCode) {
				return new R<String>().fail("验证码已失效，请重新输入!");
			}
			String verCodeStr = verCode.toString();
		   if(verCodeStr == null || code == null || code.isEmpty() || !verCodeStr.equalsIgnoreCase(code)) 
		   {
			   return new R<String>().fail("验证码错误!");
		   }
		} 
		
		{
			if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
				return new R<String>().fail("用户名或者密码不能为空！");
			}
			Authentication request = new UsernamePasswordAuthenticationToken(username, rsaService.getPassword(password));
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			req.getSession().setAttribute(KiiikContants.SPRING_CONTEXT_KEY, SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
			userService.recordLoginStatus(getSystemUser());
			req.getSession().removeAttribute(KiiikContants.VERIFY_CODE);
			if(KiiikContants.DEFAULT_PASSWORD.equals(rsaService.getPassword(password))){//认证成功，且密码为默认值，则返回状态5
				//验证用户的密码是否为默认的值，如果是则返回标记5
				return new R<String>().needToModifyPassword("密码为默认值，请修改！");
			}else{
				return new R<String>().success("登录成功！");
			}
		}
	}

	@GetMapping(value = "/loginPage", produces = { "text/html" })
	@ApiOperation("用户登陆，页面跳转用")
	public ModelAndView login(String username, String password, HttpServletRequest req) {
		ModelAndView view = new ModelAndView();
		System.out.println("username:" + username + ",password:" + password);
		try {
			if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
				view.addObject("errmsg", "用户名或者密码不能为空！");
				view.setViewName("/user/login_page");
				return view;
			}
			Authentication request = new UsernamePasswordAuthenticationToken(username,rsaService.getPassword(password));
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			req.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
		} catch (Exception e) {
			e.printStackTrace();
			view.addObject("errmsg", "登录失败");
			if (e instanceof BadCredentialsException) {
				view.addObject("errmsg", "用户名或者密码错误！");
			}
			view.setViewName("/user/login_page");
			return view;
		}
		view.setViewName("/index");
		// 直接返回首页
		return view;
	}

	@GetMapping("/toLoginPage")
	@ApiOperation("会话失效，跳转请求！")
	public ModelAndView toLoginPage() {
		log.info("正在跳转到登录页面！");
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/login_page");
		return view;
	}

	@GetMapping("/toLogin")
	@ResponseBody
	public R<String> toLogin() {
		log.info("正在跳转到登录页面！");
		return new R<String>().sessionTimeOut("会话超时，请重新登陆！");
	}

	@ResponseBody
	@GetMapping("infor")
	@ApiOperation("用户登录信息")
	public R<Map<String,Object>> getInfor() {
		Map<String,Object> userInfor = new LinkedHashMap<>();
		SystemUser user =  this.getSystemUser();
		userInfor.put("username",user.getShowUserName());
		userInfor.put("lastLoginTime", user.getLastLonginTime());
		userInfor.put("count", user.getLoginCount());
		return new R<>(userInfor);
	}

	@GetMapping("/logoutPage")
	@ApiOperation("用户登出,页面跳转用")
	public String logoutPage(HttpServletRequest req) {
		HttpSession session = req.getSession();
		SecurityContextHolder.clearContext();
		session.removeAttribute("SPRING_SECURITY_CONTEXT");
		session.invalidate();
		// 直接返回首页
		return "/user/login_page";
	}

	@GetMapping("/logout")
	@ApiOperation("用户登出,ajax请求")
	public R<String> logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		SecurityContextHolder.clearContext();
		session.removeAttribute("SPRING_SECURITY_CONTEXT");
		session.invalidate();
		// 直接返回首页
		return new R<String>().success("登出成功！");
	}

	@PutMapping("/resetPassword")
	@ApiOperation("管理员重置密码，仅管理员使用！")
	public R<String> resetPassword(String empno) throws Exception {
		User userVal = new User();
		userVal.setPassword(KiiikContants.DEFAULT_PASSWORD);//仅仅设置值
		User userCon = new User();
		userCon.setEmpNo(empno);//仅仅设置条件
		int count = genericService.updateDBEntity(userVal,userCon);
		if(count>0){
			return new R<String>().success("密码重置成功！");
		}else{
			return new R<String>().fail("密码重置失败！");
		}
	}
	@PutMapping("/updPassword")
	@ApiOperation("修改密码，仅供普通用户使用！")//@Validated
	public R<String> modifyPassword(@RequestBody  @Validated PasswordVo passvo) throws Exception{
		if(KiiikContants.DEFAULT_PASSWORD.equals(rsaService.getPassword(passvo.getNewPassword()))){
			return new R<String>().fail("新密码不能为默认密码！");
		}
		//相同密码的加密内容不一定一样，所以需要比较明文
		if (rsaService.getPassword(passvo.getNewPassword()).equals(rsaService.getPassword(passvo.getOldPassword()))) {
			return new R<String>().fail("密码相同，无需更新！");
		}
		SessionUser su = this.getSessionUser();
		User user = new User();
		user.setEmpNo(su.getEmpNo());
		user.setPassword(rsaService.getPassword(passvo.getOldPassword()));//获取密码明文
		User dbUser = genericService.queryDBEntitySingle(user);
		if (dbUser != null) {
			user.setId(dbUser.getId());
			user.setPassword(rsaService.getPassword(passvo.getNewPassword()));
			genericService.updateDBEntityByKey(user);
		} else {
			return new R<String>().fail("旧密码不正确！");
		}
		HttpSession session = getHttpSession();
		//如果用户更新时的旧是6个1,则刷新权限
		if(KiiikContants.DEFAULT_PASSWORD.equals(rsaService.getPassword(passvo.getOldPassword()))){
			//刷新用户的权限
			session.removeAttribute(KiiikContants.SPRING_CONTEXT_KEY);
			Authentication request = new UsernamePasswordAuthenticationToken(su.getEmpNo(), 
					rsaService.getPassword(passvo.getNewPassword()));
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			session.setAttribute(KiiikContants.SPRING_CONTEXT_KEY, SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
		}
		// 直接返回首页
		return new R<String>().success("密码更新成功！");
	}
}
