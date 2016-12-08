<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.baseobject.GeoLocation"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%	
String AccessKey = RestUtils.callGetValidAccessKey();
String uid = request.getParameter("UID");
String curLoc=null;
String city=null;
WeChatUser wcu;
if (session.getAttribute("location") == null) {
	GeoLocation loc = RestUtils.callGetDBUserGeoInfo(uid);
	wcu = RestUtils.getWeChatUserInfo(AccessKey, uid);
	String message = RestUtils.getUserCurLocStrWithLatLng(loc.getLAT(),loc.getLNG());
	 JSONObject demoJson = new JSONObject(message);
     if(demoJson.has("result")){
  	    JSONObject JsonFormatedLocation = demoJson.getJSONObject("result");
  	 	curLoc = JsonFormatedLocation.getString("formatted_address");
  	 	city = JsonFormatedLocation.getJSONObject("addressComponent").getString("city");
     }
	session.setAttribute("location", curLoc);
	session.setAttribute("city", city);
	session.setAttribute("wcu", wcu);
} else {
	wcu = (WeChatUser) session.getAttribute("wcu");
	curLoc = (String) session.getAttribute("location");
}
%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta charset="utf-8" />
<title>HPE - Master Data Management</title>
<!-- <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	 -->

<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="hpe" />


<link rel="stylesheet" type="text/css" href="../nkang/assets_athena/bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/assets_athena/bootstrap/css/bootstrap-responsive.min.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/assets_athena/font-awesome/css/font-awesome.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/style.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/profile.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/jquery.circliful.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/assets_athena/icomoon/iconMoon.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/style-responsive.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/style-default.css"/>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/CSS/sonhlab-base.css"/>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/CSS/openmes.css"/>
	
<script src="../nkang/js_athena/jquery-1.8.2.min.js"></script>
<script src="../nkang/js_athena/jquery.circliful.min.js"></script>
<script src="../nkang/assets_athena/bootstrap/js/bootstrap.js"></script>
<script	src="../MetroStyleFiles/sweetalert.min.js"></script>

<!-- 
<link rel="stylesheet" type="text/css" href="../nkang/assets_athena/data-tables/DT_bootstrap.css"/>

<script src="../nkang/js_athena/common-scripts.js"></script>
<script src="../nkang/assets_athena/raty/ratyViewJs.js"></script>
<script src="../nkang/js_athena/jquery.feedback_me.js"></script>
<script src="../nkang/assets_athena/data-tables/jquery.dataTables.js"></script>
<script src="../nkang/assets_athena/data-tables/DT_bootstrap.js"></script>
<script src="../nkang/js_athena/typeahead.js"></script>
<script src="../nkang/assets_athena/jquery-ui/jQuery_UI_1_10_3.js"></script>
 -->
<script>
$(function(){
	$('#myStat').circliful();
	$('#myStat1').circliful();
	$('#myStat2').circliful();
	$('#myStat3').circliful();
	$('#myStat4').circliful();
	$('#myStat5').circliful();
	$('#myStat6').circliful();
	$('#myStat7').circliful();
	$('#mySta1').circliful();
	$('#mySta2').circliful();
	$('#mySta3').circliful();
});
$(window).load(function() {
		getWeather();
		getStockData();
		getMDLUserLists();
		$("#tax_submit_button").click(function(){
			jQuery.ajax({
				type : "GET",
				url : "../userProfile/getTax",
				data : {
					taxIncome : $("#taxIncome").val(),
					taxstart : $("#taxstart").val(),
					payment : $("#payment").val()
				},
				cache : false,
				success : function(data) {
					var jsons = eval('(' + data + ')');
					$("#levelcalc").text(jsons.levelcalc);
					$("#nolevelcalc").text(jsons.nolevelcalc);
					swal("Calculate successfully!", "Congratulations!", "success"); 
				}
			});
		});
});
function register() {
	jQuery
	.ajax({
		type : "GET",
		url : "../userProfile/getMDLUserLists",
		data : {
			UID : $('#uid').val()
		},
		cache : false,
		success : function(data) {
			data = data.replace(/:null/g, ':"未注册"');
			data = '{"results":' + data + '}';
			var jsons = eval('(' + data + ')');
			if (jsons.results.length > 0) {
				if(jsons.results[0].role!="未注册"){
					$("#info_imgurl").attr("src",$('#userImage').attr('src'));
					$("#info_role span").text( jsons.results[0].role);
					data = data.replace(/:"未注册"/g, ':"未编辑"');
					jsons = eval('(' + data + ')');
					$("#info_username span").html(jsons.results[0].realName+'<img onclick="showRegister()" src="../MetroStyleFiles/edit.png" style="height: 20px; cursor: pointer;padding-left:5px;"/>');
					$("#info_phone").html('<img src="../MetroStyleFiles/group2.png"/>&nbsp;'+jsons.results[0].groupid+'<br/><img src="../MetroStyleFiles/telephone2.png"/>&nbsp;'+jsons.results[0].phone+'<br/><img src="../MetroStyleFiles/email2.png"/>&nbsp;'+jsons.results[0].email);
					$("#info_selfIntro").text(jsons.results[0].selfIntro);
					$('#UserInfo').modal('show');
				}else{
					$("#info_phone").html('');
					$("#info_selfIntro").text('');
					showRegister();
				}
			}else{
				$("#info_phone").html('');
				$("#info_selfIntro").text('');
				showRegister();
			}
		}
	});
	}
	
