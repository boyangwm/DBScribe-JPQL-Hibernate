<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.myform {
	width: 230px;
	margin: 20px auto;
}

.form_field {
	display: block;
	width: 100%;
	text-align: left;
	margin: 10px auto;
	verticle-align: center;
}

.form_field>div {
	display: inline-block;
}
</style>
<script type="text/javascript">
    function check(barcode,bookname,price){
    	if(barcode.value == null||barcode.value==""){
			alert("图书条码不能为空！");
			return false;
		}
		if(bookname.value == null||bookname.value==""){
			alert("图书名称不能为空");
			return false;
		}
		
		if(price.value == null||price.value==""){
			alert("图书单价不能为空");
			return false;
		}

	}
    </script>
</head>
<body>
<div align="center"><font color="red"><s:property value="msg"></s:property></font></div>
	<s:form action="book!add" cssClass="myform" theme="css_xhtml"
		onsubmit="return check(barcode,bookname,price)">
		<s:textfield key="图书条码" name="barcode" labelposition="left"/>
		<s:textfield key="图书名称" name="bookname" labelposition="left" />
		<s:textfield key="图书单价" name="price" labelposition="left" />
		<s:select list="#session.publist" name="publisher"  label="出版社" labelposition="left" listKey="pubid" listValue="publisherName"  headerKey="1"/>
		<s:submit value="添加" align="center" />
	</s:form>
</body>
</html>