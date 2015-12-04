<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
    function check(pubid,publisherName,address){
		if(pubid.value == null||pubid.value==""){
			alert("出版社ID不能为空！");
			return false;
		}
		if(publisherName.value == null||publisherName.value==""){
			alert("出版社名不能为空！");
			return false;
		}
		if(address.value == null||address.value==""){
			alert("地址不能为空！");
			return false;
		}

	}
    </script>
</head>
<body>
<div align="center"><font color="red"><s:property value="msg"></s:property></font></div>
	<s:form action="publisher!add" cssClass="myform" theme="css_xhtml"
		onsubmit="return check(pubid,publisherName,address)">
		<s:textfield key="出版社ID" name="pubid" labelposition="left"/>
		<s:textfield key="出版社名" name="publisherName" labelposition="left" />
		<s:textfield key="地址" name="address" labelposition="left" />
		<s:submit value="添加" align="center" />
	</s:form>
</body>
</html>