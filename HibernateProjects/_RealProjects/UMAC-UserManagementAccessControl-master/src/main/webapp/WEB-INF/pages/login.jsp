<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

	<head>
	
		<meta http-equiv = "Content-Type" content = "text/html; charset=ISO-8859-1">
				
		<title>UMAC</title>	
		
		<link href = "common/img/logo.ico" type = "image/x-icon" rel = "icon"/>
		<link href = "common/img/logo.ico" type = "image/x-icon" rel = "shortcut icon"/>	
			
		<script src = "common/Script/jquery-ui/js/jquery-1.9.1.js" type = "text/javascript"></script>
		<script src = "common/Script/jquery-ui/js/jquery-ui-1.10.3.custom.js" type = "text/javascript"></script>
		<link  href = "common/Script/jquery-ui/css/ui-lightness/jquery-ui-1.10.3.custom.css" type = "text/css" rel = "stylesheet">	
	
	</head>
	
	<body>

		<form method="post" action="<c:url value='j_spring_security_check'/>" >
			<input type="text" name="j_username" id="j_username" size="30" maxlength="20"  />
			<input type="password" name="j_password" id="j_password" size="30" maxlength="20" />
			<input type="submit" value="Login" />
			<input type="checkbox" name="_spring_security_remember_me" />
		</form>	
	
		<a href="${pageContext.request.contextPath}/index">Public Area</a>

	</body>
	
	<script type="text/javascript">
	
	</script>
</html>