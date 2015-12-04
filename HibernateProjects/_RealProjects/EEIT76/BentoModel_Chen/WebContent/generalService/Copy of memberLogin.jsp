﻿<!--    本網頁 login.jsp 提供登入的畫面，然後將userId與pswd傳給
   <Form>的action屬性為login.do，它對應的程式為_02_login.controller.LoginServlet.java
   ，由該Servlet來檢查userId與pswd是否正確。    -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登入</title>


</head>


<%-- <c:if test="${ ! empty sessionScope.timeOut }" > <!-- 表示使用逾時，重新登入 --> --%>
<%--    <c:set var="msg" value="<font color='red'>${sessionScope.timeOut}</font>" /> --%>
<%-- </c:if> --%>
<!-- 引入共同的頁首 -->

<CENTER>
${needLogin}
<Form action="/BentoDelivery/MemberLoginloginServlet" method="POST" name="loginForm">
    <Table>
         <TR>
             <TH width="180">&nbsp;</TH>
             <TH width="180">&nbsp;</TH>
         </TR>
         <TR>
             <TD colspan='2' align="CENTER" style="font-size:0.6cm;font-weight: 300;"> 
                <Font color="#006600" face="標楷體">
                  	  會員登入
                </Font>
             </TD>
         </TR>
         <TR>
             <TD height='50' colspan='2' align="CENTER" style="font-size:0.5cm;font-weight: 300;"> 
                <Font color="#006600"  face="標楷體">
                    ${msg}
                </Font>
             </TD>
         </TR>
         <TR height='10'>
             <TD align="CENTER" colspan='2'>&nbsp;</TD>
         </TR>
         <TR>
             <TD width="180" align="right">帳號：</TD>
             <TD width="180" colspan='2' align="LEFT">
             <input  type="text" name="userId" size="10" value="${sessionScope.user}">
             &nbsp;<small><Font color='red' size="-1">${ErrorMsgKey.AccountEmptyError}</Font></small></TD>
         </TR>
         <TR>
             <TD width="180" align="right">密碼：</TD>
             <TD width="180" colspan='2' align="LEFT" >
             <input  type="password" name="pswd"  size="10" value="${sessionScope.password}">
             
             &nbsp;<small><Font color='red'  size="-1">${ErrorMsgKey.PasswordEmptyError}</Font></small></TD>
             
         </TR>  
         <tr>
         <TD width="180" align="right" >
             <input type="checkbox" name="rememberMe" 
               <c:if test='${sessionScope.rememberMe==true}'>
                  checked='checked'
               </c:if> 
             value="true">
         </TD>
         <TD width="180"  colspan='2' align="left"><small>記住密碼</small></TD>
             
         </tr>
         <TR height='10'>
             <TD align="CENTER" colspan='2'>&nbsp;<Font color='red' size="-1">${ErrorMsgKey.LoginError}&nbsp;</Font></TD>
         </TR>
        <TR>
            <TD colspan="2" align="center"><input type="submit" value="提交"> </TD>
         </TR>
         <TR height='10'>
             <TD align="CENTER" colspan='2'>&nbsp;</TD>
         </TR>
    </Table>
</Form>
</CENTER>
</body>
</html>