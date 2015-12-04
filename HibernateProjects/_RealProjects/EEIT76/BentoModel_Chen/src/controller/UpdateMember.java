package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import login.model.LoginService;

import com.member.model.MemberDAO;
import com.member.model.MemberVO;

/**
 * Servlet implementation class UpdateMember
 */
@WebServlet("/member/UpdateMember")
public class UpdateMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("memberVO");
		String acc = mvo.getMemberAcc();
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String action = request.getParameter("action");
		
		if ("updateInfo".equals(action)){
			
			mvo.setMemberFirstName(request.getParameter("firstname"));
			mvo.setMemberLastName(request.getParameter("lastname"));
			mvo.setMemberPhone(request.getParameter("phone"));
			mvo.setMemberCel(request.getParameter("cell"));
			mvo.setMemberEmail(request.getParameter("email"));
			
			MemberDAO dao = new MemberDAO();
			dao.update(mvo);
			
			session.removeAttribute("memberVO");
			session.setAttribute("memberVO", mvo);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("info","更新成功");
			System.out.println(jsonObject.toString());
			out.println(jsonObject.toString());
			return;
		}
		if("updatePwd".equals(action)){
			
			String oldpwd = request.getParameter("pwd");
			LoginService ls = new LoginService();
			MemberVO tempvo = ls.login(acc, oldpwd);
			
			if(tempvo!=null){
				if(!request.getParameter("newpwd1").equals(request.getParameter("newpwd2"))){
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("info","兩個新密碼並不相符");
					out.println(jsonObject.toString());
					return;
				}
				tempvo.setMemberPwd(request.getParameter("newpwd1"));
				MemberDAO dao = new MemberDAO();
				dao.update(tempvo);
				
				session.removeAttribute("memberVO");
				session.setAttribute("memberVO", mvo);
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("info","更新成功");
				System.out.println(jsonObject.toString());
				out.println(jsonObject.toString());
				return;
				
			}
			
		}
		
		
		
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("info","更新失敗");
		System.out.println(jsonObject.toString());
		out.println(jsonObject.toString());
		return;
		}
//		
//		
		
}



