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
<script language="javascript">

function cancel(){
	var arr=[];
    var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	arr.push(select[i].value);
      }
    }    
    window.location.href="reserve!cancelReserve?json="+arr;
}
</script>
</head>
<body>
<s:action name="reserve!loadByReader"></s:action>
<button  type="button" onclick="cancel()">取消预约</button>
<table style="text-align:center;" id="table">
<tr id="th" >
	<th id="th" width="25%">预约号</th><th id="th" width="25%">预约人</th><th id="th" width="25%">书名</th><th id="th" width="25%">预约时间</th>
</tr>
<s:iterator value="#session.reservelist" id="obj">
<tr> 
<td id="td"><input type="checkbox" name="select" style="float:left;" value="<s:property value="#obj.reserveid"/>"><s:property value="#obj.reserveid"/></td>
	<td id="td"><s:property value="#obj.beanreader.readerName"></s:property></td>
	<td id="td"><s:property value="#obj.beanbook.bookname"></s:property></td>
	<td id="td"><s:property value="#obj.reserveDate"></s:property></td>
</tr>
</s:iterator>
</table>
</body>
</html>