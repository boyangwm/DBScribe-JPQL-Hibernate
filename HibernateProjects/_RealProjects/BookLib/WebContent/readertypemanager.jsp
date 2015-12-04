<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
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
    window.location.href="readertype!delete?json="+arr;
}

function editPub(){
	var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	window.location.href="readertype!editValueSend?readerTypeId="+select[i].value;
      }
    }    
}

</script>
</head>
<body>
<s:action name="readertype!loadAll"/>
<div >
	<div >
		<div >
			 <button  type="button" onclick="window.location.href='addtype.jsp'">增加读者类别</button>
			 <button  type="button" onclick="editPub()">修改读者类别信息</button>
			 <button  type="button" onclick="deletePub()">删除读者类别</button>
		</div>
	</div>
</div>
<br>
<table style="text-align:center;">
<tr>
	<th width="20%">读者类型ID</th><th width="40%">读者类型名</th><th width="40%">可借阅书本上限</th>
</tr>
<s:iterator value="#session.typelist" id="obj">
<tr> 
<td><input type="checkbox" name="select" style="float:left;" value="<s:property value="#obj.readerTypeId"/>"><s:property value="#obj.readerTypeId"/></td>
	<td><s:property value="#obj.readerTypeName"></s:property></td>
	<td><s:property value="#obj.lendBookLimitted"></s:property></td>
</tr>
</s:iterator>
</table>
</body>
</html>