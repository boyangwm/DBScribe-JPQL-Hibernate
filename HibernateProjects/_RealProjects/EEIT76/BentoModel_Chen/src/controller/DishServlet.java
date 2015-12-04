package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dish.model.DishService;
import com.dish.model.DishVO;
import com.servicearea.model.ServiceAreaVO;

public class DishServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			NullPointerException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			NullPointerException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		DishService DS = new DishService();

		if ("add".equals(action)) {
			System.out.println("IN add dish Servlet");
			JSONObject jsonObject = new JSONObject();
			JSONObject errorMsgs = new JSONObject();
			try {
				String dishName = request.getParameter("dishName");
				if (dishName == null || dishName.trim().length() == 0) {
					errorMsgs.put("Dishname", "請勿空白!");
				}
				System.out.println("dishName : "+dishName);
				String temp1 = request.getParameter("price");
				System.out.println("temp1 : "+temp1);
				Integer price = null;
				if (temp1.equals(0) || (temp1.trim()).length() == 0) {
					errorMsgs.put("Price", "請輸入價錢!");
				} else {
					try {
						price = Integer.valueOf(temp1);
					} catch (Exception e) {
						errorMsgs.put("Price", "價錢格式不正確!");
					}
				}
				Integer specialPrice = null;
				String temp2 = request.getParameter("specialPrice");
				System.out.println("temp2 : "+temp2);
				if (!temp2.equals("") || (temp2.trim()).length() != 0) {
					try {
						specialPrice = Integer.valueOf(temp2);
					} catch (Exception e) {
						errorMsgs.put("SpecialPrice", "價錢格式不正確!");
					}
				}
				if (price != null && specialPrice != null) {
					if (specialPrice > price) {
						errorMsgs.put("Specialprice", "價錢錯誤!!");
					}
				}
				SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
				String temp3 = request.getParameter("startDate");
				System.out.println("temp3 : "+temp3);
				java.sql.Date StartDate = null;
				if (temp3 != null && temp3.trim().length() != 0 && !temp3.equals("undefined-undefined-undefined")) {
					StartDate = java.sql.Date.valueOf(temp3.trim());
				}
				String temp4 = request.getParameter("endDate");
				System.out.println("temp4 : "+temp4);
				java.sql.Date EndDate = null;
				if (temp4 != null && temp4.trim().length() != 0 && !temp4.equals("--")) {
					EndDate = java.sql.Date.valueOf(temp4);
				}
				System.out.println("StartDate : "+StartDate+", EndDate : "+ EndDate);
				if (StartDate != null && EndDate != null) {
					if (java.sql.Date.valueOf(temp3).after(
							java.sql.Date.valueOf(temp4))
							|| java.sql.Date.valueOf(temp3).equals(
									java.sql.Date.valueOf(temp4))) {
						errorMsgs.put("EndDate", "日期錯誤!!");
					}
				}
				Integer RestID = Integer.valueOf(request.getParameter("restID"));
				System.out.println(dishName + "," + price + "," + specialPrice
						+ "," + StartDate + "," + EndDate + "," + RestID);

				if (DS.dishNameExists(dishName, RestID)) { // 判斷品名是否重複
					errorMsgs.put("Dishname", "品名重複");
				}
				if (errorMsgs.length() != 0) {
					jsonObject.put("errorMsgs", errorMsgs);
					out.print(jsonObject.toString());
					return;// 程式中斷
				}
				DishVO dishVO = DS.addDish(dishName, price, specialPrice,
						StartDate, EndDate, RestID);
				JSONObject dish = new JSONObject();
				dish.put("dishName", dishVO.getDishName());
				dish.put("price", dishVO.getPrice());
				dish.put("specialPrice", dishVO.getSpecialPrice());
				dish.put("startDate", dishVO.getStartDate());
				dish.put("endDate", dishVO.getEndDate());
				dish.put("dishID", dishVO.getDishID());
				dish.put("restID", dishVO.getRestID());
				out.println(dish.toString());
				return;
			} catch (Exception e) {
				errorMsgs.put("Exception", "無法成功新增:" + e.getMessage());
				jsonObject.put("errorMsgs", errorMsgs);
				out.print(errorMsgs.toString());
				out.close();
				return;
			}
		}	

		if ("updateDish".equals(action)) { // dish.jsp更新
			JSONObject jsonObject = new JSONObject();
			JSONObject errorMsgs = new JSONObject();
			try {
				Integer dishID = new Integer(request.getParameter("dishID"));			
				String dishName = request.getParameter("dishName");
				String temp1 = request.getParameter("price");
				Integer price = null;
				if (temp1.equals(0) || (temp1.trim()).length() == 0) {
					errorMsgs.put("Price", "請輸入價錢!");
				} else {
					try {
						price = Integer.valueOf(temp1);
					} catch (Exception e) {
						errorMsgs.put("Price", "價錢格式不正確!");
					}
				}
				Integer specialPrice = null;
				String temp2 = request.getParameter("specialPrice");
				if (!temp2.equals("") || (temp2.trim()).length() != 0) {
					try {
						specialPrice = Integer.valueOf(temp2);
					} catch (Exception e) {
						errorMsgs.put("specialPrice", "價錢格式不正確!");
					}
				}
				if (price != null && specialPrice != null) {
					if (specialPrice > price) {
						errorMsgs.put("specialPrice", "價錢錯誤!!");
					}
				}
				
				SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
				String temp3 = request.getParameter("startDate");
				java.sql.Date startDate = null;
				if (temp3 != null && temp3.trim().length() != 0) {
					startDate = java.sql.Date.valueOf(temp3.trim());
				}
				String temp4 = request.getParameter("endDate");
				java.sql.Date endDate = null;
				if (temp4 != null && temp4.trim().length() != 0) {
					endDate = java.sql.Date.valueOf(temp4);
				}
				if (startDate != null && endDate != null) {
					if (java.sql.Date.valueOf(temp3).after(
							java.sql.Date.valueOf(temp4))
							|| java.sql.Date.valueOf(temp3).equals(
									java.sql.Date.valueOf(temp4))) {
						errorMsgs.put("EndDate", "日期錯誤!!");
					}
				}
				
				System.out.println(dishID+","+dishName+","+price+","+specialPrice+","+startDate+","+endDate);
				if (errorMsgs.length() != 0) {
					jsonObject.put("errorMsgs", errorMsgs);
					out.print(jsonObject.toString());
					return;// 程式中斷
				}
				
				DishVO dishVO = new DishVO();
				dishVO.setDishID(dishID);
				dishVO.setDishName(dishName);
				dishVO.setPrice(price);
				dishVO.setSpecialPrice(specialPrice);
				dishVO.setStartDate(startDate);
				dishVO.setEndDate(endDate);
				Integer restID = Integer.valueOf(request.getParameter("restID"));
				DishService dishSvc = new DishService();
				dishVO = dishSvc.updateDish(dishID, dishName, price,
						specialPrice, startDate, endDate);
				JSONObject dish = new JSONObject();
				dish.put("dishName", dishVO.getDishName());
				dish.put("price", dishVO.getPrice());
				dish.put("specialPrice", dishVO.getSpecialPrice());
				dish.put("startDate", dishVO.getStartDate());
				dish.put("endDate", dishVO.getEndDate());
				dish.put("dishID", dishVO.getDishID());
				dish.put("restID", restID);
				out.println(dish.toString());
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.put("Exception", "無法成功修改:" + e.getMessage());
				jsonObject.put("errorMsgs", errorMsgs);
				out.print(errorMsgs.toString());
				out.close();
				return;
			}
		}

		if ("remove".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			String requestURL = request.getParameter("requestURL"); 

			String temp4 = request.getParameter("dishID");
			Integer dishID = Integer.parseInt(temp4);
			DishService dishSvc = new DishService();
			dishSvc.deleteDish(dishID);
			
			JSONObject dish = new JSONObject();
			dish.put("dishID", dishID);
			out.println(dish.toString());
			return;
			
			
			
			
		}

	}

	private JSONArray generateJSON(List<DishVO> list) {
		JSONArray dishes = new JSONArray();
		for (DishVO dishVO : list) {
			JSONObject dish = new JSONObject();
			dish.put("dishName", dishVO.getDishName());
			dish.put("price", dishVO.getPrice());
			dish.put("specialPrice", dishVO.getSpecialPrice());
			dish.put("startDate", dishVO.getStartDate());
			dish.put("endDate", dishVO.getEndDate());
			dish.put("dishID", dishVO.getDishID());
			dish.put("restID", dishVO.getRestID());
			dishes.put(dish);
		}
		return dishes;
	}

}
