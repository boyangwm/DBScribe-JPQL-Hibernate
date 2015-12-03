package com.jspdev.biyesheji;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterConfig;
public class EncodingFilter implements Filter{
    protected FilterConfig filterConfig;
    private String targetEncoding="gb2312";
    public void init(FilterConfig config) throws ServletException{
    	this.filterConfig=config;
    	this.targetEncoding=config.getInitParameter("encoding");
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
		request.setCharacterEncoding(targetEncoding);
		chain.doFilter(srequest, sresponse);
	}
     
}
