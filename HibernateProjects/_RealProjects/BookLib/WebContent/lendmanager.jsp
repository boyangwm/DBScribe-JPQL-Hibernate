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
    	var sBook=document.getElementById("book"); 
    	if(sReader.value!=""){
    	window.location.href="lend!loadByReader?readerid="+sReader.value+"&bookid="+sBook.value;
    	}
    }
    
    function check(readerid,bookid){
    	if(readerid.value == null||readerid.value==""){
			alert("读者不能为空！");
			return false;
		}
    	if(bookid.value == null||bookid.value==""){
			alert("图书不能为空！");
			return false;
		}
    	var state='<%=request.getAttribute("state")%>';
    	if(state=="已借出"||state=="已删除"){
    		alert("图书"+state);
    		return false;
    	}
	}
    </script>
</head>
<body>
<div id="div">
<form action="lend!add" onsubmit="return check(readerid,bookid)">
<div>
			读者：<s:textfield id="reader"  name="readerid" onkeyup="search()" value="%{#request.readeridvalue}"/>
			<font color="black"><s:property value="reader"></s:property></font>
		    图书：<s:textfield  id="book" name="bookid" onkeyup=" search()" id="book" value="%{#request.bookidvalue}"/>
		    <font color="black"><s:property value="book"></s:property></font>
		    <font color="red"><s:property value="state"></s:property></font>
			<input type="submit" value="借阅"/>
			</div>
</form>
</div>
<br>
<br>
<table style="text-align:center;" id="table">
<tr id="th" >
	<th id="th" width="20%">条码</th><th id="th" width="20%">借阅者</th><th id="th" width="20%">书名</th><th id="th" width="20%">出版社</th><th id="th" width="20%">价格</th>
</tr>
<s:iterator value="lendlist" id="obj">
<tr> 
<td id="td"><s:property value="#obj.id"/></td>
	<td id="td"><s:property value="#obj.beanreader.readerName"></s:property></td>
	<td id="td"><s:property value="#obj.beanbook.bookname"></s:property></td>
	<td id="td"><s:property value="#obj.beanbook.beanpublisher.publisherName"></s:property></td>
	<td id="td"><s:property value="#obj.beanbook.price"></s:property></td>
</tr>
</s:iterator>
</table>
</body>
</html>