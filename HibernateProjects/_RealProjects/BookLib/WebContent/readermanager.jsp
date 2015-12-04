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
function deletereader(){
	var arr=[];
    var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	arr.push(select[i].value);
      }
    }    
    window.location.href="reader!delete?json="+arr;
}

function editreader(){
	var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	window.location.href="reader!editValueSend?readerid="+select[i].value;
      }
    }    
}

function losereader(){
	var arr=[];
    var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	arr.push(select[i].value);
      }
    }    
    window.location.href="reader!lose?json="+arr;
}

function removelose(){
	var arr=[];
    var select=document.getElementsByName("select"); 
    for(var i=0;i<select.length;i++){
        if(select[i].checked){
        	arr.push(select[i].value);
      }
    }    
    window.location.href="reader!removeLose?json="+arr;
}

function searchreader(){
	var type=document.getElementesByName("readerType");
	var name=document.getElementesByName("readerName");
	window.location.href="reader!searchReader?readerType="+type+"&readerName="+name;
}
</script>
</head>
<body>
<s:action name="reader!loadAll"/>
<div >
	<div >
		<div id="div">
			 <button  type="button" onclick="window.location.href='addreader.jsp'">添加</button>
			 <button  type="button" onclick="editreader()">修改</button>
			 <button  type="button" onclick="losereader()">挂失</button>
			 <button  type="button" onclick="removelose()">解挂</button>
			 <button  type="button" onclick="deletereader()">删除</button>
			 </div>
			 <div id="div">
			 <s:form  action="reader!loadAll" theme="simple">
			 <s:select list="#session.typelist" name="readerType"  labelposition="left" listKey="readerTypeId" listValue="readerTypeName"  headerKey="1"/>
			 <s:textfield name="readerName"/>
			 <s:submit value="查询" align="center"/>
			 </s:form>
		</div>
	</div>
</div>
<br>
<br>
<table style="text-align:center;" id="table">
<tr id="th" >
	<th id="th" width="20%">读者证号</th><th id="th" width="20%">姓名</th><th id="th" width="20%">类别</th><th id="th" width="20%">借阅限额</th><th id="th" width="20%">状态</th>
</tr>
<s:iterator value="#session.readerlist" id="obj">
<tr> 
<td id="td"><input type="checkbox" name="select" style="float:left;" value="<s:property value="#obj.readerid"/>"><s:property value="#obj.readerid"/></td>
	<td id="td"><s:property value="#obj.readerName"></s:property></td>
	<td id="td"><s:property value="#obj.readerType"></s:property></td>
	<td id="td"><s:property value="#obj.lendBookLimitted"></s:property></td>
	<td id="td"><s:property value="#obj.state"></s:property></td>
</tr>
</s:iterator>
</table>
</body>
</html>