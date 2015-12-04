package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.orderdetail.model.OrderDetailVO;
import com.ordersum.model.OrderSumVO;
import com.ordersum.model.RestOrderService;

@WebServlet("/owner/restaurant/FindRestNameOrderServlet")
public class FindRestNameOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FindRestNameOrderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		
		if ("get_By_Name".equals(action)) { // 業主要求根據客戶姓名秀出訂單
			
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String temp1 = request.getParameter("restID");
				String memberLastName = request.getParameter("term1");
				String memberFirstName = request.getParameter("term2");				
				Integer restID = null;			
				restID = Integer.valueOf(temp1);
				/*************************** 2.開始查詢資料 *****************************************/
				RestOrderService restSvc = new RestOrderService();
				List<OrderSumVO> list = restSvc.getByMemberName(restID, memberLastName, memberFirstName);
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				session.setAttribute("OrderSumVOlist", list);
				response.sendRedirect("findRestNameOrder.jsp");			
		
				/*************************** 其他可能的錯誤處理 **********************************/
			
		
		}

	}
}
