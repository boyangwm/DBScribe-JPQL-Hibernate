package com.jspdev.biyesheji;

import java.io.IOException;
//import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class f_tScServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public f_tScServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws Exception 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,IOException {
        request.setCharacterEncoding("gb2312");
		response.setContentType("text/html;charset=gb2312");
//		PrintWriter out = response.getWriter();
		String snos[]=request.getParameterValues("sno");
		String cno=request.getParameter("cno");
		try{
		    for(int i=0;i<snos.length;i++){
		       Sc sc=new Sc();
			   sc=sc.getSc(snos[i], cno);
			   sc.f_tSc(snos[i], cno);
		    }
		}
		catch(Exception e){
			e.printStackTrace();
		}	
		request.setAttribute("cno",cno);
		javax.servlet.RequestDispatcher dis=request.getRequestDispatcher("/form/choseStudent.jsp");
		dis.forward(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws Exception 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