function showRegister(){
	$('#UserInfo').modal('hide');
	$('#registerform').modal('show');
	$.ajax({
		type : "GET",
		url : "../userProfile/getMDLUserLists",
		data : {
			UID : $('#uid').val()
		},
		cache : false,
		success : function(data) {
			data = data.replace(/:null/g, ':"未注册"');
			data = '{"results":' + data + '}';
			var jsons = eval('(' + data + ')');
			if (jsons.results.length > 0) {
				$("#realname").val(jsons.results[0].realName);
				$("#phone").val(jsons.results[0].phone);
				$("#email").val(jsons.results[0].email);
				$("#roleSelect option[value='"+jsons.results[0].role+"']").attr("selected",true);
			    var count=$("#roleSelect option").length;
			    for(var i=0;i<count;i++)  
			       {           if($("#roleSelect").get(0).options[i].text == jsons.results[0].role)  
			          {  
			              $("#roleSelect").get(0).options[i].selected = true;  
			            
			              break;  
			          }  
			      }
			    count=$("#groupSelect option").length;
			    for(var i=0;i<count;i++)  
			       {           if($("#groupSelect").get(0).options[i].text == jsons.results[0].groupid)  
			          {  
			              $("#groupSelect").get(0).options[i].selected = true;  
			            
			              break;  
			          }  
			      }
					    
				$("#selfIntro").val(jsons.results[0].selfIntro);
			}
		}
	});
	$("#registerBtn").click(function(){
		var uid = $("#uid").val();
		var name = $("#realname").val();
		var phone = $("#phone").val();
		var email = $("#email").val();
		//var suppovisor = $("#suppovisor").val();
		var role = $("#roleSelect option:selected").val();
		var group = $("#groupSelect option:selected").val();
		var selfIntro = $("#selfIntro").val();
		
		 var emailFilter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		 var phoneFilter = /^1[0-9]{10}/;
		 if (name.replace(/(^ *)|( *$)/g,'')==''){
			 swal("Registered fail!", "Pls input your correct name information.", "error");
		 }else if (!phoneFilter.test(phone)){
			 swal("Registered fail!", "Pls input your correct phone information.", "error");
		 }else if (!emailFilter.test(email)){
			 swal("Registered fail!", "Pls input your correct E-mail information.", "error");
		 }else if (selfIntro==''){
			 swal("Registered fail!", "Pls input your correct self-introduction information.", "error");
		 }else{
			$.ajax({
				url:"../regist",
				data:{uid:uid,name:name,telephone:phone,email:email,
					role:role,group:group,selfIntro:selfIntro,},
				type:"POST",
				dataType:"json",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				cache:false,
				async:false,
				success:function(result) {
					if(result){
						$('#registerform').modal('hide');
						swal("Registered successfully!", "Congratulations!", "success"); 
					} else {
						swal("Registered fail!", "Pls input your correct information.", "error");
					}
				}
			});
		}
	});
	
}
function getUserInfo(username, headimgurl, openId) {
			$("#info_imgurl").attr("src",headimgurl);
			$("#info_username span").html(username);
			//$("#info_username span").html(username+'<img src="../MetroStyleFiles/edit.png" style="height: 20px; cursor: pointer;padding-left:5px;"/>');
	jQuery
			.ajax({
				type : "GET",
				url : "../userProfile/getMDLUserLists",
				data : {
					UID : openId
				},
				cache : false,
				success : function(data) {
					data = data.replace(/:null/g, ':"未注册"');
					data = '{"results":' + data + '}';
					var jsons = eval('(' + data + ')');
					if (jsons.results.length > 0) {
						$("#info_role span").text( jsons.results[0].role);
						if(jsons.results[0].role!="未注册"){
							$("#info_username span").html(jsons.results[0].realName);
							data = data.replace(/:"未注册"/g, ':"未编辑"');
							jsons = eval('(' + data + ')');
							$("#info_phone").html('<img src="../MetroStyleFiles/group2.png"/>&nbsp;'+jsons.results[0].groupid+'<br/><img src="../MetroStyleFiles/telephone2.png"/>&nbsp;'+jsons.results[0].phone+'<br/><img src="../MetroStyleFiles/email2.png"/>&nbsp;'+jsons.results[0].email);
							$("#info_selfIntro").text(jsons.results[0].selfIntro);
						}else{
							$("#info_phone").html('');
							$("#info_selfIntro").text('');
						}
						$('#UserInfo').modal('show');
					}
				}
			});
}
function getMDLUserLists() {
	jQuery
			.ajax({
				type : "GET",
				url : "../userProfile/getMDLUserLists",
				data : {},
				cache : false,
				success : function(data) {
					data = '{"results":' + data + '}';
					var jsons = eval('(' + data + ')');
					var ul = "";
					ul='<div class="Work_Mates_div_list_div2">'
					+'<span class="total_num"><img src="../MetroStyleFiles/role.png"/>'+ jsons.results.length
					+'</span><div class="clear"></div></div>';
					for (var i = 0; i < jsons.results.length; i++) {
						var temp = jsons.results[i];
						var selfIntro=temp.selfIntro;
						var role=temp.role;
						var workDay=temp.workDay;
						if(selfIntro==null||selfIntro=='null'){
							selfIntro="nothing";
						}else{
							if(selfIntro.length>10){
								selfIntro=(selfIntro.substr(0,12)+'...');
							}
						}
						if(role==null||role=='null'){
							role="";
						}
						if(workDay==null||workDay=='null'||workDay==0){
							workDay="";
						}else{
							workDay='<div style="float:right;margin-top:-45px;background-color:#eee;color:#333;font-size:13px;padding:3px;">'+workDay+' Days</div>';
						}
						var li='	<div class="Work_Mates_div_list_div2">'
							+'                                           	 	<div class="Work_Mates_img_div2">'
							+'                                        			 <img src="'
							+ temp.headimgurl
							+ '" alt="userImage" class="matesUserImage" alt="no_username" onclick="getUserInfo(\''
							+ temp.nickname
							+ '\',\''
							+ temp.headimgurl
							+ '\',\''
							+ temp.openid
							+ '\');"/> '
							+'                                         		</div>'
							+'                                         		<div class="Work_Mates_text_div">'
							+'                                        			 <h2><span  onclick="getUserInfo(\''
							+ temp.nickname
							+ '\',\''
							+ temp.headimgurl
							+ '\',\''
							+ temp.openid
							+ '\');">'
							+ temp.nickname
							+ '</span><span class="role">'
							+role+'</span></h2>'
							+ '<div>'
							+'													<div class="tag">'
							+'														<span>HTML</span>'
							+'													</div>'
							+'													<div class="tag">'
							+'														CSS3'
							+'													</div><br/>'
							+'													<span class="selfIntro">'+selfIntro+'</span>'
							+'												</div>'
							+'                                        		</div>'
							+workDay
							+'                                                <div class="clear"></div>'
							+'                                          </div>';
						ul += li;
					}
					$("#Work_Mates_div").html(ul);
				}
			});
}
function getLocation() {
	//$("#locationImg").attr("src","../MetroStyleFiles/setuplocation.png" );
	$("#locationImg").attr("src", "../MetroStyleFiles/loading.gif");
	jQuery.ajax({
		type : "GET",
		url : "../userProfile/getLocation",
		data : {
			uid : $("#uid").val()
		},
		cache : false,
		success : function(data) {
			if (data != "")
				$("#location").text(data);
			$("#locationImg").attr("src",
					"../MetroStyleFiles/setuplocation.png");
		}
	});
}
function getWeather() {
	if ($("#refreshImg") != null && $("#refreshImg") != undefined)
		$("#refreshImg").attr("src", "../MetroStyleFiles/loading.gif");
	jQuery.ajax({
				type : "GET",
				url : "../userProfile/getWeather",
				data : {},
				cache : false,
				success : function(data) {
					var jsons = eval('(' + data + ')');
					if (jsons.status == 'success'
							&& jsons.results.length > 0) {
						var tbody;
						for (var i = 0; i < jsons.results[0].weather_data.length; i++) {
							var temp = jsons.results[0].weather_data[i];
							var tr = "<tr>";
							if (i == 0) {
								var dateT = temp.date;
								var start = dateT.lastIndexOf("：");
								var end = dateT.lastIndexOf(")");
								dateT = dateT.substring(start + 1, end);
								tr += '<td colspan="3" align="center"><div  style="float:left;padding-bottom:10px;margin-bottom:-50px;"><img id="refreshImg" src="../MetroStyleFiles/refresh.png" style="height:25px;cursor:pointer;" onclick="getWeather();"/></div><b> <img src="../MetroStyleFiles/temperature.png" style="height:25px;"/>'
										+ dateT
										+ '</b> <div  style="float:right;padding-bottom:10px;margin-bottom:-50px;"><a class="" data-toggle="modal" href="#WeatherDetails"  data-dismiss="modal" aria-hidden="true"><img src="../MetroStyleFiles/details.png" style="height:25px;cursor:pointer;"/> </a></div></td></tr><tr>';
								//tr+='<td colspan="3" align="center"><b>'+jsons.results[0].currentCity+'&nbsp;&nbsp;'+temp.date+'</b> <img src="../MetroStyleFiles/refresh.png" style="height:30px;cursor:pointer;" onclick="getWeather();"/></td></tr><tr>';
								tr += '<td width="20%" align="left">今天</td>';
							} else {
								tr += '<td width="20%" align="left">'
										+ temp.date + '</td>';
							}
							tr += '<td width="55%" align="left"><img src="'+temp.dayPictureUrl +'"/><img src="'+temp.nightPictureUrl +'"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
									+ temp.weather + '</td>';
							tr += '<td width="25%" align="right">'
									+ temp.temperature + ' </td>';
							tr += "</tr>";
							tbody += tr;
						}
						tbody += '<tr><td colspan="3" align="center">'
								+ getNowFormatDate() + '</td></tr>';
						$('#weather').html(tbody);
						//	$('weather_div_loading').css({'display':'none'});
						//	$('weather_div').css({'display':'block'});
						tbody = "";
						for (var i = 0; i < jsons.results[0].index.length; i++) {
							var temp = jsons.results[0].index[i];
							var tr = "<tr>";
							var tipt;
							if (temp.tipt.length > 4) {
								tipt = "紫外线";
							} else {
								tipt = temp.tipt;
							}
							tr += '<td width="15%" align="right" valign="top" ><nobr><b>'
									+ tipt + '：</b></nobr></td>';
							tr += '<td width="85%" align="left">'
									+ temp.des + '</td>';
							tr += "</tr>";
							tbody += tr;
						}
						$('#weather_suggest').html(tbody);
						if ($("#refreshImg") != null
								&& $("#refreshImg") != undefined)
							$("#refreshImg").attr("src",
									"../MetroStyleFiles/refresh.png");
					}
				}
			});
}

	function getStockData(){
		var url = "http://hq.sinajs.cn/list=gb_$ixic,gb_$dji,gb_$inx,gb_hpe,gb_hpq,gb_csc";
		getNewData(url);
		var refreshData = self.setInterval("getNewData('"+url+"')",2000);
	}

   function getNewData(url){
   	var parameters = url.split('=')[1].split(',');
   	var length = url.split('=')[1].split(',').length;
   	var list = new Array();
   	for(var i=0; i<length; i++){
   		list.push("hq_str_"+parameters[i]);
   	}
   	$.ajax({  
           cache : true,  
           url:url,
           type: 'GET', 
           dataType: 'script', 
           timeout: 2000, 
           success: function(data, textStatus, jqXHR){
           	if(textStatus=='success'){
           		var tbody;
                var tr = "<tr>";
   				tr+="<td>证券名称</td><td>现价</td><td>涨幅</td><td>涨跌</td></tr>";
           		for(var i=0;i<list.length;i++){
           			var stockData = eval(list[i]).split(',');
           			tr+="<tr class='stockline'>"+"<td>"+stockData[0].substring(0,26)+"</td>"+"<td class='stockcolor'>"+stockData[1]+"</td>"+"<td id='increase' class='stockcolor'>"+parseFloat(stockData[2])+"%"+"</td>"+"<td class='stockcolor'>"+stockData[4]+"</td></tr>";
           		}
           	} 
			tbody += tr;
			$('#stock').html(tbody); 
			$(".stockline").each(function(){
				if(parseFloat($(this).find('td').eq(2).text())>0){
		             $(this).find('td').eq(1).css("color","red");
		             $(this).find('td').eq(2).css("color","red");
		             $(this).find('td').eq(3).css("color","red");
				} else if(parseFloat($(this).find('td').eq(2).text())<0){
					$(this).find('td').eq(1).css("color","green");
		            $(this).find('td').eq(2).css("color","green");
		            $(this).find('td').eq(3).css("color","green");
				}
			})
			
           }
       });
   }
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	if (hour >= 0 && hour <= 9) {
		hour = "0" + hour;
	}
	if (minute >= 0 && minute <= 9) {
		minute = "0" + minute;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1
			+ strDate + " " + hour + seperator2 + minute;
	//  + seperator2 + date.getSeconds();
	return currentdate;
}
</script>
</head>
<body style="margin: 0px; padding: 0px !important;">
	<input id="uid" type="hidden" value="<%=uid%>" />
	<div class="navbar" style="width: 100%;">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a href="../DQMenu?UID=<%=uid%>"> <img
					src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=015900000053FQo&oid=00D90000000pkXM&lastMod=1438220916000"
					alt="HP Logo" class="HpLogo" />
				</a>
				<ul class="nav pull-right top-menu">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"
						style="font-size: 18px; margin: 0px; padding: 5px 0px;">
							Welcome <span class="username colorBlue" id="username"> <%=wcu.getNickname() %>
						</span>
					</a> <span><a style="float: right;"> <img id="userImage"
								src="<%=wcu.getHeadimgurl() %>" alt="userImage"
								class="userImage" alt="no_username" onclick="register()" />
						</a></span></li>
				</ul>
			</div>
		</div>
	</div>

	<!-- BEGIN CONTAINER -->
	<div id="container" class="row-fluid">
		<!-- BEGIN PAGE -->
		<div id="main-content">
			<div class="BDbg">
				<div class="BDheading">
					<div class="span12">
						<div id="divBoardName" style="dispaly: none" title='LBName'></div>
						<h2>
							<nobr>
								<span class="colorDarkBlue" id="location"><%=curLoc%></span> <span
									style="float: right; margin-right: 10px;" class="colorDarkBlue"
									id="location"> <img
									src="../MetroStyleFiles/setuplocation.png"
									onclick="getLocation();" id="locationImg"
									style="height: 25px; cursor: pointer; margin-top: -5px;" />
								</span>
							</nobr>
						</h2>
					</div>
				</div>
				<div class="row-fluid mtop10">
					<div class="span12">
						<div class="PositionR">
							<img src="../MetroStyleFiles/image/socialHPE.png"
								class="BoardDetailImage" />
						</div>
					</div>
				</div>
			</div>
			<div class="container-fluid">
				<div class="row-fluid mtop20">
					<div class="span12">
						<div class="TABclass">
							<ul class="nav nav-tabs" id="myTabs">
								<li id="liSocialElements" class="active"><a
									href="#SocialElements" data-toggle="tab">Socialization</a></li>
								<li id="liBoardContent"><a href="#BoardContent"
									data-toggle="tab">Recognition</a></li>
								<li id="liWorkMates"><a href="#WorkMates" data-toggle="tab">Work
										Mates</a></li>
							</ul>
							<div class="tab-content" id="dvTabContent" style="border: 0px;">
								<div class="tab-pane" id="BoardContent">
									<div>
										<div class="panel-group" id="accordion">
											<div id="DivLearnings"
												style="text-align: center; padding: 50px 0px;">

												<img src="../MetroStyleFiles/image/Maintenace.gif" />

											</div>
										</div>
									</div>
								</div>
								<div class="tab-pane active" id="SocialElements">
									<!-- start -->
									<div id="WeatherDetails" class="modal hide fade" tabindex="-1"
										role="dialog" aria-labelledby="myModalLabel1"
										aria-hidden="true" data-backdrop="static">
										<div class="modal-header" style="text-align: center;">
											<img src="../MetroStyleFiles/index.png" style="height: 55px;" />
											<img src="../MetroStyleFiles/Close.png" data-dismiss="modal"
												aria-hidden="true"
												style="float: right; height: 25px; cursor: pointer;" />
										</div>
										<div class="modal-body readmoreHpop"
											style="white-space: pre-line; padding: 0px;">
											<table width="95%" align="center" id="weather_suggest"
												style="margin-top: -15px;">
											</table>
										</div>
										<!-- 
										<div class="modal-footer">
											<button class="btnAthena" data-dismiss="modal"
												aria-hidden="true">Cancel</button>
										</div>
										 -->
									</div>
									<!-- end -->
									<!-- 
               		   <div id="weather_div_loading">
               		   <img alt="内容加载中,请稍后..." src="../MetroStyleFiles/loading.gif" height="55" width="55">
               		   		<b>内容加载中,请稍后...</b>
               		   </div> style="display:none"
               		    -->
									<div id="weather_div">
										<table class="Socialization_menu">
											<tr>
												<td><a class="" data-toggle="modal"
													href="#weather_main_div"> <img
														src="../MetroStyleFiles/menu-weather.png" />
														<h4>天气</h4>
												</a></td>
												<td><a class="" data-toggle="modal"
													href="#tax_main_div"> <img
														src="../MetroStyleFiles/menu-tax.png" />
														<h4>税费计算</h4>
												</a></td>
												<td><a class="" data-toggle="modal"
													href="#stock_main_div"> <img
														src="../MetroStyleFiles/menu-stock.png" />
														<h4>股票</h4>
												</a></td>
												<td><img src="../MetroStyleFiles/time_zone.png" />
													<h4>开发中</h4></td>
											</tr>
										</table>
									</div>
								</div>
								<div id="weather_main_div" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="weather_main_div"
									aria-hidden="true" data-backdrop="static">
									<div class="modal-header" style="text-align: center;">
										<h3>
											<b>天气</b>
										</h3>
										<img src="../MetroStyleFiles/Close.png" data-dismiss="modal"
											aria-hidden="true"
											style="float: right; height: 25px; cursor: pointer; margin-top: -40px;" />
									</div>
									<div class="modal-body readmoreHpop"
										style="white-space: pre-line; padding: 0px 10px;">
										<table width="100%" id="weather" style="margin-bottom: -20px;">
										</table>
									</div>
								</div>
								<div id="tax_main_div" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="weather_main_div"
									aria-hidden="true" data-backdrop="static">
									<div class="modal-header" style="text-align: center;">
										<h3>
											<b>税费计算</b>
										</h3>
										<img src="../MetroStyleFiles/Close.png" data-dismiss="modal"
											aria-hidden="true"
											style="float: right; height: 25px; cursor: pointer; margin-top: -40px;" />
									</div>
									<div class="modal-body readmoreHpop"
										style="white-space: pre-line; padding: 0px 10px;">
										<table width="100%" style="margin-bottom: -20px;">
											<tr>
												<td>起征点：</td>
												<td><input type="text" id="taxstart" value="3500" /></td>
											</tr>
											<tr>
												<td>总工资：</td>
												<td><input type="text" id="taxIncome" /></td>
											</tr>
											<tr>
												<td>五险一金：</td>
												<td><input type="text" id="payment" /></td>
											</tr>
											<tr>
												<td colspan="2" style="text-align: center; padding: 0px;">
													<button class="btnAthena EbtnLess"
														style="margin-top: -30px; padding: 0px;"
														id="tax_submit_button">计算</button>
												</td>
											</tr>
											<tr>
												<td>含税级距计算：</td>
												<td><span id="levelcalc"></span></td>
											</tr>
											<tr>
												<td>不含税级距计算：</td>
												<td><span id="nolevelcalc"></span></td>
											</tr>
										</table>
									</div>
								</div>
								<div id="stock_main_div" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="stock_main_div"
									aria-hidden="true" data-backdrop="static">
									<div class="modal-header" style="text-align: center;">
										<!-- <img src="../MetroStyleFiles/Add1.png" data-dismiss="modal"
											aria-hidden="false"
											style="float: left; height: 25px; cursor: pointer; margin-top: 0px;" /> -->
										<h3>
											<b>股票行情</b>
										</h3>
										<img src="../MetroStyleFiles/Close.png" data-dismiss="modal"
											aria-hidden="true"
											style="float: right; height: 25px; cursor: pointer; margin-top: -40px;" />
											
									</div>
									<div class="modal-body readmoreHpop"
										style="white-space: pre-line; padding: 0px 10px;">
										<table width="100%" id="stock" style="margin-bottom: -20px;">
											
										</table>
									</div>
								</div>
								<div id="UserInfo" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel1"
									aria-hidden="true" data-backdrop="static">
									<div class="modal-body readmoreHpop"
										style="white-space: pre-line; padding: 0px;">
										<img src="../MetroStyleFiles/Close2.png" data-dismiss="modal"
											aria-hidden="true"
											style="float: right; height: 27px; cursor: pointer; margin-top: -15px; margin-right: 5px;" />
										<div id="userInfoDiv">
											<img id="info_imgurl"
												src="http://wx.qlogo.cn/mmopen/soSX1MtHexV6ibXOvfzOoeEwjLFW3dyR80Mic1pzmg5b1qV0EFD4aegic9hic5iawRIDgJIImrY0XybC57j16ka4SabDCqy3TTtd2/0"
												alt="userImage" class="matesUserImage2">
											<div id="info_username">
												<span></span>
											</div>
											<div id="info_role">-<span></span>-
											</div>
											<div id="info_phone"></div>
											<div id="info_selfIntro"></div>
																						<div style="width:100%; overflow-x: scroll;padding:0px;">
												<table>
													<tr>
														<td>
				<div style="margin-bottom:-20px;" id="myStat" data-dimension="70" data-text="35%" data-info="" data-width="8" data-fontsize="18" data-percent="35" data-fgcolor="#61a9dc" data-bgcolor="#eee" data-fill="#ddd"></div>
				<span>html</span>
														</td><td>
				<div style="margin-bottom:-20px;"  id="myStat1" data-dimension="70" data-text="35%" data-info="" data-width="8" data-fontsize="18" data-percent="35" data-fgcolor="#61a9dc" data-bgcolor="#333" data-fill="#ddd"></div>
				<span>html</span>
														</td><td>
				<div style="margin-bottom:-20px;"  id="myStat2" data-dimension="70" data-text="35%" data-info="" data-width="8" data-fontsize="18" data-percent="35" data-fgcolor="#61a9dc" data-bgcolor="#666" data-fill="#ddd"></div>
				<span>html</span>
														</td><td>
				<div style="margin-bottom:-20px;"  id="myStat3" data-dimension="70" data-text="35%" data-info="" data-width="8" data-fontsize="18" data-percent="35" data-fgcolor="#61a9dc" data-bgcolor="#eee" data-fill="#ddd"></div>
				<span>html</span>
														</td><td>
				<div style="margin-bottom:-20px;"  id="myStat4" data-dimension="70" data-text="35%" data-info="" data-width="8" data-fontsize="18" data-percent="35" data-fgcolor="#61a9dc" data-bgcolor="#eee" data-fill="#ddd"></div>
				<span>html</span>
														</td><td>
				<div style="margin-bottom:-20px;"  id="myStat5" data-dimension="70" data-text="35%" data-info="" data-width="8" data-fontsize="18" data-percent="35" data-fgcolor="#61a9dc" data-bgcolor="#eee" data-fill="#ddd"></div>
				<span>html</span>
														</td><td>
				<div style="margin-bottom:-20px;"  id="myStat6" data-dimension="70" data-text="35%" data-info="" data-width="8" data-fontsize="18" data-percent="35" data-fgcolor="#61a9dc" data-bgcolor="#eee" data-fill="#ddd"></div>
				<span>html</span>
														</td><td>
				<div style="margin-bottom:-20px;"  id="myStat7" data-dimension="70" data-text="35%" data-info="" data-width="8" data-fontsize="18" data-percent="35" data-fgcolor="#61a9dc" data-bgcolor="#eee" data-fill="#ddd"></div>
				<span>html</span>
														</td>
													</tr>
												</table>											
												
											</div>
											
										</div>
									</div>
								</div>


							
								<div id="registerform" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel1"
									aria-hidden="true" data-backdrop="static">
									<div class="modal-body readmoreHpop"
										style="white-space: pre-line; padding: 0px;">
										<img src="../MetroStyleFiles/Close2.png" data-dismiss="modal"
											aria-hidden="true"
											style="float: right; height: 27px; cursor: pointer; margin-top: -15px; margin-right: 5px;" />
												<!-- <form id="registerFormSubmit" autocomplete="on"> -->
												    <table id="tableForm" style="margin-top:-20px;">
												    <tr>
												        <td class="tdText"><img class='imgclass' src='../MetroStyleFiles/username2.png'/></td>
												        <td class="tdInput">
												          <input type="text" placeholder="请输入真实姓名" id="realname"  pattern="^[\u4E00-\u9FA0\s]+$|^[a-zA-Z\s]+$" required/>
												        </td>
												      </tr>
												      <tr>
												        <td class="tdText"><img class='imgclass' src='../MetroStyleFiles/telephone2.png'/></td>
												        <td class="tdInput">
												          <input type="text" placeholder="请输入电话号码" id="phone" pattern="^1[34578]\d{9}$" required/>
												        </td>
												      </tr>
												      <tr>
												        <td class="tdText"><img class='imgclass' src='../MetroStyleFiles/email2.png'/></td>
												        <td>
												          <input class="inputClass" placeholder="请输入邮箱地址" type="email" id="email" required/>
												        </td>
												      </tr>
												      <tr>
												        <td class="tdText"><img class='imgclass' src='../MetroStyleFiles/role2.png'/></td>
												        <td>
												          <select id="roleSelect">
															<option selected="selected">Contributor</option> 
															<option>Team Lead</option>
															<option>Technical Lead</option>
															<option>Project Mananger</option> 
															<option>Other</option>
														</select>
												        </td>
												      </tr>
												      <tr>
												        <td class="tdText"><img class="imgclass" src="../MetroStyleFiles/group2.png"/></td>
												        <td>
												         <select id='groupSelect'>
															<option selected="selected">Garden</option>
															<option>Achi</option>
															<option>NKang</option>
															<option>Channing</option>
															<option>Other</option>
														</select>
												        </td>
												      </tr>
												      <tr>
												        <td class="tdText"><img class="imgclass" src="../MetroStyleFiles/selfIntro2.png"/></td>
												        <td>
												          <input class="inputClass" type="text" placeholder="请输入个人简介" id="selfIntro" required/>
												        </td>
												      </tr>
												 </table>
											    <button class="btnAthena EbtnLess" id="registerBtn">在一起吧</button>
										<!-- 	</form>  -->
									</div>
								</div>

								<div class="tab-pane" id="WorkMates">
									<div class="Work_Mates_div2" id="Work_Mates_div">
									</div>
								</div>
							</div>
						</div>
					</div>





				</div>
			</div>
		</div>
		<!-- END PAGE -->
	</div>

	<!-- Modal PAGE Start-->
	<div id="DivMyAlter" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
		<div class="modal-header">
			<h3>Error Message</h3>
		</div>
		<div class="modal-body">
			<div class="span5 ml0" id="myAlterMessage"></div>
		</div>
		<div class="modal-footer">
			<button class="btnAthena EbtnLess" data-dismiss="modal"
				aria-hidden="true">OK</button>
		</div>
	</div>
	<!--
	<div id="DivMyConfirm" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
		<div class="modal-header">
			<h3>Confirm Message</h3>
		</div>
		<div class="modal-body">
			<div class="span5 ml0" id="myConfirmMessage"></div>
		</div>
		<div class="modal-footer">
			<button class="btnAthena EbtnLess" data-dismiss="modal"
				aria-hidden="true">Cancel</button>
			<button class="btnAthena EbtnLess" id="registerBtn">Submit</button>
		</div>
	</div>
 Modal PAGE End-->

	<!-- BEGIN FOOTER -->
	<div id="footer">
		<span><nobr>© Hewlett-Packard Enterprise Development
				Company, L.P. | HP Restricted </nobr></span>
	</div>
	<!-- END FOOTER -->
</body>
</html>