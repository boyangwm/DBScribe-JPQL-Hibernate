<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.restaurant.model.*"%>
<%@ page import="com.owner.model.*"%>
<%@ page import="com.dish.model.*"%>
<%@ page import="com.ordercond.model.*"%>
<%@ page import="com.kindlist.model.*"%>
<%@ page import="com.restkind.model.*"%>
<%@ page import="com.ad.model.*"%>
<%@ page import="com.accuse.model.*"%>
<%@ page import="com.restdiscuss.model.*"%>
<%@ page import="java.util.*"%>
<%@page import="java.sql.Date"%>
<%
	OwnerService ownerservice = new OwnerService();
	OwnerVO ownerVO = (OwnerVO) session.getAttribute("ownerVO");
	String ownacc = ownerVO.getOwnAcc();
	OwnerVO OwnerVO = ownerservice.getByOwnAcc(ownacc);
	pageContext.setAttribute("ownerVO",ownerVO);

	OrderCondService OCservice = new OrderCondService();
	List<OrderCondVO> orderCondList = OCservice.getAll();
	pageContext.setAttribute("orderCondList", orderCondList);

	int cityNum = 0;
	String city="";
	Set<RestaurantVO> restaurantVOs = ownerVO.getRestaurants();
	for(RestaurantVO rvo: restaurantVOs ){
		city = rvo.getRestCity();
	}
	String[] cityArray = {"台北市","基隆市","新北市","連江縣","宜蘭縣","新竹市","新竹縣","桃園縣","苗栗縣","台中市",
			"彰化縣","南投縣","嘉義市","嘉義縣","雲林縣","台南市","高雄市","澎湖縣","金門縣","屏東縣",
			"台東縣","花蓮縣"};
	for(int j=0;j<22;j++){
		if(cityArray[j] == city) {cityNum=j; break;}
	}
	pageContext.setAttribute("cityNum",cityNum);
	
	RestKindDAO ro = new RestKindDAO();
	List<RestKindVO> rvos = ro.getByRest(restaurantVOs.iterator().next().getRestID());
	pageContext.setAttribute("rvos",rvos);
	
	KindlistDAO ko = new KindlistDAO();
	List<KindlistVO> kvos = ko.getAll();
	pageContext.setAttribute("kvos",kvos);
	
	
	
	ADservice aDservice = new ADservice();
	AdVO adVO = aDservice.getByRestID(restaurantVOs.iterator().next().getRestID());
	pageContext.setAttribute("adVO",adVO);
	
	
	DishService dishsvc = new DishService();
	List<DishVO> dishList = dishsvc.getAllByRestID(restaurantVOs.iterator().next().getRestID());
	pageContext.setAttribute("dishList",dishList);
	
	AccuseService accuseService = new AccuseService();
	List<AccuseVO> accuseList = accuseService.getByRestID(restaurantVOs.iterator().next().getRestID());
	pageContext.setAttribute("accuseList",accuseList);
	
	RestDiscussDAO restdisdao = new RestDiscussDAO();
	List<RestDiscussVO> restdislist = restdisdao.getAll();
	pageContext.setAttribute("restdislist",restdislist);
	
	pageContext.setAttribute("RESTID",restaurantVOs.iterator().next().getRestID());
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/bootstrap/css/style.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/Css/eeit76-design.css" rel="stylesheet">

