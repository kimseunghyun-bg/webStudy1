package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request=(HttpServletRequest)req;
		HttpSession session=request.getSession();
		String uri=request.getRequestURI();
		String []loginUrl={"/board/","/notice/created.do","/notice/created_ok.do","/notice/update.do","/notice/update_ok.do","/notice/delete.do","/member/update.do","/member/update_ok.do","/member/delete.do"};
		
		boolean login=false;
		boolean check=false;
		
		if(session!=null){
			if(session.getAttribute("member")!=null)
				login=true;
		}
		
		for(String u : loginUrl){
			if(uri.indexOf(u)!=-1){
				check=true;
				break;
			}
		}
		
		if(check && !login){
			String path="/WEB-INF/views/member/login.jsp";
			RequestDispatcher rd=request.getRequestDispatcher(path);
			rd.forward(req, resp);
			return;
		}
		
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
	
}
