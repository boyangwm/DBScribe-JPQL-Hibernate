package controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.randompwd.model.*;
import com.owner.model.OwnerService;
import com.owner.model.OwnerVO;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String URL = request.getScheme()+"://"+request.getServerName()+":"+
				request.getServerPort()+request.getContextPath();
		System.out.println(URL);
		System.out.println("----------------------------------------------");
//		String action = request.getParameter("action");
		OwnerService ownerSvc = new OwnerService();
		RandomPwd randompwd = new RandomPwd();
		RandomPwdSercvice randompwdsvc = new RandomPwdSercvice();
		CheckAccount checkacc = new CheckAccount();
//		if ("owner_addfirst".equals(action)) {
			Map<String,String> errorMsgs = new HashMap<String,String>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			String OwnAcc = request.getParameter("OwnAcc");
			if(checkacc.CheckAccount(OwnAcc)){
				errorMsgs.put("OwnAcc","此帳號重複，請換一個!");
			}
//			if({OwnAcc == null || OwnAcc.trim().length() == 0){
//				errorMsgs.put("OwnAcc","請輸入帳號!");
//			}
			String OwnPwd = request.getParameter("OwnPwd");
//			if(OwnPwd == null || OwnPwd.trim().length() == 0){
//				errorMsgs.put("OwnPwd","請輸入密碼!");
//			}
			String OwnEmail = request.getParameter("OwnEmail");
//			if(OwnEmail == null || OwnEmail.trim().length() == 0){
//				errorMsgs.put("OwnEmail","請輸入Email!");
//			}
			String OwnFirstName = request.getParameter("OwnFirstName");
//			if(OwnFirstName == null || OwnFirstName.trim().length() == 0){
//				errorMsgs.put("OwnFirstName","請勿空白!");
//			}
			String OwnLastName = request.getParameter("OwnLastName");
//			if(OwnLastName == null || OwnLastName.trim().length() == 0){
//				errorMsgs.put("OwnLastName","請勿空白!");
//			}
			
			Boolean OwnGender = Boolean.parseBoolean(request.getParameter("gender"));
			System.out.println(OwnGender);
			
			boolean OwnOpen = false;
			OwnerVO ownerVOtemp = new OwnerVO();
			ownerVOtemp.setOwnAcc(OwnAcc);
			ownerVOtemp.setOwnPwd(OwnPwd);
			ownerVOtemp.setOwnEmail(OwnEmail);
			ownerVOtemp.setOwnFirstName(OwnFirstName);
			ownerVOtemp.setOwnLastName(OwnLastName);
			ownerVOtemp.setOwnGender(OwnGender);
			ownerVOtemp.setOwnOpen(OwnOpen);
			if(!errorMsgs.isEmpty()){
//				request.setAttribute("ownerVO1", ownerVO1);
				RequestDispatcher failureView = request.getRequestDispatcher("/index.jsp");
//				RequestDispatcher failureView = request.getRequestDispatcher("/register/register_Owner.jsp");
				failureView.forward(request, response);
				return;//程式中斷
			}
			String RandomPwd = randompwd.getRandomPassword();
			RandomPwdVO randompwdVO = randompwdsvc.addRandomPwdVO(OwnAcc, RandomPwd);      //寄信的話再用，沒寄信單純新增別用
			OwnerVO registerowner = ownerSvc.addOwner(ownerVOtemp);   //改用傳VO物件新增
			
			String USER_NAME = "eeit76no1";  // GMail user name (just the part before "@gmail.com")
			String PASSWORD  = "1on67TIEE"; // GMail password
			String RECIPIENT = request.getParameter("OwnEmail");
			String[] to = { RECIPIENT };
			String Subject ="Welcome to our system";
//			歡迎使用日食便當網
			String q = RandomPwd;
//			String q = randompwdVO.getRandomPwd();
			String body = "<h1>請點選下面連結開通本系統帳號</h1><br><a href='"+URL+"/owner/restaurant/addRestaurant.jsp?type=owner&q="+ q + "'>" +
					URL + "/owner/restaurant/addRestaurant.jsp?type=owner&q=" + q + "</a>";
//			String body = "<a href='http://localhost:8081/BentoDelivery/owner/restaurant/addRestaurant.jsp?type=owner&q="+ q + "'>" +
//					"http://localhost:8081/BentoDelivery/owner/restaurant/addRestaurant.jsp?type=owner&q=" + q + "</a>";
			sendFromGMail(USER_NAME,PASSWORD,to,Subject,body);
			
			request.setAttribute("registerowner", registerowner);
			RequestDispatcher Success = request.getRequestDispatcher("/index.jsp");
//			RequestDispatcher Success = request.getRequestDispatcher("/register/Owner_Success.jsp"); 
			Success.forward(request, response);
//		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		this.doGet(request,response);
	}
	public void sendFromGMail(String USER_NAME, String PASSWORD, String[] to, String Subject, String body) {
		Properties props = System.getProperties();
    	String host = "smtp.gmail.com";
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.host", host);
    	props.put("mail.smtp.user", USER_NAME);
    	props.put("mail.smtp.password", PASSWORD);
    	props.put("mail.smtp.port", "587");
    	props.put("mail.smtp.auth", "true");
    	
	    Session session = Session.getDefaultInstance(props);
     	MimeMessage message = new MimeMessage(session);
     	try {
     		message.setFrom(new InternetAddress(USER_NAME));
     		InternetAddress[] toAddress = new InternetAddress[to.length];
     		// To get the array of addresses
     		for( int i = 0; i < to.length; i++ ) {
     			toAddress[i] = new InternetAddress(to[i]);
     		}
     		for( int i = 0; i < toAddress.length; i++) {
     			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
     		}
     		message.setSubject(Subject);
     		message.setContent(body, "text/html;charset=UTF-8");     //html的畫用這個
     		Transport transport = session.getTransport("smtp");
     		transport.connect(host, USER_NAME, PASSWORD);
     		transport.sendMessage(message, message.getAllRecipients());
     		transport.close();
     	}
     	catch (AddressException ae) {
     		ae.printStackTrace();
     	}
     	catch (MessagingException me) {
     		me.printStackTrace();
     	}
	}
}
