package controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.randompwd.model.RandomPwd;
import com.randompwd.model.RandomPwdSercvice;
import com.randompwd.model.RandomPwdVO;

@WebServlet("/applyMember/memberApplyServlet")
public class memberApplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		Map<String,String> errorMsgs = new HashMap<String,String>();
		request.setAttribute("errorMsgs", errorMsgs);
		MemberService MS= new MemberService();
		CheckAccount checkacc = new CheckAccount();
		String URL = request.getScheme()+"://"+request.getServerName()+":"+
				request.getServerPort()+request.getContextPath();
		System.out.println("doget");
		
		String MemberAcc = request.getParameter("account");
		if(checkacc.CheckAccount(MemberAcc)){
			errorMsgs.put("MemberAcc","此帳號重複，請換一個!");
		}
//		if(MemberAcc.equals(null)||(MemberAcc.trim()).length()==0){
//			errorMessage.put("errorAcc","請輸入帳號");
//		}
		
		String MemberPwd = request.getParameter("pwd");
		if(MemberPwd.equals(null)||(MemberPwd.trim()).length()==0){
			errorMsgs.put("errorPwd", "請輸入密碼");
		}
		
		String MemberLastName = request.getParameter("lastname");
		if(MemberLastName.equals(null)||(MemberLastName.trim()).length()==0){
			errorMsgs.put("errorLast", "請輸入姓");
		}
		
		String MemberFirstName = request.getParameter("firstname");
		if(MemberFirstName.equals(null)||(MemberFirstName.trim()).length()==0){
			errorMsgs.put("errorFirst", "請輸入名");
		}
		
		String MemberPhone = request.getParameter("phone");
//		if(MemberPhone.equals(null)||(MemberPhone.trim()).length()==0){
//			errorMessage.put("errorPhone", "�q�ܿ�J���~");
//		}
//		
		String MemberCel = request.getParameter("cell");
//		if(MemberCel.equals(null)||(MemberCel.trim()).length()==0){
//			errorMessage.put("errorPhone", "��ʹq�ܿ�J���~");
//		}
		
		String MemberEmail = request.getParameter("email");
	
		
		boolean MemberGender = Boolean.parseBoolean(request.getParameter("gender"));
		boolean MemberOpen = false;  //開通欄位預設
		
		if(!errorMsgs.isEmpty()){
			RequestDispatcher failureView = request
					.getRequestDispatcher("/index.jsp");
			failureView.forward(request, response);
			return;
		}

//		MemberVO mvo = null;
//		try{
//			mvo = MS.addMember(MemberAcc, MemberPwd, MemberLastName, MemberFirstName, 
//					MemberPhone, MemberCel, MemberEmail, MemberGender ,MemberOpen);
//		}catch(Exception e){
//			
//		}
		MemberVO mvo = new MemberVO();
		mvo.setMemberAcc(MemberAcc);
		mvo.setMemberPwd(MemberPwd);
		mvo.setMemberEmail(MemberEmail);
		mvo.setMemberFirstName(MemberFirstName);
		mvo.setMemberLastName(MemberLastName);
		mvo.setMemberPhone(MemberPhone);
		mvo.setMemberCel(MemberCel);
		mvo.setMemberGender(MemberGender);
		mvo.setMemberOpen(MemberOpen);
		
		RandomPwd randompwd = new RandomPwd();								
		RandomPwdVO randompwdVO = new RandomPwdVO(); 
		RandomPwdSercvice randompwdsvc = new RandomPwdSercvice();
		String RandomPwd = randompwd.getRandomPassword();					//取得亂數
		randompwdVO = randompwdsvc.addRandomPwdVO(MemberAcc, RandomPwd);	//存到資料庫
		//�s�W���\�A��ܮi�ܼh
		MemberVO registermember = MS.addMember(mvo);
		request.setAttribute("registermember", registermember);
		String USER_NAME = "EEIT76no1";  // GMail user name (just the part before "@gmail.com")
		String PASSWORD  = "1on67TIEE"; // GMail password
		String RECIPIENT = MemberEmail;
		String[] to = { RECIPIENT };
		String Subject ="Welcome to our system";
		String q = RandomPwd; //隨機亂數
		String body ="<h1>請點選下面連結開通本系統帳號</h1><br><a href='"+ URL +"/index.jsp?type=member&q="+ q +"'>" +
				URL + "/index.jsp?type=member&q=" + q + "</a>";
//		String body ="<a href='http://localhost:8081/BentoDelivery/index.jsp?type=member&q="+ q +"'>" +
//				"http://localhost:8081/BentoDelivery/index.jsp?type=member&q=" + q + "</a>";
		sendFromGMail(USER_NAME,PASSWORD,to,Subject,body);
		RequestDispatcher Success = request.getRequestDispatcher("/index.jsp");
		Success.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("dopost");
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
