package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.UserVO;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private UserService userService;
	
	//로그인 시 세션 생성
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		UserVO uvo = new UserVO();
		uvo.setId(id);
		uvo.setPw(pw);
		
		UserVO authUser = userService.getUser(uvo);
		if(authUser==null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		System.out.println("들어온다");
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect("/");
		
		return false;
	}
}
