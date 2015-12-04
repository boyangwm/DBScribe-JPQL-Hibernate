package filiter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns="/owner/*")
public class OwnerChecker implements Filter {
	
	private ServletContext context; 
	private String targetURI; 
	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("現在在OwnerChecker filiter裡");
		HttpServletRequest httpRequest = (HttpServletRequest) request; 
        HttpServletResponse httpResponse = (HttpServletResponse) response; 
//        HttpSession session = httpRequest.getSession(false); 
        HttpSession session = httpRequest.getSession();
        request.setCharacterEncoding("UTF-8");
        httpRequest.setCharacterEncoding("UTF-8");
        if (session != null) { 
            String passed = (String) session.getAttribute("ownerLoginOK");
        if(passed!=null){
            if (passed.equals("true")) { 
                chain.doFilter(httpRequest, httpResponse); 
                return; 
            }
        }
//            else if (passed.equals("passing")) { 
//                if (new String(httpRequest.getRequestURI()) 
//                    .equals("/filter/LoginChecker")) { 
//                    chain.doFilter(httpRequest, httpResponse); 
//                    return; 
//                } 
//            }
        else { 
        	 httpRequest.setAttribute("needLogin","如欲使用本功能請先登入");
        } 

            session.removeAttribute("passed"); 
        }
        
        StringBuffer requestURL = httpRequest.getRequestURL(); 
        String query = httpRequest.getQueryString(); 
        if (query != null) 
            requestURL.append(query); 
        httpRequest.setAttribute("originalURI", new String(requestURL));
        targetURI = httpRequest.getRequestURI();
        session.setAttribute("requestURI",targetURI );
        
       
        httpRequest.getRequestDispatcher("/generalService/ownerLogin.jsp").forward(httpRequest, httpResponse); 
     
	}
	
	
	
	public void destroy() {
	}


}
