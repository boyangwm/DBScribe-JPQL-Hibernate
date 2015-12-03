<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UMAC User Management</title>
</head>
<body> 
<form:form commandName="userForm" action="${pageContext.request.contextPath}/user-management/save" method="POST">
	<form:errors path="*" element="div" />
   	<table>
    	<tr>
        	<td>
        		<form:input path="firstname" />
        		<form:errors path="firstname" />
        	</td>
    	</tr>
    	<tr>
        	<td>
        		<form:input path="lastname" />
        		<form:errors path="lastname" />
        	</td>
    	</tr>
    	<tr>
        	<td>
        		<form:input path="email" />
        		<form:errors path="email" />
        	</td>
    	</tr>
    	<tr>
        	<td>
        		<form:input path="birth" />
        		<form:errors path="birth" />
        	</td>
    	</tr>
    	<tr>
        	<td>
        		<form:input path="username" />
        		<form:errors path="username" />
        	</td>
    	</tr>
    	<tr>
        	<td>
        		<form:input path="password" />
        		<form:errors path="password" />
        	</td>
    	</tr>
    	<tr>
    		<td>
    			<form:select path="role" items="${roles}" />
    		</td>
    	</tr>
    	<tr>
        	<td colspan="2">
            	<input type="submit" value="Save"/>
        	</td>
    	</tr>
	</table>  
</form:form>

</body>
</html>