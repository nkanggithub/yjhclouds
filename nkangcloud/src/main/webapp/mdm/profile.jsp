﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.nkang.kxmoment.baseobject.GeoLocation"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%@ page import="com.nkang.kxmoment.baseobject.ClientMeta"%>
<%	
SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd"); 
Date date=new Date();
String currentDate = format.format(date);
String AccessKey = RestUtils.callGetValidAccessKey();
String uid = request.getParameter("UID");

MongoDBBasic.updateUser(uid);
String curLoc=null;
String city=null;
//WeChatUser wcu;
session.setAttribute("UID", uid);
if (session.getAttribute("location") == null) {
	GeoLocation loc = RestUtils.callGetDBUserGeoInfo(uid);
//	wcu = RestUtils.getWeChatUserInfo(AccessKey, uid);
	String message = RestUtils.getUserCurLocStrWithLatLng(loc.getLAT(),loc.getLNG());
	 JSONObject demoJson = new JSONObject(message);
     if(demoJson.has("result")){
  	    JSONObject JsonFormatedLocation = demoJson.getJSONObject("result");
  	 	curLoc = JsonFormatedLocation.getString("formatted_address");
  	 	city = JsonFormatedLocation.getJSONObject("addressComponent").getString("city");
     }
	session.setAttribute("location", curLoc);
	session.setAttribute("city", city);
//	session.setAttribute("wcu", wcu);
} else {
//	wcu = (WeChatUser) session.getAttribute("wcu");
	curLoc = (String) session.getAttribute("location");
}
HashMap<String, String> res=MongoDBBasic.getWeChatUserFromOpenID(uid);
MongoDBBasic.updateVisited(uid,currentDate,"profile",res.get("HeadUrl"),res.get("NickName"));
boolean isInternalSeniorMgt=MongoDBBasic.checkUserAuth(uid, "isInternalSeniorMgt");
boolean isInternalImtMgt=MongoDBBasic.checkUserAuth(uid, "isInternalImtMgt");
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
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/font-awesome.css">
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
<link rel="stylesheet" type="text/css" href="../nkang/assets_athena/bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="../mdm/uploadfile_css/demo.css" />
<link rel="stylesheet" type="text/css" href="../mdm/uploadfile_css/component.css" />
<script type="text/javascript" src="../Jsp/JS/jquery-1.8.0.js"></script>
<script type="text/javascript" src="../nkang/jquery-form.js"></script>
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
.imgSelect{
height:50%;
width:24%;
position:relative;
float:left;
}
.imgSelect img{
width: 100%;height:100%;}
.imgCB
{
position: absolute;
bottom: 2px;
right: 2px;
width: 15px;
height: 15px;}
.taxTable{
font-size:13px;
margin-left:5%;
width:90%;
}
.taxTable th{
text-align:left;
font-size:15px;
border:1px solid gray;
height:30px;
}
.taxTable td{
border:1px solid gray;
}
/*ajax*/

.sk-circle {
  margin: 40px auto;
  width: 40px;
  height: 40px;
      position: absolute;
    top: 38%;
    left: 45%;
    display:none;
     }
  .sk-circle .sk-child {
    width: 100%;
    height: 100%;
    position: absolute;
    left: 0;
    top: 0; }
  .sk-circle .sk-child:before {
    content: '';
    display: block;
    margin: 0 auto;
    width: 15%;
    height: 15%;
    background-color: white;
    border-radius: 100%;
    -webkit-animation: sk-circleBounceDelay 1.2s infinite ease-in-out both;
            animation: sk-circleBounceDelay 1.2s infinite ease-in-out both; }
  .sk-circle .sk-circle2 {
    -webkit-transform: rotate(30deg);
        -ms-transform: rotate(30deg);
            transform: rotate(30deg); }
  .sk-circle .sk-circle3 {
    -webkit-transform: rotate(60deg);
        -ms-transform: rotate(60deg);
            transform: rotate(60deg); }
  .sk-circle .sk-circle4 {
    -webkit-transform: rotate(90deg);
        -ms-transform: rotate(90deg);
            transform: rotate(90deg); }
  .sk-circle .sk-circle5 {
    -webkit-transform: rotate(120deg);
        -ms-transform: rotate(120deg);
            transform: rotate(120deg); }
  .sk-circle .sk-circle6 {
    -webkit-transform: rotate(150deg);
        -ms-transform: rotate(150deg);
            transform: rotate(150deg); }
  .sk-circle .sk-circle7 {
    -webkit-transform: rotate(180deg);
        -ms-transform: rotate(180deg);
            transform: rotate(180deg); }
  .sk-circle .sk-circle8 {
    -webkit-transform: rotate(210deg);
        -ms-transform: rotate(210deg);
            transform: rotate(210deg); }
  .sk-circle .sk-circle9 {
    -webkit-transform: rotate(240deg);
        -ms-transform: rotate(240deg);
            transform: rotate(240deg); }
  .sk-circle .sk-circle10 {
    -webkit-transform: rotate(270deg);
        -ms-transform: rotate(270deg);
            transform: rotate(270deg); }
  .sk-circle .sk-circle11 {
    -webkit-transform: rotate(300deg);
        -ms-transform: rotate(300deg);
            transform: rotate(300deg); }
  .sk-circle .sk-circle12 {
    -webkit-transform: rotate(330deg);
        -ms-transform: rotate(330deg);
            transform: rotate(330deg); }
  .sk-circle .sk-circle2:before {
    -webkit-animation-delay: -1.1s;
            animation-delay: -1.1s; }
  .sk-circle .sk-circle3:before {
    -webkit-animation-delay: -1s;
            animation-delay: -1s; }
  .sk-circle .sk-circle4:before {
    -webkit-animation-delay: -0.9s;
            animation-delay: -0.9s; }
  .sk-circle .sk-circle5:before {
    -webkit-animation-delay: -0.8s;
            animation-delay: -0.8s; }
  .sk-circle .sk-circle6:before {
    -webkit-animation-delay: -0.7s;
            animation-delay: -0.7s; }
  .sk-circle .sk-circle7:before {
    -webkit-animation-delay: -0.6s;
            animation-delay: -0.6s; }
  .sk-circle .sk-circle8:before {
    -webkit-animation-delay: -0.5s;
            animation-delay: -0.5s; }
  .sk-circle .sk-circle9:before {
    -webkit-animation-delay: -0.4s;
            animation-delay: -0.4s; }
  .sk-circle .sk-circle10:before {
    -webkit-animation-delay: -0.3s;
            animation-delay: -0.3s; }
  .sk-circle .sk-circle11:before {
    -webkit-animation-delay: -0.2s;
            animation-delay: -0.2s; }
  .sk-circle .sk-circle12:before {
    -webkit-animation-delay: -0.1s;
            animation-delay: -0.1s; }

