package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ad.model.ADservice;
import com.ad.model.AdVO;
import com.dish.model.DishService;
import com.dish.model.DishVO;
import com.treatcase.model.TreatCaseVO;

@WebServlet("/CheckAD")
public class CheckADServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ADservice ADSVC = new ADservice();
		String action = request.getParameter("action");
		if ("success".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			Integer RestID = new Integer(request.getParameter("RestID"));
			System.out.println(RestID);
			AdVO advo = ADSVC.getByRestID(RestID);
			AdVO advonew = ADSVC.CheckSuccess(advo);
			request.setAttribute("advo", advonew);
			RequestDispatcher successView = request.getRequestDispatcher("/manager/manager.jsp");
//			RequestDispatcher successView = request.getRequestDispatcher("/generalService/advertisement.jsp");
			successView.forward(request, response);
		}
		if ("failure".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			String TreatDetail = request.getParameter("TreatDetail");
			Integer RestID = new Integer(request.getParameter("RestID"));
			System.out.println(TreatDetail);
			System.out.println(RestID);
			AdVO advo = ADSVC.getByRestID(RestID);
			advo.setTreatDetail(TreatDetail);
			AdVO advonew = ADSVC.CheckFailure(advo);
			request.setAttribute("advo", advonew);
			RequestDispatcher successView = request.getRequestDispatcher("/manager/manager.jsp");
//			RequestDispatcher successView = request.getRequestDispatcher("/generalService/advertisement.jsp");
			successView.forward(request, response);
		}
		if ("delete".equals(action)) {
			Integer RestID = new Integer(request.getParameter("RestID"));
			AdVO advo = ADSVC.getByRestID(RestID);
			ADSVC.deleteAD(advo);
			RequestDispatcher successView = request.getRequestDispatcher("/manager/manager.jsp");
			successView.forward(request, response);
		}
	}	
}