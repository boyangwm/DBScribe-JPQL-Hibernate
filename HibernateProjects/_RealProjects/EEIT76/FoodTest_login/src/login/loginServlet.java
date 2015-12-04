package login;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.memberService;

@WebServlet(urlPatterns  = {"/loginServlet"})
public class loginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Map<String,String> errorMessage = new HashMap<>();
		request.setAttribute("ErrorMsg", errorMessage);
		
		String account = request.getParameter("userId");
		if (account==null||account.trim().length()==0){
			errorMessage.put("id","帳號請勿空白");
		}
		String password = request.getParameter("pswd");
		if (password==null||password.trim().length()==0){
			errorMessage.put("password", "密碼請勿空白");
		}
		if (!errorMessage.isEmpty()){
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
			return;
		}
		memberService MS = new memberService();
		String passkey = MS.login(account, password);
		System.out.println(account);
		System.out.println(passkey);
		System.out.println(password);
		if(passkey==null){
			System.out.println("查無此帳號");
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
			return;
		}
		else if(passkey.equals(password)){
			System.out.println("pass=password");
			request.setAttribute("account", account);
			RequestDispatcher rd = request.getRequestDispatcher("/pages/loginSucess.jsp");
			rd.forward(request, response);
			return;
		}
			else{
			//	System.out.println(account);
				request.setAttribute("acconut",account);
				System.out.println("密碼錯誤");
				errorMessage.put("password","密碼錯誤");
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
				rd.forward(request, response);
				return;
			}
		
		
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("dopost");
		this.doGet(req,resp);
		
	}

}
