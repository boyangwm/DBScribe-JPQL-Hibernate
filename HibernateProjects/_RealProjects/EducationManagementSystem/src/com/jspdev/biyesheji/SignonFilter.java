package com.jspdev.biyesheji;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpSession;

public class SignonFilter implements Filter{
    protected FilterConfig filterConfig;
    public void init(FilterConfig config) throws ServletException{
    	this.filterConfig=config;
    }
	public void destroy() {
		this.filterConfig=null;
		
	}
    public void setFilterConfig(final FilterConfig filterConfig){
    	this.filterConfig=filterConfig;
    }
    public void doFilter(ServletRequest srequest, ServletResponse sresponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)srequest;
		HttpServletResponse response=(HttpServletResponse)sresponse;
		HttpSession session=request.getSession();
		String isLogin="false";
		try{
			isLogin=(String)session.getAttribute("isLogin");
			if((isLogin!=null)&&isLogin.equals("true"))
				chain.doFilter(srequest, sresponse);
			else
				response.sendRedirect("../login.jsp");
		}
		catch(Exception e){}
		
	}
}
