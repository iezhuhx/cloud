package com.kiiik.config.sec;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年9月3日上午10:55:25
 */
@Service
public class AuthAccessDecisionManager implements AccessDecisionManager {

	// decide 方法是判定是否拥有权限的决策方法，
	// authentication 是释CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
	// object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request =
	// ((FilterInvocation) object).getHttpRequest();
	// configAttributes 为MyInvocationSecurityMetadataSource的getAttributes(Object
	// object)这个方法返回的结果，此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide
	// 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		if (null == configAttributes || configAttributes.size() <= 0) {
			return;
		}
		ConfigAttribute c;
		String needRole;
		for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext();) {
			c = iter.next();
			needRole = c.getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {// authentication
				if (needRole.trim().equals(ga.getAuthority())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("无权限访问！");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
