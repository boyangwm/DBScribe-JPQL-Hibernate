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

import com.restaurant.model.RestaurantService;
import com.restaurant.model.RestaurantVO;
import com.restkind.model.RestKindDAO;
import com.restkind.model.RestKindVO;
import com.servicearea.model.ServiceAreaHibernateDAO;
import com.servicearea.model.ServiceAreaVO;

/**
 * Servlet implementation class addRestaurantServlet
 */
@WebServlet("/owner/restaurant/AddRestKindServlet")
public class AddRestKindServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddRestKindServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		RestaurantService restaurantService = new RestaurantService();
		RestKindDAO restKindDAO = new RestKindDAO();
		
		String restidtemp = request.getParameter("restID");
		int restid = Integer.parseInt(restidtemp);
		System.out.println(restid);
		List<RestKindVO> rks = restKindDAO.getByRest(restid);
		for(RestKindVO rk:rks){
			System.out.println(rk.getKindID() +","+rk.getRestID());
		restaurantService.deleteRestKind(rk.getKindID(),rk.getRestID());
		}
		
		String kinds = request.getParameter("kind");
		String[] kindArray = kinds.split(",");
		for(String kind:kindArray){
			System.out.println(kind);
		}
		
		RestKindVO restKindVO = null;
		try{
			for(String s:kindArray )
			{
				restKindVO = restaurantService.addRestKind(restid, Integer.parseInt(s));
			}
		}catch(Exception e){
			//���~�B�z
		}
			
		rks = restKindDAO.getByRest(restid);
		session.setAttribute("RestKindVOs", rks);
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray serviceAreas = this.generateJSON(rks);
		jsonObject.put("serviceAreas", serviceAreas);		
		System.out.println(jsonObject.toString());
		out.println(jsonObject.toString());
		return;
		
//		response.sendRedirect("addRestKind.jsp");
//		RequestDispatcher Success = request
//				.getRequestDispatcher("/owner/restaurant/updateRestaurant.jsp");
//		Success.forward(request, response);
	}
	
	private JSONArray generateJSON(List<RestKindVO> list) {
		JSONArray serviceAreas = new JSONArray();
		for (RestKindVO restKindVO : list) {
			JSONObject serviceArea = new JSONObject();
			serviceArea.put("KindName", restKindVO.getKindlistVO().getKindName());
			serviceAreas.put(serviceArea);
		}
		return serviceAreas;
	}
}
