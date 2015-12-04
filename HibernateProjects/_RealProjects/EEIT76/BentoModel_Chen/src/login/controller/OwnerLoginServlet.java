package login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;




import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.model.LoginService;
import login.model.OwnerLoginService;

import com.member.model.MemberVO;
import com.owner.model.OwnerVO;


@WebServlet("/OwnerLoginServlet")
public class OwnerLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Map<String, String> errorMsgMap = new HashMap<String, String>();
		request.setAttribute("ErrorMsgKey", errorMsgMap);
		String userId = request.getParameter("userId");
		String password = request.getParameter("pswd");
		String rm = request.getParameter("rememberMe");
		String requestURI = (String) session.getAttribute("requestURI");
		System.out.println(requestURI);
		if (userId == null || userId.trim().length() == 0) {
			errorMsgMap.put("AccountEmptyError", "帳號錯誤");
		}
		if (password == null || password.trim().length() == 0) {
			errorMsgMap.put("PasswordEmptyError",  "密碼錯誤");
		}
		Cookie cookieUser = null;
		Cookie cookiePassword = null;
		Cookie cookieRememberMe = null;
		
		if (rm != null) {
			cookieUser = new Cookie("user", userId);
			cookieUser.setMaxAge(30*60*60);
			cookieUser.setPath(request.getContextPath());
			cookiePassword = new Cookie("password", password);
			cookiePassword.setMaxAge(30*60*60);
			cookiePassword.setPath(request.getContextPath());
			cookieRememberMe = new Cookie("rm", "true");
			cookieRememberMe.setMaxAge(30*60*60);
			cookieRememberMe.setPath(request.getContextPath());
		} else {
			cookieUser = new Cookie("user", userId);
			cookieUser.setMaxAge(0);
			cookieUser.setPath(request.getContextPath());
			cookiePassword = new Cookie("password", password);
			cookiePassword.setMaxAge(0);
			cookiePassword.setPath(request.getContextPath());
			cookieRememberMe = new Cookie("rm", "false");
			cookieRememberMe.setMaxAge(30*60*60);
			cookieRememberMe.setPath(request.getContextPath());
		}
		response.addCookie(cookieUser);
		response.addCookie(cookiePassword);
		response.addCookie(cookieRememberMe);
		if (!errorMsgMap.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
			return;
		}
		OwnerLoginService ms;
		ms = new OwnerLoginService();
		
		OwnerVO mvo = ms.login(userId, password);
		if (mvo != null) {
			session.setAttribute("ownerVO", mvo);
			session.setAttribute("ownerLoginOK","true");
		} else {
			errorMsgMap.put("LoginError", "登入失敗");
		}
		if (errorMsgMap.isEmpty()) {
			if (requestURI != null) {
				requestURI = (requestURI.length() == 0 ? request
						.getContextPath() : requestURI);
				System.out.println(requestURI);
				response.sendRedirect(response.encodeRedirectURL(requestURI));
				return;
			} else {
				response.sendRedirect(response.encodeRedirectURL(request
						.getContextPath()));
				return;
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("generalService/ownerLogin.jsp");
			rd.forward(request, response);
			return;
		}
	
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	

}
