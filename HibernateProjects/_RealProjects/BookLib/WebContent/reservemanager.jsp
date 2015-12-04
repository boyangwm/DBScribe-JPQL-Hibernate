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
    	var sBook=document.getElementById("book"); 
    	if(sBook.value!=""){
    	window.location.href="reserve!loadByBook?bookid="+sBook.value;
    	}
    }
    
    function check(bookid){
    	if(bookid.value == null||bookid.value==""){
			alert("图书不能为空！");
			return false;
		}
    
	}
    </script>
</head>
<body>
<form action="reserve!add" onsubmit="return check(bookid)">
<div>
		    图书：<s:textfield  id="book" name="bookid" onkeyup=" search()" id="book" value="%{#request.bookidvalue}"/>
		    <font color="black"><s:property value="book"></s:property></font>
		    <font color="red"><s:property value="state"></s:property></font>
			<input type="submit" value="预约"/>
			</div>
</form>
<br>
<table style="text-align:center;" id="table">
<tr id="th" >
	<th id="th" width="25%">预约号</th><th id="th" width="25%">预约人</th><th id="th" width="25%">书名</th><th id="th" width="25%">预约时间</th>
</tr>
<s:iterator value="reservelist" id="obj">
<tr> 
<td id="td"><s:property value="#obj.reserveid"/></td>
	<td id="td"><s:property value="#obj.beanreader.readerName"></s:property></td>
	<td id="td"><s:property value="#obj.beanbook.bookname"></s:property></td>
	<td id="td"><s:property value="#obj.reserveDate"></s:property></td>
</tr>
</s:iterator>
</table>
</body>
</html>