<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="BIG5"%>
<%@ page import="javax.mail.*" %>                     
<%@ page import="javax.mail.internet.*" %>            
<%@ page import="javax.activation.*" %>                
<%@ page import="java.util.*,java.io.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	String USER_NAME = "avalon781117";  // GMail user name (just the part before "@gmail.com")
	String PASSWORD  = "njpbjo4rmp"; // GMail password
	String RECIPIENT = request.getParameter("To");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<%
	String[] to = { RECIPIENT };
	String Subject =request.getParameter("Subject");
	String body =request.getParameter("Message");
	
	sendFromGMail(USER_NAME,PASSWORD,to,Subject,body);
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
        message.setText(body);
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
<title>Insert title here</title>
</head>
<body>

<h1>OK!</h1>
</body>
</html>