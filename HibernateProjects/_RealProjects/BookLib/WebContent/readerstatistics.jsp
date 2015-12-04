<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
            <%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#div{
	float:left;
}

#table{
	width:100%;
	border-collapse:collapse;
	  border:1px solid black;
  border-color:#ADADAD;
}

#th{
	background-color:#ebebeb;
	  border:1px solid black;
  border-color:#ADADAD;
}

#td
  {
  border:1px solid black;
  border-color:#ADADAD;
  }
  
input {
 	vertical-align:middle；
 }
  
</style>
</head>
<body>
<s:action name="reader!loadByReader"/>
<table style="text-align:center;" id="table">
<tr id="th" >
	<th id="th" width="25%">读者证号</th><th id="th" width="25%">姓名</th><th id="th" width="25%">借阅数量</th><th id="th" width="25%">罚金总额</th>
</tr>
<s:iterator value="#session.readerlist" id="obj">
<tr> 
<td id="td"><s:property value="#obj.readerid"/></td>
	<td id="td"><s:property value="#obj.readerName"></s:property></td>
	<td id="td"><s:property value="#obj.count"></s:property></td>
	<td id="td"><s:property value="#obj.penalSum"></s:property></td>
</tr>
</s:iterator>
</table>
</body>
</html>