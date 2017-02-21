<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.baseobject.GeoLocation"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%@ page import="com.nkang.kxmoment.baseobject.ClientMeta"%>
<%	


String AccessKey = RestUtils.callGetValidAccessKey();
String uid = request.getParameter("UID");
String curLoc=null;
String city=null;
WeChatUser wcu;
session.setAttribute("UID", uid);
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
<title>永佳和塑胶有限公司</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="wonderful" />
<link rel="icon" type="image/x-icon" href="../webitem/yjh.ico">
<link rel="short icon" type="image/x-icon" href="../webitem/yjh.ico">

<link rel="stylesheet" type="text/css" href="../nkang/assets_athena/bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/assets_athena/bootstrap/css/bootstrap-responsive.min.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/assets_athena/font-awesome/css/font-awesome.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/style.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/profile.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/assets_athena/icomoon/iconMoon.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/style-responsive.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/style-default.css"/>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/CSS/sonhlab-base.css"/>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/CSS/openmes.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../nkang/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../nkang/demo.css">
<link rel="stylesheet" type="text/css" href="../nkang/animate.min.css">
<link rel="stylesheet" type="text/css" href="../nkang/autocomplete/jquery-ui.css">
<script type="text/javascript" src="../nkang/easyui/jquery.min.js"></script>
<script type="text/javascript">var $113 = $;</script>
<script type="text/javascript" src="../nkang/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../Jsp/JS/jquery-1.8.0.js"></script>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles//CSS/animation-effects.css"/>
<link rel="stylesheet" type="text/css" href="../Jsp/CSS/common.css">
<script type="text/javascript" src="../Jsp/JS/slides.js"></script>


<!-- <link rel="stylesheet" href="../Jsp/CSS/w3.css"> -->
<style>
	.mySlides {display:none}
	.w3-left, .w3-right, .w3-badge {cursor:pointer}
	.w3-badge {height:13px;width:13px;padding:0}
        #return-top{position:fixed;bottom:40px;right:10px; text-align:center; display:none;} 
    
input#search{
	height:31px;
    width: 10px;
    box-sizing: border-box;
    border: 0px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    font-weight:bold;
    color:#fff !important;
    background-color:rgba(0, 0, 0, 0);
    background-image: url('../MetroStyleFiles/searchicon.png');
    background-position: 5px 5px; 
    background-repeat: no-repeat;
    padding: 12px 0px 12px 40px;
 /*    -webkit-transition: width 0.4s ease-in-out; */
    transition: width 0.4s ease-in-out;
    margin-top:3px;
    margin-bottom:8px;
    position: relative;
}

.PositionR {
    position: relative;
   /*  margin-top: -35px; */
}

input#search:focus {
    width: 100%;
    background-color:rgba(200, 200, 200, 1);
} 
.icon {
    display: inline-block;
    width: 30px; height: 30px;
    overflow: hidden;
    -overflow: hidden;
}
.icon > img.exit {
    position: relative;
    left: -30px;
    border-right: 30px solid transparent;
    -webkit-filter: drop-shadow(30px 0 #fff);
    filter: drop-shadow(20px 0);   
}
</style>


<script src="../nkang/js_athena/jquery.circliful.min.js"></script>
<script src="../nkang/assets_athena/bootstrap/js/bootstrap.js"></script>
<script	src="../MetroStyleFiles/sweetalert.min.js"></script>
<script type="text/javascript" src="../MetroStyleFiles//JS/openmes.min.js"></script>
<script src="../Jsp/JS/modernizr.js"></script>
<script src="../Jsp/JS/jSignature.min.noconflict.js"></script>
<script type="text/javascript" src="../nkang/autocomplete/jquery-ui.js"></script>

<script>
$(function(){  
    $('#return-top').hide();  
    $(function(){  
        $(window).scroll(function(){  
            if($(window).scrollTop()>200){  
                $('#return-top').fadeIn(200);  
                }  
                else{$('#return-top').fadeOut(200);}  
              
            });  
            $('#return-top').click(function(){  
                  
                $('body,html').animate({scrollTop:0},200);  
                return false;  
                  
                })  
          
        })  
      
      
    });

var LastToLikeDate="",lastLikeTo="";
var HpLogoSrc="",copyRight="",clientThemeColor="";
getLogo();
$(window).load(function() {
	$('head').append("<style>.naviArrow.is-selected::after{content: ''; display: block;width: 0;height: 0;border-left: .9em solid transparent;border-right: .9em solid transparent;border-top: .9em solid "+clientThemeColor+";position: relative;top: 0px;left: 50%;-webkit-transform: translateX(-50%); -ms-transform: translateX(-50%);transform: translateX(-50%);}</style>");
	$("#navSupport").on("click",function(){
		$(this).append("<a class='naviArrow is-selected'></a>").css("border-top","10px solid "+clientThemeColor);
		$(".naviArrow.is-selected::after").css("border-top",".9em solid "+clientThemeColor);
		$("#navApp").css("border-top","10px solid #B4B8BB");
		$("#navApp a").remove();
		$("#navMember").css("border-top","10px solid #B4B8BB");
		$("#navMember a").remove();
		$("#SocialElements").hide();
		$("#BoardContent").show();
		$("#WorkMates").hide();
	});
	$("#navApp").on("click",function(){
		$(this).append("<a class='naviArrow is-selected'></a>").css("border-top","10px solid "+clientThemeColor);
		$(".naviArrow.is-selected::after").css("border-top",".9em solid "+clientThemeColor);
		$("#navSupport").css("border-top","10px solid #B4B8BB");
		$("#navSupport a").remove();
		$("#navMember").css("border-top","10px solid #B4B8BB");
		$("#navMember a").remove();
		$("#SocialElements").show();
		$("#BoardContent").hide();
		$("#WorkMates").hide();
	});
	$("#navMember").on("click",function(){
		$(this).append("<a class='naviArrow is-selected'></a>").css("border-top","10px solid "+clientThemeColor);
		$("naviArrow is-selected::after").css("border-top",".9em solid "+clientThemeColor);
		$("#navSupport").css("border-top","10px solid #B4B8BB");
		$("#navSupport a").remove();
		$("#navApp").css("border-top","10px solid #B4B8BB");
		$("#navApp a").remove();
		$("#SocialElements").hide();
		$("#BoardContent").hide();
		$("#WorkMates").show();
	});
	//$(".mes-openbt").openmes({ext: 'php'});
	$('input#search:focus').css('background-color',clientThemeColor);
		var stockUrl = "http://hq.sinajs.cn/list=gb_$ixic,gb_$dji,gb_$inx,gb_hpe,gb_hpq,gb_csc";
		checkReg();
		getStockData(stockUrl);
		getMDLUserLists();
		getCompanyInfo();
		getRealName();
		getAllRegisterUsers();
});
function getLogo(){
	jQuery.ajax({
		type : "GET",
		url : "../QueryClientMeta",
		data : {},
		cache : false,
		success : function(data) {
			var jsons = eval(data);
			HpLogoSrc=jsons.clientLogo;
			copyRight=jsons.clientCopyRight;
			clientThemeColor=jsons.clientThemeColor;
			$('img.HpLogo').attr('src',HpLogoSrc);
			$('span.clientCopyRight').text('©'+copyRight);
			$('span.clientSubName').text(jsons.clientSubName);
			$('h1.clientName').text(jsons.clientName);
			$('.clientTheme').css('background-color',clientThemeColor);
			$('.clientThemefont').css('color',clientThemeColor);
			$('ul#myTabs').css('border-color',clientThemeColor);
			$('ul#myTabs li a').css('border-right-color',clientThemeColor);
			$('ul#myTabs li a').css('border-top-color',clientThemeColor);
			$('ul#myTabs li a').css('border-left-color',clientThemeColor);
			$("#navApp").css("border-top","10px solid "+clientThemeColor);
			if(jsons.slide!=null){
				$("#bgstylec a img").attr("src",jsons.slide[0]);
				$("#bgstylea a img").attr("src",jsons.slide[1]);
				$("#bgstyleb a img").attr("src",jsons.slide[2]);
			}
		}
	});
}
function checkReg() {
	jQuery.ajax({
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
				if(jsons.results[0].IsActive=="未注册"||jsons.results[0].IsActive=="false"){
						swal("你还未注册哦", "未注册用户很多功能不能使用,建议点击头像立即注册！", "error");
				}
			}
		}
	});
}
function startDictation() {
    if (window.hasOwnProperty('webkitSpeechRecognition')) 
      var recognition = new webkitSpeechRecognition();
      recognition.continuous = false;
      recognition.interimResults = false;
      recognition.lang = "cmn-Hans-CN";
      recognition.start();
      recognition.onresult = function(e) {
        document.getElementById('transcript').value = e.results[0][0].transcript;
        recognition.stop();
        document.getElementById('labnol').submit();
      };
      recognition.onerror = function(e) {
        recognition.stop();
      }
}
function toLike(likeToName,ToOpenId){
	if(ToOpenId==$('#uid').val()){
		swal("不能Like自己哦!", "可别太自恋啦。。。", "error"); 
	}else if(LastToLikeDate!=""&&getNowFormatDate().indexOf(LastToLikeDate.substring(0,10))==0){
		swal("你今天已经Like了"+lastLikeTo+"!", "可不能太花心哦!", "error");
	}else{
		$.ajax({  
	        cache : false,  
	        url:"../userProfile/updateUserWithLike",
	        type: 'GET', 
	        contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			data : {
				openid : $('#uid').val(),
				likeToName : likeToName,
				ToOpenId : ToOpenId
			},
	        timeout: 2000, 
	        success: function(data,textStatus){
	        	if(textStatus=='success'){
	        		swal("Congratulations！", "今天你成功Like了"+likeToName, "success"); 
	        		$("span.like").text(parseInt($("span.like").text())+1);
	        		getMDLUserLists();
	        	}else{
	        		swal("服务器繁忙！", "", "error"); 
	        	}
	        }
	  	});
	}
}
function getOld(){
	var url="../CallGetUserWithSignature?openid="+$('#uid').val();
	  	$.ajax({  
	        cache : true,  
	        url:url,
	        type: 'GET', 
	        timeout: 2000, 
	        success: function(data,textStatus){
	        	if(textStatus=='success'){
		        	if(data!=null&&data!=""&&data!="null"){
		       		 	$('#old').html(data.substring(1,data.length-1));
		        	}else{
		        		$('#old').html("你还未保存个性签名！");
		        	}
	        	}else{
	        		$('#old').html("服务器繁忙！");
	        	}
	        }
	  	});
	}
