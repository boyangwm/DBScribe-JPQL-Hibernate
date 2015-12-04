package controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ordersum.model.MemberOrderService;
import com.ordersum.model.OrderSumVO;

public class CommitOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CommitOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		if("add_Order".equals(request.getParameter("action"))) {
			
			JSONObject jsonObject = new JSONObject();
			JSONArray errorMsgs = new JSONArray();
			PrintWriter out = response.getWriter();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				
				Integer restID = Integer.valueOf(request.getParameter("restID"));
				String memberAcc = request.getParameter("memberAcc");
				String memberPhone = request.getParameter("memberPhone");
				String city = request.getParameter("city");
				String area = request.getParameter("area");
				String addr = request.getParameter("addr");
				Integer totalPrice = Integer.valueOf(request.getParameter("totalPrice"));
				
				Date expectDate = null;
				try {
					expectDate = Date.valueOf(request.getParameter("expectDate"));
				} catch (Exception e) {
					errorMsgs.put("日期格式不正確");
				}
				Time expectTime = null;
				try {
					expectTime = Time.valueOf(request.getParameter("expectTime"));
				} catch (Exception e) {
					errorMsgs.put("時間格式不正確");
				}
				if(errorMsgs.length() != 0){
					jsonObject.put("errorMsgs", errorMsgs);
					out.print(jsonObject.toString());
					return;// 程式中斷
				}
				String memo = request.getParameter("memo");
				String orderDetail = request.getParameter("orderDetail");
				/*************************** 2.開始查詢資料 *****************************************/
				MemberOrderService memberOrderService = new MemberOrderService();
				OrderSumVO orderSumVO = memberOrderService.addOrder(restID, memberAcc, memberPhone, city, area, addr, totalPrice, expectDate, expectTime, memo, orderDetail);
				if (orderSumVO.equals(null)) {
					errorMsgs.put("訂餐失敗");
					jsonObject.put("errorMsgs",errorMsgs);
					out.print(jsonObject.toString());
					out.close();
					return;// 程式中斷
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				jsonObject.put("success", "訂餐成功");
				out.print(jsonObject.toString());
				out.close();
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.put("無法成功訂餐:" + e.getMessage());
				jsonObject.put("errorMsgs", errorMsgs);
				out.print(jsonObject.toString());
				out.close();
			}
		}
	}

}