@-webkit-keyframes sk-circleBounceDelay {
  0%, 80%, 100% {
    -webkit-transform: scale(0);
            transform: scale(0); }
  40% {
    -webkit-transform: scale(1);
            transform: scale(1); } }

@keyframes sk-circleBounceDelay {
  0%, 80%, 100% {
    -webkit-transform: scale(0);
            transform: scale(0); }
  40% {
    -webkit-transform: scale(1);
            transform: scale(1); } }
</style>


<script src="../nkang/js_athena/jquery.circliful.min.js"></script>
<script src="../nkang/assets_athena/bootstrap/js/bootstrap.js"></script>
<script	src="../MetroStyleFiles/sweetalert.min.js"></script>
<script type="text/javascript" src="../MetroStyleFiles//JS/openmes.min.js"></script>
<script src="../Jsp/JS/modernizr.js"></script>
<script src="../Jsp/JS/jSignature.min.noconflict.js"></script>
<script type="text/javascript" src="../nkang/autocomplete/jquery-ui.js"></script>

<script>
$(document).ajaxStart(function () {
	$(".sk-circle").show();
	$("#shadow").show();
    }).ajaxStop(function () {
    	$(".sk-circle").hide();
    	$("#shadow").hide();
    });
  
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
	
	$(".imgSelect input").live("click",function(){
		$(this).parent().siblings().find("input").attr("checked", false);
	})
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
		//getMDLUserLists();
		getCompanyInfo();
		getRealName();
		getAllRegisterUsers();
});
function uploadPic(obj){
		if($(obj).val()!='') {
			  $("#submit_form").ajaxSubmit(function(message) {
				  console.log(message);
				  $("#hiddenPic").val(message);
			  } );

		}
		return false;
}
function uploadGiftPic(obj){
	if($(obj).val()!='') {
		  $("#submit_gift").ajaxSubmit(function(message) {
			  console.log(message);
			  $("#hiddenGift").val(message);
		  } );

	}
	return false;
}
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
			skim(jsons.skimNum);
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

function skim(jsons){
	var table="";
	var skimTotalNum=0,nowSkimTotalNum=0;
	if(jsons!=null&&jsons!='null'){
		table+="<tr><th>日期</th><th>访问量</th></tr>";
		for (var i = 0; i < jsons.length; i++) {
			var tr="<tr>";
			tr+="	<td width='50%' align='center'>"+jsons[i].date+"</td>";
			tr+="	<td width='50%' align='center'>"+jsons[i].num+"</td>";
			tr+="</tr>";
			table+=tr;
			skimTotalNum+=jsons[i].num;
		}
		nowSkimTotalNum=jsons[jsons.length-1].num;
	}
//	$("#skimHis").html(table);
	$("#skimTotalNum").html(skimTotalNum);
	$("#nowSkimTotalNum").html("今日访问量:"+nowSkimTotalNum);
}
function noAuth(flag){
	if(flag==1){
		swal("你没有权限哦", "只有高级管理和拥有信息发布的权限的员工才能访问该模块！", "error");
	}else{
		swal("你没有权限哦", "只有高级管理才能访问该模块！", "error");
	}
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
				if(jsons.results[0].IsRegistered!="true"){
						swal("你还未注册哦", "未注册用户很多功能不能使用,建议点击头像立即注册！", "error");
				}
			}
		}
	});
}
function showHGPart(){
	$("#hanguo").show();
	$("#taiwan").hide();
	$("#riben").hide();
	$("#xinjiapo").hide();
}
function showRBPart(){
	$("#hanguo").hide();
	$("#taiwan").hide();
	$("#riben").show();
	$("#xinjiapo").hide();
}
function showXJPPart(){
	$("#hanguo").hide();
	$("#taiwan").hide();
	$("#riben").hide();
	$("#xinjiapo").show();
}
function showTWPart(){
	$("#hanguo").hide();
	$("#taiwan").show();
	$("#riben").hide();
	$("#xinjiapo").hide();
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
	        	//	getMDLUserLists();
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
	var imgType="0";
	var img="";
	if($("#hiddenGift").val()!=""){
		img=$("#hiddenGift").val();
	    imgType="1";
	}
	var userImage=$("#userPic").val();
	console.log("img-------"+img);
	console.log("imgType-------"+imgType);
	var to = $("#to option:selected").val();
	swal({  
        title:"是否发送给所有人？",  
        text:"<input type='hidden'>",
        html:"true",
        showConfirmButton:"true", 
		showCancelButton: true,   
		closeOnConfirm: false,  
        confirmButtonText:"是",  
        cancelButtonText:"否",
        animation:"slide-from-top"  
      }, 
		function(inputValue){
			if (inputValue === false){
				$.ajax({
			        cache: false,
			        type: "POST",
			        url:"../userProfile/userCongratulate",
			        data:{
			        	openID:$("#openID").val(),
			        	isAll:"false",
			        	from:$("#from").text(),
			        	points:$("#points").val(),
			        	to:$("#to option:selected").val(),
			        	type:$("#type option:selected").val(),
			        	comments:$("#comments").val(),
			        	img:img,
			        	imgType:imgType,
			        	userImage:userImage
			        	
			        },
			        async: true,
			        error: function(request) {
			            alert("Connection error");
			        },
			        success: function(data) {
			        	swal("恭喜!", "给"+to+"的Recognition已发送成功，他已收到该消息！", "success");
			        	hideBouncePanel();
			        }
			    });
			 return false;}
			else{
				$.ajax({
			        cache: false,
			        type: "POST",
			        url:"../userProfile/userCongratulate",
			        data:{
			        	openID:$("#openID").val(),
			        	isAll:"true",
			        	from:$("#from").text(),
			        	points:$("#points").val(),
			        	to:$("#to option:selected").val(),
			        	type:$("#type option:selected").val(),
			        	comments:$("#comments").val(),
			        	img:img,
			        	imgType:imgType,
			        	userImage:userImage
			        	
			        },
			        async: true,
			        error: function(request) {
			            alert("Connection error");
			        },
			        success: function(data) {
			        	swal("恭喜!", "给"+to+"的Recognition已发送成功，有"+data+"个人已收到该消息！", "success");
			        	hideBouncePanel();
			        }
			    });
			}});
}
function closeQuotation()
{
	//$("#fastPush").hide();
	}
