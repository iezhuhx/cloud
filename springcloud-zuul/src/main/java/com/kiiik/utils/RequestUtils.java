package com.kiiik.utils;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.cyb.date.DateUtil;
import com.kiiik.pub.bean.SessionUser;
import com.kiiik.pub.contant.KiiikContants;
import com.kiiik.utils.req.RequestParamAnalysis;
import com.kiiik.utils.req.RequestParamAnalyst;
import com.kiiik.web.log.bean.SystemLog;
import com.kiiik.web.system.vo.SystemUser;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年9月3日下午3:05:37
 */
public class RequestUtils {
	Log log = LogFactory.getLog(RequestUtils.class);
	
	static RequestParamAnalysis analysis = new RequestParamAnalyst();
	
	public static String getProjectAbsPath(HttpServletRequest req){
		return req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+"/"+req.getContextPath();
	}
	
	public static SystemLog getSystemLog(HttpServletRequest request,Boolean parseParam)  {
		SystemLog log = new SystemLog();
		if(!StringUtils.isEmpty(request.getRemoteUser())){
			log.setOperator(request.getRemoteUser());
		}else{
			log.setOperator(KiiikContants.DEFAULT_OPERATOR_NAME);
		}
		log.setUri(request.getRequestURI());
		if(!StringUtils.isEmpty(log.getUri())){
			log.setModule(log.getUri().split("/")[1]);
		}
		if(parseParam){
			log.setParam(analysis.parseParams(request));//BodyReaderHttpServletRequestWrapper
		}
		log.setClientIp(request.getRemoteHost());
		log.setVisitorTime(DateUtil.date2long14());
		return log;
	}

	public static SessionUser getSessionUser(Authentication authentication) {
		try{
			SystemUser user = (SystemUser) authentication.getPrincipal();
			SessionUser suser = new SessionUser();
			@SuppressWarnings("unchecked")
			List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
			if (!CollectionUtils.isEmpty(roles)) {
				List<String> rs = new ArrayList<String>(roles.size());
				for (SimpleGrantedAuthority sga : roles) {
					rs.add(sga.getAuthority());
				}
				suser.setRoles(rs);
			} else {
				suser.setRoles(new ArrayList<String>());
			}
			suser.setUserId(user.getId());
			suser.setUserName(user.getUsername());
			return suser;
		}catch( Exception e){
			return new SessionUser();
		}
		
	}
	
	static AntPathMatcher matcher = new AntPathMatcher();
	/**
	 * 
	 *作者 : iechenyb<br>
	 *方法描述: 是否需要存储对应的资源访问日志<br>
	 *创建时间: 2017年7月15日hj12
	 *@param request
	 *@return
	 */
	public static boolean needSaveLog(HttpServletRequest request){
		for(String path:KiiikContants.logStaticFile){
			if(matcher.match(path, request.getRequestURI())){
				return false;
			}
		}
		return true;//需要存储
	}

	public static void initPatternList(String paths) {
		if(CollectionUtils.isEmpty(KiiikContants.logStaticFile)){
			KiiikContants.logStaticFile = new ArrayList<>();
			synchronized(KiiikContants.logStaticFile){
				String path[] = paths.split(",");
				for(int i=0;i<path.length;i++){
					KiiikContants.logStaticFile.add(path[i].trim());
				}
			}
		}
	}
	
}
