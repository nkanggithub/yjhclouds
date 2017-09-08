<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.baseobject.Visited"%>
<%@ page import="com.nkang.kxmoment.baseobject.ArticleMessage"%>
<%@ page import="com.nkang.kxmoment.baseobject.PlasticItem"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.service.PlasticItemService"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%	
List<String> dates=MongoDBBasic.getLastestDate(-14);
SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd"); 
Date date=new Date();
String currentDate = format.format(date);
String uid = request.getParameter("UID");
System.out.println("uid======"+uid);
HashMap<String, String> res=MongoDBBasic.getWeChatUserFromOpenID(uid);
String nickName=res.get("NickName");
String imgUrl=res.get("HeadUrl");
boolean isInternalSeniorMgt=MongoDBBasic.checkUserAuth(uid, "isInternalSeniorMgt");
MongoDBBasic.addSkimNum();
%> 
<!DOCTYPE html>
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>永佳和塑胶有限公司-数据可视化</title>
    <link href="../Jsp/JS/pizzaChart/css/app.css" media="screen, projector, print" rel="stylesheet" type="text/css" />
<link href="../Jsp/JS/pizzaChart/css/pizza.css" media="screen, projector, print" rel="stylesheet" type="text/css" />
<script src="../Jsp/JS/pizzaChart/js/custom.modernizr.js"></script>
<link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
<script	src="../MetroStyleFiles/sweetalert.min.js"></script>
<style>
p{
margin-bottom:0px;
}
#svg2 svg{
width:70%;
height:240px;
margin-left:15%;}
ul li{
border-top:1px solid #F0F0F0;
border-bottom:1px solid #F0F0F0;
height:40px;
line-height:40px;
}
.text-left{
display: block;
width:60%;
text-align:left!important;
float:left;
padding-left:10px;
}
.text-right{
display: block;
width:40%;
text-align:right!important;
float:left;
padding-right:10px;
}
.arrow{
position:absolute;
top:-13px;
width:6%;
left:47%;
z-index:1000;
}
#detailPanel{
height:300px;
width:100%;
}
.title{
    display: block;
    margin-left: 10px;
    font-size: 13px;
    color: #d3d3d3;
    height: 40px;
	margin-top:-15px;
	line-height:45px;}
.singleArticle{
width:100%;
height:50px;
font-size:14px;
border-bottom:1px solid #F0F0F0;
position:relative;
}
.singleArticle span
{ 
    position: absolute;
    z-index: 1000;
    top: 17px;
    right: 15px;
    font-size: 14px;
    color: #d3d3d3;

}
.singleArticle p
{
line-height:50px;
float:left;
height:100%;
}

.rank{
width:20%;
text-align:center;
font-size:20px;
color:orange;
}
.at{
width:80%;
color:#9A9A9A;
font-weight:bold;
}
/*
#HQdetail .rank,#HQdetail .at
{
  color: #FFC444;
}
#JSdetail .rank,#JSdetail .at
{
  color: #FF9A38;
}
#BJdetail .rank,#BJdetail .at
{
  color: #FFD427;
}
#GTdetail .rank,#GTdetail .at
{
  color: #EA4E2F;
}*/
#HQdetail .rank
{
  color: #FFC444;
}
#JSdetail .rank
{
  color: #FFD427;
}
#BJdetail .rank
{
  color: #FF9A38;
}
#GTdetail .rank
{
  color: #EA4E2F;
}
#menu
{
    height: 30px;
    width: 70%;
    margin-left: 15%;
    border: 1px solid black;
    margin-top: 20px;
    border-radius: 5px;
	font-size:14px;
	overflow:hidden;
}
#menu p
{
width:33.33%;
height:100%;
float:left;
text-align:center;
line-height:30px;
border-left:1px solid black;
}
.selected
{
background-color:black;
color:white;
}
#quotationList
{

    margin-top: 10px;
    border-top: 1px solid #D3D3D3;
    width: 100%;
    overflow: scroll;

}
.singleQI {
    height: 45px;
    width: 100%;
    border-bottom: 1px solid #D3D3D3;
    position:relative;
    cursor:pointer;
}

