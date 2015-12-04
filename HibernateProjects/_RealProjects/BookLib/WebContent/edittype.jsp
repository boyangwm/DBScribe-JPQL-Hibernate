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
    function check(readerTypeName,lendBookLimitted){
		if(readerTypeName.value == null||readerTypeName.value==""){
			alert("读者类型名不能为空！");
			return false;
		}
		if(lendBookLimitted.value == null||lendBookLimitted.value==""){
			alert("可借阅书本上限不能为空");
			return false;
		}

	}
</script>
</head>
<body>
<div align="center"><font color="red"><s:property value="msg"></s:property></font></div>
	<s:form action="readertype!edit" cssClass="myform" theme="css_xhtml"
		onsubmit="return check(readerTypeName,lendBookLimitted)">
		<s:textfield key="类型ID" name="readerTypeId" readonly="true" labelposition="left" value="%{#editvalue.readerTypeId}"/>
		<s:textfield key="读者类型名" name="readerTypeName" labelposition="left" value="%{#editvalue.readerTypeName}"/>
		<s:textfield key="可借阅书本上限" name="lendBookLimitted" labelposition="left" value="%{#editvalue.lendBookLimitted}"/>
		<s:submit value="修改" align="center" />
	</s:form>
</body>
</html>