function getTax(){
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
}
function getCompanyInfo(){
	$.ajax({
		type : "post",
		url : "../userProfile/getCompanyInfo",
		cache : false,
		success : function(data) {
		
		}
	});
}
function getRecognitionInfoByOpenID(){
	var uid=$("#uid").val();
	$.ajax({
		type : "post",
		url : "../userProfile/getRecognitionInfoByOpenID",
		data:{
			openID:uid
		},
		cache : false,
		success : function(data) {
		if(data)
			{
			var text="";
			for(var i=0;i<data.length;i++)
				{
				text=text+"<div class='rs' onclick='showRecognitionDetail(\""+data[i].from+"\",\""+data[i].to+"\",\""+data[i].point+"\",\""
				+data[i].type+"\",\""+data[i].comments+"\")'><p class='rfrom'>From:"+data[i].from+"</p><p class='rtype'>"+data[i].type
				+"</p><p class='rcomment'>"+data[i].comments+"</p><p class='rdate'>"+data[i].congratulateDate+"</p></div>";
				}
			$("#myRecognitionList").html(text);
			}
		}
	});
}
function getAllRegisterUsers(){
	$.ajax({
		type : "post",
		url : "../userProfile/getAllRegisterUsers",
		cache : false,
		success : function(data) {
			var text="";
		for(var i=0;i<data.length;i++)
			{
			text=text+"<option>"+data[i]+"</option>";
			}
		$("#hiddenSelect").html(text);
		}
	});
}
function getRealName(){
	var uid=$("#uid").val();
	$.ajax({
		type : "post",
		url : "../userProfile/getRealName",
		data:{
			openID:uid
		},
		cache : false,
		success : function(data) {
			if(data){
		$("#realName").val(data.toString());}
			else
				{$("#realName").val("");}
		}
	});
}
function showRecognitionDetail(from,to,point,type,coments)
{
	var text=coments.toString();
	$("body").append("<div id='recognitionCenter' style='width:100%;height:100%;'> <div style='height:90px;font-family: HP Simplified, Arial, Sans-Serif;border-bottom:5px solid #56B39D'><img style='position:absolute;top:20px;left:35px;' src='"+HpLogoSrc+"' alt='Logo' class='HpLogo'></div>"
			+"<div style='position:absolute;top:140px;width:80%;left:10%;font-size:16px;'>"
			+"<p style='float:left;width:110px;'>Congratulations</p><p id='to' style='float:left;'>"+to+"</p><p style='float:left;'>!</p></div>"
			+"<div style='position:absolute;top:180px;width:80%;left:10%;height:auto;font-size:14px;font-family: HP Simplified, Arial, Sans-Serif;'>"
			+"<p style='line-height:22px;'>You must have done something amazing! "+from+" has recognized you in the Manager-to-Employee FY16 program for M2E: "+type+". <p>"
			+"<p style='width:100%;line-height:22px;font-size:16px;margin:15px 0px;'>Your award</p>"
			+"<p style='line-height:22px;'>"+point+" Points have been added to your <a href='https://login.ext.hpe.com/idp/startSSO.ping?PartnerSpId=hpe_biw_sp'>MyRecognition@hpe</a> account. Enjoy surfing the catalogue and finding something that is perfect just for you: merchandise, travel, gift cards or vouchers. <p>"
			+"<p style='width:100%;line-height:22px;font-size:16px;margin:15px 0px;'>Here’s what was said about you</p>"
			+"<p style='line-height:22px;'>Thanks <span id='to'>"+to+"</span> for "+text+"</p>"
			+"<img onclick='hideRecognitionCenter()' src='../MetroStyleFiles/EXIT1.png' style='width: 30px; height: 30px;position:relative;top:20px;left:250px;'></div></div>");
	$('#recognitionCenter').addClass('bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass("bounceInDown animated");
	    });
	}
