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
<script language="javascript">
function deletebook(){
	var arr=[];
    var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	arr.push(select[i].value);
      }
    }    
    window.location.href="book!delete?json="+arr;
}

function editbook(){
	var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	window.location.href="book!editValueSend?barcode="+select[i].value;
      }
    }    
}
</script>
</head>
<body>
<s:action name="book!loadAll"/>
<div >
	<div >
		<div id="div">
			 <button  type="button" onclick="window.location.href='addbook.jsp'">添加</button>
			 <button  type="button" onclick="editbook()">修改</button>
			 <button  type="button" onclick="deletebook()">下架</button>
			 </div>
			 <div id="div">
			 <div id="div">
			 <s:form  action="book!loadAll" theme="simple">
			 <s:select list="#{'在库':'在库','已借出':'已借出','已删除':'已删除'}" name="state" labelposition="left" listKey="key" listValue="value"  headerKey="1"/>
			 <s:textfield name="bookname"/>
			 <s:submit value="查询" align="center"/>
			 </s:form>
		</div>
		</div>
	</div>
</div>
<br>
<br>
<table style="text-align:center;" id="table">
<tr id="th" >
	<th id="th" width="20%">条码</th><th id="th" width="20%">书名</th><th id="th" width="20%">出版社</th><th id="th" width="20%">价格</th><th id="th" width="20%">状态</th>
</tr>
<s:iterator value="#session.booklist" id="obj">
<tr> 
<td id="td"><input type="checkbox" name="select" style="float:left;" value="<s:property value="#obj.barcode"/>"><s:property value="#obj.barcode"/></td>
	<td id="td"><s:property value="#obj.bookname"></s:property></td>
	<td id="td"><s:property value="#obj.publisher"></s:property></td>
	<td id="td"><s:property value="#obj.price"></s:property></td>
	<td id="td"><s:property value="#obj.state"></s:property></td>
</tr>
</s:iterator>
</table>
</body>
</html>