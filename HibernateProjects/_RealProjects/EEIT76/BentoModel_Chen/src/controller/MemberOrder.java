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

import org.json.JSONArray;
import org.json.JSONObject;

import com.orderdetail.model.OrderDetailVO;
import com.ordersum.model.MemberOrderService;
import com.ordersum.model.OrderSumVO;
import com.ordersum.model.RestOrderService;

@WebServlet("/member/MemberOrder")
public class MemberOrder extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		
//		=============member==============
		if ("getAll_For_member".equals(action)) { // member要求秀出所有訂單
			JSONObject jsonObject = new JSONObject();
			System.out.println("member要求秀出所有訂單");
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = request.getParameter("memberAcc");
				if (str == null || (str.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "帳號錯誤");
				} else {
					String memberAcc = str;
					try {
						/*************************** 2.開始查詢資料 *****************************************/
						MemberOrderService memberSvc = new MemberOrderService();
						List<OrderSumVO> list = memberSvc.getAll(memberAcc);
						if (list.isEmpty()) {
							jsonObject.put("errorMsgs", "查無訂單");
						} else {
							/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
							JSONArray orders = this.generateJSON(list);
							jsonObject.put("orders", orders);
							System.out.println(jsonObject.toString());
							out.println(jsonObject.toString());
							return;
						}
					} catch (Exception e) {
						jsonObject.put("errorMsgs", "格式不正確");
					}
				}
				if (jsonObject.length() != 0) {
					out.print(jsonObject.toString());
				}
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				jsonObject.put("errorMsgs", "無法取得資料:" + e.getMessage());
				out.print(jsonObject.toString());
			}
		}
		
		if ("get_By_Expect".equals(action)) { // 業主要求根據取餐日期秀出訂單
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String memberAcc = request.getParameter("memberAcc");
				String temp2 = request.getParameter("term1");

					Date expectDate = null;
					
						try {
							expectDate = Date.valueOf(temp2);
							/*************************** 2.開始查詢資料 *****************************************/
							MemberOrderService memberSvc = new MemberOrderService();
							List<OrderSumVO> list = memberSvc.getByMemberExpectDate(
									memberAcc, expectDate);
							if (list.isEmpty()) {
								jsonObject.put("errorMsgs", "查無訂單");
							} else {
								/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
								JSONArray orders = this.generateJSON(list);
								jsonObject.put("orders", orders);
								out.println(jsonObject.toString());
								return;
							}
						} catch (Exception e) {
							jsonObject.put("errorMsgs", "日期格式不正確");
						}
					
				
				if (jsonObject.length() != 0) {
					out.print(jsonObject.toString());
				}
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				jsonObject.put("errorMsgs", "無法取得資料:" + e.getMessage());
				out.print(jsonObject.toString());
			}
		}
		
		
		if ("get_By_Cond".equals(action)) { // 業主要求根據訂單狀態秀出訂單
			System.out.println("getBYcond");
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String temp1 = request.getParameter("memberAcc");
				String temp2 = request.getParameter("term1");
				if (temp1 == null || (temp1.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "您尚未新增店家");
				} else if (temp2 == null || (temp2.trim()).length() == 0) {
					jsonObject.put("errorMsgs","請選擇訂單狀態");
				} else {
					Integer restID = null;
					Integer orderCondID = null;
				
						try {
							String memberAcc = temp1;
							orderCondID = Integer.valueOf(temp2);
							/*************************** 2.開始查詢資料 *****************************************/
							MemberOrderService memberSvc = new MemberOrderService();
							List<OrderSumVO> list = memberSvc.getByMemberOrderCondID(memberAcc, orderCondID);
							if (list.isEmpty()) {
								jsonObject.put("errorMsgs", "查無訂單");
							} else {
								/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
								JSONArray orders = this.generateJSON(list);
								jsonObject.put("orders", orders);
								out.println(jsonObject.toString());
								return;
							}
						} catch (Exception e) {
							jsonObject.put("errorMsgs", "訂單狀態格式不正確");
						}
					} 
				
				if (jsonObject.length() != 0) {
					out.print(jsonObject.toString());
				}
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				jsonObject.put("errorMsgs", "無法取得資料:" + e.getMessage());
				out.print(jsonObject.toString());
			}
		}
		
		if ("get_By_Name".equals(action)) { // 業主要求根據客戶姓名秀出訂單
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String temp1 = request.getParameter("memberAcc");
				String restName = request.getParameter("term1");
				if (temp1 == null || (temp1.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "帳號有誤");
				}
				 else {
					
					try {
						String MemberAcc = temp1;
							/*************************** 2.開始查詢資料 *****************************************/
							MemberOrderService memberSvc = new MemberOrderService();
							List<OrderSumVO> list = memberSvc.getByRestName(MemberAcc, restName);
							if (list.isEmpty()) {
								jsonObject.put("errorMsgs", "查無訂單");
							} else {
								/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
								JSONArray orders = this.generateJSON(list);
								jsonObject.put("orders", orders);
								out.println(jsonObject.toString());
								return;
							}
					} catch (Exception e) {
						jsonObject.put("errorMsgs", "店家格式不正確");
					}
				}
				if (jsonObject.length() != 0) {
					out.print(jsonObject.toString());
				}
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				jsonObject.put("errorMsgs", "無法取得資料:" + e.getMessage());
				out.print(jsonObject.toString());
			}
		}
		
		
		
					//		=============member==============
		
		
		
		
	}
	//將撈到的訂單資料包成資料型態為JSONArray的orders的method
	private JSONArray generateJSON(List<OrderSumVO> list) {
		JSONArray orders = new JSONArray();
		for (OrderSumVO orderSumVO : list) {
			JSONObject order = new JSONObject();
			JSONObject ordersum = new JSONObject();
			ordersum.put("OrderSumID", orderSumVO.getOrderSumID());
			ordersum.put("TotalPrice", orderSumVO.getTotalPrice());
			ordersum.put(
					"Address",
					new StringBuilder().append(orderSumVO.getCity())
							.append(orderSumVO.getArea())
							.append(orderSumVO.getAddr()).toString());
			ordersum.put(
					"MemberName",
					new StringBuilder()
							.append(orderSumVO.getRestaurantVO()
									.getRestName()).toString());
			ordersum.put("MemberPhone", orderSumVO.getRestaurantVO().getRestPhone());
			ordersum.put("ExpectDatetime",
					new StringBuilder().append(orderSumVO.getExpectDate())
							.append(" ").append(orderSumVO.getExpectTime())
							.toString());
			ordersum.put("OrderDatetime",
					new StringBuilder().append(orderSumVO.getOrderDate())
							.append(" ").append(orderSumVO.getOrderTime())
							.toString());
			ordersum.put("Memo", orderSumVO.getMemo());
			ordersum.put("MemoResponse", orderSumVO.getMemoResponse());
			ordersum.put("OrderCond", orderSumVO.getOrderCondVO()
					.getOrderCond());
			order.put("orderSum", ordersum);
			JSONArray orderdetails = new JSONArray();
			for (OrderDetailVO orderDetailVO : orderSumVO.getOrderDetails()) {
				JSONObject orderdetail = new JSONObject();
				orderdetail.put("DishName", orderDetailVO.getDishVO()
						.getDishName());
				orderdetail.put("Price", orderDetailVO.getPrice());
				orderdetail.put("Quantity", orderDetailVO.getQuantity());
				orderdetail.put("Subtotal", orderDetailVO.getSubtotal());
				orderdetails.put(orderdetail);
				order.put("orderdetails", orderdetails);
			}
			System.out.println("產生完JSON了");
			orders.put(order);
		}
		return orders;
	}
}