function postRecognition(){
	var isAll="false";
	if($("#sendAll").is(':checked'))
		{
		isAll="true";
		}
	else
		{
		isAll="false";
		}
	var to = $("#to option:selected").val();
	$.ajax({
        cache: false,
        type: "POST",
        url:"../userProfile/userCongratulate",
        data:{
        	openID:$("#openID").val(),
        	isAll:isAll,
        	from:$("#from").text(),
        	points:$("#points").val(),
        	to:$("#to option:selected").val(),
        	type:$("#type option:selected").val(),
        	comments:$("#comments").val()
        	
        },
        async: true,
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
        	swal("Congratulations!", "Recognization to "+to+" already sent successful!", "success");
        	hideBouncePanel();
        }
    });

}
function postNotification(){
	var type=$("#notificationType option:selected").val();
	$.ajax({
        cache: false,
        type: "POST",
        url:"../userProfile/addNotification",
        data:{
        	openId:$("#uid").val(),
        	title:$("#notificationTitle").val(),
        	url:$("#notificationURL").val(),
        	type:type,
        	content:$("#content").val()
        	
        },
        async: true,
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
        	swal("Success!", "Your Notification has been submitted successfully", "success");
        	hideBouncePanel();
        }
    });

}
function  WeatherPanel(){
	showCommonPanel();
	jQuery.ajax({
				type : "GET",
				url : "../userProfile/getWeather",
				data : {},
				cache : false,
				success : function(data) {
					var jsons = eval('(' + data + ')');
					if (jsons.status == 'success'
							&& jsons.results.length > 0) {
						var tbody="";
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
						$("body").append('<div id="WeatherPart" class="bouncePart" style="position:fixed;z-index:999;top:100px;width:80%;margin-left:10%;"><legend>天气</legend><div style="margin-top:0px;margin-bottom: -20px;background-color:#fff;">'
								+'<table width="100%" id="weather2" style="margin-left:auto;margin-right:auto;">'
								+tbody+'							</table>'
								+'							</div>');
						$('#WeatherPart').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
						      $(this).removeClass("bounceInDown animated");
						    });
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
function SpeechPanel(){
	showCommonPanel();
	$("body").append('<div id="taxPart" class="bouncePart" style="position:fixed;z-index:10000;top:100px;width:80%;margin-left:10%;"><legend>智能语音</legend><form id="labnol" method="get" action="https://www.bing.com/search">'
			+'         <div class="speech">'
			+'           <input type="text" name="q" id="transcript" placeholder="Speak" />'
			+'           <img onclick="startDictation()" src="../MetroStyleFiles/cHidSVu.gif" />'
			+'         </div>'
			+'       </form></div>');
	$('#taxPart').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass("bounceInDown animated");
	    });
}
function taxPanel(){
		showCommonPanel();
		$("body").append('<div id="taxPart" class="bouncePart" style="position:fixed;z-index:10000;top:100px;width:80%;margin-left:10%;"><legend>税费计算</legend><table class="tax" style="margin-right:auto;margin-left:auto;">'
				+'											<tr>'
				+'												<td>起征点：</td>'
				+'												<td><input type="text" id="taxstart" value="3500" /></td>'
				+'											</tr>'
				+'											<tr>'
				+'												<td>总工资：</td>'
				+'												<td><input type="text" id="taxIncome" value=""/></td>'
				+'											</tr>'
				+'											<tr>'
				+'												<td>五险一金：</td>'
				+'												<td><input type="text" id="payment" value=""/></td>'
				+'											</tr>'
				+'											<tr>'
				+'												<td colspan="2" style="text-align: center; padding: 0px;">'
				+'													<button class="btnAthena EbtnLess"'
				+'														style="padding: 0px;background-color:'+clientThemeColor+';"'
				+'														id="tax_submit_button" onclick="getTax()">计算</button>'
				+'												</td>'
				+'											</tr>'
				+'											<tr>'
				+'												<td>含税级距计算：</td>'
				+'												<td><span id="levelcalc"></span></td>'
				+'											</tr>'
				+'											<tr>'
				+'												<td>不含税级距计算：</td>'
				+'												<td><span id="nolevelcalc"></span></td>'
				+'											</tr>'
				+'										</table></div>');
		$('#taxPart').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
		      $(this).removeClass("bounceInDown animated");
		    });
}
function signaturePanel(){
	showCommonPanel();
	$("body").append('<div id="taxPart" class="bouncePart" style="position:fixed;z-index:10000;top:100px;width:80%;margin-left:10%;"><legend>电子签名</legend><div id="old" style="vertical-align:middle;margin-bottom:-90px;padding-top:5px;height:170px;border:2px #56B39D solid;width:100%;margin-left:auto;margin-right:auto;text-align:center;margin-top:-10px;"></div>'
			+'<div id="content">		'
			+'	<div id="signatureparent">'
			+'		<div id="signature"></div></div>'
			+'	<div id="tools"></div>'
			+'	'
			+'</div></div>');
	$('#taxPart').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass("bounceInDown animated");
	    });
	getOld();
	// This is the part where jSignature is initialized.
	var $sigdiv = $("#signature").jSignature({'UndoButton':true})
	// All the code below is just code driving the demo. 
	var $tools = $('#tools')

	$('<input type="button" name="svg" value="保存签名"  style="float:right;color:#fff !important;background-color:#56B39D;border:none;padding:10px;font-size:18px;margin-top:0px;">').bind('click', function(e){
		if (e.target.value !== ''){
			//var data = $sigdiv.jSignature('getData', e.target.value)
			var data = $sigdiv.jSignature('getData', 'svg')
			if($.isArray(data) && data.length === 2){
				var start=data[1].indexOf("<svg ");
				var svg=data[1].substring(start,data[1].length);
				if(svg!='<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="0" height="0"></svg>')
				jQuery.ajax({
					type : "GET",
					url : "../userProfile/setSignature",
					data : {
						svg : svg
					},
					cache : false,
					success : function(data) {
						if(data.indexOf("true")==0){
							swal("签名保存成功!", "", "success"); 
							getOld();
							/*var canvas = document.getElementsByClassName('jSignature')[0];
						  	var context = canvas.getContext('2d');
						  	context.clearRect(0, 0, canvas.width, canvas.height); */
						  	var $sigdiv = $("#signature")
						  	$sigdiv.jSignature("reset"); //重置画布，可以进行重新作画.
						}else{
							swal("签名保存失败!", "", "error"); 
						}
					}
				});
			}
		}
	}).appendTo($tools)
	
	if (Modernizr.touch){
		$('#scrollgrabber').height($('#content').height())		
	}
}
function mesSend(){
	showCommonPanel();
	$("body").append("	<div id='sendR'>"
			+"	<div class='rcommon'><p class='bsLabel'>标题</p><input id='notificationTitle' type='text' placeholder='请输入标题' class='input-xlarge bsBtn'></div>"
			+"	<div class='rcommon'><p class='bsLabel'>类型</p><select class='bsBtn' id='notificationType'><option value='js'>技术</option><option value='bj'>报价</option><option value='gt'>沟通</option><option value='hq'>行情</option></select></div>"
			+"	<div class='rcommon'><p class='bsLabel'>网页链接</p><input id='notificationURL' type='text' placeholder='请输入网页链接' class='input-xlarge bsBtn'></div>"
			+"	<div class='rcommon'><textarea id='content' style='height:180px;width:95%;line-height:20px' placeholder='请输入内容' class='input-xlarge bsBtn'></textarea></div>"
			+"	<div class='rcommon' style='text-align:center;'><button style='margin-top:20px' onclick='postNotification()' name='doublebutton-0' class='btn'>提交</button></div>"
			+"	</div>"
			+"<div id='footer'><span class='clientCopyRight'><nobr>"+copyRight+"</nobr></span></div>");
	$('#sendR').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass("bounceInDown animated");
	    });
}
function recognizationPanel(){
	var realName=$("#realName").val();
	var selectContent=$("#hiddenSelect").html();
	if(realName!="")
		{
		showCommonPanel();
		
		$("body").append("<div class='TAB2class bouncePart' id='recognizeForm'>"
				+"	<ul class='nav nav-tabs' id='myTabs'>"
				+"	<li id='aaElements' class='active'><a href='#aElements' data-toggle='tab'>发送Recognition</a></li>"
				+"	<li id='bbElements'><a href='#bElements' onclick='getRecognitionInfoByOpenID()' data-toggle='tab'>收到的Recognition</a></li></ul>"
				+"  <div class='tab-content' id='dvTabContent' style='border: 0px;'>"
				+"	<div class='tab-pane active' id='aElements'>"
				+"	<div id='sendRe'>"
				+"	<div class='rcommon'><p class='bsLabel'>发送人</p><p class='bsBtn' id='from'>"+realName+"</p></div>"
				+"	<div class='rcommon'><p class='bsLabel'>接收人</p><select class='bsBtn' id='to'>"+selectContent+"</select></div>"
				+"	<div class='rcommon'><p class='bsLabel'>类型</p><select class='bsBtn' id='type'><option>Bais For Action</option><option>Innovators at Heart</option><option>Partnership First</option></select></div>"
				+"	<div class='rcommon'><p class='bsLabel'>Points</p><input id='points' type='text' placeholder='请输入points' class='input-xlarge bsBtn'></div>"
				+"	<div class='rcommon'><textarea id='comments' style='height:130px;width:95%;line-height:20px' placeholder='请输入感言' class='input-xlarge bsBtn'></textarea></div>"
				+"	<div class='rcommon' style='text-align:center;margin-top:100px'><button onclick='postRecognition()' name='doublebutton-0' class='btn'>提交</button><div style='position: relative;top: -20px;left: -95px;' ><input type='checkbox' id='sendAll'>发送所有人</div></div>"
				+"	</div>"
				+"	</div>"
				+"  <div class='tab-pane' id='bElements'>"
				+"	<div id='myRecognitionList'>"
				+"  </div>"
				+"		</div>"
				+"	</div>"
				+"</div><div id='footer'><span class='clientCopyRight'><nobr>"+copyRight+"</nobr></span></div>");
		$('#recognizeForm').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
		      $(this).removeClass("bounceInDown animated");
		    });
		}else
			{swal("Sorry", "you have no access to this page,please register", "error");}

}
function recognizationPanelByPerson(personName){
	var realName=$("#realName").val();
	var selectContent=$("#hiddenSelect").html();
	if(realName!=""){
		showCommonPanel();
		
		$("body").append("	<div class='TAB2class bouncePart' id='recognizeForm'>"
				+"	<ul class='nav nav-tabs' id='myTabs'>"
				+"	<li id='aaElements' class='active'><a href='#aElements' data-toggle='tab'>发送 Recognition</a></li>"
				+"	<li id='bbElements'><a href='#bElements' onclick='getRecognitionInfoByOpenID()'  data-toggle='tab'>收到的 Recognition</a></li></ul>"
				+"  <div class='tab-content' id='dvTabContent' style='border: 0px;'>"
				+"	<div class='tab-pane active' id='aElements'>"
				+"	<div id='sendRe'>"
				+"	<div class='rcommon'><p class='bsLabel'>发送人</p><p class='bsBtn' id='from'>"+realName+"</p></div>"
				+"	<div class='rcommon'><p class='bsLabel'>接送人</p><select class='bsBtn' id='to'>"+selectContent+"</select></div>"
				+"	<div class='rcommon'><p class='bsLabel'>类型</p><select class='bsBtn' id='type'><option>Bais For Action</option><option>Innovators at Heart</option><option>Partnership First</option></select></div>"
				+"	<div class='rcommon'><p class='bsLabel'>Points</p><input id='points' type='text' placeholder='请输入points' class='input-xlarge bsBtn'></div>"
				+"	<div class='rcommon'><textarea id='comments' style='height:130px;width:95%;line-height:20px' placeholder='请输入感言' class='input-xlarge bsBtn'></textarea></div>"
				+"	<div class='rcommon' style='text-align:center;margin-top:100px'><button onclick='postRecognition()' name='doublebutton-0' class='btn'>提交</button><div style='position: relative;top: -20px;left: -95px;' ><input type='checkbox' id='sendAll'>发送所有人</div></div>"
				+"	</div>"
				+"	</div>"
				+"  <div class='tab-pane' id='bElements'>"
				+"  <div id='myRecognitionList'></div>"
				+"		</div>"
				+"	</div>"
				+"</div><div id='footer'><span  class='clientCopyRight'><nobr>"+copyRight+"</nobr></span></div>");
		$('#recognizeForm').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
		      $(this).removeClass("bounceInDown animated");
		    });}else
			{swal("Sorry", "you have no access to this page,please register", "error");}
	}