<style type="text/css">
body {
    font-family: "Open Sans","Helvetica Neue",Helvetica,Arial,sans-serif;
    background: url('../Image/backgroundSun2.png') no-repeat bottom right fixed;
}
.map-canvas {
	width: 800px;
	height: 400px;
	margin: 10px 0;
}
#showOrders, #showDishes, #addDishes
{
	float:left;
	margin:5px auto;
	height:100%;
}
#manageRest, #manageKind, #manageArea{
	float:left;
	margin:50px;
}
#addDishes, #manageKind, #manageArea{
	margin-left: 50px;
}
.pointer{
	cursor:pointer;
}
.lead {
  font-size: 14px;
}
.box{
padding: 10px 20px 20px 20px;
border-radius:10px;
}
</style>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/Script/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/scripts.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-table.min.js"></script>
<script src="<%=request.getContextPath()%>/Script/countyAreaSelector.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/locales/bootstrap-datetimepicker.zh-TW.js" charset="UTF-8"></script>
<title>日日食便當網</title>
</head>
<body>
<div id="wrapper">
	<!-- Sidebar -->
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">
                <a href="<%=request.getContextPath()%>/index.jsp">
                	日日食便當網
                </a>
            </li>
            <li>
                <a href="#viewOrders">檢視訂單</a>
            </li>
            <li>
                <a href="#manageRest">管理店家</a>
            </li>
            <li>
                <a href="#manageDish">管理菜單</a>
            </li>
            <li>
                <a href="#manageAd">管理廣告</a>
            </li>
            <li>
                <a href="#manageSelf">管理個人資料</a>
            </li>
            <li>
                <a href="#listAccuse">檢舉列表</a>
            </li>
            <li>
                <a href="<%=request.getContextPath()%>/generalService/restaurant.jsp?restID=${RESTID}">店家頁面</a>
            </li>
        </ul>
    </div>
    <!-- /#sidebar-wrapper -->
    
    <!-- Page Content -->
    <a name="viewOrders"></a>
	<div id="page-content-wrapper">
		<!-- Header -->
    <div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">
                        <h2>檢視訂單</h2>
                        <hr class="section-heading-spacer">
							<input type="button" id="getAll_For_Display" value="全部">
							<input type="text" id="orderDatepicker" readonly>
							<input type="button" id="get_By_Expect" value="取餐日期">
							<select id="condSelect">
							<c:forEach var="orderCond" items="${orderCondList}">
								<option id="${orderCond.orderCondID}">${orderCond.orderCond}</option>
							</c:forEach>
							</select>
							<input type="button" id="get_By_Cond" value="訂單狀態">
							姓:<input type="text" maxlength="10" size="4" id="lastName">
							名:<input type="text" maxlength="10" size="8" id="firstName">
							<input type="button" id="get_By_Name" value="姓名">
							<input type="text" maxlength="15" size="12" id="phone">
							<input type="button" id="get_By_Phone" value="電話">
							<div id="map-canvas"></div>
							<div id="showOrders">
								<select id='cond' hidden='true'><c:forEach var='orderCond' items='${orderCondList}'>
								<option id='${orderCond.orderCondID}'>${orderCond.orderCond}</option></c:forEach></select>
								<input type='button' id='change' value='修改訂單狀態' hidden='true'>
								<input type='button' id='response' value='輸入回覆' data-toggle='modal' data-target='#updateResponse' hidden='true'>
								<input type='button' id='commitAccuse' value='檢舉' data-toggle='modal' data-target='#sendAccuse' hidden='true'>
								<table id='sum' class='table table-striped' style='display: none;'>
									<thead>
										<tr><th><input type='checkbox' id='checkToggle'></th>
											<th></th><th>訂單編號</th><th>總價</th><th>送餐地址</th><th>顧客姓名</th><th>聯絡電話</th>
											<th>期望送達時間</th><th>訂單成立時間</th><th>訂單狀態</th><th>檢舉</th></tr>
									</thead>
									<tbody></tbody>
								</table>
								<span id='errorMsgs'></span>
							</div>				
                    </div>         
                </div>
        </div>
        <!-- /.container -->

    </div>
    <!-- /.intro-header -->
    <a  name="manageRest"></a>
    <div class="content-section-a">
            <div class="row">
                <div class="col-lg-5 col-sm-6">
                <h2>管理店家</h2>
                    <hr class="section-heading-spacer">
                    <div class="eeit76-classified-div">
                    <h4>基本資料</h4>
                    	<c:forEach var="restaurantVO" items="${ownerVO.restaurants}">
								<input type="hidden" id="restID" name="restID" value="${restaurantVO.restID}">
								<div><label for="restname">店名:</label><input class="form-control" type="text" id="restname" name="restname" value="${restaurantVO.restName}"><font color='red'></font></div>
								<div><label for="restPhone">市內電話:</label><input class="form-control" type="text" id="restPhone" name="restPhone" value="${restaurantVO.restPhone}"><font color='red'></font></div>
								<div><label for="restCel">手機:</label><input class="form-control" type="text" id="restCel" name="restCel" value="${restaurantVO.restCel}"></div>
								<div><label for="restCity">城市:</label><select class="form-control" id="county" name="restCity" ><option id="restCity"  value="${cityNum}">${restaurantVO.restCity}</option></select><font color='red'></font></div>
								<div><label for="restArea">區域:</label><select class="form-control" id="area"  name="restArea"><option id="restArea" value="${restaurantVO.restArea}">${restaurantVO.restArea}</option></select><font color='red'></font></div>
								<div><label for="restAddr">地址:</label><input class="form-control" type="text" id="restAddr" name ="restAddr"value="${restaurantVO.restAddr}"><font color='red'></font></div>
								<input type="hidden" id="restLatitude" name ="restLatitude"value="${restaurantVO.restLatitude}">
								<input type="hidden" id="restLongitude" name ="restLongitude"value="${restaurantVO.restLongitude}">		
								<div><label for="midday">白天最後訂餐時間:</label><input class="form-control" type="text" id="midday" name ="midday" value="${restaurantVO.lastOrder_midday}" readonly><font color='red'></font></div>
								<div><label for="night">晚上最後訂餐時間:</label><input class="form-control" type="text" id="night" name="night" value="${restaurantVO.lastOrder_night}" readonly><font color='red'></font></div><br>
								<div><input type="hidden" id="ownacc" name="ownacc" value="${ownerVO.ownAcc}">
								<input type="button" class="btn btn-default" id="updateRest" name="updateRest" value="送出修改"></div>						
							</c:forEach>
                    </div>
                </div>
                <div class="col-lg-5 col-lg-offset-1 col-sm-6" style="padding-top:30%;">
                	<img class="img-responsive" src="<%=request.getContextPath()%>/Image/img01.png">
                </div>
            </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->
    <div class="content-section-b">

            <div class="row">
                <div class="col-lg-5 col-lg-offset-2 col-sm-push-4  col-sm-6 box">
                    <hr class="section-heading-spacer">
                    <h4>外送地區</h4>
                    <lable for="county2">城市:</lable><select class="form-control" id="county2" name="restCity2" ><option title="${param.restCity}">請選擇</option></select>
					<lable for="area2">區域:</lable><select class="form-control" id="area2" name="restArea2"><option title="${param.restArea}">請選擇</option></select><br>

					<input class="btn btn-default" type="button" id="addServiceArea" value="新增外送地區">

                </div>
                <div class="col-lg-3 col-sm-pull-6  col-sm-6" style="padding-top:5%">
                    <div id="showTest">
								<c:forEach var="restaurantVO" items="${ownerVO.restaurants}">
									<input type="hidden" name="restID" value="${restaurantVO.restID}"><br>			
									<table id='sum2'>
										<thead>
											<tr>
												<th>點選刪除</th>
												<th>城市</th>
												<th>區域</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach var="serviceAreaVO" items="${restaurantVO.serviceAreas}">
											<tr>
												<td><input type='radio' id='checkToggle' name='deleteradio' value="${serviceAreaVO.serviceAreaID}"></td>
												<td>${serviceAreaVO.city}</td>
												<td>${serviceAreaVO.area}</td>
											</tr>
										</c:forEach>
										</tbody>								
									</table>				
									<br>
								</c:forEach>
								</div>
								<input class="btn btn-default" type="button" value="刪除" name="deleteServiceArea" id="deleteServiceArea">
                    
                </div>
            </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-b -->
    <div class="content-section-a">
            <div class="row">
                <div class="col-lg-6 col-sm-6">
                    <hr class="section-heading-spacer">
                    <h4>店家種類</h4>
                    <c:forEach var="restaurantVO" items="${ownerVO.restaurants}">
							    <input type="hidden" name="restid" value="${restaurantVO.restID}">
							            現有種類:
							    <br>
							    <div id="showKind">
								    <c:forEach var="rvo" items="${rvos}">	    
										${rvo.kindlistVO.kindName} 
									</c:forEach>
								    <br>
							    </div>
							    <hr>
							    <c:forEach var="kvo" items="${kvos}">							
							      	<label><input type="checkbox" id="${kvo.kindID}" value="${kvo.kindID}" name="kind">${kvo.kindName}</label>
								</c:forEach>
								<input class="btn btn-default" type="button" id="addRestKind" value="送出">
							</c:forEach>
                </div>
                <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                </div>
            </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->
