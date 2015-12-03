<%@ page language="java" pageEncoding="gb2312"%>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<jsp:useBean id="message" class="com.jspdev.biyesheji.Message" scope="request"/>

<c:set var="messages" value="<%=message.getAllMessage() %>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>viewMessage.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body bgcolor="#f1fcfe"><br>
    <%int k=1; %>
    <center><font size="6">查看留言</font></center><br>
      <logic:iterate id="mess" name="messages">
      <table align="center" border="1" width="70%">
       <tr align="center">
         <td width="20%" bgcolor="#ddf5cd">主题(<font color="blue"><%=k++ %></font>):</td>
         <td><bean:write name="mess" property="title"/></td>
       </tr> 
       <tr align="center">
         <td width="20%" bgcolor="#ddf5cd">留言人:</td>
         <td><bean:write name="mess" property="name"/></td>
       </tr>
       <tr align="center">
         <td width="20%" bgcolor="#ddf5cd">Email:</td>
         <td><bean:write name="mess" property="email"/></td>
       </tr>
       <tr align="center">
         <td  width="20%"bgcolor="#ddf5cd">留言时间:</td>
         <td><bean:write name="mess" property="time"/></td>
       </tr>
       <tr align="center">
         <td width="20%" bgcolor="#ddf5cd">留言内容:</td>
         <td><bean:write name="mess" property="content"/></td>
       </tr>  
      </table><br>    
      </logic:iterate>
      </body>
</html:html>


   <%--
      <%if(pageCtl.maxPage!=1){ %>
      <form name="pageForm" action="/biyesheji/servlet/PageControlServlet" method="post">
    
       <script language="text/JavaScript">   
       <!--
       function Jumping(){
         document.pageForm.submit();
         return;
       }
       function gotoPage(pageNum){
         document.pageForm.jumpPage.value=pageNum;
         document.pageForm.submit();
         return;
       }
       -->
       </script> 
    
        每页<%=pageCtl.rowsPerPage %>行
        共<%=pageCtl.maxRowCount %>行
        第<%=pageCtl.curPage %>页
        共<%=pageCtl.maxPage %>页
       <br>
       <%if(pageCtl.curPage==1){ 
           out.print("  首页   上一页");}
         else{
       %>
           <a href="javascript:gotoPage(1)">首页</a>
           <a href="javascript:gotoPage(<%=pageCtl.curPage-1 %>)">上一页</a>
       <%} %> 
       <%if(pageCtl.curPage==pageCtl.maxPage){ 
           out.print("  下一页   末页");}
         else{
       %>
           <a href="javascript:gotoPage(<%=pageCtl.curPage+1 %>)">下一页</a>
           <a href="javascript:gotoPage(<%=pageCtl.maxPage %>)">末页</a>
       <%} %> 
       转到第
        <select name="jumpPage" onchange="Jumping()"> 
            <%for(int i=1;i<=pageCtl.maxPage;i++){
                if(i==pageCtl.curPage){
            %>
                   <option selected value=<%=i %>><%=i %></option>
            <%}else{%>
                   <option value=<%=i %>><%=i %></option>
            <%}}%>
         </select>
        页
      </form>
      <%} %>    
 --%>
  
  