function sendSMS(){
	swal({  
        title:"确定一键发送短信吗",  
        text:"",
        html:"true",
        showConfirmButton:"true", 
		showCancelButton: true,   
		closeOnConfirm: false,  
        confirmButtonText:"提交",  
        cancelButtonText:"取消",
        animation:"slide-from-top"  
      }, 
		function(inputValue){
			if (inputValue === false){
			 return false;}
			else{
				$.ajax({
			        cache: false,
			        type: "POST",
			        url:"../userProfile/sendSMS",
			        data:{
			        },
			        async: true,
			        error: function(request) {
			            alert("Connection error");
			        },
			        success: function(data) {
			        	swal("恭喜！",data, "success");
			        	hideBouncePanel();
			        }
			    });
			}});
}
function showFastPost()
{
	//$("#fastPush").show();
	var img=$(".imgSelect input[type='checkbox']:checked").siblings("img").attr("src");
	var imgType="0";
	if($("#hiddenPic").val()!=""){
		img=$("#hiddenPic").val();
		imgType="1";
	}
	swal({  
        title:"确定一键智能发布吗",  
        text:"",
        html:"true",
        showConfirmButton:"true", 
		showCancelButton: true,   
		closeOnConfirm: false,  
        confirmButtonText:"提交",  
        cancelButtonText:"取消",
        animation:"slide-from-top"  
      }, 
		function(inputValue){
			if (inputValue === false){
			$("#commonPush").show();
			 return false;}
			else{
				$.ajax({
			        cache: false,
			        type: "POST",
			        url:"../sendQuotationMessage",
			        data:{
			        	openid:$("#uid").val(),
			        	title:$("#notificationTitle").val(),
			        	img:img,
			        	imgType:imgType
			        },
			        async: true,
			        error: function(request) {
			            alert("Connection error");
			        },
			        success: function(data) {
			        	swal("恭喜！", "您的报价已成功推送到"+data+"个人", "success");
			        	hideBouncePanel();
			        }
			    });
			}});
	}