.singleQI span
{
    height: 100%;
    line-height: 45px;
    padding-left: 20px;
    font-size: 15px;
    font-weight:bold;
}
.singleQI img
{
 position: absolute;
    z-index: 1000;
    top: 15px;
    right: 15px;
    width:10px;
}
#chart-container
{
display:none;
}
.singleV{
    height: 45px;
    border-bottom: 1px solid #F0F0F0;
    width: 100%;
    position:relative;
}
.singleV img{
    border-radius: 25px;
    height: 35px;
    width: 35px;
    position: absolute;
    top: 5px;
    left: 20px;
}
.VNickName{
width: 15%;
    margin-left: 18%;
    height: 100%;
    font-size: 14px;
    line-height: 45px;
    float: left;
    color: #9A9A9A;
    margin-right: 10px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.VPhone{
     width: 24%; 
    /* margin-left: 18%; */
    height: 100%;
    font-size: 14px;
    line-height: 45px;
    float: left;
    color: #9A9A9A;
    margin-right: 10px;
}
.VCompany{
	width: 35%;
    /* margin-left: 18%; */
    height: 100%;
    font-size: 14px;
    line-height: 45px;
    float: left;
    color: #9A9A9A;
    text-align: left;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.visitedNum{
    width: 47%;
    margin-right: 5%;
    float: left;
    line-height: 45px;
    text-align: right;
        font-size: 14px;
    color: #d3d3d3;
}
.visitedMenu
{
height: 30px;
border-bottom:1px solid #f0f0f0;
width:100%;
text-align: center;
margin-bottom: 5px;
}
.visitedMenu p
{
 height: 30px;
 width: 15%;
 border:1px solid #f0f0f0;
 border-bottom:none;
 border-radius: 6px 6px 0px 0px;
 line-height: 30px;
 font-size: 14px;
 float: left;
}
.active
{
background-color: white;
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
    	<script type="text/javascript" src="../Jsp/JS/fusioncharts.js"></script>
    	<script type="text/javascript" src="../Jsp/JS/fusioncharts.powercharts.js"></script>
	<script type="text/javascript" src="../Jsp/JS/fusioncharts.theme.fint.js"></script>

</head>
<body>
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
<p style="position: absolute;right: 10px;top: 13px;font-size: 15px;">欢迎，<%=nickName%></p>
<img src="<%=imgUrl%>" style="position: absolute;border-radius: 25px;height: 35px;width: 35px;right: 10px;top: 43px;">
<%if(isInternalSeniorMgt==true) { %> 
<select id="xsdbList" style="position: absolute;width: 25%;left: 20px;top: 110px;z-index: 1000;">												
  <option value="">-请选择-</option>
  <option value="oij7nt2wV7C_dYVLxJvFJgOG9GpQ">&nbsp;王素萍</option>
  <option value="oij7nt60inaYfekRpCpSIVnhjwVU">&nbsp;邓立铭</option>
  <option value="oij7nt0gk8vOYth0_0gzoLwg2YyU">&nbsp;胡贵花</option>
  <option value="oij7nt82eNsDS6cYo_362sJIBtFs">&nbsp;罗成洪</option>
  <option value="oij7nt-E02pgCKU2EfHGIAxOF5cA">&nbsp;罗浩</option>
  <option value="oij7nt2EjSi-GPboZaaODgBfgNT8">&nbsp;陈博</option>
  <option value="oij7ntyMv00uo_vwhNN5UM2b2uHY">&nbsp;段阳</option>
  <option value="oij7nt_g_9Nk0AEfRm_pRiKbP1c4">&nbsp;郑仁利</option>
  <option value="oij7nt9jFLpGhyvkk5BSBM9QThE4">&nbsp;罗斯威</option>
  <option value="oij7ntyGSa-1ZH8qvv5ykfA5BwKA">&nbsp;温小兵</option>
  <option value="oij7nt8-8xoKGXWQXoaOnIhT7fis">&nbsp;马家勇</option>
  <option value="oij7ntxdF2qaQ8pirWJjVL9fI854">&nbsp;郝海涛</option>
</select>
<%} %>
<div style="padding:10px;padding-top:5px;border-bottom:2px solid #0067B6;position:relative"> 
	<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM" alt="Logo" class="HpLogo" style="display:inline !important;height:35px !important;width:auto !important;float:none;padding:0px;vertical-align:bottom;padding-bottom:10px;">
	<span class="clientSubName" style="font-size:12px;padding-left:7px;color:#333;">市场如水 企业如舟</span>
	<h2 style="color:#333;font-size:18px;padding:0px;padding-left:5px;font-weight:bold;margin-top:5px;font-family:HP Simplified, Arial, Sans-Serif !important;" class="clientName">永佳和塑胶有限公司</h2>
<!-- 	<p style="position: absolute;top: 1px;right: 10px;font-size: 15px;">欢迎您 </p><img style="border-radius:25px;height:35px;width:35px;position:absolute;top:36px;right:10px;" src="" alt=""/>	 -->			
</div>
<div id="quoteVisited" style="margin-top: 30px;">
<div id="chart-container2"></div>
<div class="visitedMenu">
    <p id="read" class="active">已读</p><p style="border:none;width:27%" id="dateDetail" style="border-left: none;width:20%;"></p>
</div>
<div id="visitedDetail"></div>

</div>



<script type="text/javascript" src="../nkang/jquery-1.8.0.js"></script>
<script src="../Jsp/JS/pizzaChart/js/snap.svg.js"></script>
<script>
$(document).ajaxStart(function () {
	$(".sk-circle").show();
	$("#shadow").show();
    }).ajaxStop(function () {
    	$(".sk-circle").hide();
    	$("#shadow").hide();
    });
    $(window).load(function() {
$("svg path").live("click",function(){
var i=$(this).index();
	 console.log($(this).index()/2);
	  $("ul li:nth-child("+i/2+")").show();
	  $("ul li:nth-child("+i/2+")").siblings().hide();
	  var j=i/2+1;
	  $("div#detailPanel .commonPanel:nth-child("+j+")").show();
	   $("div#detailPanel .commonPanel:nth-child("+j+")").siblings(".commonPanel").hide();

	  
    });
	$(function(){
		var commonUid='<%=uid%>';
		var commonName='<%=nickName%>';
		  FusionCharts.ready(function () {
		        var estProcChart = new FusionCharts({
		            type: 'errorline',
		            renderAt: 'chart-container2',
		            width: '380',
		            height: '350',
		            dataFormat: 'json',
		            dataSource: {
		                "chart": {
		                    "theme": "fint",
		                    "xaxisname": "",
		                    "yaxisname": "",
		                    "numberSuffix":"",
		                    "caption": "胖和阅读统计",
		                    "subcaption": "(最近十五天访问量)",
		                    "showvalues": "0",
		                    "plottooltext": "$seriesname, $value",
		                    //Error bar configuration
		                    "halferrorbar": "0",
		                    "errorBarColor": "#990000",
		                    "errorBarAlpha": "50",
		                    "errorBarThickness": "4",
		                    "errorBarWidth": "8"
		                },
		                "categories": [
		                    {
		                        "category": [
		                             <%  for(int i=0;i<dates.size();i++){ 
		                            	 if(i==dates.size()-1){%>
		                            	 { "label":" <%=dates.get(i)%>" }
		                            	 <%}else{%>
		                            { "label": "<%=dates.get(i)%>" },
		                            <%}%>
		                            <%}%>
		                            
		                        ]
		                    }
		                ],
		                "dataset": [
				                    {
				                        "seriesname": "客户报价访问统计",
				                        "data": [
				                                 <%  for(int j=0;j<dates.size();j++){ 
					                            	 if(j==dates.size()-1){%>
					                            	 {
					                                     "value": "<%=MongoDBBasic.getVisitedCustomerByXSDB(uid, dates.get(j), nickName).size()%>",
					                                     "errorvalue": ""
					                                 }
					                            	 <%}else{%>
					                            	 {
					                                     "value": "<%=MongoDBBasic.getVisitedCustomerByXSDB(uid, dates.get(j), nickName).size()%>",
					                                     "errorvalue": ""
					                                 },
					                            <%}%>
					                            <%}%>
				                                
				                        ]
				                    }
				                ]
				            }
		        }).render();
		        
		    });
		  var dates=new Array();
		  <% for(int i=0;i<dates.size();i++){%>
		  dates[<%=i%>]='<%=dates.get(i).toString()%>';
		  <%}%>
	$("#chart-container2").show();
	$("#chart-container2 circle").css("cursor","pointer");
	$("#xsdbList").on("change",function(){
		var openId=$(this).val();
		commonUid=openId;
		var nickName=$(this).find("option:selected").text().trim();
		commonName=nickName;
		console.log("openID--"+openId+"\n nickName--"+nickName);
		$.ajax({
			type : "post",
			async: false,
			url : "../getSharedCustomerChart",
			data:{
				uid:openId,
				nickName:nickName
			},
			cache : false,
			success : function(data) {
				FusionCharts.ready(function () {
			        var estProcChart = new FusionCharts({
			            type: 'errorline',
			            renderAt: 'chart-container2',
			            width: '380',
			            height: '350',
			            dataFormat: 'json',
			            dataSource: {
			                "chart": {
			                    "theme": "fint",
			                    "xaxisname": "",
			                    "yaxisname": "",
			                    "numberSuffix":"",
			                    "caption": "胖和阅读统计",
			                    "subcaption": "(最近十五天访问量)",
			                    "showvalues": "0",
			                    "plottooltext": "$seriesname, $value",
			                    //Error bar configuration
			                    "halferrorbar": "0",
			                    "errorBarColor": "#990000",
			                    "errorBarAlpha": "50",
			                    "errorBarThickness": "4",
			                    "errorBarWidth": "8"
			                },
			                "categories": [
			                    {
			                        "category": [
			                             <%  for(int i=0;i<dates.size();i++){ 
			                            	 if(i==dates.size()-1){%>
			                            	 { "label":" <%=dates.get(i)%>" }
			                            	 <%}else{%>
			                            { "label": "<%=dates.get(i)%>" },
			                            <%}%>
			                            <%}%>
			                            
			                        ]
			                    }
			                ],
			                "dataset":[
					                    {
					                        "seriesname": "客户访问量统计",
					                        "data": data
					                    }
					                ]
			                	}
			        }).render();
			});
		}
		});
			
		
	});
	 $(document).on("click","#chart-container2 circle",function(){
		 console.log("....."+$(this).index());
		 var index=$(this).index();
		 $("#dateDetail").html(dates[index].toString());
		 var detail=$("#fusioncharts-tooltip-element").children("span").text();
		 var details=detail.split(",");
	 	 $.ajax({
				type : "post",
				async: false,
				url : "../getVisitedCustomerByXSDB",
				data:{
					uid:commonUid,
					dateIndex:index,
					nickName:commonName
				},
				cache : false,
				success : function(data) {
					if(data){
					var html="";
					//var imgUrl="";
				for(var i=0;i<data.length;i++)
				{
/*					console.log("date========"+data[i].date);
					console.log("nickName========"+data[i].nickName);
					console.log("imgUrl========"+data[i].imgUrl);
 					imgUrl=data[i].imgUrl;
					if(imgUrl!="null")
					{	 */
						html+="<div class='singleV'><img src='"+data[i].imgUrl+"' /><p class='VNickName'>"+data[i].nickName+"</p><p class='VPhone'>"+data[i].phone+"</p><p class='VCompany'>"+data[i].companyName+"</p></div>";
						//html+="<div class='singleV'><img src='"+data[i].imgUrl+"' /><p class='VNickName'>"+data[i].nickName+"</p></div>";
						
					/* }; */
				}/* 
				$("#dateDetail").text(data[0].date); */
				$("#visitedDetail").html(html);
					}
		 }
				
		 }); 
	 });
	 
		});


	  
	  });
	
		
  </script>
</body></html>