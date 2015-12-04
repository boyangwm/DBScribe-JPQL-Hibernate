<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%@ taglib prefix="s" uri="/struts-tags" %> 
<html>   
<head>   
<title> 登录页面 </title> 
<style >
	.myform {
width: 210px;
margin: 20px auto;
}

.form_field{
display: block;
width: 100%;
text-align: left;
margin: 10px auto;
verticle-align: center;
}

.form_field>div{
display: inline-block;
}


	</style>
</head>   
  <body>   
  <div align="center" >
  <img src="banner.png" width="*" height="80">
  </div>
		<div align="center"><font color="red"><s:property value="msg"></s:property></font></div>
			<s:form  action="user!login" cssClass="myform" theme="css_xhtml">
			<s:textfield  key="账号" name="uid" labelposition="left"/>
		    <s:password key="密码" name="pwd" labelposition="left"/>
		    <div class="form_field" style="text-align:center;">
			<s:submit value="登陆" align="center"/>
			<input type="button" onclick="window.location.href='register.jsp'" value="注册">
			</div>
			</s:form>
  </body>   
</html>  