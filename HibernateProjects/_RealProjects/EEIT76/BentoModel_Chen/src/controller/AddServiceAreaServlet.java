package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
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
import com.owner.model.OwnerService;
import com.owner.model.OwnerVO;
import com.restaurant.model.RestaurantService;
import com.restaurant.model.RestaurantVO;
import com.servicearea.model.ServiceAreaHibernateDAO;
import com.servicearea.model.ServiceAreaVO;

/**
 * Servlet implementation class addRestaurantServlet
 */
@WebServlet("/owner/restaurant/AddServiceAreaServlet")
public class AddServiceAreaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddServiceAreaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String action = request.getParameter("action");
		if ("add".equals(action)) {
			HttpSession session = request.getSession();
			RestaurantService restaurantService = new RestaurantService();
			JSONObject jsonObject = new JSONObject();
			
			String restIDTemp = request.getParameter("restID");
			Integer restID = Integer.valueOf(restIDTemp);
			String cityNum = request.getParameter("restCity2");
			String restCity ="";
			String[] cityArray = {"台北市","基隆市","新北市","連江縣","宜蘭縣","新竹市","新竹縣","桃園縣","苗栗縣","台中市",
					"彰化縣","南投縣","嘉義市","嘉義縣","雲林縣","台南市","高雄市","澎湖縣","金門縣","屏東縣",
					"台東縣","花蓮縣"};
			if(cityNum.equals("請選擇")||(cityNum.trim()).length()==0){
				jsonObject.put("errorMessage","請選擇城市");
				out.println(jsonObject.toString());
				return;
			}else{restCity = cityArray[Integer.valueOf(cityNum)];}
			
			String restArea = request.getParameter("restArea2");
			
			ServiceAreaVO serviceAreaVO = null;
			try{
				serviceAreaVO = 
						restaurantService.addServiceArea(restID, restCity, restArea);
			}catch(Exception e){
				jsonObject.put("errorMessage","新增失敗");
				out.println(jsonObject.toString());
				return;
			}

			RestaurantVO restaurantVO = null;
			restaurantVO = restaurantService.getOneRestaurant(restID);
			
			OwnerVO ownerVO =null;
			OwnerService ownerService = new OwnerService();
			ownerVO = ownerService.getByOwnAcc(restaurantVO.getOwnAcc());
			
			session.removeAttribute("ownerVO");
			session.setAttribute("ownerVO", ownerVO);
						
			ServiceAreaHibernateDAO sadao = new ServiceAreaHibernateDAO();
			List<ServiceAreaVO> serviceAreaVOs = sadao.getByRest(restID);
			
			JSONArray serviceAreas = this.generateJSON(serviceAreaVOs);
			jsonObject.put("serviceAreas", serviceAreas);		
			System.out.println(jsonObject.toString());
			out.println(jsonObject.toString());
			return;
		}
		else if ("delete".equals(action)){
			HttpSession session = request.getSession();
			RestaurantService restaurantService = new RestaurantService();
			String restIDTemp = request.getParameter("restID");
			Integer restID = Integer.parseInt(restIDTemp);
			String serviceAreaIDTemp = request.getParameter("serviceAreaID");
			System.out.println(serviceAreaIDTemp);
			Integer serviceAreaID = Integer.parseInt(serviceAreaIDTemp);
			restaurantService.deleteServiceArea(serviceAreaID);
			
			RestaurantVO restaurantVO = null;
			restaurantVO = restaurantService.getOneRestaurant(restID);
			
			OwnerVO ownerVO =null;
			OwnerService ownerService = new OwnerService();
			ownerVO = ownerService.getByOwnAcc(restaurantVO.getOwnAcc());
			
			session.removeAttribute("OwnerVO");
			session.setAttribute("OwnerVO", ownerVO);
			
			JSONObject jsonObject = new JSONObject();
			//jsonObject.put("info","更新成功");
			
			ServiceAreaHibernateDAO sadao = new ServiceAreaHibernateDAO();
			List<ServiceAreaVO> serviceAreaVOs = sadao.getByRest(restID);
			
			JSONArray serviceAreas = this.generateJSON(serviceAreaVOs);
			jsonObject.put("serviceAreas", serviceAreas);		
			System.out.println(jsonObject.toString());
			out.println(jsonObject.toString());
			return;
			
			
//			RequestDispatcher Success = request
//					.getRequestDispatcher("/owner/restaurant/updateRestaurant.jsp");
//			Success.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request,response);
	}

	private JSONArray generateJSON(List<ServiceAreaVO> list) {
		JSONArray serviceAreas = new JSONArray();
		for (ServiceAreaVO serviceAreaVO : list) {
			JSONObject serviceArea = new JSONObject();
			serviceArea.put("City", serviceAreaVO.getCity());
			serviceArea.put("Area", serviceAreaVO.getArea());
			serviceArea.put("ServiceAreaID", serviceAreaVO.getServiceAreaID());
			serviceAreas.put(serviceArea);
		}
		return serviceAreas;
	}
}
