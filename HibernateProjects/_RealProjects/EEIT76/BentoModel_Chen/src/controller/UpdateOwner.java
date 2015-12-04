package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.model.LoginService;
import login.model.OwnerLoginService;

import org.json.JSONObject;

import com.member.model.MemberDAO;
import com.member.model.MemberVO;
import com.ordersum.model.MemberOrderService;
import com.owner.model.OwnerDAO;
import com.owner.model.OwnerService;
import com.owner.model.OwnerVO;
import com.restaurant.model.RestaurantService;
import com.restaurant.model.RestaurantVO;
@WebServlet("/owner/UpdateOwner")
public class UpdateOwner extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateOwner() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			System.out.println("In Servlet");
			HttpSession session = request.getSession();
			OwnerVO ownerVO = (OwnerVO) session.getAttribute("ownerVO");
			String ownAcc = ownerVO.getOwnAcc();
			
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			String action = request.getParameter("action");
			
			if ("update".equals(action)){
				System.out.println("updateInfo");
				OwnerLoginService ls = new OwnerLoginService();
				OwnerVO newVO = ls.login(ownAcc, request.getParameter("ownPwd"));
				if(newVO!=null){
					newVO.setOwnEmail(request.getParameter("ownEmail"));
					newVO.setOwnLastName(request.getParameter("ownLastName"));
					newVO.setOwnFirstName(request.getParameter("ownFirstName"));
					
					OwnerDAO dao = new OwnerDAO();
					dao.update(newVO);
					
					session.removeAttribute("ownerVO");
					session.setAttribute("ownerVO", newVO);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("info","更新成功");
					System.out.println("更新成功 : "+jsonObject.toString());
					out.println(jsonObject.toString());
					return;
				}else{
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("info","更新失敗");
				System.out.println("更新失敗 : "+ jsonObject.toString());
				out.println(jsonObject.toString());
				return;
				}
			}
			
			else if("updateOwnPwd".equals(action)){
				String oldpwd = request.getParameter("oldownPwd");
				OwnerLoginService ls = new OwnerLoginService();
				OwnerVO tempvo = ls.login(ownAcc, oldpwd);
				
				if(tempvo!=null){
					if(!request.getParameter("newownPwd1").equals(request.getParameter("newownPwd2"))){
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("info","兩個新密碼並不相符");
						out.println(jsonObject.toString());
						return;
					}
					tempvo.setOwnPwd(request.getParameter("newownPwd1"));
					OwnerDAO dao = new OwnerDAO();
					dao.update(tempvo);
					
					session.removeAttribute("OwnerVO");
					session.setAttribute("OwnerVO", tempvo);
					
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("info","更新成功");
					System.out.println(jsonObject.toString());
					out.println(jsonObject.toString());
					return;
					
				}
			}
	}

}
