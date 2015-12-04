<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.owner.model.*" %>
<%@ page import="com.member.model.*" %>             
<%@ page import="javax.mail.*" %>                     
<%@ page import="javax.mail.internet.*" %>            
<%@ page import="javax.activation.*" %>                
<%@ page import="java.util.*,java.io.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String USER_NAME = "eeit76no1";  // GMail user name (just the part before "@gmail.com")
	String PASSWORD  = "1on67TIEE"; // GMail password
	String RECIPIENT = request.getParameter("Email");
%>
<html>
<head>
<%
	String[] to = { RECIPIENT };
	String Subject ="密碼已更新，請確認";
%>
<%!
	void sendFromGMail(String USER_NAME, String PASSWORD, String[] to, String Subject, String body) {
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
// 		message.setText(body);         //傳純文字mail
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
%>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>請到您的信箱確認您的新密碼!</h1>
<%	
	if("Owner".equals(request.getParameter("who"))){
		OwnerVO ownervo = (OwnerVO) request.getAttribute("ownervo");
		String newpwd = ownervo.getOwnPwd();
		String body ="<h1>您的新密碼為:"+ newpwd +"</h1>"+
				"<a href='http://localhost:8081/BentoDelivery/generalService/ownerLogin.jsp?'>"+
				"http://localhost:8081/BentoDelivery/generalService/ownerLogin.jsp</a>";
		sendFromGMail(USER_NAME,PASSWORD,to,Subject,body);
	}
%>

<%	
	if("Member".equals(request.getParameter("who"))){
		MemberVO membervo = (MemberVO) request.getAttribute("membervo");
		String newpwd = membervo.getMemberPwd();
		String body ="<h1>您的新密碼為:"+ newpwd +"</h1>"+
				"<a href='http://localhost:8081/BentoDelivery/generalService/memberLogin.jsp?'>"+
				"http://localhost:8081/BentoDelivery/generalService/memberLogin.jsp</a>";
		sendFromGMail(USER_NAME,PASSWORD,to,Subject,body);
	}
%>

</body>
</html>