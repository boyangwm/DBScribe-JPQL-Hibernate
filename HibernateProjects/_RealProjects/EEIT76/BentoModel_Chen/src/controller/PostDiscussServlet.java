package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.accuse.model.AccuseHibernateDAO;
import com.accuse.model.AccuseVO;
import com.member.model.MemberVO;
import com.restaurant.model.RestaurantService;
import com.restaurant.model.RestaurantVO;
import com.restdiscuss.model.RestDiscussDAO;
import com.restdiscuss.model.RestDiscussVO;
import com.servicearea.model.ServiceAreaVO;

/**
 * Servlet implementation class PostDiscussServlet
 */
@WebServlet("/generalService/PostDiscussServlet")
public class PostDiscussServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PostDiscussServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In PostDiscussServlet");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");	
		if ("commit_discuss".equals(action)) {
			System.out.println("In commit_discuss");
			HttpSession session = request.getSession();
			JSONObject jsonObject = new JSONObject();
			JSONObject errorMessage = new JSONObject();
			MemberVO mvo = (MemberVO)session.getAttribute("memberVO");		
				
			String MemberAcc = mvo.getMemberAcc();
			String temp = request.getParameter("restID");
			Integer restID = Integer.parseInt(temp);
			
			String judgetemp =  request.getParameter("judge");
			Boolean judge = Boolean.valueOf(judgetemp);
			String discussion =  request.getParameter("discussion");
			if(discussion.equals(null)||(discussion.trim()).length()==0){
				System.out.println("In reponseDiscuss errorInput");
				errorMessage.put("errorInput","請輸入內容");
				jsonObject.put("errorMessage", errorMessage);
				out.println(jsonObject.toString());
				return;
			}
			java.util.Date date=new java.util.Date();
			java.sql.Date judgeDate=new java.sql.Date (date.getTime());
			System.out.println(MemberAcc+','+restID+','+judge+','+discussion+','+judgeDate);
			
			RestDiscussVO restDiscussVO = new RestDiscussVO();
			restDiscussVO.setRestID(restID);
			restDiscussVO.setMemberAcc(MemberAcc);
			restDiscussVO.setJudge(judge);
			restDiscussVO.setJudgeDate(judgeDate);
			restDiscussVO.setDiscussion(discussion);
			RestDiscussDAO restDiscussDAO = new RestDiscussDAO();
			restDiscussDAO.insert(restDiscussVO);
			
			RestaurantVO restaurantVO = null;
			RestaurantService restaurantService = new RestaurantService();
			restaurantVO = restaurantService.getOneRestaurant(restID);
			session.setAttribute("restaurantVO", restaurantVO);
			
//			List<RestDiscussVO> restDiscussVOs = restDiscussDAO.getByRestID(restID);	
//			JSONArray restDiscusses = this.generateJSON(restDiscussVOs);
//			jsonObject.put("restDiscusses", restDiscusses);		
//			jsonObject.put("RestDiscussID", RestDiscussID);		
			jsonObject.put("MemberAcc", MemberAcc);		
			jsonObject.put("RestID", restID);		
			jsonObject.put("Judge", judge);		
			jsonObject.put("Discussion", discussion);		
			jsonObject.put("Response", response);		
			jsonObject.put("JudgeDate", judgeDate);			
			
			
			System.out.println(jsonObject.toString());
			out.println(jsonObject.toString());
			return;
			
			//response.sendRedirect("../generalService/restaurant.jsp");
		}
		
		if ("reponseDiscuss".equals(action)) {
			System.out.println("In reponseDiscuss");
			HttpSession session = request.getSession();
			JSONObject jsonObject = new JSONObject();
			JSONObject errorMessage = new JSONObject();
			
			String temp1 = request.getParameter("restID");
			Integer restID = Integer.parseInt(temp1);
			String temp2 = request.getParameter("restDiscussID");
			Integer restDiscussID = Integer.parseInt(temp2);
			String ownerResponse = request.getParameter("response");
			if(ownerResponse.equals(null)||(ownerResponse.trim()).length()==0){
				System.out.println("In reponseDiscuss errorInput");
				errorMessage.put("errorInput","請輸入內容");
				jsonObject.put("errorMessage", errorMessage);
				out.println(jsonObject.toString());
				return;
			}
			System.out.println(restDiscussID);
			
			RestDiscussVO restDiscussVO = new RestDiscussVO();
			RestDiscussDAO restDiscussDAO = new RestDiscussDAO();
			restDiscussVO = restDiscussDAO.getByPrimaryKey(restDiscussID);
			restDiscussVO.setResponse(ownerResponse);
			restDiscussDAO.insert(restDiscussVO);
			
			RestaurantVO restaurantVO = null;
			RestaurantService restaurantService = new RestaurantService();
			restaurantVO = restaurantService.getOneRestaurant(restID);
			session.setAttribute("restaurantVO", restaurantVO);
		
			jsonObject.put("ownerResponse", ownerResponse);	
			jsonObject.put("restDiscussID", restDiscussID);	
			System.out.println(jsonObject.toString());
			out.println(jsonObject.toString());
			return;
			
		}
		
		if ("sendAccuse".equals(action)) {
			System.out.println("In sendAccuse");
			JSONObject jsonObject = new JSONObject();
			JSONObject errorMessage = new JSONObject();
			
			String temp1 = request.getParameter("restID");
			Integer restID = Integer.parseInt(temp1);
			String temp2 = request.getParameter("restDiscussID");
			Integer restDiscussID = Integer.parseInt(temp2);
			String reason = request.getParameter("reason");
			String temp3 = request.getParameter("caseID");
			Integer caseID = Integer.parseInt(temp3);
			String memberAcc = request.getParameter("memberAcc");
			if(reason.equals(null)||(reason.trim()).length()==0){
				System.out.println("In sendAccuse errorInput");
				errorMessage.put("errorInput","請輸入內容");
				jsonObject.put("errorMessage", errorMessage);
				out.println(jsonObject.toString());
				return;
			}
			System.out.println(restID+","+restDiscussID+","+reason+","+caseID+","+memberAcc);
			
			AccuseVO accuseVO = new AccuseVO();
			AccuseHibernateDAO accuseHibernateDAO =new AccuseHibernateDAO();
			RestaurantVO restVO = new RestaurantVO();
			RestaurantService restSVC = new RestaurantService();
			restVO.setRestName(restSVC.getOneRestaurantName(restID));
			restVO.setRestID(restID);
			accuseVO.setCaseID(caseID);
			accuseVO.setMemberAcc(memberAcc);
			accuseVO.setReason(reason);
			accuseVO.setRestDiscussID(restDiscussID);
			accuseVO.setRestID(restID);
			accuseVO.setRestaurantVO(restVO);
			accuseVO.setDealCond(false);
			accuseHibernateDAO.insert(accuseVO);
					
			jsonObject.put("reason", reason);	
			jsonObject.put("restDiscussID", restDiscussID);	
			System.out.println(jsonObject.toString());
			out.println(jsonObject.toString());
			return;
			
		}
	}
	
	private JSONArray generateJSON(List<RestDiscussVO> list) {
		JSONArray restDiscusses = new JSONArray();
		for (RestDiscussVO restDiscussVO : list) {
			JSONObject restDiscuss = new JSONObject();
			restDiscuss.put("RestDiscussID", restDiscussVO.getRestDiscussID());
			restDiscuss.put("MemberAcc", restDiscussVO.getMemberAcc());
			restDiscuss.put("RestID", restDiscussVO.getRestID());
			restDiscuss.put("Judge", restDiscussVO.isJudge());
			restDiscuss.put("Discussion", restDiscussVO.getDiscussion());
			restDiscuss.put("Response", restDiscussVO.getResponse());
			restDiscuss.put("JudgeDate", restDiscussVO.getJudgeDate());
			restDiscusses.put(restDiscuss);
		}
		return restDiscusses;
	}
}