function postNotification(){
	var img=$(".imgSelect input[type='checkbox']:checked").siblings("img").attr("src");
	var imgType="0";
	var type=$("#notificationType option:selected").val();
	if($("#hiddenPic").val()!=""){
		img=$("#hiddenPic").val();
	    imgType="1";
	}
	$.ajax({
        cache: false,
        type: "POST",
        url:"../userProfile/addNotification",
        data:{
        	openId:$("#uid").val(),
        	title:$("#notificationTitle").val(),
        	url:$("#notificationURL").val(),
        	type:type,
        	img:img,
        	imgType:imgType,
        	content:$("#content").val()
        	
        },
        async: true,
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
        	swal("恭喜！", "您的图文已成功推送到"+data+"个人", "success");
        	$("#fastPush").hide();
        	hideBouncePanel();
        }
    });

}
function postQuotation(){
	$.ajax({
        cache: false,
        type: "POST",
        url:"../sendQuotationMessage",
        data:{
        	openid:$("#uid").val(),
        	title:$("#notificationTitle").val()
        },
        async: true,
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
        	swal("恭喜！", "您的报价发布成功", "success");
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
		$("body").append("<div id='taxPart' class='bouncePart form-horizontal' style='position:absolute;z-index:10000;top:90px;width:96%;height:80%;overflow:scroll;margin-left:2%;'>"
				 +" <ul class='nav nav-tabs' id='myTabs' style='border-bottom: 1px solid #0067B6!important;'>"
				  +"<li class='active'><a href='#aElements' onclick='showTWPart()'data-toggle='tab'>中国台湾</a></li>"	
				  +"<li class=''><a href='#bElements' onclick='showXJPPart()' data-toggle='tab'>新加坡</a></li>"
				+"<li class=''><a href='#cElements' onclick='showRBPart()' data-toggle='tab'>日本</a></li>"
				+"<li class=''><a href='#dElements' onclick='showHGPart()' data-toggle='tab'>韩国</a></li></ul>"
				 +" <table class='taxTable' id='taiwan'>"
				  +"<tbody>"
				  +"<tr><th>化学品名</th><th>关税率(中国台湾)</th><th>计算</th></tr>"
				  +"<tr> <td style='width:140px;'>ABS丙烯腈-丁二烯-苯乙烯共聚物</td><td style='width:70px;'>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>" 
				  +"<tr><td>AS丙烯腈-苯乙烯</td><td>0%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>PS聚苯乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>POM聚甲醛</td><td>0%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>PC聚碳酸酯</td><td>0%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>PMMA聚甲基丙烯酸甲酯MMA60%PS40%</td><td>0%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>共聚PP</td><td>0%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>均聚PP</td><td>5%</td><td>单价*汇率*17%*5</td></tr>"
				  +"<tr><td>HDPE 低压高密度聚乙烯 &gt;=0.94</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>LDPE 高压低密度聚乙烯 &lt;0.94</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>LLDPE 线型低密度聚乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>PVC 聚氯乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>PA6 PA66 聚酰胺(尼龙)</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>PBT</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>K/Q胶 苯乙烯丁二烯聚合物</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>MS 甲基丙烯酸-苯乙烯共聚物</td><td>0%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>SM 苯乙烯单体</td><td>0%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>PC/ABS (PC含量50%以上)</td><td>0%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>ASA 丙烯腈-苯乙烯-丙烯酸酯</td><td>0%</td><td>单价*汇率*17%</td></tr>"
				 +" </tbody>"
				  +"</table>"
				   
				 +" <table class='taxTable' id='xinjiapo' style='display:none'>"
				  +"<tbody>"
				    +"<tr><th>化学品名</th><th>关税率(新加坡)</th><th>计算</th></tr>"
				  +"<tr> <td style='width:140px;'>ABS丙烯腈-丁二烯-苯乙烯共聚物</td><td style='width:120px;'>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>" 
				  +"<tr><td>AS丙烯腈-苯乙烯</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>PS聚苯乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>POM聚甲醛</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>PC聚碳酸酯</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>PMMA聚甲基丙烯酸甲酯MMA60%PS40%</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>共聚PP</td><td>0%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>均聚PP</td><td>6.5%</td><td>单价*汇率*17%*5</td></tr>"
				  +"<tr><td>HDPE 低压高密度聚乙烯 &gt;=0.94</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>LDPE 高压低密度聚乙烯 &lt;0.94</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>LLDPE 线型低密度聚乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>PVC 聚氯乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>PA6 PA66 聚酰胺(尼龙)</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>PBT</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>K/Q胶 苯乙烯丁二烯聚合物</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>MS 甲基丙烯酸-苯乙烯共聚物</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"</tbody>"
				  +"</table>"
				 
				   +"  <table class='taxTable' id='riben' style='display:none'>"
				  +"<tbody>"
				    +"<tr><th>化学品名</th><th>关税率(日本)</th><th>计算</th></tr>"
				  +"<tr> <td style='width:140px;'>ABS丙烯腈-丁二烯-苯乙烯共聚物</td><td style='width:120px;'>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>AS丙烯腈-苯乙烯</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>PS聚苯乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>POM聚甲醛</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>PC聚碳酸酯</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>PMMA聚甲基丙烯酸甲酯MMA60%PS40%</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>共聚PP</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"<tr><td>均聚PP</td><td>6.5%</td><td>单价*汇率*17%*5</td></tr>"
				  +"<tr><td>HDPE 低压高密度聚乙烯 &gt;=0.94</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>LDPE 高压低密度聚乙烯 &lt;0.94</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>LLDPE 线型低密度聚乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>PVC 聚氯乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>PA6 PA66 聚酰胺(尼龙)</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>PBT</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>K/Q胶 苯乙烯丁二烯聚合物</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
				  +"<tr><td>MS 甲基丙烯酸-苯乙烯共聚物</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				  +"</tbody>"
				+"	 </table>"

				   +"  <table class='taxTable' id='hanguo' style='display:none'>"
				  +"<tbody>"
				    +"<tr><th>化学品名</th><th>关税率(韩国)</th><th>计算</th></tr>"
				    +"<tr> <td style='width:140px;'>ABS丙烯腈-丁二烯-苯乙烯共聚物</td><td style='width:120px;'>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
					  +"<tr><td>AS丙烯腈-苯乙烯</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
					  +"<tr><td>PS聚苯乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
					  +"<tr><td>POM聚甲醛</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
					  +"<tr><td>PC聚碳酸酯</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
					  +"<tr><td>PMMA聚甲基丙烯酸甲酯MMA60%PS40%</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
					  +"<tr><td>共聚PP</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
					  +"<tr><td>均聚PP</td><td>6.5%</td><td>单价*汇率*17%*5</td></tr>"
					  +"<tr><td>HDPE 低压高密度聚乙烯 &gt;=0.94</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
					  +"<tr><td>LDPE 高压低密度聚乙烯 &lt;0.94</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
					  +"<tr><td>LLDPE 线型低密度聚乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
					  +"<tr><td>PVC 聚氯乙烯</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
					  +"<tr><td>PA6 PA66 聚酰胺(尼龙)</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
					  +"<tr><td>PBT</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
					  +"<tr><td>K/Q胶 苯乙烯丁二烯聚合物</td><td>6.5%</td><td>单价*汇率*17%*6.5%</td></tr>"
					  +"<tr><td>MS 甲基丙烯酸-苯乙烯共聚物</td><td>6.5%</td><td>单价*汇率*17%</td></tr>"
				 +" </tbody>"
				+"	 </table>"
				+"</div>");
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
	$("body").append("<div id='sendR'>"
			+"	<div class='rcommon' style='height:40px'><p class='bsLabel'>图文标题</p><input id='notificationTitle' style='height:35px;border:1px solid #005CA1' type='text' placeholder='请输入标题' class='input-xlarge bsBtn'></div>"
			+"	<div class='rcommon' style='height:40px'><p class='bsLabel'>主题图片</p><form id='submit_form' name='submit_form' action='../userProfile/uploadPicture' enctype='multipart/form-data' method='post'><input id='file-1' type='file' name='file-1'  onchange='uploadPic(this)' class='inputfile inputfile-1' data-multiple-caption='{count} files selected' multiple=''><label for='file-1' style='height: 15px;border-radius: 5px;line-height: 10px;text-align: center;width: 65%;'><svg xmlns='http://www.w3.org/2000/svg' width='20' height='17' viewBox='0 0 20 17'><path d='M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z'></path></svg> <span style='color: white;font-size: 16px;'>Choose a file…</span></label></form><input id='hiddenPic' type='hidden' /></div>"
			+"  <div class='rcommon' style='height:160px;margin-bottom: 8px;'><div class='imgSelect'><input type='checkbox' class='imgCB' checked='true'><img src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DnHEr&oid=00D90000000pkXM'></div><div class='imgSelect'><input type='checkbox' class='imgCB'><img src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DnUBS&oid=00D90000000pkXM'></div><div class='imgSelect'><input type='checkbox' class='imgCB'><img src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DnUC1&oid=00D90000000pkXM'></div><div class='imgSelect'><input type='checkbox' class='imgCB'><img src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DnUD9&oid=00D90000000pkXM'></div><div class='imgSelect'><input type='checkbox' class='imgCB'><img src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000Dnn6l&amp;oid=00D90000000pkXM'></div><div class='imgSelect'><input type='checkbox' class='imgCB'><img src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000Dnn6g&amp;oid=00D90000000pkXM'></div><div class='imgSelect'><input type='checkbox' class='imgCB'><img src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000Dnn6R&amp;oid=00D90000000pkXM'></div><div class='imgSelect'><input type='checkbox' class='imgCB'><img src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000Dnn6M&amp;oid=00D90000000pkXM'></div></div>"
			+"	<div class='rcommon' onclick='showFastPost()' style='height:40px;cursor:pointer;'><p class='bsLabel' style='width:96%!important;text-align:center!important;background-color: #005CA1;color: white;height:40px;line-height:40px;border-radius:5px;'>不想再写了？点击我一键智能发布吧</p></div>"
			<%if(isInternalSeniorMgt==true) { 
			%>
			+"<img onclick='sendSMS()' style='width:100px;cursor:pointer;position: fixed;bottom: 40px;' src='https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000EDQRU&amp;oid=00D90000000pkXM'>"
			<% } %>
			+"  <div id='commonPush' style='display:none;margin-top: 10px;'><div class='rcommon'><p class='bsLabel'>图文类型</p><select class='bsBtn' id='notificationType' style='border: 1px solid #005CA1;'><option value='js'>技术</option><option value='gt'>沟通</option><option value='hq'>行情</option></select></div>"
			+"	<div class='rcommon'><p class='bsLabel'>网页链接</p><input id='notificationURL' type='text' style='width:75%;height:35px;border:1px solid #005CA1' placeholder='不想输入网络链接？那直接填内容吧'  class='input-xlarge bsBtn'></div>"
			+"	<div class='rcommon'><textarea id='content' style='height:180px;width:95%;line-height:20px;border:1px solid #005CA1' placeholder='请输入内容' class='input-xlarge bsBtn'></textarea></div>"
			+"	<div class='rcommon' ><button style='margin-top:20px;width:95%;background:#005CA1;text-shadow:none;color:white!important;' onclick='postNotification()' name='doublebutton-0' class='btn'>提交</button></div></div>"
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
				+"	<li id='aaElements' class='active'><a href='#aElements' data-toggle='tab'>发布表彰</a></li>"
				+"	<li id='bbElements'><a href='#bElements' onclick='getRecognitionInfoByOpenID()' data-toggle='tab'>收到的表彰</a></li></ul>"
				+"  <div class='tab-content' id='dvTabContent' style='border: 0px;'>"
				+"	<div class='tab-pane active' id='aElements'>"
				+"	<div id='sendRe' style='height:110%;width:108%;overflow:scroll;'>"
				+"	<div class='rcommon'><p class='bsLabel'>发送人</p><p class='bsBtn' id='from'>"+realName+"</p></div>"
				+"	<div class='rcommon'><p class='bsLabel'>接收人</p><select style='border:1px solid #005CA1;' class='bsBtn' id='to'>"+selectContent+"</select></div>"
				+"	<div class='rcommon' style='height:40px'><p class='bsLabel'>礼物图片</p><form id='submit_gift' name='submit_gift' action='../userProfile/uploadPicture' enctype='multipart/form-data' method='post'><input id='file-1' type='file' name='file-1'  onchange='uploadGiftPic(this)' class='inputfile inputfile-1' data-multiple-caption='{count} files selected' multiple=''><label for='file-1' style='height: 15px;border-radius: 5px;line-height: 10px;text-align: center;width: 62%;'><svg xmlns='http://www.w3.org/2000/svg' width='20' height='17' viewBox='0 0 20 17'><path d='M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z'></path></svg> <span style='color: white;font-size: 16px;'>Choose a file…</span></label></form><input id='hiddenGift' type='hidden' /></div>"
				+"	<div class='rcommon'><p class='bsLabel'>类型</p><select class='bsBtn'  style='border:1px solid #005CA1;' id='type'><option>Bais For Action</option><option>Innovators at Heart</option><option>Partnership First</option></select></div>"
				+"	<div class='rcommon'><p class='bsLabel'>虚拟货币</p><input id='points' type='text' style='height:35px;border:1px solid #005CA1;' placeholder='请输入虚拟货币' class='input-xlarge bsBtn'></div>"
				+"	<div class='rcommon'><textarea id='comments' style='height:130px;width:95%;line-height:20px;border:1px solid #005CA1;' placeholder='请输入感言' class='input-xlarge bsBtn'></textarea></div>"
				+"	<div class='rcommon' style='margin-top:100px'><button onclick='postRecognition()' style='height:35px;width:95%;background:#005CA1;text-shadow:none;color:white!important;' name='doublebutton-0' class='btn'>提交</button><div style='position: relative;top: -25px;text-align:left;width:30%;' ></div></div>"
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
		
		$("body").append("<div class='TAB2class bouncePart' id='recognizeForm'>"
				+"	<ul class='nav nav-tabs' id='myTabs'>"
				+"	<li id='aaElements' class='active'><a href='#aElements' data-toggle='tab'>发送Recognition</a></li>"
				+"	<li id='bbElements'><a href='#bElements' onclick='getRecognitionInfoByOpenID()' data-toggle='tab'>收到的Recognition</a></li></ul>"
				+"  <div class='tab-content' id='dvTabContent' style='border: 0px;'>"
				+"	<div class='tab-pane active' id='aElements'>"
				+"	<div id='sendRe' style='height:110%;width:108%;overflow:scroll;'>"
				+"	<div class='rcommon'><p class='bsLabel'>发送人</p><p class='bsBtn' id='from'>"+realName+"</p></div>"
				+"	<div class='rcommon'><p class='bsLabel'>接收人</p><select style='border:1px solid black;' class='bsBtn' id='to'>"+selectContent+"</select></div>"
				+"	<div class='rcommon' style='height:40px'><p class='bsLabel'>礼物图片</p><form id='submit_gift' name='submit_gift' action='../userProfile/uploadPicture' enctype='multipart/form-data' method='post'><input id='file-1' type='file' name='file-1'  onchange='uploadGiftPic(this)' class='inputfile inputfile-1' data-multiple-caption='{count} files selected' multiple=''><label for='file-1' style='height: 15px;border-radius: 5px;line-height: 10px;text-align: center;width: 65%;'><svg xmlns='http://www.w3.org/2000/svg' width='20' height='17' viewBox='0 0 20 17'><path d='M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z'></path></svg> <span style='color: white;font-size: 16px;'>Choose a file…</span></label></form><input id='hiddenGift' type='hidden' /></div>"
				+"	<div class='rcommon'><p class='bsLabel'>类型</p><select class='bsBtn'  style='border:1px solid black;' id='type'><option>Bais For Action</option><option>Innovators at Heart</option><option>Partnership First</option></select></div>"
				+"	<div class='rcommon'><p class='bsLabel'>Points</p><input id='points' type='text' style='height:35px;border:1px solid black;' placeholder='请输入points' class='input-xlarge bsBtn'></div>"
				+"	<div class='rcommon'><textarea id='comments' style='height:130px;width:95%;line-height:20px;border:1px solid black;' placeholder='请输入感言' class='input-xlarge bsBtn'></textarea></div>"
				+"	<div class='rcommon' style='margin-top:100px'><button onclick='postRecognition()' style='height:35px;width:95%;background:black;text-shadow:none;color:white!important;' name='doublebutton-0' class='btn'>提交</button><div style='position: relative;top: -25px;text-align:left;width:30%;' ></div></div>"
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
	$("#fastPush").hide();
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
					$("#info_group").html("&nbsp;&nbsp;&nbsp;&nbsp;"+jsons.results[0].companyName);
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
//	$('#UserInfo').modal('hide');
//	$('#registerform').modal('show');
	$.ajax({
		type : "GET",
		url : "../userProfile/getMDLUserLists",
		data : {
			UID : $('#uid').val()
		},
		cache : false,
		success : function(data) {
			if(data){ 
				var realName="";
				var phone="";
				var email="";
				var selfIntro="";
			   data = data.replace(/:null/g, ':"未注册"');
				data = '{"results":' + data + '}';
				var jsons = eval('(' + data + ')');
				if (jsons.results.length > 0) {
					if(jsons.results[0].realName !="未注册"){
						realName=jsons.results[0].realName; 
						 $("#validateCode").val("");
					}
					
					if(jsons.results[0].phone !="未注册"){
						phone=jsons.results[0].phone;
					}
					if(jsons.results[0].email !="未注册"){
						email=jsons.results[0].email;
					}
					 if(jsons.results[0].selfIntro !="未注册"){
							selfIntro=jsons.results[0].selfIntro;
					    }
					
				}
				 
				$("#realname").val(realName);
				$("#phone").val(phone);
				$("#email").val(email);
				$("#selfIntro").val(selfIntro);
				 $(".registerArea").show();
			} 


			    
			   
			} 
	 }); 
}
function MathRand() 
{ 
var Num=""; 
for(var i=0;i<6;i++) 
{ 
Num+=Math.floor(Math.random()*10); 
} 
return Num;
} 
var code="";
function sendValidateCode(phone){
	var phone = $("#phone").val();
	 var phoneFilter = /^1[0-9]{10}/;
	if(""==phone||!phoneFilter.test(phone)){
		 swal("发送失败!", "请输入正确的号码信息", "error");
		 return;
	}else{
	code=MathRand();
	$.ajax({
        cache: false,
        type: "POST",
        url:"../sendValidateCode",
        data:{
        	phone:phone,
        	code:code	
        },
        async: true,
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
        	if(data=="OK"){
        	swal("恭喜!", "已收到您的请求，请耐心等候", "success");
        	}
        }
    });}
	}
function returnRegisterBack()
{
	$(".registerArea").hide();
	}
	 function updateInfo(){
		var uid = $("#uid").val();
		var name = $("#realname").val();
		var phone = $("#phone").val();
		var email = $("#email").val();
		var validateCode = $("#validateCode").val();
		//var suppovisor = $("#suppovisor").val();
		//var role = $("#roleSelect option:selected").val();
		//var group = $("#groupSelect option:selected").val();
		/* var javatag = $("#javatag").val();
		var htmltag = $("#htmltag").val(); 
		var webservicetag = $("#webservicetag").val();
	    var etltag = $("#etltag").val(); */
		var selfIntro = $("#selfIntro").val();
		
		 var emailFilter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		 var phoneFilter = /^1[0-9]{10}/;
		 if (name.replace(/(^ *)|( *$)/g,'')==''){
			 swal("注册失败!", "请输入正确的姓名信息！", "error");
		 }else if (!phoneFilter.test(phone)){
			 swal("注册失败!", "请输入正确的电话信息！", "error");
		 }else if (!emailFilter.test(email)){
			 swal("注册失败!", "请输入正确的邮箱信息！", "error");
		 }else if(validateCode==""||validateCode!=code){
				 swal("注册失败!", "请输入验证码或验证码不正确！", "error");
		 }else{
			$.ajax({
				url:"../regist",
				data:{uid:uid,name:name,telephone:phone,email:email,selfIntro:selfIntro},
				type:"POST",
				dataType:"json",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				cache:false,
				async:false,
				success:function(result) {
					if(result){
						$('#registerform').modal('hide');
						swal("注册成功!", "恭喜!", "success"); 
						$("#realName").val(name);
						returnRegisterBack();
					} else {
						swal("注册失败!", "请输入正确的个人信息", "error");
					}
				}
			});
		}
	};

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
			+"<h3><b>期货行情</b></h3>"
			+"</div>"
			+"<table width='100%' id='stock' style='margin-bottom: -20px;'>"
			+"</table>"
			+"<div id='addStock' style='display:none;margin: 5px;' >"
			+"<div id='stockTableForm' >"
			+"<span>请输入期货代码：</span><input type='text' placeholder='期货代码' id='stockcodeKey'  required style='width:100px;'/><input id='addStockBtn' type='button' value='添加' style='margin: 5px;' />"
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
  <div class="registerArea" >
    <div class="register_title"><i class="fa fa-angle-left fa-lg return" onclick="returnRegisterBack()"></i>会员注册</div>
<div id="fillPanel">
<div class="singleInput">
<p class="icon">  <i class="fa fa-user fa-lg" style="font-size:23px;"></i></p>
<p class="inputArea"><input id="realname" type="text" placeholder="请输入你的姓名" /></p>
</div>
<div class="singleInput">
<p class="icon">  <i class="fa fa-mobile fa-lg" style="margin-left:3px;"></i></p>
<p class="inputArea">  <input id="phone" type="text" placeholder="请输入你的手机号" /></p>
</div>
<div class="singleInput" id="codePanel">
<p class="icon">  <i class="fa fa-check fa-lg" style="font-size:21px;"></i></p>
<p class="inputArea"><input id="validateCode" type="text" placeholder="请输入你的验证码"/> </p>
<p class="sendCode" onclick="sendValidateCode()">发送验证码</p>
</div>
<div class="singleInput">
<p class="icon">  <i class="fa fa-envelope-o fa-lg" style="font-size:21px;"></i></p>
<p class="inputArea"><input id="email" type="text" placeholder="请输入你的邮箱地址"/> </p>
</div>
<div class="singleInput">
<p class="icon">  <i class="fa fa-pencil fa-lg" style="font-size:21px;"></i></p>
<p class="inputArea"><input id="selfIntro" type="text" placeholder="请输入你的个人简介"/> </p>
</div>
</div>
<div class="register_btn" onclick="updateInfo()">提交</div>
  </div>
<div id="shadow" style="display:none;width:100%;height:100%;position:absolute;z-index:99999;top:0px;left:0px;opacity:0.4;background:black;"></div>
 <div class="sk-circle">
      <div class="sk-circle1 sk-child"></div>
      <div class="sk-circle2 sk-child"></div>
      <div class="sk-circle3 sk-child"></div>
      <div class="sk-circle4 sk-child"></div>
      <div class="sk-circle5 sk-child"></div>
      <div class="sk-circle6 sk-child"></div>
      <div class="sk-circle7 sk-child"></div>
      <div class="sk-circle8 sk-child"></div>
      <div class="sk-circle9 sk-child"></div>
      <div class="sk-circle10 sk-child"></div>
      <div class="sk-circle11 sk-child"></div>
      <div class="sk-circle12 sk-child"></div>
    </div>
<div id="fastPush" style="display:none;width:70%;position:absolute;left:15%;top:200px;height:100px;z-index:100000;border:1px solid gray;border-radius:5px;background-color:white;">
  <p style="width:100%;text-align:center;margin-top:20px;">确定一键发布吗？</p>
  <button style="margin-top: 20px;position: absolute;bottom: 15px;width: 30%;left: 15%;" onclick="postQuotation()" name="doublebutton-0" class="btn">确定</button>
  <button style="margin-top: 20px;position: absolute;bottom: 15px;width: 30%;left: 55%;" onclick="closeQuotation()" name="doublebutton-0" class="btn">取消</button></div>
	<input id="uid" type="hidden" value="<%=uid%>" />
	<input id="timer" type="hidden" value="" />
	<input id="realName" type="hidden" value="" />
	<input id="userPic" type="hidden" value="<%=res.get("HeadUrl") %>" />
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
							欢迎 <span class="username colorBlue" id="username"> <%=res.get("NickName")  %>
						</span>
					</a> <span><a style="float: right;"> <img id="userImage"
								src="<%=res.get("HeadUrl") %>" alt="userImage"
								class="userImage" alt="no_username" onclick="showRegister()" />
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

                	<div class="obj-e"><img style="width:100%;height:221px" src="../MetroStyleFiles/image/datalakedashboard.png" /></div>

            	</a>

            </div>

            <div class="slide" id="bgstyleb">

            	<a stat="sslink-2" href="" target="_blank">

                	<div class="obj-e"><img style="width:100%;height:221px" src="../MetroStyleFiles/image/datalakepure.png" /></div>

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
								<div id="navMember"  class="navi"><p class="naviText">微流量</p></div>
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
														<h4>关税汇率 </h4>
												</td>
												<td> <img onclick="stockModule()"
														src="../MetroStyleFiles/menu-stock.png" />
														<h4>期货行情</h4>
												</td>
												
													<td> <!-- onclick="signaturePanel()"  -->
												 <a href="Signature.jsp?UID=<%=uid%>">
												<img 
														src="../MetroStyleFiles/menu-pen.png" /></a>
														<h4>电子签名</h4>
												</td>
											</tr>
											<tr>
											<td><img <%if(isInternalSeniorMgt==true||isInternalImtMgt==true) { %> onclick="mesSend()" <%}else{ %>onclick="noAuth(1)"<%} %> src="../MetroStyleFiles/menu-technology.png" />
													<h4>消息推送</h4></td>
												<td>		<img <%if(isInternalSeniorMgt==true) { %> onclick="recognizationPanel()"<%}else{ %>onclick="noAuth(2)"<%} %>
														src="../MetroStyleFiles/menu-recognition.png" />
														<h4>奖项管理</h4>
												</td>
												<td> <a <%if(isInternalSeniorMgt==true) { %> href="DataVisualization.jsp"  <%} else{ %>onclick="noAuth(2)"<%} %>><img  class="mes-openbt" data-mesid="message-tax" 
														src="../MetroStyleFiles/menu-price.png" />
														<h4>胖和数据</h4></a>
												</td>
												<td><a href="quoteDetail.jsp"><img src="../MetroStyleFiles/Add1.png" />
													<h4>更多应用</h4></a></td>
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
											<b>关税汇率 </b>
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
											<b>期货行情</b>
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
												        	<span>请输入期货代码：</span><input type="text" placeholder="期货代码" id="stockcodeKey"  required style="width:100px;"/><input id="addStockBtn" type="button" value="添加" />
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


								<div class="tab-pane" id="WorkMates"  style="text-align:center;">
	<svg id="fillgauge5" height="220" style="margin-left:auto;margin-right:auto;width:200px;"><g transform="translate(0,6.5)"><path d="M0,103.5A103.5,103.5 0 1,1 0,-103.5A103.5,103.5 0 1,1 0,103.5M0,87.975A87.975,87.975 0 1,0 0,-87.975A87.975,87.975 0 1,0 0,87.975Z" transform="translate(103.5,103.5)" style="fill: rgb(255, 255, 255);"></path><text class="liquidFillGaugeText" text-anchor="middle" font-size="38.8125px" transform="translate(103.5,73.071)" style="fill: rgb(0, 178, 135);">86%</text><defs><clipPath id="clipWavefillgauge5" transform="translate(-41.400000000000006,37.77336)"><path d="M0,178.227L1.449,178.227L2.898,178.227L4.3469999999999995,178.227L5.796,178.227L7.245,178.227L8.693999999999999,178.227L10.142999999999999,178.227L11.592,178.227L13.041,178.227L14.49,178.227L15.939000000000002,178.227L17.387999999999998,178.227L18.837,178.227L20.285999999999998,178.227L21.735,178.227L23.184,178.227L24.633,178.227L26.082,178.227L27.531,178.227L28.98,178.227L30.429000000000002,178.227L31.878000000000004,178.227L33.327,178.227L34.775999999999996,178.227L36.225,178.227L37.674,178.227L39.123000000000005,178.227L40.571999999999996,178.227L42.021,178.227L43.47,178.227L44.919000000000004,178.227L46.368,178.227L47.817,178.227L49.266,178.227L50.715,178.227L52.164,178.227L53.61300000000001,178.227L55.062,178.227L56.511,178.227L57.96,178.227L59.409,178.227L60.858000000000004,178.227L62.306999999999995,178.227L63.75600000000001,178.227L65.205,178.227L66.654,178.227L68.10300000000001,178.227L69.55199999999999,178.227L71.001,178.227L72.45,178.227L73.899,178.227L75.348,178.227L76.797,178.227L78.24600000000001,178.227L79.69500000000001,178.227L81.14399999999999,178.227L82.593,178.227L84.042,178.227L85.491,178.227L86.94,178.227L88.389,178.227L89.83800000000001,178.227L91.28699999999999,178.227L92.736,178.227L94.185,178.227L95.634,178.227L97.083,178.227L98.532,178.227L99.98100000000001,178.227L101.43,178.227L102.87899999999999,178.227L104.328,178.227L105.777,178.227L107.22600000000001,178.227L108.675,178.227L110.124,178.227L111.57300000000001,178.227L113.022,178.227L114.471,178.227L115.92,178.227L117.369,178.227L118.818,178.227L120.26700000000001,178.227L121.71600000000001,178.227L123.165,178.227L124.61399999999999,178.227L126.06299999999999,178.227L127.51200000000001,178.227L128.961,178.227L130.41,178.227L131.859,178.227L133.308,178.227L134.757,178.227L136.20600000000002,178.227L137.655,178.227L139.10399999999998,178.227L140.553,178.227L142.002,178.227L143.451,178.227L144.9,178.227L146.349,178.227L147.798,178.227L149.247,178.227L150.696,178.227L152.145,178.227L153.594,178.227L155.04299999999998,178.227L156.49200000000002,178.227L157.941,178.227L159.39000000000001,178.227L160.839,178.227L162.28799999999998,178.227L163.73700000000002,178.227L165.186,178.227L166.635,178.227L168.084,178.227L169.533,178.227L170.982,178.227L172.431,178.227L173.88,178.227L175.329,178.227L176.778,178.227L178.227,178.227L179.67600000000002,178.227L181.125,178.227L182.57399999999998,178.227L184.023,178.227L185.472,178.227L186.92100000000002,178.227L188.37,178.227L189.819,178.227L191.268,178.227L192.717,178.227L194.166,178.227L195.615,178.227L197.064,178.227L198.513,178.227L199.96200000000002,178.227L201.411,178.227L202.86,178.227L204.309,178.227L205.75799999999998,178.227L207.20700000000002,178.227L208.656,178.227L210.105,178.227L211.554,178.227L213.003,178.227L214.45200000000003,178.227L215.901,178.227L217.35,178.227L218.799,178.227L220.248,178.227L221.697,178.227L223.14600000000002,178.227L224.595,178.227L226.044,178.227L227.493,178.227L228.942,178.227L230.39100000000002,178.227L231.84,178.227L231.84,-4.347L230.39100000000002,-4.293481216567064L228.942,-4.134242676335035L227.493,-3.8732053606468293L226.044,-3.516796874547896L224.595,-3.0737931778179184L223.14600000000002,-2.555102491715386L221.697,-1.973496702367812L220.248,-1.343296874547895L218.799,-0.6800206195298782L217.35,9.318885071640953e-15L215.901,0.6800206195298814L214.45200000000003,1.3432968745478981L213.003,1.973496702367815L211.554,2.555102491715388L210.105,3.0737931778179206L208.656,3.5167968745478975L207.20700000000002,3.8732053606468306L205.75799999999998,4.1342426763350355L204.309,4.293481216567064L202.86,4.347L201.411,4.293481216567064L199.96200000000002,4.1342426763350355L198.513,3.8732053606468297L197.064,3.516796874547896L195.615,3.073793177817919L194.166,2.5551024917153864L192.717,1.9734967023678125L191.268,1.3432968745478955L189.819,0.6800206195298787L188.37,6.65711525369418e-15L186.92100000000002,-0.6800206195298809L185.472,-1.3432968745478975L184.023,-1.9734967023678145L182.57399999999998,-2.5551024917153877L181.125,-3.07379317781792L179.67600000000002,-3.5167968745478975L178.227,-3.8732053606468306L176.778,-4.1342426763350355L175.329,-4.293481216567064L173.88,-4.347L172.431,-4.293481216567064L170.982,-4.1342426763350355L169.533,-3.8732053606468297L168.084,-3.5167968745478966L166.635,-3.0737931778179193L165.186,-2.555102491715387L163.73700000000002,-1.973496702367813L162.28799999999998,-1.343296874547896L160.839,-0.6800206195298794L159.39000000000001,8.254177144462244e-15L157.941,0.6800206195298804L156.49200000000002,1.343296874547897L155.04299999999998,1.973496702367814L153.594,2.5551024917153877L152.145,3.07379317781792L150.696,3.516796874547897L149.247,3.87320536064683L147.798,4.1342426763350355L146.349,4.293481216567065L144.9,4.347L143.451,4.293481216567065L142.002,4.1342426763350355L140.553,3.8732053606468337L139.10399999999998,3.516796874547897L137.655,3.0737931778179197L136.20600000000002,2.555102491715381L134.757,1.9734967023678136L133.308,1.3432968745478893L131.859,0.6800206195298798L130.41,0L128.961,-0.6800206195298798L127.51200000000001,-1.3432968745478893L126.06299999999999,-1.9734967023678136L124.61399999999999,-2.555102491715381L123.165,-3.0737931778179197L121.71600000000001,-3.516796874547897L120.26700000000001,-3.8732053606468337L118.818,-4.1342426763350355L117.369,-4.293481216567065L115.92,-4.347L114.471,-4.293481216567065L113.022,-4.134242676335033L111.57300000000001,-3.8732053606468373L110.124,-3.516796874547897L108.675,-3.07379317781792L107.22600000000001,-2.5551024917153815L105.777,-1.973496702367807L104.328,-1.343296874547897L102.87899999999999,-0.6800206195298804L101.43,-5.323539635893545e-16L99.98100000000001,0.6800206195298794L98.532,1.343296874547896L97.083,1.973496702367806L95.634,2.5551024917153806L94.185,3.0737931778179193L92.736,3.5167968745478966L91.28699999999999,3.8732053606468373L89.83800000000001,4.134242676335033L88.389,4.293481216567065L86.94,4.347L85.491,4.293481216567065L84.042,4.134242676335033L82.593,3.8732053606468377L81.14399999999999,3.5167968745478975L79.69500000000001,3.07379317781792L78.24600000000001,2.555102491715382L76.797,1.9734967023678074L75.348,1.3432968745478975L73.899,0.6800206195298809L72.45,1.064707927178709e-15L71.001,-0.6800206195298787L69.55199999999999,-1.3432968745478955L68.10300000000001,-1.9734967023678092L66.654,-2.555102491715383L65.205,-3.0737931778179215L63.75600000000001,-3.516796874547894L62.306999999999995,-3.873205360646835L60.858000000000004,-4.134242676335033L59.409,-4.293481216567065L57.96,-4.347L56.511,-4.293481216567065L55.062,-4.134242676335033L53.61300000000001,-3.873205360646834L52.164,-3.5167968745478975L50.715,-3.073793177817926L49.266,-2.555102491715382L47.817,-1.973496702367808L46.368,-1.3432968745478981L44.919000000000004,-0.680020619529889L43.47,-1.5970618907680634e-15L42.021,0.6800206195298858L40.571999999999996,1.343296874547895L39.123000000000005,1.9734967023678052L37.674,2.5551024917153797L36.225,3.073793177817924L34.775999999999996,3.516796874547896L33.327,3.8732053606468364L31.878000000000004,4.134242676335032L30.429000000000002,4.293481216567065L28.98,4.347L27.531,4.293481216567065L26.082,4.134242676335034L24.633,3.8732053606468346L23.184,3.516796874547898L21.735,3.0737931778179264L20.285999999999998,2.5551024917153824L18.837,1.9734967023678085L17.387999999999998,1.3432968745478986L15.939000000000002,0.6800206195298896L14.49,2.129415854357418e-15L13.041,-0.6800206195298854L11.592,-1.3432968745478946L10.142999999999999,-1.9734967023678045L8.693999999999999,-2.555102491715379L7.245,-3.0737931778179237L5.796,-3.5167968745478952L4.3469999999999995,-3.873205360646833L2.898,-4.134242676335032L1.449,-4.293481216567065L0,-4.347Z" t="0.679" transform="translate(39.354839378356935,0)"></path></clipPath></defs><g clip-path="url(#clipWavefillgauge5)"><circle cx="103.5" cy="103.5" r="86.94" style="fill: rgb(0, 92, 161);"></circle><text class="liquidFillGaugeText" text-anchor="middle" font-size="38.8125px" transform="translate(100.5,112.071)" style="fill: rgb(252, 252, 252);" id="skimTotalNum"></text><text class="liquidFillGaugeText" text-anchor="middle" font-size="13px" transform="translate(100.5,135.071)" style="fill: rgb(252, 252, 252);" id="nowSkimTotalNum">今日访问量：</text></g></g></svg>
										<table width="100%" id="skimHis"></table>
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