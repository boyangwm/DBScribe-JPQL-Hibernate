<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ page import="javax.mail.*" %>                     
<%@ page import="javax.mail.internet.*" %>            
<%@ page import="javax.activation.*" %> 
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="com.member.model.*" %>
<%@ page import="com.randompwd.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	RandomPwdVO randompwdVO  = (RandomPwdVO) request.getAttribute("randompwdVO");  
	String USER_NAME = "EEIT76no1";  // GMail user name (just the part before "@gmail.com")
	String PASSWORD  = "1on67TIEE"; // GMail password
	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
// 	String RECIPIENT = request.getParameter("MemberEmail");
	String RECIPIENT = memberVO.getMemberEmail();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>

<%
	String[] to = { RECIPIENT };
	String Subject ="歡迎使用日食便當網";
	String q = randompwdVO.getRandomPwd(); //隨機亂數
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

</head>
<body>

<%
// 	String body ="感謝您的使用,"+ q;
	String body = 
		"<a href='http://localhost:8081/BentoDelivery/Test/testlink_Member.jsp?q="+ q +"'>" +
		"http://localhost:8081/BentoDelivery/Test/testlink_Member.jsp?q=" + q + "</a>";
	sendFromGMail(USER_NAME,PASSWORD,to,Subject,body);
%>

<h1>Hello dear ,${memberVO.memberAcc} 歡迎您成為會員 , 請至信箱確認</h1>

</body>
</html>