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
		// ���Ͱ� �޸� ������ �� �ѹ��� ���� �ȴ�.
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//�߿�.. ��¥ ���ϴ� ���
		
		//request ����
		
		long begin=System.currentTimeMillis();
		
		chain.doFilter(req, resp);	// ���� ���� �Ǵ� ������ ���͸� JSP�� ���� ����
		
		//response ����
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
		// ���Ͱ� �޸� �Ҵ� ���� �� �ѹ��� ���� �ȴ�.
		this.config=config;
	}

}
