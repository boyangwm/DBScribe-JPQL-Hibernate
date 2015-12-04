package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.ordersum.model.MemberOrderService;
import com.restaurant.model.RestaurantService;
import com.restaurant.model.RestaurantVO;
@WebServlet("/owner/restaurant/UpdateRestaurantServlet")
public class UpdateRestaurantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateRestaurantServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
//		String action = request.getParameter("action");
//		if ("update_restaurant".equals(action)) {
			HttpSession session = request.getSession();
			RestaurantService restaurantService = new RestaurantService();
			JSONObject jsonObject = new JSONObject();
			JSONObject errorMessage = new JSONObject();
			
			String restIDTemp = request.getParameter("restID");
			Integer restID = Integer.parseInt(restIDTemp);
			
			String restname = request.getParameter("restname");
			if(restname.equals(null)||(restname.trim()).length()==0){
				errorMessage.put("errorName","請輸入店名");
			}
			
			String restPhone = request.getParameter("restPhone");
			if(restPhone.equals(null)||(restPhone.trim()).length()==0){
				errorMessage.put("errorPhone","請輸入電話");
			}
			
			String restCel = request.getParameter("restCel");
			
			String cityNum = request.getParameter("restCity");
//			System.out.println("cityNum : "+cityNum);
			String restCity = "";
			String[] cityArray = {"台北市","基隆市","新北市","連江縣","宜蘭縣","新竹市","新竹縣","桃園縣","苗栗縣","台中市",
					"彰化縣","南投縣","嘉義市","嘉義縣","雲林縣","台南市","高雄市","澎湖縣","金門縣","屏東縣",
					"台東縣","花蓮縣"};
			if(cityNum.equals("請選擇")||(cityNum.trim()).length()==0){
				errorMessage.put("errorCity","請輸入城市");
			}else{restCity = cityArray[Integer.parseInt(cityNum)];}
			
			String restArea = request.getParameter("restArea");
			if(restArea.equals(null)||(restArea.trim()).length()==0){
				errorMessage.put("errorArea","請輸入區域");
			}
			
			String restAddr = request.getParameter("restAddr");
			if(restAddr.equals(null)||(restAddr.trim()).length()==0){
				errorMessage.put("errorAddr","請輸入地址");
			}
			
			Double restLatitude = 25.0;
			Double restLongitude = 121.0;
			if(!cityNum.equals("請選擇")){
				String[] LatLng = MemberOrderService.getLatLngByAddress(restCity, restArea, restAddr).split(",");
				restLatitude = Double.valueOf(LatLng[0]);
				restLongitude = Double.valueOf(LatLng[1]);
			}
			
			
			String temp_midday = request.getParameter("midday");
			Time midday = null;
			if(!temp_midday.equals(null)&&!((temp_midday.trim()).length()==0)){
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				long temp_midday2 = 0;
				try {
					temp_midday2 = sdf.parse(temp_midday).getTime();
				} catch (ParseException e2) {
					errorMessage.put("errormidday","請輸入正確時間格式");
					e2.printStackTrace();
				}			
				midday = new Time(temp_midday2);
			}
					
			
			String temp_night = request.getParameter("night");
			Time night = null;
			if(!temp_night.equals(null)&&!((temp_night.trim()).length()==0)){
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
				long temp_night2 = 0;
				try {
					temp_night2 = sdf2.parse(temp_night).getTime();
				} catch (ParseException e2) {
					errorMessage.put("errornight","請輸入正確時間格式");
					e2.printStackTrace();
				}
				night = new Time(temp_night2);	
			}	
			
			String ownacc = request.getParameter("ownacc");	
			
			if(errorMessage.length()==0){
				String[] LatLng = MemberOrderService.getLatLngByAddress(restCity, restArea, restAddr).split(",");
				restLatitude = Double.valueOf(LatLng[0]);
				restLongitude = Double.valueOf(LatLng[1]);
				RestaurantVO restaurantVO = null;
				try{
					restaurantVO = 
							restaurantService.updateRestaurant(restID,restname, restPhone, restCel, restCity, restArea, restAddr,restLatitude, restLongitude, midday, night, ownacc);
				}catch(Exception e){
					errorMessage.put("info","更新失敗");
				}
				
				session.removeAttribute("restaurantVO");
				session.setAttribute("restaurantVO", restaurantVO);
				jsonObject.put("info","更新成功");
//				System.out.println(jsonObject.toString());
				out.println(jsonObject.toString());
				return;
//				response.sendRedirect("updateRestaurant.jsp");
				
			}
			else {
				jsonObject.put("errorMessage", errorMessage);
//				System.out.println(jsonObject.toString());
				out.println(jsonObject.toString());
				return;
			}
			
	
//		}
	}

}