function showCommonPanel()
{
	$("body").append("<div  id='data_model_div' style='z-index:999;'  class='dataModelPanel'><i class='icon' style='position:absolute;top:20px;left:20px;'><img class='exit' onclick='hideBouncePanel()' src='../MetroStyleFiles/EXIT1.png' style='width: 30px; height: 30px; -webkit-filter: drop-shadow(30px 0 "+clientThemeColor+");' /></i>	<img style='position:absolute;top:8px;right:20px;' class='HpLogo' src='"+HpLogoSrc+"' alt='Logo' class='HpLogo'><div style='width:100%;height:4px;background:"+clientThemeColor+";position:absolute;top:70px;'></div></div>");
	$('#data_model_div').removeClass().addClass('panelShowAnmitation').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass();
	    }); }
function hideBouncePanel()
{
	$("body").find(".bouncePart").remove();
	$("body").find("#data_model_div").remove();
	$("body").find("#sendR").remove();
	}
function hideRecognitionCenter()
{
	$("body").find("#recognitionCenter").remove();
	}
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
				$("#info_tag tr").html("");
				if(jsons.results[0].role!="未注册"){
					$("#info_interact").css("display","none");
					$("#info_interact2").css("display","none");
					$("#info_imgurl").attr("src",$('#userImage').attr('src'));
					if(jsons.results[0].tag!="未注册"){
						for(var j=0;j<jsons.results[0].tag.length;j++){
							var tag=jsons.results[0].tag[j];
							for (var key in tag) { 
								var td='<td>'
									+'				<div id="'+key+'" data-dimension="70" data-text="'
									+tag[key]
									+'%" data-info="" data-width="8" data-fontsize="18" data-percent="'
									+tag[key]
									+'" data-fgcolor="#FFF" data-bgcolor="#aaa" data-fill=""></div>'
									+'				<span style="font-size:12px;">'
									+key
									+'</span>'
									+'														</td>';

								$("#info_tag tr").append(td);
								$('#'+key).circliful();
							}
						}
					}
					data = data.replace(/:"未注册"/g, ':"未编辑"');
					jsons = eval('(' + data + ')');
					$("#info_username span").html(jsons.results[0].realName+'<span style="font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;['+jsons.results[0].role+']&nbsp;</span>'+'<img onclick="showRegister()" src="../MetroStyleFiles/edit.png" style="height: 20px; cursor: pointer;padding-left:5px;"/>');
					$("#info_all").css('display','table');
					$("#info_phone").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].phone);
					$("#info_group").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].groupid);
					$("#info_email").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].email);
					$("#info_selfIntro").text(jsons.results[0].selfIntro);
					$('#UserInfo').modal('show');
				}else{
					$("#info_all").css('display','none');
					$("#info_selfIntro").text('');
					showRegister();
				}
			}else{
				$("#info_all").css('display','none');
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
				if(jsons.results[0].companyName !="未注册"){
					$("#companyname").val(jsons.results[0].companyName);
				}
				if(jsons.results[0].realName !="未注册"){
					$("#realname").val(jsons.results[0].realName);
				}
				if(jsons.results[0].phone !="未注册"){
					$("#phone").val(jsons.results[0].phone);
				}
				if(jsons.results[0].email !="未注册"){
					$("#email").val(jsons.results[0].email);
				}
				/* $("#roleSelect option[value='"+jsons.results[0].role+"']").attr("selected",true);
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
			    if(jsons.results[0].tag!="未注册"){
					for(var j=0;j<jsons.results[0].tag.length;j++){
						var tag=jsons.results[0].tag[j];
						for (var key in tag) { 
							if(key=="java"){
								$113("#javatag").slider("setValue",tag[key]);
							} 
							
							if(key =="html"){
								$113("#htmltag").slider("setValue",tag[key]);
							} 
							
							if(key =="webservice"){
								$113("#webservicetag").slider("setValue",tag[key]);
							} 
							
							if(key =="etl"){
								$113("#etltag").slider("setValue",tag[key]);
							}
						}
					}
				}
			    
			    if(jsons.results[0].selfIntro !="未注册"){
					$("#selfIntro").val(jsons.results[0].selfIntro);
			    } */
			}
		}
	});
	$("#registerBtn").click(function(){
		var uid = $("#uid").val();
		var companyname = $("#companyname").val();
		var name = $("#realname").val();
		var phone = $("#phone").val();
		var email = $("#email").val();
		//var suppovisor = $("#suppovisor").val();
		/* var role = $("#roleSelect option:selected").val();
		var group = $("#groupSelect option:selected").val();
		var javatag = $("#javatag").val();
		var htmltag = $("#htmltag").val();
		var webservicetag = $("#webservicetag").val();
		var etltag = $("#etltag").val();
		var selfIntro = $("#selfIntro").val(); */
		
		 var emailFilter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		 var phoneFilter = /^1[0-9]{10}/;
		 if (name.replace(/(^ *)|( *$)/g,'')==''){
			 swal("Registered fail!", "Pls input your correct name information.", "error");
		 }else if (!phoneFilter.test(phone)){
			 swal("Registered fail!", "Pls input your correct phone information.", "error");
		 }else if (!emailFilter.test(email)){
			 swal("Registered fail!", "Pls input your correct E-mail information.", "error");
		 }else{
			$.ajax({
				url:"../regist",
				data:{uid:uid,companyname:companyname,name:name,telephone:phone,email:email
					},//role:role,group:group,javatag:javatag,htmltag:htmltag,webservicetag:webservicetag,etltag:etltag,selfIntro:selfIntro
				type:"POST",
				dataType:"json",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				cache:false,
				async:false,
				success:function(result) {
					if(result){
						$('#registerform').modal('hide');
						swal("Registered successfully!", "Congratulations!", "success"); 
						$("#realName").val(name);
					} else {
						swal("Registered fail!", "Pls input your correct information.", "error");
					}
				}
			});
		}
	});
	
}

