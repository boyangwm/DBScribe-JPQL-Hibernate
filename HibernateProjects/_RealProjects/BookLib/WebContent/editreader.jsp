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
    function check(readerid,readerName){
		if(readerid.value == null||readerid.value==""){
			alert("读者证号不能为空！");
			return false;
		}
		if(readerName.value == null||readerName.value==""){
			alert("读者姓名不能为空");
			return false;
		}

	}
</script>

</head>
<body>
<div align="center"><font color="red"><s:property value="msg"></s:property></font></div>
	<s:form action="reader!edit" cssClass="myform" theme="css_xhtml"
		onsubmit="return check(readerid,readerName)">
		<s:textfield key="读者证号" name="readerid" readonly="true" labelposition="left" value="%{#editvalue.readerid}"/>
		<s:textfield key="读者姓名" name="readerName" labelposition="left" value="%{#editvalue.readerName}"/>
		<s:select list="#session.typelist" name="readerType"  label="用户类型" labelposition="left" listKey="readerTypeId" listValue="readerTypeName"  headerKey="1"/>
		<s:submit value="修改" align="center" />
	</s:form>
</body>
</html>