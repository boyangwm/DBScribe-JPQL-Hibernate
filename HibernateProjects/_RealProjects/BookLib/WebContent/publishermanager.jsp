<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<script language="javascript">
function deletePub(){
	var arr=[];
    var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	arr.push(select[i].value);
      }
    }    
    window.location.href="publisher!delete?json="+arr;
}

function editPub(){
	var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	window.location.href="publisher!editValueSend?pubid="+select[i].value;
      }
    }    
}

</script>
</head>
<body>
<s:action name="publisher!loadAll"/>
<div >
	<div >
		<div >
			 <button  type="button" onclick="window.location.href='addpublisher.jsp'">添加出版社</button>
			 <button  type="button" onclick="editPub()">修改出版社</button>
			 <button  type="button" onclick="deletePub()">删除出版社</button>
			 <font color="red"><s:property value="count"></s:property></font>
		</div>
	</div>
</div>
<br>
<table style="text-align:center;">
<tr>
	<th width="30%">出版社ID</th><th width="30%">出版社名</th><th width="40%">地址</th>
</tr>
<s:iterator value="#session.publisherlist" id="obj">
<tr> 
<td><input type="checkbox" name="select" style="float:left;" value="<s:property value="#obj.pubid"/>"><s:property value="#obj.pubid"/></td>
	<td><s:property value="#obj.publisherName"></s:property></td>
	<td><s:property value="#obj.address"></s:property></td>
</tr>
</s:iterator>
</table>
</body>
</html>