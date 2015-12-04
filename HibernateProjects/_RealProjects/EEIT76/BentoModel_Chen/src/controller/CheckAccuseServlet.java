package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.accuse.model.AccuseService;
import com.accuse.model.AccuseVO;
import com.restdiscuss.model.RestDiscussDAO;
import com.restdiscuss.model.RestDiscussVO;


@WebServlet("/CheckAccuse")
public class CheckAccuseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		AccuseService accuseSVC = new AccuseService();
		String action = request.getParameter("action");
		if ("不通過".equals(action)) {
			Map<String,String> errorMsgs = new HashMap<String,String>();
			request.setAttribute("errorMsgs", errorMsgs);
			Integer AccuseID = new Integer(request.getParameter("AccuseID"));
			System.out.println(AccuseID);
			
			String DealDetail = request.getParameter("DealDetail");
			System.out.println(DealDetail);
			
			if(DealDetail == null || DealDetail.trim().length() == 0){
				errorMsgs.put("DealDetail","理由請勿空白!");
			}
			
			if(!errorMsgs.isEmpty()){
//				RequestDispatcher failureView = request.getRequestDispatcher("/Test/checkAccuse.jsp");
				RequestDispatcher failureView = request.getRequestDispatcher("/manager/manager.jsp");
				failureView.forward(request, response);
				return;//程式中斷
			}
			AccuseVO accusevo = accuseSVC.getByPrimaryKey(AccuseID);
			AccuseVO accusevonew = accuseSVC.CheckSuccess(accusevo,DealDetail);
			request.setAttribute("accusevo", accusevonew);
			RequestDispatcher successView = request.getRequestDispatcher("/manager/manager.jsp");
//			RequestDispatcher successView = request.getRequestDispatcher("/Test/checkAccuse.jsp");
			successView.forward(request, response);
		}
		if ("通過".equals(action)) {
			Map<String,String> errorMsgs = new HashMap<String,String>();
			request.setAttribute("errorMsgs", errorMsgs);
			Integer AccuseID = new Integer(request.getParameter("AccuseID"));
			System.out.println(AccuseID);
			
			String DealDetail = request.getParameter("DealDetail");
			System.out.println(DealDetail);
			
			if(DealDetail == null || DealDetail.trim().length() == 0){
				errorMsgs.put("DealDetail","理由請勿空白!");
			}
			if(!errorMsgs.isEmpty()){
				RequestDispatcher failureView = request.getRequestDispatcher("/manager/manager.jsp");
//				RequestDispatcher failureView = request.getRequestDispatcher("/Test/checkAccuse.jsp");
				failureView.forward(request, response);
				return;//程式中斷
			}
			AccuseVO accusevo = accuseSVC.getByPrimaryKey(AccuseID);
			AccuseVO accusevonew = accuseSVC.CheckSuccess(accusevo,DealDetail);
			RestDiscussDAO restDiscussDAO = new RestDiscussDAO();
			RestDiscussVO restdiscussvo = restDiscussDAO.getByPrimaryKey(accusevo.getRestDiscussID());
			restdiscussvo.setDiscussion("此留言已被刪除");
			restDiscussDAO.update(restdiscussvo);
			
			request.setAttribute("accusevo", accusevonew);
			RequestDispatcher successView = request.getRequestDispatcher("/manager/manager.jsp");
//			RequestDispatcher successView = request.getRequestDispatcher("/Test/checkAccuse.jsp");
			successView.forward(request, response);
		}
	}
}
