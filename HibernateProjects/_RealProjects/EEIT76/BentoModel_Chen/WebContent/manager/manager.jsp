<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ad.model.*"%>
<%@ page import="com.restaurant.model.*"%>
<%@ page import="com.restdiscuss.model.*"%>
<%@ page import="com.accuse.model.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
  <meta charset="utf-8">
  <title>日日食便當</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

	
	<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/bootstrap/css/style.css" rel="stylesheet">

  
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/scripts.js"></script>

<style>	
	html,body{background-color: #DDDDDD}
	img{max-width: 100px;}
	td,th{ width:200px;  height:80px;
	font-size:20px; text-align:center;border-style: solid;border-width: thin; font-family: "標楷體";
	}
	tbody tr:nth-child(2n){background-color: #CCEEFF}
	tbody tr:nth-child(2n+1){background-color: #77FFEE}
	th{background-color: #AAAAAA}
</style>	
</head>

<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="tabbable" id="tabs-797877">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-1" data-toggle="tab">待審核廣告</a>
					</li>
					<li>
						<a href="#panel-2" data-toggle="tab">運行中的廣告</a>
					</li>
					<li>
						<a href="#panel-3" data-toggle="tab">待審核檢舉(Accuse)</a>
					</li>
					<li>
						<a href="<%=request.getScheme()+"://"+request.getServerName()+":"+
							request.getServerPort()+request.getContextPath()%>/index.jsp" >回首頁</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-1">
						<p>	<%  ADservice ADSvc1 = new ADservice();
								List<AdVO> adlist1 = ADSvc1.getByTreatID(1); 	
								pageContext.setAttribute("adlist1",adlist1);   
							%>
							<table>
								<tr>
									<th>餐廳名稱</th><th>圖片</th><th>廣告描述</th><th>開始日期</th><th>結束日期</th><th>審核通過</th><th>審核不通過</th>
								</tr>
								<c:forEach var="adVO" items="${adlist1}" >
									<tr>
										<td>${adVO.restaurantVO.restName}</td>
										<td><img src='${pageContext.servletContext.contextPath}/getImage?TreatID=1&RestID=${adVO.restID}' ></td>
										<td>${adVO.context}</td>
										<td>${adVO.startDate}</td>
										<td>${adVO.endDate}</td>
										<td>
											<form METHOD="post" ACTION="<%=request.getContextPath()%>/CheckAD.do">
												<input type="submit" value="通過">
												<input type="hidden" name="RestID" value="${adVO.restID}"/>
												<input type="hidden" name="action"	value="success">
											</form>
										</td>
										<td>
											<form METHOD="post" ACTION="<%=request.getContextPath()%>/CheckAD.do">
												<input type="text" name="TreatDetail" placeholder="請輸入原因" />
												<input type="submit" value="不通過">
												<input type="hidden" name="RestID" value="${adVO.restID}"/>
												<input type="hidden" name="action"	value="failure">
											</form>
										</td>
									</tr>	
								</c:forEach>
							</table>
						</p>
					</div>
					<div class="tab-pane" id="panel-2">
						<p> <%  ADservice ADSvc2 = new ADservice();
								List<AdVO> adlist2 = ADSvc2.getByTreatID(2); 	
								pageContext.setAttribute("adlist2",adlist2);   
							%>
							<table>
								<tr>
									<th>餐廳名稱</th><th>圖片</th><th>廣告描述</th><th>開始日期</th><th>結束日期</th><th>結束廣告</th>
								</tr>
								<c:forEach var="adVO" items="${adlist2}" >
									<tr>
										<td>${adVO.restaurantVO.restName}</td>
										<td><img src='${pageContext.servletContext.contextPath}/getImage?TreatID=2&RestID=${adVO.restID}' ></td>
										<td>${adVO.context}</td>
										<td>${adVO.startDate}</td>
										<td>${adVO.endDate}</td>
										<td><form METHOD="post" ACTION="<%=request.getContextPath()%>/CheckAD.do">
												<input type="submit" value="終止廣告"/>
												<input type="hidden" name="RestID" value="${adVO.restID}"/>
												<input type="hidden" name="action" value="delete">
											</form>
										</td>
									</tr>	
								</c:forEach>
							</table>
						</p>
					</div>
					<div class="tab-pane" id="panel-3">
						<p><%   AccuseService accusesvc = new AccuseService();
								List<AccuseVO> accuselist = accusesvc.getByCaseID(1);
								RestDiscussDAO restdisdao = new RestDiscussDAO();
								List<RestDiscussVO> restdislist = restdisdao.getAll();
								pageContext.setAttribute("accuselist",accuselist);
								pageContext.setAttribute("restdislist",restdislist);
							%>
						<c:if test="${not empty errorMsgs}">
								<c:forEach var="message" items="${errorMsgs}">
									<li ><font color='red'>${errorMsgs.DealDetail}</font></li>
								</c:forEach>
						</c:if>
						<table>
							<tr>
								<th>檢舉編號</th>
								<th>留言內容</th>
								<th>理由</th>
								<th>餐廳</th>
								<th>會員</th>
								<th>處理狀況</th>
								<th>處理理由</th>
								<th>描述</th>
							</tr>
						<c:forEach var="accusevo" items="${accuselist}" >
							<tr>
								<td>${accusevo.accuseID}</td>
								<td><c:forEach var="disvo" items="${restdislist}" >
										<c:if test="${accusevo.restDiscussID == disvo.restDiscussID}">
											${disvo.discussion}
										</c:if>
									</c:forEach>
								</td>
								<td>${accusevo.reason}</td>
								<td>${accusevo.restaurantVO.restName}</td>  
								<td>${accusevo.memberAcc}</td>
								<td><c:if test="${!accusevo.dealCond}">未處理</c:if>
									<c:if test="${accusevo.dealCond}">已處理</c:if></td>
								<c:if test="${!accusevo.dealCond}" >
									<td>
									<form METHOD="post" ACTION="<%=request.getContextPath()%>/CheckAccuse.do">
										<input type="text" name="DealDetail"  placeholder="請輸入原因">
										<input type="submit" name="action"  value="通過">
										<input type="submit" name="action" value="不通過">
										<input type="hidden" name="AccuseID" value="${accusevo.accuseID}">
									</form>
									</td>
								</c:if>
								<c:if test="${accusevo.dealCond}" ><td></td></c:if>
								<td>${accusevo.dealDetail}</td>
							</tr>	
						</c:forEach>
					</table>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
