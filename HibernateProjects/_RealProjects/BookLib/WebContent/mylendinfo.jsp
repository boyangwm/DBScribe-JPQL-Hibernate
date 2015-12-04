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
  
  
</style>
<script type="text/javascript">
    function search(){
    	var sReader=document.getElementById("reader"); 
    	if(sReader.value!=""){
    	window.location.href="lend!loadByReaderid?readerid="+sReader.value;
    	}
    }
    
    </script>
</head>
<body>
<s:action name="lend!listMyLend"></s:action>
<table style="text-align:center;" id="table">
<tr id="th" >
	<th id="th" width="20%">条码</th><th id="th" width="20%">借阅者</th><th id="th" width="20%">借阅时间</th><th id="th" width="20%">归还时间</th><th id="th" width="20%">罚金</th>
</tr>
<s:iterator value="#session.lendlist" id="obj">
<tr> 
<td id="td"><s:property value="#obj.id"/></td>
	<td id="td"><s:property value="#obj.beanreader.readerName"></s:property></td>
	<td id="td"><s:property value="#obj.lendDate"></s:property></td>
	<td id="td"><s:property value="#obj.returnDate"></s:property></td>
	<td id="td"><s:property value="#obj.penalSum"></s:property></td>
</tr>
</s:iterator>
</table>
</body>
</html>