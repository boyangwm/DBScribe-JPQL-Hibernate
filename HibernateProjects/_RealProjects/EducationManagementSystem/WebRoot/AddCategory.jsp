<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*,java.util.*" errorPage="" %>
<%@ include file="Connections/Local.jsp" %> 
<%@ include file="MenuMaker.jsp" %>
<%
StringBuffer menu=new StringBuffer();
%>
<html>
<head>
<title>类型选择</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="JavaScript" type="text/JavaScript" src="../js/Basic.js"></script>
<script language="JavaScript" type="text/JavaScript" src="../js/MenuStatus.js"></script><br>
<script language="JavaScript" type="text/JavaScript" src="../js/checkType.js"></script>
<script language="JavaScript" type="text/JavaScript">
var	OverMenu="OverMenu";
var	OutMenu="OutMenu";
var path="Category";
var addcnt=0; 
function SubThis(pId){ 
	if(!sbmt){
		sbmt=true;	
		turnLayer("chooser","hide");
		findInDoc("ParentId").value=pId;		
	}
}
function check(src){
	if(src.Name.value==""){
		alert("请填好子分类名称");
		return false;
	}else{
		return true;
	}
}
function choose(){
	sbmt=false;
	findInDoc(path).value='';
	turnLayer('chooser','show');
	again();
}
function addAtt(){
	var next=addcnt+1;
	var inn="<br>名称:<input name='added_"+addcnt+"' type='text' size='5'>描述:<input name='des_"+addcnt+"' type='text' size='10'>&nbsp;&nbsp;类型:<select  onChange='controller(\""+addcnt+"\")' name='type_"+addcnt+"'><option value='int'>整数</option><option value='float'>浮点数</option><option value='nvarchar'>字符串</option></select>&nbsp;&nbsp;大小:<input onBlur='checkType(this,\"int\")' name='size_"+addcnt+"' type='text' size='5'>";
	inn=inn+"<div align='center' id='addto_"+next+"'></div>";
	findInDoc('addto_'+addcnt).innerHTML=inn;
	addcnt=next;
}
function again(){
	findInDoc('OldAtt').innerHTML="";
	findInDoc('addto_0').outerHTML="<div style='border:solid 1px;'align='center' id='addto_0'></div>";
	addcnt=0;
	return true;
}
function controller(number){
	type=findInDoc('type_'+number).value;
	text=findInDoc('size_'+number);
	if(type=='int'){
		text.disabled=true;
		text.value='';
	}
	else{
		text.disabled=false;
		if(type=='float') text.value='25';
		else if(type=='nvarchar') text.value='50';
	}
}
</script>
<link href="/css/Operation.css" rel="stylesheet" type="text/css">
<link href="/css/Menu.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="Layer2" style="position:absolute; left:56px; top:31px; width:416px; height:171px; z-index:1"> 
  <form action="InsertCategory.jsp" method="post" name="form1" id="form1" onSubmit="javascript: return check(this)">
    <table width="441" height="165" >
      <tr> 
        <td height="26" colspan="2">&nbsp;</td>
      </tr>
      <tr> 
        <td height="25"><div align="right"> 
            <input type="button" value="选择" onClick="choose();">
            父分类： </div></td>
        <td width="303"><input name="Category" type="text" class="input" id="Category" readonly="true"> 
          <font size="2"> 
          <input name="ParentId" type="hidden" id="ParentId">
          </font> <div id="Tittle" style="position:absolute; left:0px; top:-20px; z-index:1; width: 170px;"> 
            <table width="441" cellpadding="0" cellspacing="0" style="border: 1px solid #000000;">
              <tr bgcolor="#636563"> 
                <td width="20"><img src="../pic/NewCategory.gif" width="16" height="16" hspace="2" vspace="2"></td>
                <td background="../pic/linear.jpg"><font color="#FFFFFF">添加新的分类</font></td>
              </tr>
            </table>
          </div>
          <font size="2">&nbsp; </font></td>
      </tr>
      <tr> 
        <td><div align="right">添加的子分类：</div></td>
        <td><input name="Name" type="text" class="input" id="Name"> <font size="2">【此项不能为空</font><font size="2">】</font></td>
      </tr>
      <tr> 
        <td colspan="2"><div id="OldAtt"></div></td>
      </tr>
      <tr> 
        <td colspan="2" style="padding-left: 5px;padding-right: 5px;"><div style='border:solid 1px;'align='center' id='addto_0'> 
          </div></td>
      </tr>
      <tr> 
        <td> <div align="right"> 
            <input name="Submit" type="submit" class="input" value="添加">
          </div></td>
        <td><div align="center"> 
            <input name="Reset" type="reset" class="input" id="Reset" value="重填" onClick="again();">
          </div></td>
      </tr>
      <tr> 
        <td colspan="2"><div align="center"> <font size="2"><br>
            </font></div></td>
      </tr>
    </table>
    <div id="chooser" style="position:absolute; left:4px; top:32px; width:98px; height:55px; z-index:1; visibility: hidden;"><%=menu.toString()%></div>
  </form>  
</div>
</body>
</html>
<%
%>