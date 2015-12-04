<%@page import="org.eclipse.jdt.internal.compiler.ast.ThisReference"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
<style >
	.myform {
width: 230px;
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
	
    <script type="text/javascript">
    function check(userId,userName,password,password2){
		if(userId.value == null||userId.value==""){
			alert("账号不能为空！");
			return false;
		}
		if(userName.value == null||userName.value==""){
			alert("用户名不能为空！");
			return false;
		}
		if(password.value == null||password.value==""){
			alert("密码不能为空！");
			return false;
		}
		if(password2.value == null||password2.value==""){
			alert("请确认密码！");
			return false;
		}
		if(password.value != password2.value){
			alert("两次输入密码不一致！");
			return false;
		}else{
			return true;
		}
	}
    </script>
</head>
<body>
<div align="center"><font color="red"><s:property value="msg"></s:property></font></div>
<s:form  action="user!register" cssClass="myform" theme="css_xhtml" onsubmit = "return check(uid,uname,pwd,pwd2)">
		   <s:textfield  key="账号" name="uid" labelposition="left"/>
		   <s:textfield  key="昵称" name="uname" labelposition="left"/>
		    <s:password key="密码" name="pwd" labelposition="left"/>
		    <s:password key="确认密码" name="pwd2" labelposition="left"/>
		    <s:submit value="注册" align="center"/>
		    </s:form>
</body>
</html>