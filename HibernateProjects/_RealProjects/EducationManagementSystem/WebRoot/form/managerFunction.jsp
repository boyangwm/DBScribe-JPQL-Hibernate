<%@ page language="java" pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>flyout-竖向三级弹出菜单</title>
<STYLE type=text/css>
BODY {
	SCROLLBAR-FACE-COLOR: #eaeaf2; FONT-SIZE: 14px; MARGIN: 0px; SCROLLBAR-HIGHLIGHT-COLOR: #ffffff; SCROLLBAR-SHADOW-COLOR: #9395c0; SCROLLBAR-3DLIGHT-COLOR: #f3f3f8; SCROLLBAR-ARROW-COLOR: #9395c0; SCROLLBAR-TRACK-COLOR: #f3f3f8; FONT-FAMILY: "????", "Verdana", "Arial", "Helvetica", "sans-serif"; SCROLLBAR-DARKSHADOW-COLOR: #f3f3f8; BACKGROUND-COLOR: #f1fcfe
}
TABLE {
	BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px
}
TD {
	FONT: 12px ????
}
IMG {
	BORDER-RIGHT: 0px; BORDER-TOP: 0px; VERTICAL-ALIGN: bottom; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px
}
A {
	FONT: 12px ????; COLOR: #111111; TEXT-DECORATION: none
}
A:hover {
	COLOR: #6c70aa; TEXT-DECORATION: underline
}
.sec_menu {
	BORDER-RIGHT: white 1px solid; BACKGROUND: #fbfbfc; OVERFLOW: hidden; BORDER-LEFT: white 1px solid; BORDER-BOTTOM: white 1px solid
}
.menu_title {
	
}
.menu_title SPAN {
	FONT-WEIGHT: bold; LEFT: 8px; COLOR: #4a4f80; POSITION: relative; TOP: 2px
}
.menu_title2 {
	
}
.menu_title2 SPAN {
	FONT-WEIGHT: bold; LEFT: 8px; COLOR: #596099; POSITION: relative; TOP: 2px
}
INPUT {
	FONT-SIZE: 12px; FONT-FAMILY: ????,Verdana, Arial, Helvetica, sans-serif
}
SELECT {
	FONT-SIZE: 12px; FONT-FAMILY: ????,Verdana, Arial, Helvetica, sans-serif
}
TEXTAREA {
	FONT-SIZE: 12px; FONT-FAMILY: ????,Verdana, Arial, Helvetica, sans-serif
}
.style1 {color: #FFFFFF}
</STYLE><script>
	function closebut(x, y) {
		if(document.img0) document.img0.src='images/hot_p.gif';
		if(document.img1) document.img1.src='images/hot_p.gif';
		if(document.img2) document.img2.src='images/hot_p.gif';
		if(document.img3) document.img3.src='images/hot_p.gif';
		if(document.img4) document.img4.src='images/hot_p.gif';
		if(document.img5) document.img5.src='images/hot_p.gif';
		if(document.getElementById('div0')) document.getElementById('div0').style.display='none';
		if(document.getElementById('div1')) document.getElementById('div1').style.display='none';
		if(document.getElementById('div2')) document.getElementById('div2').style.display='none';
		if(document.getElementById('div3')) document.getElementById('div3').style.display='none';
		if(document.getElementById('div4')) document.getElementById('div4').style.display='none';
		if(document.getElementById('div5')) document.getElementById('div5').style.display='none';
		x.style.display='block';
		y.src='images/hot_p.gif';
	}
	function t(x, y) {
		if(x.style.display!='none') {
			x.style.display='none';
			y.src='images/hot_p.gif';
		}
		else
			closebut(x, y);
	}
	</script>
</head>

<body bgcolor="#f1fcfe">
<div class="menu">
<br/>
 <ul>
 <li><a class="hide"><font size="3">学生管理</font></a>
  <ul>
  <li><a href="addStudent.jsp" target="right">添加学生</a></li>
  <li><a href="viewStudents.jsp" target="right">查看学生信息</a></li>
  <li><a href="changeGrade.jsp" target="right">修改学生成绩</a></li>
  </ul>
 </li><br/>
 
 <li><a class="hide"><font size="3">教师管理</font></a>
  <ul>
  <li><a href="addTeacher.jsp" target="right">添加教师</a></li>
  <li><a href="viewTeachers.jsp" target="right">查看教师信息</a></li>
  </ul>
 </li><br/>

 <li><a class="hide"><font size="3">课程管理</font></a>
  <ul>
  <li><a href="addCourse.jsp" target="right">添加课程</a></li>
  <li><a href="viewCourses.jsp" target="right">查看课程信息</a></li>
  </ul>
 </li><br/>

 <li><a class="hide"><font size="3">留言管理</font></a>
  <ul>
  <li><a href="addMessage.jsp?name=yesqcy" target="right">发表留言</a></li>
  <li><a href="deleteMessage.jsp" target="right">删除留言</a></li>
  </ul>
 </li><br/>

 <li><a class="hide"><font size="3">系统公告</font></a>
  <ul>
  <li><a href="viewNotes.jsp" target="right">查看公告</a></li>
  <li><a href="addNotes.jsp" target="right">更新公告</a></li>
  </ul>
 </li><br/>

 <li><a class="hide" href="../login.jsp" target="_parent"><font size="3">退出系统</font></a></li>
 </ul>

</div>
</body>
</html>