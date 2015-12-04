<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%@ taglib prefix="s" uri="/struts-tags" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
table{
	width:100%;
	border-collapse:collapse;
}

table th{
	background-color:#ebebeb;
}

table, td, th
  {
  border:1px solid black;
  border-color:#ADADAD;
  }
  
input {
 	vertical-align:middle；
 }
  
</style>
</head>
<script language="javascript">
function deleteUser(){
	var arr=[];
    var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	arr.push(select[i].value);
      }
    }    
    window.location.href="user!delete?json="+arr;
}

function resetPwd(){
	var arr=[];
    var select=document.getElementsByName("select");  
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	arr.push(select[i].value);
      }
    }    
    window.location.href="user!resetPwd?json="+arr;
    alert("密码重置为：123456");
}
</script>

<body>
<s:action name="user!loadAllUser"/>
<div >
	<div >
		<div >
			 <button  type="button" onclick="window.location.href='adduser.jsp'">增加用户</button>
			 <button  type="button" onclick="resetPwd()">密码重置</button>
			 <button  type="button" onclick="deleteUser()">删除用户</button>
		</div>
	</div>
</div>
<br>
<table style="text-align:center;">
<tr>
	<th>用户账号</th><th>用户名</th><th>用户类别</th>
</tr>
<s:iterator value="#session.userlist" id="obj">
<tr> 
<td><input type="checkbox" name="select" style="float:left;" value="<s:property value="#obj.userid"/>"><s:property value="#obj.userid"/></td>
	<td><s:property value="#obj.username"></s:property></td>
	<td><s:property value="#obj.usertype"></s:property></td>
</tr>
</s:iterator>
</table>

</body>
</html>