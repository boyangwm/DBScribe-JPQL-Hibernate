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
			/***********************1.�o�̭n�B�z�~�D�b�ۤv���a�����׭��������U���׷|����q�X�P�ӷ|��������O�����ШD*************************/
		    /***********************����restaurant.jsp���ШD*************************/
			//���\����浹Owner/Order��Ƨ��̪�ShowOrders.jsp(�Ф��n��forward)
		}
		if ("getOrder_By_OrderSumID".equals(action)) {
			/***********************2.�o�̭n�B�z�~�D�b�ۤv���a�����׭��������U�ӵ��ת��q��s���q�X�ӥ���O�����ШD*************************/
		    /***********************����restaurant.jsp���ШD*************************/
			//���\����浹Owner/Order��Ƨ��̪�ShowOrders.jsp(�Ф��n��forward)
		}
		if ("getDiscuss_For_Update".equals(action)) {
			/***********************3.�o�̭n�B�z�~�D�b�ۤv���a�����׭�������J����ת��^������U"�T�w"���ШD*************************/
		    /***********************����restaurant.jsp���ШD*************************/
			//���\��N��ӷ~�D��J���^���নjson�r���^��restaurant.jsp(���F����AJAX)
		}
	}

}