function getUserInfo(username, headimgurl, openId) {
			$("#info_interact").css("display","block");
			$("#info_interact2").css("display","block");
			$("#info_imgurl").attr("src",headimgurl);
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
						$("#info_tag tr").html("");
						$("#info_interact img.like").attr("onclick","toLike('"+username+"','"+jsons.results[0].openid+"')");
						$("#info_interact2 span.like").text(jsons.results[0].like.number==""?0:jsons.results[0].like.number);
						if(jsons.results[0].role!="未注册"){
							$("#info_username span").html(jsons.results[0].realName);
							$("#info_interact img.zan").attr("onclick","recognizationPanelByPerson('"+jsons.results[0].realName+"')");
							$("#info_interact2 span.zan").text(jsons.results[0].CongratulateNum);
							if(jsons.results[0].tag!="未注册"){
								for(var j=0;j<jsons.results[0].tag.length;j++){
									var tag=jsons.results[0].tag[j];
									for (var key in tag) { 
										var td='<td>'
											+'				<div id="'+key+'" data-dimension="70" data-text="'
											+tag[key]
											+'%" data-info="" data-width="8" data-fontsize="18" data-percent="'
											+tag[key]
											+'" data-fgcolor="#FFF" data-bgcolor="#aaa" data-fill=""></div>'
											//+'" data-fgcolor="#61a9dc" data-bgcolor="#eee" data-fill="#ddd"></div>'
											+'				<span style="font-size:12px;">'
											+key
											+'</span>'
											+'														</td>';

										$("#info_tag tr").append(td);
										$('#'+key).circliful();
									}
								}
							}
							data = data.replace(/:"未注册"/g, ':"未编辑"');
							jsons = eval('(' + data + ')');
							$("#info_all").css('display','table');
							$("img.zan").css('display','block');
							$("span.zan").css('display','block');
							$("#info_username span").html(username+'<span style="font-size:13px;">&nbsp;&nbsp;&nbsp;&nbsp;['+jsons.results[0].role+']</span>');
							$("#info_phone").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].phone);
							$("#info_group").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].groupid);
							$("#info_email").html("&nbsp;&nbsp;&nbsp;&nbsp;<a style='color:#fff;' href='mailto:"+jsons.results[0].email+"'>"+jsons.results[0].email+"</a>");
							$("#info_selfIntro").text(jsons.results[0].selfIntro);
						}else{
							$("#info_username span").html('未注册');
							$("img.zan").css('display','none');
							$("span.zan").css('display','none');
							$("#info_all").css('display','none');
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
					var ul = "",regNumber=0;
					ul='<div class="Work_Mates_div_list_div2" style="border-bottom:0px;">'
					for (var i = 0; i < jsons.results.length; i++) {
						var temp = jsons.results[i];
						var selfIntro=temp.selfIntro;
						var role=temp.role;
						var workDay=temp.workDay;
						var tag=temp.tag;
						var tagHtml="";
						var congratulate="";
						
						if(temp.openid==$('#uid').val()){
							LastToLikeDate=temp.like.lastLikeDate;
							lastLikeTo=temp.like.lastLikeTo;
						}

						
						
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
						if(tag!=null&&tag!='null'){
							for(var j=0;j<tag.length&&j<4;j++){
								for (var key in tag[j]) { 
									tagHtml+='													<div class="tag">'
									+key
									+'													</div>';
								}
							}
						}
						if(workDay==null||workDay=='null'||workDay==0){
							workDay="";
						}else{
							regNumber++;
							workDay='<div style="float:right;margin-top:-45px;background-color:#eee;color:#333;font-size:13px;padding:3px;">'+workDay+' Days</div>';
						}
						if(temp.congratulateNum==null||temp.congratulateNum=='null'||temp.congratulateNum==undefined||temp.congratulateNum==0){
							
						}else{
							congratulate='<div style="float:right;"><img src="../MetroStyleFiles/reward.png" style="height:25px;"/>'
								+ '<span style="font-size:12px;color:#07090B;font-weight:normal;">'+temp.congratulateNum+'</span><div>';
						}
						var li='	<div class="Work_Mates_div_list_div2">'
							+'                                           	 	<div class="Work_Mates_img_div2">'
							+'                                        			 <img src="'
							+ ((temp.headimgurl==null||temp.headimgurl=='')?'../MetroStyleFiles/image/user.jpg':temp.headimgurl)
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
							+role+'</span>'
							+congratulate
							+'</h2>'
							+ '<div>'
							+tagHtml
							+'<br/>'
							+'													<span class="selfIntro">'+selfIntro+'</span>'
							+'												</div>'
							+'                                        		</div>'
							+workDay
							+'                                                <div class="clear"></div>'
							+'                                          </div>';
						ul += li;
					}
					ul='<div class="Work_Mates_div_list_div2">'
					/* +'<img id="syncUser"  src="../MetroStyleFiles/refresh2.png" style="height:20px;"/>' */
					+'<span class="total_num"><img src="../MetroStyleFiles/role.png"/>总人数：'+ jsons.results.length
					+'&nbsp;&nbsp;&nbsp;已注册人数：'+regNumber
					+'</span><div class="clear"></div></div>'+ul;
					$("#Work_Mates_div").html(ul);
					$("#syncUser").click(function(){
						syncUser();
					});
				}
			});
}
function syncUser(){
	$("#syncUser").attr("src","../MetroStyleFiles/loading.gif");
	jQuery.ajax({
		type : "GET",
		url : "../userProfile/getWechatUserLists",
		data : {},
		cache : false,
		success : function(data) {
			swal("Congratulations！", data, "success"); 
			$("#syncUser").attr("src","../MetroStyleFiles/refresh2.png");
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
						$('#weather2').html(tbody);
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

function stockModule(){
	showCommonPanel();

	$("body").append("	<div class='TAB2class bouncePart' id='allstockcodes'>"
			+"<div id='stockListHeader' class='modal-header' style='text-align: center;'>"
			+"<img src='../MetroStyleFiles/Add1.png'"
			+"style='float: right; height: 20px; cursor: pointer; margin-top: 10px;' onclick='addStock()'/>"
			+"<h3><b>股票行情</b></h3>"
			+"</div>"
			+"<table width='100%' id='stock' style='margin-bottom: -20px;'>"
			+"</table>"
			+"<div id='addStock' style='display:none;margin: 5px;' >"
			+"<div id='stockTableForm' >"
			+"<span>请输入股票代码：</span><input type='text' placeholder='股票代码' id='stockcodeKey'  required style='width:100px;'/><input id='addStockBtn' type='button' value='添加' style='margin: 5px;' />"
			+"</div></div>"
			+"</div><div id='footer'><span  class='clientCopyRight'><nobr>'"+copyRight+"</nobr></span></div>");
	$('#allstockcodes').addClass('form-horizontal bounceInDown animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
	      $(this).removeClass("bounceInDown animated");
	    });
}

function getStockData(url){
	getNewData(url);
	var refreshDataDefault = self.setInterval("getNewData('"+url+"')",2000);
	$("#timer").val(refreshDataDefault);
	
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
       		var tbody="";
            var tr = "<tr>";
				tr+="<td>证券名称</td><td>现价</td><td>涨幅</td><td>涨跌</td></tr>";
       		for(var i=0;i<list.length;i++){
       			var stockData = eval(list[i]).split(',');
       			var stockType = list[i].substring(7,stockData.length);
       			if(stockType.indexOf("gb")==0){
       				tr+="<tr class='stockline'>"+"<td>"+stockData[0].substring(0,26)+"</td>"+"<td class='stockcolor'>"+stockData[1]+"</td>"+"<td id='increase' class='stockcolor'>"+parseFloat(stockData[2])+"%"+"</td>"+"<td class='stockcolor'>"+stockData[4]+"</td></tr>";
       			} else {
       				tr+="<tr class='stockline'>"+"<td>"+stockData[0].substring(0,26)+"</td>"+"<td class='stockcolor'>"+stockData[1]+"</td>"+"<td id='increase' class='stockcolor'>"+parseFloat(stockData[2])+"%"+"</td>"+"<td class='stockcolor'>"+stockData[3]+"</td></tr>";
       			}
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

	var allAddStockCodes = new Array();
function addStock() {
	//$('#allstockcodes').css("display", "none");
	$('#stock').hide();
	$("#addStock").css("display","block");
	$("#stockTableForm").css("display","block");
	$('#stockcodeKey').val("");
	$.ajax({
		url : "../getCodes",
		dataType : "json",
		success : function(data) {
			var jsonData = new Array();
			jsonData = data;
			if(jsonData!=null || jsonData.length>0){
				for(var x=0;x<jsonData.length;x++){
					jsonData[x] = jsonData[x].substring(2,jsonData.length);
				}
			}
			$("#stockcodeKey").autocomplete({
				source:jsonData,
				autoFocus: true,
				delay: 200,
				max:10,
				minLength:3,
				response: function( event, ui ) {
			    },
				select: function( event, ui ) {
					var stockCode = ui.item.label;
					
					$("#addStockBtn").click(function(){
						self.clearInterval($("#timer").val());
						$('#stockListHeader').css("display","block");
						$('#stock').css("display","block");
						$('#addStock').css("display","none");
						var url = "http://hq.sinajs.cn/list=gb_$ixic,gb_$dji,gb_$inx,gb_hpe,gb_hpq,gb_csc";
						
					    if(stockCode!=null || stockCode!="undefined"){
					    	if(allAddStockCodes.length==0){
					    		if(stockCode.indexOf("6")==0){
									   allAddStockCodes.push("s_sh"+stockCode);
								   }else if(stockCode.indexOf("0")==0 || stockCode.indexOf("3")==0){
									   allAddStockCodes.push("s_sz"+stockCode);
								   }
					    	} else{
					    		if($.inArray(stockCode, allAddStockCodes)==-1){
					    			if(stockCode.indexOf("6")==0){
										   allAddStockCodes.push("s_sh"+stockCode);
									  }else if(stockCode.indexOf("0")==0 || stockCode.indexOf("3")==0){
										   allAddStockCodes.push("s_sz"+stockCode);
									  }
					    		}
					    	}
					   }
					   var temp = "";
					   for(var i=0;i<allAddStockCodes.length;i++){
						   temp +=",";
						   temp +=allAddStockCodes[i];
					   }
					   url += temp;
					   getStockData(url);
					   stockCode="";
					});
			    }
			});
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
	<input id="timer" type="hidden" value="" />
	<input id="realName" type="hidden" value="" />
	<select id="hiddenSelect" style="display:none">
	</select>
	<div class="navbar" style="width: 100%;">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a href="../DQMenu?UID=<%=uid%>" style="float: left;padding-top:10px;"> 
					<img src=""
					alt="Logo" class="HpLogo" style="display:inline !important;height:30px;float:none;padding:0px;vertical-align:bottom;"/><span class="clientSubName" style="font-size:12px;padding-left:7px;color:#333;"></span>
					<h1 style="color:#333;font-size:18px;" class="clientName"></h1>
				</a>
				<div class="clear"></div>
				<ul class="nav pull-right top-menu" style="margin-top:-70px;">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"
						style="font-size: 15px; margin: 0px; padding: 5px 0px;">
							欢迎 <span class="username colorBlue" id="username"> <%=wcu.getNickname() %>
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
				<div class="clientTheme  BDheading">
					<div class="span12">
						<div id="divBoardName" style="dispaly: none" title='LBName'></div>
						<h2>
							<nobr>
								<span class="colorDarkBlue" id="location" style="float: left;padding-left:25px;"><%=curLoc%></span>  <img
									src="../MetroStyleFiles/setuplocation.png"
									onclick="getLocation();" id="locationImg"
									style="height: 21px;padding-top:4px;float:right; cursor: pointer; margin-top: -5px;z-index:10000;" />
							</nobr>
						</h2>
					</div>
				</div>
				 
		
		
				<div class="row-fluid mtop10">
					<div class="span12">
						<div class="PositionR">
						<form id="searchForm" method="get" action="https://www.bing.com/search"  style="padding:0px;" >
									<input type="text" id="search"  name="q" placeholder="Search.." style="margin-top:-63px;"> 
      						    </form>
					<!-- 	<input type="text" id="search" name="search" placeholder="Search.."> -->
								
							   <!-- slides show start -->
							   <div class="slide-main" id="touchMain" style="margin-top:-20px;">

        <a class="prev" style="display:none" href="javascript:;" stat="prev1001"><img src="../MetroStyleFiles/image/l-btn.png" /></a>

        <div class="slide-box" id="slideContent">

            <div class="slide" id="bgstylec">

            	<a stat="sslink-3" href="" target="_blank">

                	<div class="obj-e"><img style="width:100%;height:221px" src="../MetroStyleFiles/image/socialHPE.png" /></div>

            	</a>

            </div>

            <div class="slide" id="bgstylea">

            	<a stat="sslink-1" href="" target="_blank">

                	<div class="obj-e"><img style="width:100%;height:221px" src="../MetroStyleFiles/image/datalakedashboard.jpg" /></div>

            	</a>

            </div>

            <div class="slide" id="bgstyleb">

            	<a stat="sslink-2" href="" target="_blank">

                	<div class="obj-e"><img style="width:100%;height:221px" src="../MetroStyleFiles/image/datalakepure.jpg" /></div>

            	</a>

            </div>

        </div>

        <a class="next" style="display:none"  href="javascript:;" stat="next1002"><img width=100% height=100% src="../MetroStyleFiles/image/r-btn.png" /></a>

        <div class="item">

        	<a class="cur" stat="item1001" href="javascript:;"></a><a href="javascript:;" stat="item1002"></a><a href="javascript:;" stat="item1003"></a>

        </div>

    </div>
    <!-- slides show end -->
						</div>
					</div>
				</div>
				
				
				
				
				 	</div>
			<div class="container-fluid">
				<div class="row-fluid mtop20">
					<div class="span12">
						<div class="TABclass">
							<div class="naviPanel">
						<div id="navApp"  class="navi">
						<a class="naviArrow is-selected" ></a>
						<p class="naviText">微应用</p>
							</div>
							<div id="navSupport"   class="navi"><p class="naviText">微表情</p></div>
								<div id="navMember"  class="navi"><p class="naviText">微成员</p></div>
							</div>
							<div class="tab-content" id="dvTabContent" style="border: 0px;margin-top:-30px">
								<div class="tab-pane" id="BoardContent">
									<div>
										<div class="panel-group" id="accordion">
											<div id="DivLearnings"
												style="text-align: center; padding: 0px 0px;">
												<img src="../mdm/images/delivering.gif" width="100"/>
												<img src="../mdm/images/moneymoney.gif" width="100" />
												<img src="../mdm/images/nodeliverywithoutpay.gif" width="100" />
												<img src="../mdm/images/noinventory.gif"  width="100"/>
												<img src="../mdm/images/nosales.gif"  width="100"/>
												<img src="../mdm/images/orderplease.gif"  width="100"/>
												<img src="../mdm/images/payreceived.gif"  width="100"/>
												<img src="../mdm/images/payreceived2.gif"  width="100"/>
												<img src="../mdm/images/priceincrease.gif"  width="100"/>
												<img src="../mdm/images/quotation.gif"  width="100"/>
												<img src="../mdm/images/selfservice.gif"  width="100"/>
												<img src="../MetroStyleFiles/image/Maintenace.gif"  width="100"/>
											</div>
										</div>
									</div>
								</div>
								<div class="tab-pane active" id="SocialElements">
									<!-- start -->
									<div id="WeatherDetails"  style="z-index: 10000;" class="modal hide fade" tabindex="-1"
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
												<td>
											<!-- 	<a class="" data-toggle="modal"	href="#weather_main_div"></a> -->
													 <img   onclick="WeatherPanel()"
														src="../MetroStyleFiles/menu-weather.png" />
														<h4>天气</h4>
												</td>
												<td>
												<!-- <a class="" data-toggle="modal" href="#tax_main_div"> </a> -->
												<img   onclick="taxPanel()" 
														src="../MetroStyleFiles/menu-tax.png" />
														<h4>税费计算</h4>
												</td>
												<td> <img onclick="stockModule()"
														src="../MetroStyleFiles/menu-stock.png" />
														<h4>股票</h4>
												</td>
												<td><img onclick="mesSend()" src="../MetroStyleFiles/menu-technology.png" />
													<h4>消息推送</h4></td>
											</tr>
											<tr>
												<td>		<img  onclick="recognizationPanel()"
														src="../MetroStyleFiles/menu-recognition.png" />
														<h4>奖项管理</h4>
												</td>
												<td><a href="DataVisualization.jsp"><img  class="mes-openbt" data-mesid="message-tax" 
														src="../MetroStyleFiles/menu-price.png" />
														<h4>胖和数据</h4></a>
												</td>
												<td> <!-- onclick="signaturePanel()"  -->
												 <a href="Signature.jsp?UID=<%=uid%>">
												<img 
														src="../MetroStyleFiles/menu-pen.png" /></a>
														<h4>电子签名</h4>
												</td>
												<td><a href="quoteDetail.jsp"><img src="../MetroStyleFiles/menu-develop.png" />
													<h4>开发中</h4></a></td>
											</tr>
											<%-- <tr>
											<td>
												 <a href="face.jsp?UID=<%=uid%>">
												 <img src="../MetroStyleFiles/menu-face.png" /></a>
													<h4>测颜值</h4></td>
												<td>
												<!--  onclick="SpeechPanel()" -->
												 <a href="speak.jsp?UID=<%=uid%>"><img 
														src="../MetroStyleFiles/menu-signature.png" /></a>
														<h4>智能语音</h4>
												</td>
												<td><a href="quoteDetail.jsp"><img src="../MetroStyleFiles/menu-develop.png" />
													<h4>开发中</h4></a></td>
												<td><img src="../MetroStyleFiles/menu-develop.png" />
													<h4>开发中</h4></td>
												
											</tr> --%>
										</table>
									</div>
								</div>
							<!-- 	<div id="weather_main_div" class="modal hide fade" tabindex="-1"
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
										<table width="100%" id="weather" style="margin-top:0px;margin-bottom: -20px;background-color:#fff;">
										</table>
									</div>
								</div> -->
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
												<td><input type="text" id="taxIncome2" /></td>
											</tr>
											<tr>
												<td>五险一金：</td>
												<td><input type="text" id="payment2" /></td>
											</tr>
											<tr>
												<td colspan="2" style="text-align: center; padding: 0px;">
													<button class="btnAthena EbtnLess clientTheme"
														style="margin-top: -30px; padding: 0px;background-color:#00B287;"
														id="tax_submit_button">计算</button>
												</td>
											</tr>
											<tr>
												<td>含税级距计算：</td>
												<td><span id="levelcalc2"></span></td>
											</tr>
											<tr>
												<td>不含税级距计算：</td>
												<td><span id="nolevelcalc2"></span></td>
											</tr>
										</table>
									</div>
								</div>
								<!-- <div id="stock_main_div" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="stock_main_div"
									aria-hidden="true" data-backdrop="static">
									<div id="stockListHeader" class="modal-header" style="text-align: center;">
										<img src="../MetroStyleFiles/Add1.png" 
											style="float: left; height: 25px; cursor: pointer; margin-top: 0px;" onclick="addStock()"/>
										<h3>
											<b>股票行情</b>
										</h3>
										<img id="stockClose" src="../MetroStyleFiles/Close.png" data-dismiss="modal"
											aria-hidden="true"
											style="float: right; height: 25px; cursor: pointer; margin-top: -40px;" />
											
									</div>
									<div id="stockListBody" class="modal-body readmoreHpop"
										style="white-space: pre-line; padding: 0px 10px;">
										<table width="100%" id="stock" style="margin-bottom: -20px;">
											
										</table>
									</div>
									<div id="addStock" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel1"
									aria-hidden="true" data-backdrop="static">
									<div class="modal-body readmoreHpop"
										style="white-space: pre-line; padding: 0px; ">
												    <table id="stockTableForm">
												    <tr>
												        <td>
												        	<span>请输入股票代码：</span><input type="text" placeholder="股票代码" id="stockcodeKey"  required style="width:100px;"/><input id="addStockBtn" type="button" value="添加" />
												        </td>
												      </tr>
												 </table>
									</div>
									
								</div>
								</div> -->
								<div id="UserInfo" class="modal hide fade" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel1"
									aria-hidden="true" data-backdrop="static">
									<div class="modal-body readmoreHpop"
										style="white-space: pre-line; padding: 0px;">
										<img src="../MetroStyleFiles/Close2.png" data-dismiss="modal"
											aria-hidden="true"
											style="float: right; height: 27px; cursor: pointer; margin-top: -15px; margin-right: 5px;" />
										<div id="userInfoDiv">
											<div id="info_interact"  style="position: absolute;width:100%;">
												<img class="like" src="../MetroStyleFiles/like.png"/>
												<img class="zan"  data-dismiss="modal" aria-hidden="true" onclick="recognizationPanel()" src="../MetroStyleFiles/zan.png"/>
											</div>
											<div id="info_interact2"
												style="position: absolute; width: 100%; display: block; margin-top: 45px;">
												<span class="like"
													style="float: left; margin-left: 25px; width: 40px; text-align: center;"></span>
												<span class="zan"
													style="float: right; margin-right: 30px; margin-top: -20px; width: 40px; text-align: center;"></span>
											</div>
											<img id="info_imgurl"
												src="http://wx.qlogo.cn/mmopen/soSX1MtHexV6ibXOvfzOoeEwjLFW3dyR80Mic1pzmg5b1qV0EFD4aegic9hic5iawRIDgJIImrY0XybC57j16ka4SabDCqy3TTtd2/0"
												alt="userImage" class="matesUserImage2" style="position: relative;">
											<div id="info_username" style="margin-top:-20px;">
												<span></span>
											</div>
											<table id="info_all">
												<tr>
													<td><img src="../MetroStyleFiles/group2.png"/></td>
													<td><div id="info_group"></div></td>
												</tr>
												<tr>
													<td><img src="../MetroStyleFiles/telephone2.png"/></td>
													<td><div id="info_phone"></div></td>
												</tr>
												<tr>
													<td><img src="../MetroStyleFiles/email2.png"/></td>
													<td><div id="info_email"></div></td>
												</tr>
											</table>
											<div id="info_selfIntro" style="margin-top:-10px;width:100%;text-align:center;"></div>
											<div style="width:100%; padding:0px;margin-top:-35px;margin-bottom:-40px;overflow-x: auto;">
												<table id="info_tag" style="margin-left:auto;margin-right:auto;">
													<tr>
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
												    <table id="tableForm" style="margin-top:20px;">
												    <tr>
												        <td class="tdText"><img class='imgclass' src='../MetroStyleFiles/username2.png'/></td>
												        <td class="tdInput">
												          <input type="text" placeholder="请输入公司名称" id="companyname"  pattern="^[\u4E00-\u9FA0\s]+$|^[a-zA-Z\s]+$" required/>
												        </td>
												      </tr>
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
												      <!--
												      <tr>
												        <td class="tdText"><img class='imgclass' src='../MetroStyleFiles/role2.png'/></td>
												        <td>
												          <select id="roleSelect">
															<option selected="selected">Individual Contributor</option> 
															<option>Team Lead</option>
															<option>Technical Lead</option>
															<option>Bussiness Analysis</option>
															<option>Other</option>
														</select>
												        </td>
												      </tr>
												       <tr>
												        <td class="tdText"><img class="imgclass" src="../MetroStyleFiles/group2.png"/></td>
												        <td>
												         <select id='groupSelect'>
															<option selected="selected">Chen, Hua-Quan</option>
															<option>Kang, Ning</option>
															<option>Zeng, Qiang</option>
															<option>Li, Jian-Jun</option>
															<option>Wu, Sha</option>
															<option>Other</option>
														</select>
												        </td>
												      </tr> -->
												      <!-- <tr>
												        <td class="tdText"><img class="imgclass" src="../MetroStyleFiles/selfIntro2.png"/></td>
												        <td>
												          <input class="inputClass" type="text" placeholder="请输入个人简介" id="selfIntro" required/>
												        </td>
												      </tr> -->
												      <!-- <tr class="sliderclass">
												        <td style="width:50px" >Java</td>
												        <td>
															<input id="javatag" class="easyui-slider" style="width:220px" data-options="
																		showTip:true
																	"/>
												        </td>
												      </tr>
												      <tr>
												        <td style="width:50px">H5</td>
												        <td>
															<input id="htmltag" class="easyui-slider" style="width:220px" data-options="
																		showTip:true
																	"/>
												        </td>
												      </tr>
												      <tr>
												        <td style="width:50px">WS</td>
												        <td>
															<input id="webservicetag" class="easyui-slider" style="width:220px" data-options="
																		showTip:true
																	"/>
												        </td>
												      </tr>
												      <tr>
												        <td style="width:50px">ETL</td>
												        <td>
															<input id="etltag" class="easyui-slider" style="width:220px" data-options="
																		showTip:true
																	"/>
												        </td>
												      </tr> -->
												      
												 </table>
											    <button class="btnAthena EbtnLess" style="background-color:#00B287;margin-bottom: -35px;" id="registerBtn">在一起吧</button>
										<!-- 	</form>  -->
									</div>
								</div>


								<div class="tab-pane" id="WorkMates">
									<div class="Work_Mates_div2" id="Work_Mates_div">
									</div>
<div id="return-top"><img class="scroll-top" src="../Jsp/PIC/upgrade.png" alt="" width="50px"></div>  
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


				
		
		<!-- START MESSAGE STATION -->
	<div id="mes-station">
		

<!--  Start Tax Page  -->	
		<div class="mes-container item-profileview transparent-black"
			data-mesid="message-tax">
			<!-- Start Content Holder -->
			<div class="mes-contentholder">
				<div class="item-profilebody">
					<!-- Start Background -->
					<div class="mes-content item-profilebg solid-smoke"
						data-show="hmove" data-start="-100" data-showdura="400">
						<img style="position:absolute;top:8px;right:20px;" src="" alt="Logo" class="HpLogo">
						<div class="clientTheme" style="width:100%;height:4px;background:#56B39D;position:absolute;top:70px;"></div>
						</div>
					<!-- End Background -->
					<!-- Start Control Bar -->
					<div class="mes-content item-ctrlbar-5" data-show="fade"
						data-showdura="200">
						<div class="mes-closebt light-text floatleft">
							<img src="../MetroStyleFiles//EXIT1.png"
								style="width: 30px; height: 30px;position:absolute;top:20px;left:20px;" />
						</div>
						<div class="clearspace"></div>
					</div>
					<!-- End Control Bar -->
					<!-- Start Header Photo -->
					<div class="mes-content item-headerphoto" style="width:80%;position:absolute;top:100px;left:10%;" data-show="bounceInDown">
							<legend>世界时间</legend>
							<div style="margin-top:0px;margin-bottom: -20px;background-color:#fff;">
							
									</div>
					</div> 
					<!-- End Header Photo -->
				</div>
				<img  src="../MetroStyleFiles//image/sitemaintenance_robot_animation.gif" alt="demo-headphoto">
			</div>
		</div>
		<!-- End Content Holder -->
<!--  End Tax Page  -->			
		
		
		
</div>
<!-- END MESSAGE STATION -->

	<!-- BEGIN FOOTER -->
	<div id="footer">
		<span class="clientCopyRight"><nobr></nobr></span>
	</div>
	<!-- END FOOTER -->
</body>
</html>