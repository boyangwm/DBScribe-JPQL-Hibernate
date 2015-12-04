package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DiscussServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DiscussServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if ("getOrder_By_MemberAcc".equals(action)) {
			/***********************1.這裡要處理業主在自己店家的評論頁面中按下評論會員後秀出與該會員的交易記錄的請求*************************/
		    /***********************接收restaurant.jsp的請求*************************/
			//成功後轉交給Owner/Order資料夾裡的ShowOrders.jsp(請不要用forward)
		}
		if ("getOrder_By_OrderSumID".equals(action)) {
			/***********************2.這裡要處理業主在自己店家的評論頁面中按下該評論的訂單編號秀出該交易記錄的請求*************************/
		    /***********************接收restaurant.jsp的請求*************************/
			//成功後轉交給Owner/Order資料夾裡的ShowOrders.jsp(請不要用forward)
		}
		if ("getDiscuss_For_Update".equals(action)) {
			/***********************3.這裡要處理業主在自己店家的評論頁面中輸入對評論的回應後按下"確定"的請求*************************/
		    /***********************接收restaurant.jsp的請求*************************/
			//成功後將原來業主輸入的回應轉成json字串丟回給restaurant.jsp(為了做成AJAX)
		}
	}

}
