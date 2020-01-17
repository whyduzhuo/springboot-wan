package com.duzhuo.wansystem.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author treebear
 * 
 * @since 2014年1月20日下午6:47:31
 * 
 * @description
 */
public class CustomUserFilter extends AccessControlFilter {

	/** shiro安全框架session失效后的重定向临时地址 **/
	public static String SHIRO_TEMP_REDIRECT_URL = "/base/error";

	/**
	 * Returns <code>true</code> if the request is a
	 * {@link #isLoginRequest(ServletRequest, ServletResponse) loginRequest} or
	 * if the current {@link #getSubject(ServletRequest, ServletResponse) subject} is not
	 * <code>null</code>, <code>false</code> otherwise.
	 *
	 * @return <code>true</code> if the request is a
	 *         {@link #isLoginRequest(ServletRequest, ServletResponse) loginRequest} or
	 *         if the current {@link #getSubject(ServletRequest, ServletResponse) subject}
	 *         is not <code>null</code>, <code>false</code> otherwise.
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginRequest(request, response)) {
			return true;
		} else {
			Subject subject = getSubject(request, response);
			// If principal is not null, then the user is known and should be allowed access.
			return subject.getPrincipal() != null;
		}
	}

	/**
	 * This default implementation simply calls
	 * {@link #saveRequestAndRedirectToLogin(ServletRequest, ServletResponse)
	 * saveRequestAndRedirectToLogin} and then immediately returns <code>false</code>, thereby preventing the chain from
	 * continuing so the redirect may
	 * execute.
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// saveRequestAndRedirectToLogin(request, response);
		String loginUrl = SHIRO_TEMP_REDIRECT_URL;
		WebUtils.issueRedirect(request, response, loginUrl);
		return false;
	}
}
