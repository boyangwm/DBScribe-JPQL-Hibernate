package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.ordersum.model.MemberOrderService;
import com.useraddr.model.UserAddrDAO;
import com.useraddr.model.UserAddrVO;

@WebServlet("/member/UserAddrServlet")
public class UserAddrServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		MemberService meberS = new MemberService();
		String MemberAcc = request.getParameter("MemberAcc");
		String temp = request.getParameter("addrID");
		String cityTemp = request.getParameter("city");
		String Addr = request.getParameter("addr");
		String Area = request.getParameter("area");
		
		String city = null;
		
		String[] cityArray = {"台北市","基隆市","新北市","連江縣","宜蘭縣","新竹市","新竹縣","桃園縣","苗栗縣","台中市",
				"彰化縣","南投縣","嘉義市","嘉義縣","雲林縣","台南市","高雄市","澎湖縣","金門縣","屏東縣",
				"台東縣","花蓮縣"};
		try{
			 city = cityArray[Integer.parseInt(cityTemp)];
		}catch(Exception e){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("info","請重新選擇城市");
		}
		
		
		PrintWriter out = response.getWriter();
		
		UserAddrDAO dao = new UserAddrDAO();
		UserAddrVO addrvo = new UserAddrVO();
		if(temp==""){
			addrvo.setAddr(Addr);
			addrvo.setArea(Area);
			addrvo.setCity(city);
			addrvo.setMemberAcc(MemberAcc);
			String lattemp =MemberOrderService.getLatLngByAddress(city, Area, Addr);
			String[] lat = lattemp.split(",");
			addrvo.setLatitude(Double.parseDouble(lat[0]));
			addrvo.setLongitude(Double.parseDouble(lat[1]));
			dao.insert(addrvo);
			
			MemberVO mvo = meberS.getByMemAcc(MemberAcc);
			session.setAttribute("memberVO", mvo);
			session.setAttribute("memberLoginOK","true");
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("info","新增成功");
			System.out.println(jsonObject.toString());
			out.println(jsonObject.toString());
			return;
		}
		else{
			Integer addrID = Integer.valueOf(temp);
			addrvo.setUserAddrID(addrID);
			
			addrvo.setAddr(Addr);
			addrvo.setArea(Area);
			addrvo.setCity(city);
			addrvo.setMemberAcc(MemberAcc);
			String lattemp =MemberOrderService.getLatLngByAddress(city, Area, Addr);
			String[] lat = lattemp.split(",");
			addrvo.setLatitude(Double.parseDouble(lat[0]));
			addrvo.setLongitude(Double.parseDouble(lat[1]));
			
			dao.update(addrvo);
			
			
			MemberVO mvo = meberS.getByMemAcc(MemberAcc);
			session.setAttribute("memberVO", mvo);
			session.setAttribute("memberLoginOK","true");
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("info","更新成功");
			System.out.println(jsonObject.toString());
			out.println(jsonObject.toString());
			return;
		}
		
		
		
	
	
	
	
	
	
	
	
	}

}