<a  name="manageDish"></a>
<div class="content-section-b">
<div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">
                        <h2>管理菜單</h2>
                        <hr class="section-heading-spacer">
						<div id="showDishes"><font id="showDishError" color='red'></font>
								<table id="dishTable" data-height="500" >
									<thead>
										<tr>
											<th data-field="dishName">品名</th>
											<th data-field="price">價錢</th>
											<th data-field="specialPrice">特價</th>
											<th data-field="startDate">特價開始日</th>
											<th data-field="endDate">特價結束日</th>
											<th data-field="updateDish">修改</th>
											<th data-field="removeDish">刪除</th>
										</tr>
									</thead>
								</table>
							</div>
							<div id="addDishes" class="box">
								<table class="table">
									<thead>
										<tr><th colspan="2">新增菜單</th></tr>
									</thead>
									<tbody>
										<tr>
											<td>品名:</td>
											<td><input type="text" placeholder="請輸入品名" id="dishName"/><font color='red'></font>
											</td>
										</tr>
										<tr>
											<td>價錢:</td>
											<td><input type="text" id="price"/><font color='red'></font></td>
										</tr>	
										<tr>
											<td>特價:</td>
											<td><input type="text" id="specialPrice" /><font color='red'></font></td>
										</tr>
										<tr>
											<td>開始日期:</td>
											<td><input type="text" id="startDate" readonly/>(yyyy-MM-dd)</td>
										</tr>
										<tr>
											<td>結束日期:</td>
											<td><input type="text" id="endDate" readonly/>(yyyy-MM-dd)<font color='red'></font></td>
										</tr>
									</tbody>
								</table>
								<input class="btn btn-default" type="button" value="送出新增" id="addDishButton">
								<div>
								<br>
									<form name="form" action="" method="POST" enctype="multipart/form-data">
										<table class="table">
											<thead><tr><th>批量新增菜單</th></tr></thead>
											<tbody>
												<tr><td><input name="fileToUpload" type="file" id="fileToUpload"></td></tr>
											</tbody>
										</table>
										<input id="uploadfile" class="btn btn-default" type="button" value="上傳Excel" onclick="ajaxFileUpload();return false;">
									</form>
								</div>
							</div>	
                    </div>
                </div>

        </div>
</div>
<a name="manageAd"></a>
<div class="content-section-a">
            <div class="row">
                <div class="col-lg-5 col-lg-offset-2 col-sm-push-4  col-sm-6">
                	<h2>管理廣告</h2>
                    <hr class="section-heading-spacer">
                    <div class="eeit76-classified-div">
                    	<form enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/addAD.do">
								<lable for="adDescription">廣告描述:</lable><input class="form-control" id="adDescription" type="text" name="Context"  value="${param.Context}" >
									   <font color='red'>${errorMsgs.Context}</font>
								<lable for="adFile">選擇圖片:</lable><input class="form-control" id="adFile" type="file" name="image"  />
									   <font color='red'>${errorMsgs.fileName}</font>
									   <font color='red'>${errorMsgs.fileNameerr}</font>
								<lable for="startAd">開始日期:</lable><input class="form-control" id="startAd" type="text" name="StartDate"  value="${param.StartDate}" readonly>
									   <font color='red'>${errorMsgs.StarDate}</font>
								<lable for="endAd">結束日期:</lable><input class="form-control" type="text" id="endAd" name="EndDate"  value="${param.EndDate}" readonly>
									   <font color='red'>${errorMsgs.EndDate}</font><br>  
								<input class="btn btn-default" type="submit" value="送出" id="sendAdvertisement"/>
							</form>
                    </div>
                  </div> 
                    <div class="col-lg-5 col-sm-pull-6  col-sm-5" style="padding-top:2%">
                    <hr class="section-heading-spacer">
                    	<h2>現有廣告</h2>
                    	<c:if test="${adVO.treatCaseVO.treatID == 1}"><h3>待審核</h3><img style="max-width: 300px" alt="" src="${pageContext.servletContext.contextPath}/getImage?TreatID=1&RestID=${adVO.restID}"></c:if>
                    	<c:if test="${adVO.treatCaseVO.treatID == 2}"><h3>已通過</h3><img style="max-width: 300px" alt="" src="${pageContext.servletContext.contextPath}/getImage?TreatID=2&RestID=${adVO.restID}"></c:if>
                    	<c:if test="${adVO.treatCaseVO.treatID == 3}"><h3>未通過</h3>原因:${adVO.treatDetail}<img style="max-width: 300px" alt="" src="${pageContext.servletContext.contextPath}/getImage?TreatID=3&RestID=${adVO.restID}"></c:if>
                    </div>
                 
            </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->
<a name="manageSelf"></a>
<div class="content-section-b">
            <div class="row">
                <div class="col-lg-5 col-lg-offset-2 col-sm-push-4  col-sm-6 box">
                <h2>管理個人資料</h2>
                    <hr class="section-heading-spacer">
                    <div class="eeit76-classified-div">
                    	<label for="ownLastName">姓:</label><input class="form-control" type="text" id="ownLastName"name ="ownLastName" value="${ownerVO.ownLastName}">
								<label for="ownFirstName">名:</label><input class="form-control" type="text" id="ownFirstName" name ="ownFirstName" value="${ownerVO.ownFirstName}">
								<label for="ownEmail">信箱:</label><input class="form-control" type="text" id="ownEmail" name ="ownEmail" value="${ownerVO.ownEmail}">
								<br>
								<label for="ownPwd">請輸入舊密碼以確認此次修正:</label><input class="form-control" type="password" name="ownPwd" id="ownPwd"><br>
								<input class="btn btn-default" type="button" id="updateOwner" value="修改資料">
							
                    </div>
                </div>
                <div class="col-lg-2 col-sm-pull-6  col-sm-4" style="padding-top:5%">
                <hr class="section-heading-spacer">
                <h4>修改密碼</h4>
                	<label for="oldownPwd">原有密碼:</label><input class="form-control" type="password" id="oldownPwd">
							<label for="newownPwd1">新密碼:</label><input class="form-control" type="password" id="newownPwd1">
							<label for="newownPwd2">確認新密碼:</label><input class="form-control" type="password" id="newownPwd2"><br>
							<input class="btn btn-default" type="button" id="updateOwnPwd" value="修改密碼">
                </div>
            </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-b -->
<a name="listAccuse"></a>
<div class="content-section-a">
            <div class="row">
                <div class="col-lg-6">
                    <div class="intro-message">
                        <h2>檢舉列表</h2>
                        <hr class="section-heading-spacer">
								<table id="accuseTable" data-height="400" >
									<thead>
										<tr>
											<th data-field="accuseID">檢舉編號</th>
											<th data-field="OSRDID">留言或訂單編號</th>
											<th data-field="memberAcc">會員</th>
											<th data-field="reason">事由</th>
											<th data-field="dealCond">處理狀況</th>
											<th data-field="dealDetail">描述</th>											
										</tr>
									</thead>
								</table>
                    </div>
                </div>

        </div>
        <!-- /.container -->

    </div>
</div>
	<div class="modal fade" id="updateResponse" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content" style="width:300px; margin:20px auto;">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">回覆</h4>
	      </div>
	      <div class="modal-body">
	        <textarea id="responseTA" cols="30" rows="5" maxlength="60"></textarea>
	      </div>
	      <div class="modal-footer">
	        <button type="button" data-dismiss="modal">取消</button>
	        <button type="button" id="commitResponse">確認</button>
	      </div>
	    </div>
	  </div>
	</div>
	<div class="modal fade" id="sendAccuse" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content" style="width:300px; margin:20px auto;">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">檢舉</h4>
	      </div>
	      <div class="modal-body">
	        <textarea id="accuseOS" cols="30" rows="5" maxlength="100"></textarea>
	      </div>
	      <div class="modal-footer">
	        <button type="button" data-dismiss="modal">取消</button>
	        <button type="button" id="sureAccuse">確認</button>
	      </div>
	    </div>
	  </div>
	</div>
