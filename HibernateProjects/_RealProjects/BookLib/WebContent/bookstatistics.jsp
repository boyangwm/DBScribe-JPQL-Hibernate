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
<s:action name="book!loadByBook"/>
<table style="text-align:center;" id="table">
<tr id="th" >
	<th id="th" width="30%">条码</th><th id="th" width="40%">书名</th><th id="th" width="40%">借阅次数</th>
</tr>
<s:iterator value="#session.booklist" id="obj">
<tr> 
<td id="td"><s:property value="#obj.barcode"/></td>
	<td id="td"><s:property value="#obj.bookname"></s:property></td>
	<td id="td"><s:property value="#obj.count"></s:property></td>
</tr>
</s:iterator>
</table>
</body>
</html>