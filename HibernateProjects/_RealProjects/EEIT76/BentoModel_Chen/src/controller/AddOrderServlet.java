package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dish.model.DishService;
import com.dish.model.DishVO;
import com.ordersum.model.MemberOrderService;
import com.ordersum.model.OrderSumVO;
import com.restaurant.model.RestaurantVO;

public class AddOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");
		if ("getDish_For_Add".equals((String)session.getAttribute("action"))) {
			List<String> errorMsgs = new LinkedList<String>();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer restID = ((RestaurantVO)session.getAttribute("restaurantVO")).getRestID();
				/*************************** 2.開始查詢資料 *****************************************/
				DishService dishsvc = new DishService();
				List<DishVO> list = dishsvc.getAllByRestID(restID);
				if (list.isEmpty()) {
					errorMsgs.add("查無任何餐點");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = request
							.getRequestDispatcher("../../generalService/restaurant.jsp");
					failureView.forward(request, response);
					return;// 程式中斷
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				session.setAttribute("list", list);
				String url = "addOrder.jsp";
				response.sendRedirect(url);// 修改成功後,轉交addOrder.jsp
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = request
						.getRequestDispatcher("../../generalService/restaurant.jsp");
				failureView.forward(request, response);
			}
			return;
		}
	}
}
