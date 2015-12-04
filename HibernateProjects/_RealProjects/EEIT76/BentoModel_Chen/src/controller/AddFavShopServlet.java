package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.favorite.model.FavoriteDAO;
import com.favorite.model.FavoriteVO;
import com.member.model.MemberVO;

@WebServlet("/member/AddFavShopServlet")
public class AddFavShopServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("memberVO");
		
		PrintWriter out = response.getWriter();
		
		String MemberAcc = mvo.getMemberAcc();
		String temp = request.getParameter("restID");
		Integer RestID = Integer.parseInt(temp);
		
		
		FavoriteDAO dao = new FavoriteDAO();
		FavoriteVO fvo = new FavoriteVO();
		
		fvo.setMemberAcc(MemberAcc);
		fvo.setRestID(RestID);
		dao.insert(fvo);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("info","已收藏此店家囉");
		System.out.println(jsonObject.toString());
		out.println(jsonObject.toString());
		return;
		
		
	
	
	}

}
