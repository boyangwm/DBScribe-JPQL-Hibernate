package controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.kindlist.model.KindlistDAO;
import com.kindlist.model.KindlistVO;
import com.restaurant.model.RestaurantService;
import com.restaurant.model.RestaurantVO;
import com.restkind.model.RestKindDAO;
import com.restkind.model.RestKindVO;
import com.servicearea.model.ServiceAreaVO;

public class RestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public RestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
         
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		if ("getRest_By_CityArea".equals(action)) {
			/***********************1.這裡要處理使用者選擇城巿及地區後搜尋店家的結果*************************/
		    /***********************接收index.jsp按下"搜尋店家"的請求*************************/
			//成功後轉交給GeneralService資料夾裡的searchResult.jsp(請不要用forward)
			HttpSession session = request.getSession();
			
			String countyNum = request.getParameter("county");
			countyNum =new String(countyNum.getBytes("ISO-8859-1"),"UTF-8");
			String areaNum = request.getParameter("area");
			areaNum =new String(areaNum.getBytes("ISO-8859-1"),"UTF-8");
			String[] cityArray = {"台北市","基隆市","新北市","連江縣","宜蘭縣","新竹市","新竹縣","桃園縣","苗栗縣","台中市",
					"彰化縣","南投縣","嘉義市","嘉義縣","雲林縣","台南市","高雄市","澎湖縣","金門縣","屏東縣",
					"台東縣","花蓮縣"};
			String restCity = "";
			if(countyNum.equals("請選擇")||(countyNum.trim()).length()==0){
//				RequestDispatcher failureView = request
//						.getRequestDispatcher("/index.jsp");
//				failureView.forward(request, response);
				response.sendRedirect("/BentoDelivery/index.jsp");
				return;
			}else{restCity = cityArray[Integer.parseInt(countyNum)];}
			System.out.println(restCity+","+areaNum);		
			RestaurantService restaurantService = new RestaurantService();
			List<ServiceAreaVO> list = restaurantService.getByArea(restCity,areaNum);		
			session.setAttribute("serviceAreaVOlist", list);
			session.setAttribute("restCityNow", restCity);
			session.setAttribute("areaNumNow", areaNum);
			
			
			
			response.sendRedirect("searchResult.jsp");
			
		}	
		if ("getRest_For_Display".equals(action)) {
		    /***********************2.這裡要處理使用者在店家列表頁面點下某一店家的請求*************************/
		    /***********************接收GeneralService資料夾裡的searchResult.jsp按下"進入店家"的請求*************************/
			//成功後轉交給GeneralService資料夾裡的restaurant.jsp(請不要用forward)
			HttpSession session = request.getSession();
			String restID = request.getParameter("restID");
			RestaurantService restaurantService = new RestaurantService();
			RestaurantVO restaurantVO = restaurantService.getOneRestaurant(Integer.parseInt(restID));
			session.setAttribute("restaurantVO", restaurantVO);
			request.setAttribute("restID", restID);
			response.sendRedirect("restaurant.jsp");
		}
		if ("getRest_By_Restkind".equals(action)) {
			/***********************3.這裡要處理使用者選擇種類後搜尋店家的結果*************************/
		    /***********************接收searchResult.jsp按下"送出"的請求*************************/
			//成功後轉交給GeneralService資料夾裡的searchResult.jsp(請不要用forward)
			HttpSession session = request.getSession();
			
			Map<String,String> errorMessage = new HashMap<String,String>();
			request.setAttribute("errorMsgs", errorMessage);
			
			
			
			String checkedkinds = request.getParameter("kind");
			System.out.println("-----------------");
			System.out.println("checkedkind :" +checkedkinds);
			System.out.println("-----------------");
			String[] kindArray = checkedkinds.split(",");
			for(String kind:kindArray){
				System.out.println(kind);
			}
			
			JSONObject jsonObject = new JSONObject();
			JSONArray checkedkindlist = this.generateJSON(kindArray);
			jsonObject.put("checkedkindlist", checkedkindlist);		
			System.out.println(jsonObject.toString());
			out.println(jsonObject.toString());
			return;
//			if(kindid==null){
//				System.out.println("輸入error發生");
//				errorMessage.put("errorKind","請選擇種類");
//				RequestDispatcher failureView = request
//						.getRequestDispatcher("searchResult.jsp");
//				failureView.forward(request, response);
//				return;//程式中斷
//			}
//			
//			RestaurantService restaurantService = new RestaurantService();
//			List<RestKindVO> RestKindVOs =  restaurantService.getByKind(Integer.parseInt(kindid));
//			for(RestKindVO restKindVO:RestKindVOs){
//				System.out.println(restKindVO.getRestaurantVO().getRestName());
//			}
//				
//			
//			session.setAttribute("RestKindVOs", RestKindVOs);
//			session.setAttribute("searchByKind", true);
//			session.setAttribute("kindidNow", kindid);
//			response.sendRedirect("searchResult.jsp");
			
		}
	}
	
	private JSONArray generateJSON(String[] list) {
		JSONArray checkedkindlist = new JSONArray();
		for (String string : list) {
			JSONObject checkedkind = new JSONObject();
			KindlistDAO kindlistDAO = new KindlistDAO();
			if(string!=""){
				KindlistVO kindlistVO = kindlistDAO.getByPrimaryKey(Integer.parseInt(string));
				checkedkind.put("KindName", kindlistVO.getKindName());
			}else{checkedkind.put("KindName", "");}
			checkedkindlist.put(checkedkind);
		}
		return checkedkindlist;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}