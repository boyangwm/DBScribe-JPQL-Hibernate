<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登出</title>
</head>
<body>
<!-- 先將使用者名稱取出 -->
<c:if test="${memberVO.memberFirstName!=null}">
		<c:set var="memberName" value="${ memberVO.memberFirstName }" />
</c:if>
<c:if test="${ownerVO.ownFirstName!=null}">
		<c:set var="memberName" value="${ memberVO.memberFirstName }" />
</c:if>
<!-- 移除放在session物件內的屬性物件 -->
<c:remove var="memberVO" scope="session" />
<c:remove var="memberLoginOK" scope="session" />
<c:remove var="ownerVO" scope="session" />
<c:remove var="ownerLoginOK" scope="session" />
<!-- 下列敘述設定變數funcName的值為OUT，top.jsp 會用到此變數 -->
<%-- <c:set var="funcName" value="OUT" scope="session"/> --%>
<!-- 引入共同的頁首 -->

<!-- 下列六行敘述設定登出後要顯示的感謝訊息 -->
<c:set var="logoutMessage" scope="session">
<font color=#FFB7DD ><BR>
${ memberName }，明天也要吃飽飽喔。<BR>
</font>
</c:set>
<%
  response.sendRedirect(request.getContextPath()+"/index.jsp");
%>
<%-- <jsp:forward page="/index.jsp"/> --%>

</body> 
</html>