package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.favorite.model.FavoriteDAO;
import com.favorite.model.FavoriteVO;

@WebServlet("/member/DeleteFavoriteShop")
public class DeleteFavoriteShop extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("在DELETE FAV裡");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		FavoriteVO fvo = new FavoriteVO();
		FavoriteDAO dao = new FavoriteDAO();
		
		String memberAcc = request.getParameter("memberAcc");
		String temp = request.getParameter("restID");
		
		Integer restID = Integer.parseInt(temp);
		
		fvo.setMemberAcc(memberAcc);
		fvo.setRestID(restID);
		System.out.println("準備刪FVO");
		dao.delete(fvo);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("info","刪除成功");
		System.out.println(jsonObject.toString());
		out.println(jsonObject.toString());
		return;
		
		
	}

}