</div>
<script type="text/javascript">
function ajaxFileUpload()
{
    //這部份可以省略，或者是撰寫當 ajax 開始啟動需讀取圖片，完成之後移除圖片
//     $("#loading")
//     .ajaxStart(function(){
//         $(this).show();
//     })
//     .ajaxComplete(function(){
//         $(this).hide();
//     });
    $.ajaxFileUpload
    (
        {
            url:'UploadExcelServlet', 
            secureuri:false,
            fileElementId:'fileToUpload',
            dataType: 'json',
            success: function (data, status)
            {
                if(typeof(data.error) != 'undefined')
                {
                    if(data.error != '')
                    {
                    	var dishName = (data.error[0].dishName==undefined)?"":data.error[0].dishName;
                    	var price = (data.error[0].price==undefined)?"":data.error[0].price;
                        alert(dishName+","+price);
                    }
                }else{
                 	for (var i = 0; i < data.msg.length; i++) {
                 		var specialPrice = (data.msg[i].specialPrice == undefined)?"":data.msg[i].specialPrice;
     					var startDate = (data.msg[i].startDate == undefined)?"":data.msg[i].startDate;
     					var endDate = (data.msg[i].endDate == undefined)?"":data.msg[i].endDate;
     					var dishData = [{"dishID":data.msg[i].dishID,"dishName":"<input type='hidden' value='"+data.msg[i].dishID+"' title='dishTitle"+data.msg[i].dishID+"'>"+data.msg[i].dishName,
     						"price":data.msg[i].price,"specialPrice":specialPrice,"startDate":startDate,"endDate":endDate,
     						"updateDish":"<input type='button' class='updateDish' value='修改'>","removeDish":"<input type='button' class='removeDish' value='刪除'>"}];
     					$('#dishTable').bootstrapTable('append',dishData);
					}
                }
            },
            error: function (data, status, e)
            {
                alert(e);
            }
        }
    )
    return false;
}
$(function(){
	$('#orderDatepicker').datetimepicker({
		format: "yyyy年MMdd日",
        language:  'zh-TW',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		pickerPosition: "bottom-left",
		pickerReferer:"input"
    });
	
	$('#night').datetimepicker({
		format: "hh:ii:ss",
        language:  'zh-TW',
        weekStart: 1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 0,
		maxView: 1,
		minuteStep: 30,
		forceParse: 0,
		pickerPosition: "bottom-left",
		pickerReferer:"input"
    });
	
	$('#midday').datetimepicker({
		format: "hh:ii:ss",
        language:  'zh-TW',
        weekStart: 1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 0,
		maxView: 1,
		minuteStep: 30,
		forceParse: 0,
		pickerPosition: "bottom-left",
		pickerReferer:"input"
    });
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth()+1;
	var date = now.getDate();
	$('#startDate').datetimepicker({
		format: "yyyy年MMdd日",
        language:  'zh-TW',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		startDate: new Date(),
		endDate: new Date(year,month+1,date),
		pickerPosition: "bottom-left",
		pickerReferer:"input"
    });
	var startYear ;
	var startMonth ;
	var startDate ;
	$('#startDate').datetimepicker().on('changeDate',function(ev){
		var selectedStartDate = $('#startDate').val();
		console.log(selectedStartDate);
		startYear = selectedStartDate.substr(0,4);
		startMonth = selectedStartDate.substr(5,2);
		startDate = selectedStartDate.substr(8,2);
		console.log(startYear+','+startMonth+','+startDate);
		$('#endDate').datetimepicker('setStartDate',startYear +'-'+ startMonth +'-'+ startDate);
		
	});
	
	$('#endDate').datetimepicker({
		format: "yyyy年MMdd日",
        language:  'zh-TW',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		pickerPosition: "bottom-left",
		pickerReferer:"input"
// 		startDate: new Date(startYear,startMonth-1,startDate),
// 		endDate: new Date(startYear,startMonth,startDate),
    });
	
	var adnow = new Date();
	var adyear = adnow.getFullYear();
	var admonth = adnow.getMonth()+1;
	var addate = adnow.getDate();
	$('#startAd').datetimepicker({
		format: "yyyy年MMdd日",
        language:  'zh-TW',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		startDate: new Date(),
		endDate: new Date(year,month+1,date),
		pickerPosition: "bottom-left",
		pickerReferer:"input"
    });
	var adstartYear ;
	var adstartMonth ;
	var adstartDate ;
	$('#startAd').datetimepicker().on('changeDate',function(ev){
		var adselectedStartDate = $('#startAd').val();
		console.log(adselectedStartDate);
		adstartYear = adselectedStartDate.substr(0,4);
		adstartMonth = adselectedStartDate.substr(5,2);
		adstartDate = adselectedStartDate.substr(8,2);
		console.log(adstartYear+','+adstartMonth+','+adstartDate);
		$('#endAd').datetimepicker('setStartDate',adstartYear +'-'+ adstartMonth +'-'+ adstartDate);
		var pickStartDate = new Date(startYear,startMonth,startDate) ;
// 		console.log(pickStartDate.getFullYear() +'-'+ pickStartDate.getMonth() +'-'+ pickStartDate.getDate());
// 		$('#endDate').datetimepicker('setEndDate',pickStartDate.getFullYear() +'-'+ pickStartDate.getMonth()+1 +'-'+ pickStartDate.getDate());
	});
	
	$('#endAd').datetimepicker({
		format: "yyyy年MMdd日",
        language:  'zh-TW',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		pickerPosition: "bottom-left",
		pickerReferer:"input"
// 		startDate: new Date(startYear,startMonth-1,startDate),
// 		endDate: new Date(startYear,startMonth,startDate),
    });
	
	var restID = $('#restID').val();
	//查詢所有訂單
	$('#getAll_For_Display').on('click',function() {
		if($('#map-canvas').hasClass('map-canvas')){$(this).removeClass('map-canvas')}
		var action = "getAll_For_Display";
		var dataObj={"term1":"","term2":""}
		doAjax(action,dataObj);
	});
	//依取餐日期查詢訂單
	$('#get_By_Expect').on('click',function() {
		if($('#map-canvas').hasClass('map-canvas')){$(this).removeClass('map-canvas')}
		var action = "get_By_Expect";
		var selectedDate = $('#orderDatepicker').val();
		var Year = selectedDate.substr(0,4);
		var Month = selectedDate.substr(5,2);
		var Date = selectedDate.substr(8,2);
		var DateFormat = Year+"-"+Month+"-"+Date;
		var dataObj={"term1":DateFormat,"term2":""}
		doAjax(action,dataObj);
	});
	//依訂單狀態查詢訂單
	$('#get_By_Cond').on('click',function() {
		if($('#map-canvas').hasClass('map-canvas')){$(this).removeClass('map-canvas')}
		var action = "get_By_Cond";
		var dataObj={"term1":$('#condSelect').find(':selected').attr("id"),"term2":""}
		doAjax(action,dataObj);
	});
	//依客戶姓名查詢訂單
	$('#get_By_Name').on('click',function() {
		if($('#map-canvas').hasClass('map-canvas')){$(this).removeClass('map-canvas')}
		var action = "get_By_Name";
		var dataObj={"term1":$('#lastName').val(),"term2":$('#firstName').val()}
		doAjax(action,dataObj);
	});
	//依客戶電話查詢訂單
	$('#get_By_Phone').on('click',function() {
		if($('#map-canvas').hasClass('map-canvas')){$(this).removeClass('map-canvas')}
		var action = "get_By_Phone";
		var dataObj={"term1":$('#phone').val(),"term2":""}
		doAjax(action,dataObj);
	});
	var showMap;
	month = (month>=0 && month<=9)? "0"+month:month;
	//查詢功能呼叫AJAX的function
	doAjax = function(action,dataObj){
		if(action == "get_By_Expect" && dataObj.term1 == year+"-"+month+"-"+date){
			showMap = true;
		}else{showMap = false;}
		$.ajax({
			"url" : "treatOrder.jsp",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" : action,
				"restID" : restID,
				"term1" : dataObj.term1,
				"term2" : dataObj.term2
			},
			"success" : function(data) {
				showData(data,showMap);
			}
		});
	}
	//秀出訂單的function
	showData = function(data,showMap) {
		if (!data.errorMsgs) {
			if($("#errorMsgs").text() != ''){$("#errorMsgs").empty();}
			if($('#sum').attr('style') == 'display: none;'){$('#sum').show();}
			if(showMap){
				var mapDiv = document.getElementById('map-canvas');
				$(mapDiv).addClass('map-canvas');
				var restaurant = new google.maps.LatLng($('#restLatitude').val(),$('#restLongitude').val());
				var neighborhoods = [];
				for (var i = 0; i < data.orders.length; i++) {
					neighborhoods.push(new google.maps.LatLng(data.orders[i].orderSum.Latitude,
						data.orders[i].orderSum.Longitude));
				}
				var marker;
				var markers = [];
				var iterator = 0;
				var map;
				var mapOptions = {
					zoom : 13,
					center : restaurant
				};

				map = new google.maps.Map(mapDiv,
						mapOptions);

				marker = new google.maps.Marker({
					position : restaurant,
					map : map,
					title : $('#restname').val(),
					icon : 'http://google.com/mapfiles/kml/paddle/red-stars.png'
				});
				for (var i = 0; i < neighborhoods.length; i++) {
					setTimeout(function() {
						addMarker();
					}, i * 200);
// 					var mapDiv;
					showMap = false;
				}
				$('#sum').on('mouseover','tr[id]',function() {
					tr = $(this).get(0);
					google.maps.event.addDomListener(tr,'mouseover',function() {
										markers[$(this).attr("id")].setIcon("http://google.com/mapfiles/ms/micons/blue-dot.png");
									});
					google.maps.event.addDomListener(tr,'mouseout',function() {
										markers[$(this).attr("id")].setIcon("http://google.com/mapfiles/ms/micons/red-dot.png");
									});
				});
				function addMarker() {
					var temp = new google.maps.Marker({
						position : neighborhoods[iterator],
						map : map,
						draggable : false,
						icon : "http://google.com/mapfiles/ms/micons/red-dot.png",
						animation : google.maps.Animation.DROP
					});
					google.maps.event.addListener(
									temp,
									'mouseover',
									function() {
										temp.setIcon("http://google.com/mapfiles/ms/micons/blue-dot.png");
									});
					google.maps.event
							.addListener(
									temp,
									'mouseout',
									function() {
										temp.setIcon("http://google.com/mapfiles/ms/micons/red-dot.png");
									});
					markers.push(temp);
					console.log(markers);
					iterator++;
				}
			}
			$("#sum>tbody").empty();
			$.each(data,function() {
				for (var i = 0; i < data.orders.length; i++) {
					var expectDatetime = data.orders[i].orderSum.ExpectDatetime;
					var orderDatetime = data.orders[i].orderSum.OrderDatetime;
					$("#sum>tbody").append(
						'<tr id="' + i + '"><td name="checkbox"><input type="checkbox" name="order"></td><td><span class="glyphicon glyphicon-hand-right"></span></td><td>' + data.orders[i].orderSum.OrderSumID + '</td>'
						+ '<td>' + data.orders[i].orderSum.TotalPrice + '</td>'
						+ '<td>' + data.orders[i].orderSum.Address + '</td>'
						+ '<td name="'+data.orders[i].orderSum.MemberAcc+'">' + data.orders[i].orderSum.MemberName + '</td>'
						+ '<td>' + data.orders[i].orderSum.MemberPhone + '</td>'
						+ '<td>' + expectDatetime.substring(0,expectDatetime.lastIndexOf(':')) + '</td>'
						+ '<td>' + orderDatetime.substring(0,expectDatetime.lastIndexOf(':')) + '</td>'
						+ '<td>' + data.orders[i].orderSum.OrderCond + '</td><td></td></tr>')
						.append('<tr style="display: none;"><td colspan="11"><table id="detail'+i+'" class="table"><thead><tr><th>品名</th><th>價格</th><th>數量</th><th>小計</th></tr></thead><tbody></tbody></table></td></tr>');
					for (var j = 0; j < data.orders[i].orderdetails.length; j++) {
						$('#detail'+i+'>tbody').append(
								'<tr><td>' + data.orders[i].orderdetails[j].DishName + '</td>'
								+ '<td>' + data.orders[i].orderdetails[j].Price + '</td>'
								+ '<td>' + data.orders[i].orderdetails[j].Quantity + '</td>'
								+ '<td>' + data.orders[i].orderdetails[j].Subtotal + '</td></tr>'
								);
					}
					var memo = (data.orders[i].orderSum.Memo == undefined)?"":data.orders[i].orderSum.Memo;
					var memoResponse = (data.orders[i].orderSum.MemoResponse == undefined)?"":data.orders[i].orderSum.MemoResponse;
					$('#detail'+i+'>tbody').append('<tr><td colspan="4">備註:'+memo+'</td></tr><tr><td colspan="4">回覆:<span>'+memoResponse+'</span></td></tr>');
				}
			});
		} else {
			if($('#cond').attr('style') != "display: none;"){$('#cond').hide();}
			if($('#change').attr('style') != 'display: none;'){$('#change').hide();}
			if($('#response').attr('style') != 'display: none;'){$('#response').hide();}
			if($('#commitAccuse').attr('style') != 'display: none;'){$('#commitAccuse').hide();}
			if($('#sum').attr('style') != 'display: none;'){$('#sum').hide();}
			$("#errorMsgs").empty().append(data.errorMsgs);
		}
	}
	$('#showOrders').on('mouseenter','#sum>tbody>tr:even',function(){
		$(this).addClass('pointer');
	}).on('mouseleave','#sum>tbody>tr:odd',function(){
		$(this).removeClass('pointer');
	}).on('click','#sum>tbody>tr:even>td[name!="checkbox"]',function(){
		var details = $(this).parents('tr').next();
		if(details.attr('style') == 'display: none;'){
			$(this).parents('tr').find('span').attr('class','glyphicon glyphicon-hand-down');details.show(300);
			}else{
				details.hide(300,function(){
					$(this).prev().find('span').attr('class','glyphicon glyphicon-hand-right');
				});
			}
	}).on('change','#checkToggle',function() {
		$(':checkbox').prop("checked", $(this).prop("checked"));
	}).on('change','input[type="checkbox"]',function() {
		if($('input[name="order"]:checked').length != 0){
			$('#cond').prop("hidden",false);
			$('#change').prop("hidden",false);
			$('#response').prop("hidden",false);
			$('#commitAccuse').prop("hidden",false);
		}else{
			$('#cond').prop("hidden",true);
			$('#change').prop("hidden",true);
			$('#response').prop("hidden",true);
			$('#commitAccuse').prop("hidden",true);
		}
	}).on('click','#change',function(){
		var array = [];
		$('input[name="order"]').each(function() {
			if ($(this).prop("checked") == true) {
				array.push($(this).parents('tr').children('td:eq(2)').text());
			}
		});
		$.ajax({
			"url" : "treatOrder.jsp",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" : "update_By_Cond",
				"restID" : restID,
				"orderSumID" : "[" + array.toString() + "]",
				"orderCondID" : $('#cond').find(':selected').attr("id"),
			},
			"success" : function(data) {
				if(!data.errorMsgs){
					$('input[name="order"]').each(function() {
						if ($(this).prop("checked") == true) {
							$(this).parents('tr').children('td:eq(9)').text(data.orderCond);
						}
					});
				}else{
					alert(data.errorMsgs);
				}
			}
		});
	  });
	$('#sureAccuse').on('click',function(){
		var array1 = [];
		var array2 = [];
		var restID = $('#restID').val();
		$('input[name="order"]').each(function() {
			if ($(this).prop("checked") == true) {
				if($(this).parents('tr').children('td:eq(10)').text()=="已檢舉"){
					alert("已檢舉過訂單");
				}else{
					array1.push($(this).parents('tr').children('td:eq(2)').text());
					array2.push($(this).parents('tr').children('td:eq(5)').attr('name'));
				}
			}
		});
		$.ajax({
			"url" : "treatOrder.jsp",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" : "insertAccuseByOrderSumID",
				"restID" : restID,
				"orderSumID" : "[" + array1.toString() + "]",
				"memberAcc" : "[" + array2.toString() + "]",
				"reason" : $('#accuseOS').val()
			},
			"success" : function(data) {
				if(!data.errorMsgs){
					$('input[name="order"]').each(function() {
						if ($(this).prop("checked") == true) {
							$(this).parents('tr').children('td:eq(10)').text("已檢舉");
						}
					});
					$('#sendAccuse').modal('hide');
					alert("檢舉成功，請至檢舉列表追蹤處理進度");
					for (var i = 0; i < data.success.length; i++) {
						var accuseDatas = [{"accuseID":data.success[i].accuseID,"OSRDID":"訂單:"+data.success[i].OSRDID,"memberAcc":data.success[i].memberAcc,"reason":data.success[i].reason,
							"dealCond":"未處理","dealDetail":data.success[i].dealDetail}];
						$('#accuseTable').bootstrapTable('append',accuseDatas);
					}
				}else{
					alert(data.errorMsgs);
				}
			}
		});
	})
	$('#commitResponse').on('click',function(){
			var array = [];
			var restID = $('#restID').val();
			$('input[name="order"]').each(function() {
				if ($(this).prop("checked") == true) {
					array.push($(this).parents('tr').children('td:eq(2)').text());
				}
			});
			$.ajax({
				"url" : "treatOrder.jsp",
				"type" : "post",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"dataType" : "json",
				"data" : {
					"action" : "update_By_MemoRes",
					"restID" : restID,
					"orderSumID" : "[" + array.toString() + "]",
					"memoResponse" : $('#responseTA').val(),
				},
				"success" : function(data) {
					if(!data.errorMsgs){
						$('input[name="order"]').each(function() {
							if ($(this).prop("checked") == true) {
								$(this).parents('tr').next().find('span').text(data.memoResponse);
							}
						});
						$('#updateResponse').modal('hide');
					}else{
						alert(data.errorMsgs);
					}
				}
			});
		  });
	//修改店家
	$('#updateRest').on('click',function(){
		$.ajax({
			"url" : "restaurant/UpdateRestaurantServlet",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"restID" : $('#restID').val(),
				"restname": $('#restname').val(),
				"restPhone" :$('#restPhone').val(),
				"restCel":$('#restCel').val(),
				"restCity":$('#county').val(),
				"restArea":$('#area').val(),
				"restAddr":$('#restAddr').val(),
				"restLatitude":$('#restLatitude').val(),
				"restLongitude":$('#restLongitude').val(),
				"midday":$('#midday').val(),
				"night":$('#night').val(),
				"ownacc":$('#ownacc').val(),
			},
			"success": function(data){
				var restnameErr = $('#restname').next();
				var restPhoneErr = $('#restPhone').next();
				var countyErr = $('#county').next();
				var areaErr =$('#area').next();
				var restAddrErr =$('#restAddr').next();
				var middayErr =$('#midday').next();
				var nightErr =$('#night').next();
				if(!data.errorMessage){
					restnameErr.text("");
					restPhoneErr.text("");
					countyErr.text("");
					areaErr.text("");
					restAddrErr.text("");
					middayErr.text("");
					nightErr.text("");
					alert(data.info);
				}else{
					if(data.errorMessage.errorName){restnameErr.text(data.errorMessage.errorName);}
					if(data.errorMessage.errorPhone){restPhoneErr.text(data.errorMessage.errorPhone);}
					if(data.errorMessage.errorCity){countyErr.text(data.errorMessage.errorCity);}
					if(data.errorMessage.errorArea){areaErr.text(data.errorMessage.errorArea);}
					if(data.errorMessage.errorAddr){restAddrErr.text(data.errorMessage.errorAddr);}
					if(data.errorMessage.errormidday){middayErr.text(data.errorMessage.errormidday);}
					if(data.errorMessage.errornight){nightErr.text(data.errorMessage.errornight);}
				}
			} 
		})
	})
	//新增外送地區
	$('#addServiceArea').on('click',function(){
		var city = $('#county2>option:selected').text();
		var area = $('#area2').val();
		never = true;
		$('#sum2>tbody>tr').each(function(){
			var cityO = $(this).children('td:eq(1)').text();
			var areaO = $(this).children('td:eq(2)').text();
			if(city == cityO && area == areaO){
				never = false;
			}
		})
		if(never){
			$.ajax({
				"url" : "restaurant/AddServiceAreaServlet",
				"type" : "post",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"dataType" : "json",
				"data" : {
					"action":"add",
					"restID" : $('#restID').val(),
					"restCity2":$('#county2').val(),
					"restArea2":$('#area2').val(),
					
				},
				"success": function(data){
//	 				alert("addServiceArea : " +data.serviceAreas[0].City);
					if(!data.errorMessage){
						showServiceArea(data);
						}else{
							alert(data.errorMessage);
						}
				} 
			})
		}else{
			alert('已有此外送地區');
		}
	})
	showServiceArea = function(data) {	
				$("#sum2>tbody").empty();
				$.each(data,function() {
					for (var i = 0; i < data.serviceAreas.length; i++) {
						$("#sum2>tbody").append(
							'<tr><td><input type="radio" id="checkToggle" name="deleteradio" value="'+data.serviceAreas[i].ServiceAreaID+'"></td>'
 							+ '<td>' + data.serviceAreas[i].City + '</td>'
							+ '<td>' + data.serviceAreas[i].Area + '</td>'
							+ '</tr><br>'		
						)							
					}
				});
			
		}
	//刪除外送區域
	$('#deleteServiceArea').on('click',function(){
		radios = document.getElementsByName("deleteradio"); 	
		var SAID = 0;
		for (i = 0; i < radios.length; i++) { 
			if (radios[i].checked){ 
				SAID = radios[i].value; 
			} 
		} 
// 		alert(SAID);
		
		$.ajax({
			"url" : "restaurant/AddServiceAreaServlet",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action":"delete",
				"restID" : $('#restID').val(),
				"serviceAreaID" : SAID,
			},
			"success": function(data){
// 				alert("deleteServiceArea : " +data.serviceAreas[0].City);
				showServiceArea(data);
			} 
		})
		showServiceArea = function(data) {	
				$("#showTest").empty().append("<br><table id='sum'><thead><th>點選刪除</th><th>城市</th><th>區域</th></thead><tbody></tbody></table>");
				$.each(data,function() {
					for (var i = 0; i < data.serviceAreas.length; i++) {
						$("#sum>tbody").append(
							'<tr><td><input type="radio" id="checkToggle" name="deleteradio" value="'+ data.serviceAreas[i].ServiceAreaID +'"></td>'
 							+ '<td>' + data.serviceAreas[i].City + '</td>'
							+ '<td>' + data.serviceAreas[i].Area + '</td>'
							+ '</tr><br>'		
						)							
					}
				});
			
		}
	})
	//新增餐廳種類
	$('#addRestKind').on('click',function(){
		var array1=[];
		$('input[name="kind"]:checked').each(function(){array1.push($(this).attr("id"));});
// 		alert(array1[0]);
    	
		$.ajax({
			"url" : "restaurant/AddRestKindServlet",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"restID" : $('#restID').val(),
				"kind" :array1.toString(),
			},
			"success": function(data){
// 				alert("addRestKind : ");
				showKind(data);
			} 
		})
		showKind = function(data) {	
				$("#showKind").empty().append("<br><table id='kindlistnow'><tbody></tbody></table>");
				$.each(data,function() {
					for (var i = 0; i < data.serviceAreas.length; i++) {
						$("#kindlistnow>tbody").append(
							data.serviceAreas[i].KindName + ' '
						)							
					}
				});
			
		}
	})
	//管理菜單
	var dishDatas = [];
	'<c:forEach var="dishVO" items="${dishList}">'
	dishDatas.push({"dishName":"<input type='hidden' value='${dishVO.dishID}' title='dishTitle${dishVO.dishID}'>${dishVO.dishName}","price":"${dishVO.price}",
		"specialPrice":"${dishVO.specialPrice}","startDate":"${dishVO.startDate}","endDate":"${dishVO.endDate}",
		"updateDish":"<input type='button' class='updateDish' value='修改'>","removeDish":"<input type='button' class='removeDish' value='刪除'>"});
	'</c:forEach>'
	$('#dishTable').bootstrapTable({
		data : dishDatas
	});
	$(window).resize(function() {
		$('#dishTable').bootstrapTable('resetView');
		$('#accuseTable').bootstrapTable('resetView');
	});
	$('#addDishButton').on('click',function(){
		var selectedEndDate = $('#endDate').val();
		var endYear = selectedEndDate.substr(0,4);
		var endMonth = selectedEndDate.substr(5,2);
		var endDate = selectedEndDate.substr(8,2);
		var endDateFormat = endYear+"-"+endMonth+"-"+endDate;
		var startDateFormat = startYear+"-"+startMonth+"-"+startDate;
		$.ajax({
			"url" : "dish/DishServlet",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" : "add",
				"dishName" : $('#dishName').val(),
				"price": $('#price').val(),
				"specialPrice" :$('#specialPrice').val(),
				"startDate":startDateFormat,
				"endDate":endDateFormat,
				"restID":$('#restID').val(),
			},
			"success": function(data){
				var dishNameErr = $('#dishName').next();
				var priceErr = $('#price').next();
				var specialPriceErr = $('#specialPrice').next();
				var endDateErr =$('#endDate').next();
				if(!data.errorMsgs){
					dishNameErr.text("");
					priceErr.text("");
					specialPriceErr.text("");
					endDateErr.text("");
					var specialPrice = (data.specialPrice == undefined)?"":data.specialPrice;
					var startDate = (data.startDate == undefined)?"":data.startDate;
					var endDate = (data.endDate == undefined)?"":data.endDate;
					var dishData = [{"dishID":data.dishID,"dishName":"<input type='hidden' value='"+data.dishID+"' title='dishTitle"+data.dishID+"'>"+data.dishName,
						"price":data.price,"specialPrice":specialPrice,"startDate":startDate,"endDate":endDate,
						"updateDish":"<input type='button' class='updateDish' value='修改'>","removeDish":"<input type='button' class='removeDish' value='刪除'>"}];
					$('#dishTable').bootstrapTable('append',dishData);
				}else{
					if(data.errorMsgs.Dishname){dishNameErr.text(data.errorMsgs.Dishname);}
					if(data.errorMsgs.Price){priceErr.text(data.errorMsgs.Price);}
					if(data.errorMsgs.Specialprice){specialPriceErr.text(data.errorMsgs.Specialprice);}
					if(data.errorMsgs.EndDate){endDateErr.text(data.errorMsgs.EndDate);}
					if(data.errorMsgs.Exception){alert(data.errorMsgs.Exception);}
				}
			} 
		});
	});
	
	$('#dishTable').on('click','.updateDish',function(){//還沒寫完
		console.log($(this).parents('tr').find('td:eq(0) input').val());
		var dishID = $(this).parents('tr').find('td:eq(0) input').val();
		var oldPrice = $(this).parents('tr').find('td:eq(1)').text();
		var oldSpecialPrice = $(this).parents('tr').find('td:eq(2)').text();
		var oldStartDate = $(this).parents('tr').find('td:eq(3)').text();
		var oldEndDate = $(this).parents('tr').find('td:eq(4)').text();
		$(this).parents('tr').find('td:eq(1)').html("<input type='text' name='uPrice"+dishID+"' value='"+oldPrice+"' style='width:25px' >");
		$(this).parents('tr').find('td:eq(2)').html("<input type='text' name='uSpecialPrice"+dishID+"' value='"+oldSpecialPrice+"' style='width:25px' >");
		$(this).parents('tr').find('td:eq(3)').html("<input type='text' name='uStartDate"+dishID+"' value='"+oldStartDate+"' style='width:80px' >");
		$(this).parents('tr').find('td:eq(4)').html("<input type='text' name='uEndDate"+dishID+"' value='"+oldEndDate+"' style='width:80px' >");
		$(this).parents('tr').find('td:eq(5)').html("<input type='button' class='sendUpdate' value='送出'>");
	});
	
	$('#dishTable').on('click','.sendUpdate',function(){//還沒寫完
		console.log($(this).parents('tr').find('td:eq(0) input').val());
		var dishID = $(this).parents('tr').find('td:eq(0) input').val();
		var dishName = $(this).parents('tr').find('td:eq(0)').text();
		$.ajax({
			"url" : "dish/DishServlet",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" : "updateDish",
				"dishID" : dishID,
				"dishName" : dishName,
				"price": $('input[name="uPrice'+dishID+'"]').val(),
				"specialPrice" :$('input[name="uSpecialPrice'+dishID+'"]').val(),
				"startDate":$('input[name="uStartDate'+dishID+'"]').val(),
				"endDate":$('input[name="uEndDate'+dishID+'"]').val(),
				"restID":$('input[name="uPrice'+dishID+'"]').val(),

			},
			"success": function(data){
				if(!data.errorMsgs){
					console.log("update success :" +data.dishID );
					$('input[name="uPrice'+data.dishID+'"]').parents('td').text(data.price);
					if(data.specialPrice !=null){
						$('input[name="uSpecialPrice'+data.dishID+'"]').parents('td').text(data.specialPrice);
					}else{
						$('input[name="uSpecialPrice'+data.dishID+'"]').parents('td').text("");
					}
					if(data.startDate!=null){
						$('input[name="uStartDate'+data.dishID+'"]').parents('td').text(data.startDate);	
					}else{
						$('input[name="uStartDate'+data.dishID+'"]').parents('td').text("");
					}
					if(data.endDate!=null){
						$('input[name="uEndDate'+data.dishID+'"]').parents('td').text(data.endDate);	
					}else{
						$('input[name="uEndDate'+data.dishID+'"]').parents('td').text("");	
					}
				
					$('input[title="dishTitle'+data.dishID+'"]').parents('tr').find('td:eq(5)').html("<input type='button' class='updateDish' value='修改'>");
				}else{
					$('#showDishError').empty();
					if(data.errorMsgs.Price!=null){alert("error :"+data.errorMsgs.Price);$('#showDishError').append(data.errorMsgs.Price)}
					if(data.errorMsgs.specialPrice!=null){alert("error :"+data.errorMsgs.specialPrice);$('#showDishError').append(data.errorMsgs.specialPrice)}
					if(data.errorMsgs.EndDate!=null){alert("error :"+data.errorMsgs.EndDate);$('#showDishError').append(data.errorMsgs.EndDate)}
					
				}
			} 
			
		});
	});
	
	$('#dishTable').on('click','.removeDish',function(){//還沒寫完
		console.log($(this).parents('tr').find('td:eq(0) input').val());
		var dishID = $(this).parents('tr').find('td:eq(0) input').val();
		$.ajax({
			"url" : "dish/DishServlet",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" : "remove",
				"dishID" : dishID,
				
			},
			"success": function(data){
				console.log("remove success :" +data.dishID );
				$('input[title="dishTitle'+data.dishID+'"]').parents("tr").remove();
				$('#dishTable').bootstrapTable('refresh',{silent: true});
			} 
			
		});
	});
	//修改個人資料
	$('#updateOwner').on('click',function(){
		alert("更新資料中 請稍等");
		$.ajax({
			"url" : "UpdateOwner",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" :"update",
				"ownPwd" : $('#ownPwd').val(),
				"ownLastName": $('#ownLastName').val(),
				"ownFirstName" :$('#ownFirstName').val(),
				"ownEmail":$('#ownEmail').val(),
			},
			"success": function(data){			
				alert(data.info);
			} 
		})
	})
	
	$('#updateOwnPwd').on('click',function(){
		alert("修改密碼中 請稍等");
		$.ajax({
			"url" : "UpdateOwner",
			"type" : "post",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"dataType" : "json",
			"data" : {
				"action" :"updateOwnPwd",
				"oldownPwd" : $('#oldownPwd').val(),
				"newownPwd1": $('#newownPwd1').val(),
				"newownPwd2" :$('#newownPwd2').val(),
			},
			"success": function(data){			
				alert(data.info);
			} 
		})
	})
	
	//檢舉列表
	var accuseDatas = [];
	'<c:forEach var="accuseVO" items="${accuseList}">'
                                                  
	accuseDatas.push({"accuseID":"${accuseVO.accuseID}",
					"OSRDID":"<c:if test='${accuseVO.orderSumID != null}'>訂單:${accuseVO.orderSumID}</c:if><c:if test='${accuseVO.restDiscussID != null}'>留言:<c:forEach var='disVO' items='${restdislist}' ><c:if test='${accuseVO.restDiscussID == disVO.restDiscussID}'>${disVO.discussion}</c:if></c:forEach></c:if>",				
					"memberAcc":"${accuseVO.memberAcc}",
					"reason":"${accuseVO.reason}",
					"dealCond":"<c:if test='${!accuseVO.dealCond}'>未處理</c:if><c:if test='${accuseVO.dealCond}'>已處理</c:if>",
					"dealDetail":"${accuseVO.dealDetail}"
					});
	'</c:forEach>'
	$('#accuseTable').bootstrapTable({
		data : accuseDatas
	});
	
	$('#sendAdvertisement').on('click',function(){
		alert("已送出 請等待審核通過");
	});
})
</script>
</body>
</html>