package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberVO;
import com.ordersum.model.MemberOrderService;
import com.ordersum.model.OrderSumVO;

/**
 * Servlet implementation class FindMemberOrderServlet
 */
@WebServlet("/member/FindMemberOrderServlet")
public class FindMemberOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(); 
		
		MemberOrderService mos = new MemberOrderService(); 
	    MemberVO mvo = (MemberVO) session.getAttribute("memberVO");
	    List<OrderSumVO> ordervo = mos.getAll(mvo.getMemberAcc());
	    
	    for(OrderSumVO vo:ordervo){
	    	System.out.println(vo.getMemberPhone());
	    }
	    
		session.setAttribute("order",ordervo);
		
		System.out.println("FindORder");
		
		RequestDispatcher rd = request.getRequestDispatcher("generalService/memberIndex.jsp");
		rd.forward(request, response);
		return;
	}

}
