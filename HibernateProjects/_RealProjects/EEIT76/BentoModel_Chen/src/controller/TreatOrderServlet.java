package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.accuse.model.AccuseHibernateDAO;
import com.accuse.model.AccuseVO;
import com.orderdetail.model.OrderDetailVO;
import com.ordersum.model.MemberOrderService;
import com.ordersum.model.OrderSumVO;
import com.ordersum.model.RestOrderService;
import com.restaurant.model.RestaurantService;
import com.restaurant.model.RestaurantVO;

public class TreatOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TreatOrderServlet() {
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
		PrintWriter out = response.getWriter();
		

		
		
		
		if ("getAll_For_Display".equals(action)) { // 業主要求秀出所有訂單
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = request.getParameter("restID");
				if (str == null || (str.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "您尚未新增店家");
				} else {
					Integer restID = null;
					try {
						restID = Integer.valueOf(str);
						/*************************** 2.開始查詢資料 *****************************************/
						RestOrderService restSvc = new RestOrderService();
						List<OrderSumVO> list = restSvc.getAll(restID);
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
		if ("get_By_Expect".equals(action)) { // 業主要求根據取餐日期秀出訂單
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String temp1 = request.getParameter("restID");
				String temp2 = request.getParameter("term1");
				if (temp1 == null || (temp1.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "您尚未新增店家");
				} else if (temp2.equals("--")) {
					jsonObject.put("errorMsgs","請選擇日期");
				} else {
					Integer restID = null;
					Date expectDate = null;
					try {
						restID = Integer.valueOf(temp1);
						try {
							expectDate = Date.valueOf(temp2);
							/*************************** 2.開始查詢資料 *****************************************/
							RestOrderService restSvc = new RestOrderService();
							List<OrderSumVO> list = restSvc.getByExpectDate(
									restID, expectDate);
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
		if ("get_By_Cond".equals(action)) { // 業主要求根據訂單狀態秀出訂單
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String temp1 = request.getParameter("restID");
				String temp2 = request.getParameter("term1");
				if (temp1 == null || (temp1.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "您尚未新增店家");
				} else if (temp2 == null || (temp2.trim()).length() == 0) {
					jsonObject.put("errorMsgs","請選擇訂單狀態");
				} else {
					Integer restID = null;
					Integer orderCondID = null;
					try {
						restID = Integer.valueOf(temp1);
						try {
							orderCondID = Integer.valueOf(temp2);
							/*************************** 2.開始查詢資料 *****************************************/
							RestOrderService restSvc = new RestOrderService();
							List<OrderSumVO> list = restSvc.getByOrderCond(restID, orderCondID);
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
		if ("get_By_Name".equals(action)) { // 業主要求根據客戶姓名秀出訂單
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String temp1 = request.getParameter("restID");
				String memberLastName = request.getParameter("term1");
				String memberFirstName = request.getParameter("term2");
				if (temp1 == null || (temp1.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "您尚未新增店家");
				} else if (memberLastName == null || (memberLastName.trim()).length() == 0 || memberFirstName == null || (memberFirstName.trim()).length() == 0) {
					jsonObject.put("errorMsgs","請輸入客戶姓名");
				} else {
					Integer restID = null;
					try {
						restID = Integer.valueOf(temp1);
							/*************************** 2.開始查詢資料 *****************************************/
							RestOrderService restSvc = new RestOrderService();
							List<OrderSumVO> list = restSvc.getByMemberName(restID, memberLastName, memberFirstName);
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
		if ("get_By_Phone".equals(action)) { // 業主要求根據客戶電話秀出訂單
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String temp1 = request.getParameter("restID");
				String memberPhone = request.getParameter("term1");
				if (temp1 == null || (temp1.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "您尚未新增店家");
				} else if (memberPhone == null || (memberPhone.trim()).length() == 0) {
					jsonObject.put("errorMsgs","請輸入客戶電話");
				} else {
					Integer restID = null;
					try {
						restID = Integer.valueOf(temp1);
							/*************************** 2.開始查詢資料 *****************************************/
							RestOrderService restSvc = new RestOrderService();
							List<OrderSumVO> list = restSvc.getByMemberPhone(restID, memberPhone);
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
		if ("update_By_Cond".equals(action)) { // 業主要求更新訂單狀態
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String temp1 = request.getParameter("restID");
				String temp2 = request.getParameter("orderSumID");
				String temp3 = request.getParameter("orderCondID");
				if (temp1 == null || (temp1.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "您尚未新增店家");
				} else if (temp3 == null || (temp3.trim()).length() == 0) {
					jsonObject.put("errorMsgs","請選擇訂單狀態");
				} else {
					Integer restID = null;
					Integer orderCondID = null;
					JSONArray orderSumID = null;
					try {
						restID = Integer.valueOf(temp1);
						try {
							orderCondID = Integer.valueOf(temp3);
							try {
								orderSumID = new JSONArray(temp2);
								/*************************** 2.開始查詢資料 *****************************************/
								RestOrderService restSvc = new RestOrderService();
								String cond = null;
								for (int i = 0; i < orderSumID.length(); i++) {
									OrderSumVO orderSumVO = restSvc.updateOrderCond(String.valueOf(orderSumID.get(i)), orderCondID);
									cond = orderSumVO.getOrderCondVO().getOrderCond();
								}
								jsonObject.put("orderCond", cond);
									/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
								out.println(jsonObject.toString());
								return;
							} catch (Exception e) {
								jsonObject.put("errorMsgs", "請指定訂單編號");
							}
						} catch (Exception e) {
							jsonObject.put("errorMsgs", "訂單狀態格式不正確");
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
				jsonObject.put("errorMsgs", "更新資料失敗:" + e.getMessage());
				out.print(jsonObject.toString());
			}
		}
		if ("update_By_MemoRes".equals(action)) { // 業主要求更新回覆
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String temp1 = request.getParameter("restID");
				String temp2 = request.getParameter("orderSumID");
				String memoResponse = request.getParameter("memoResponse");
				if (temp1 == null || (temp1.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "您尚未新增店家");
				} else if (memoResponse == null || (memoResponse.trim()).length() == 0) {
					jsonObject.put("errorMsgs","請輸入文字");
				} else {
					Integer restID = null;
					JSONArray orderSumID = null;
					try {
						restID = Integer.valueOf(temp1);
						try {
							orderSumID = new JSONArray(temp2);
							/*************************** 2.開始查詢資料 *****************************************/
							RestOrderService restSvc = new RestOrderService();
							for (int i = 0; i < orderSumID.length(); i++) {
								OrderSumVO orderSumVO = restSvc.updateMemoRes(String.valueOf(orderSumID.get(i)), memoResponse);
								memoResponse = orderSumVO.getMemoResponse();
							}
							/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
							jsonObject.put("memoResponse", memoResponse);
							out.println(jsonObject.toString());
							return;
						} catch (Exception e) {
							jsonObject.put("errorMsgs", "請指定訂單編號");
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
				jsonObject.put("errorMsgs", "更新資料失敗:" + e.getMessage());
				out.print(jsonObject.toString());
			}
		}
		if ("insertAccuseByOrderSumID".equals(action)) { // 業主要求檢舉
			JSONObject jsonObject = new JSONObject();
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String temp1 = request.getParameter("restID");
				String temp2 = request.getParameter("orderSumID");
				String temp3 = request.getParameter("memberAcc");
				String reason = request.getParameter("reason");
				if (temp1 == null || (temp1.trim()).length() == 0) {
					jsonObject.put("errorMsgs", "您尚未新增店家");
				} else if (reason == null || (reason.trim()).length() == 0) {
					jsonObject.put("errorMsgs","請輸入文字");
				} else {
					Integer restID = null;
					JSONArray orderSumID = null;
					JSONArray memberAcc = null;
					try {
						restID = Integer.valueOf(temp1);
						try {
							orderSumID = new JSONArray(temp2);
							memberAcc = new JSONArray(temp3);
							/*************************** 2.開始查詢資料 *****************************************/
							JSONArray array = new JSONArray();
							for (int i = 0; i < orderSumID.length(); i++) {
								AccuseVO accuseVO = new AccuseVO();
								AccuseHibernateDAO accuseHibernateDAO =new AccuseHibernateDAO();
								RestaurantVO restVO = new RestaurantVO();
								RestaurantService restSVC = new RestaurantService();
								restVO.setRestName(restSVC.getOneRestaurantName(restID));
								restVO.setRestID(restID);
								accuseVO.setCaseID(2);
								accuseVO.setMemberAcc(String.valueOf(memberAcc.get(i)));
								accuseVO.setReason(reason);
								accuseVO.setOrderSumID(String.valueOf(orderSumID.get(i)));
								accuseVO.setRestID(restID);
								accuseVO.setRestaurantVO(restVO);
								accuseVO.setDealCond(false);
								accuseHibernateDAO.insert(accuseVO);
								accuseVO = accuseHibernateDAO.getByPrimaryKey(accuseVO.getAccuseID());
								JSONObject accuse = new JSONObject();
								accuse.put("accuseID", accuseVO.getAccuseID());
								accuse.put("OSRDID", accuseVO.getOrderSumID());
								accuse.put("memberAcc", accuseVO.getMemberAcc());
								accuse.put("reason", accuseVO.getReason());
								accuse.put("dealCond", accuseVO.getDealCond());
								accuse.put("dealDetail", accuseVO.getDealDetail());
								array.put(accuse);
							}
							/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
							jsonObject.put("success", array);
							out.println(jsonObject.toString());
							return;
						} catch (Exception e) {
							jsonObject.put("errorMsgs", "請指定訂單編號");
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
				jsonObject.put("errorMsgs", "檢舉失敗:" + e.getMessage());
				out.print(jsonObject.toString());
			}
		}
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
					"Latitude", orderSumVO.getLatitude());
			ordersum.put(
					"Longitude", orderSumVO.getLongitude());
			ordersum.put("MemberAcc", orderSumVO.getMemberVO().getMemberAcc());
			ordersum.put(
					"MemberName",
					new StringBuilder()
							.append(orderSumVO.getMemberVO()
									.getMemberLastName())
							.append(orderSumVO.getMemberVO()
									.getMemberFirstName()).toString());
			ordersum.put("MemberPhone", orderSumVO.getMemberPhone());
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
			orders.put(order);
		}
		return orders;
	}
}
