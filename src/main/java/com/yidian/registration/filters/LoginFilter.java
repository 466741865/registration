package com.yidian.registration.filters;

import com.yidian.registration.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginFilter implements Filter {
	public static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	public static final String login_page = "/login.html";

	@Autowired
	private IUserService userService;

	public void destroy(){
		
	}
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		String currentURL = request.getRequestURI();
		String ctxPath = request.getContextPath();
		//除掉项目名称时访问页面当前路径
		String targetURL = currentURL.substring(ctxPath.length());
		if (targetURL.contains("login.html")||targetURL.equals("/") || targetURL.contains("error.html")){
			filterChain.doFilter(request, response);
			return;
		}
		Cookie[] cookies = request.getCookies();
		if(null!=cookies){

//			filterChain.doFilter(request, response);

			//todo 此处无法进行验证用户有效性，需要进行修改。
			for(Cookie cookie : cookies){
				if("loginName".equals(cookie.getName())){
//					String loginName = cookie.getValue();
//					OperatorAccount operatorAccount = operatorAccoutService.getByLoginName(loginName);
//					if(Tools.isNotNull(operatorAccount)&&operatorAccount.getState().equals(OperatorStateEnum.normal.getCode())){
						filterChain.doFilter(request, response);
						return;
//					}
				}
			}
		}
		response.sendRedirect(login_page);
		return;

	}
	public void init(FilterConfig filterConfig)throws ServletException{
		
	}

}
