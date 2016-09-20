package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class TimerFilter implements Filter{
	private FilterConfig config;
	
	@Override
	public void destroy() {
		// 필터가 메모리 해제될 때 한번만 실행 된다.
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//중요.. 진짜 일하는 장소
		
		//request 필터
		
		long begin=System.currentTimeMillis();
		
		chain.doFilter(req, resp);	// 다음 필터 또는 마지막 필터면 JSP나 서블릿 실행
		
		//response 필터
		long end=System.currentTimeMillis();
		String uri;
		if(req instanceof HttpServletRequest){
			HttpServletRequest request=(HttpServletRequest)req;
			uri=request.getRequestURI();
			config.getServletContext().log(uri+":"+(end-begin)+"ms");
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// 필터가 메모리 할당 받을 때 한번만 실행 된다.
		this.config=config;
	}

}
