<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.restaurant.model.*"%>
<%@ page import="com.owner.model.*"%>
<%@ page import="java.util.*"%>
<%
// 	OwnerService  service = new OwnerService();
// 	OwnerVO OwnerVO = service.getByOwnAcc("test123");
//  	pageContext.setAttribute("ownerVO",OwnerVO);

	OwnerService ownerservice = new OwnerService();
	OwnerVO voSession = (OwnerVO) session.getAttribute("ownerVO");
	String ownacc = voSession.getOwnAcc();
	OwnerVO OwnerVO = ownerservice.getByOwnAcc(ownacc);
	pageContext.setAttribute("ownerVO",OwnerVO);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增店家</title>
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/bootstrap/css/style.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-table.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/Css/eeit76-design.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/scripts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/locales/bootstrap-datetimepicker.zh-TW.js" charset="UTF-8"></script>
<script type="text/javascript">
		$(function(){
			var countyS = document.getElementById("county");
			var areaS = document.getElementById("area");
			var temp;
			$.getJSON("../../Datas/taiwan.json", {}, function(data) {
				$.each(data, function() {
					for (var i = 0; i < data.Taiwan.length; i++) {
						var eleO = document.createElement("option");
						eleO.setAttribute("value",i);
						var txt = document.createTextNode(data.Taiwan[i].County);
						eleO.appendChild(txt);
						countyS.appendChild(eleO);			
					}
				});
				countyS.addEventListener("change",getValue,false);
				function getValue (){
					$.each(data, function() {
						$(areaS).empty();
						var county = $("#county").val();
						for (var i = 0; i < data.Taiwan[county].Area.length; i++) {
							var eleO = document.createElement("option");
							eleO.setAttribute("value",i);
							var txt = document.createTextNode(data.Taiwan[county].Area[i]);
							eleO.appendChild(txt);
							areaS.appendChild(eleO);
							eleO.setAttribute("value",data.Taiwan[county].Area[i]);								
						}					
					}); 
				}
			});		
		})
	</script>
</head>
<body>
	<div class="content-section-a" style="width:40% ;margin:50px auto;">
        <div class="row">
			<div id="addRest" class="col-lg-7 col-sm-7"  >
			<h1>請輸入資料</h1>
			<hr class="section-heading-spacer">
	            <div class="eeit76-classified-div">
				<div><label for="restname">店名:</label><input class="form-control" type="text" id="restname" name="restname" value="${param.restname}"><font color='red'></font></div>
				<div><label for="restPhone">市內電話:</label><input class="form-control" type="text" id="restPhone" name="restPhone" value="${param.restPhone}"><font color='red'></font></div>
				<div><label for="restCel">手機:</label><input class="form-control" type="text" id="restCel" name="restCel" value="${param.restCel}"><font color='red'></font></div>
				<div><label for="restCity">城市:</label><select class="form-control" id="county"  name="restCity" ><option id="restCity" value="${param.restCity}">請選擇</option></select><font color='red'></font></div>
				<div><label for="restArea">區域:</label><select class="form-control" id="area"  name="restArea"><option id="restArea" value="${param.restArea}">請選擇</option></select><font color='red'></font></div>
				<div><label for="restAddr">地址:</label><input class="form-control" type="text" id="restAddr" name ="restAddr"value="${param.restAddr}"><font color='red'></font></div>
				<div><label for="midday">白天最後訂餐時間:</label><input class="form-control" type="text" id="midday" name ="midday"value="${param.midday}10:00:00"><font color='red'></font></div>
				<div><label for="night">晚上最後訂餐時間:</label><input class="form-control" type="text" id="night" name="night" value="${param.night}16:00:00"><font color='red'></font></div>
				<input  type="hidden" id="ownacc" name="ownacc" value="${ownerVO.ownAcc}"><br>
					<input type="button" class="btn btn-default"  id="addRestaurant" value="送出">
				</div>
			</div>
		</div>
	</div>
<script>	
$(function(){
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
		pickerPosition: "bottom-left"
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
		pickerPosition: "bottom-left"
	});
	
	
	
	$('#addRestaurant').one('click',function(){
			$.ajax({
				"url" : "AddRestaurantServlet",
				"type" : "get",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"dataType" : "json",
				"data" : {
					"restname": $('#restname').val(),
					"restPhone" :$('#restPhone').val(),
					"restCel":$('#restCel').val(),
					"restCity":$('#county').val(),
					"restArea":$('#area').val(),
					"restAddr":$('#restAddr').val(),
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
						window.location.href="<%=request.getContextPath()%>/owner/owner.jsp";
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
})
</script>
</body>
</html>