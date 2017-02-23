<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%@ page import="com.nkang.kxmoment.baseobject.ClientMeta"%>
<%	
/* String AccessKey = RestUtils.callGetValidAccessKey();
String uid = request.getParameter("UID");
WeChatUser wcu;
wcu = RestUtils.getWeChatUserInfo(AccessKey, uid); */
%> 
<!DOCTYPE html>
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>永佳和塑料有限公司-数据可视化</title>
    <link href="../Jsp/JS/pizzaChart/css/app.css" media="screen, projector, print" rel="stylesheet" type="text/css" />
<link href="../Jsp/JS/pizzaChart/css/pizza.css" media="screen, projector, print" rel="stylesheet" type="text/css" />
<script src="../Jsp/JS/pizzaChart/js/custom.modernizr.js"></script>
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
.singleArticle img
{ 
    position: absolute;
    z-index: 1000;
    top: 17px;
    right: 15px;
    width: 8px;

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
  color: #FF9A38;
}
#BJdetail .rank
{
  color: #FFD427;
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
}
#menu p
{
width:50%;
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
</style>
    	<script type="text/javascript" src="../Jsp/JS/fusioncharts.js"></script>
    	<script type="text/javascript" src="../Jsp/JS/fusioncharts.powercharts.js"></script>
	<script type="text/javascript" src="../Jsp/JS/fusioncharts.theme.fint.js"></script>

    <script>
    FusionCharts.ready(function () {
        var estProcChart = new FusionCharts({
            type: 'errorline',
            renderAt: 'chart-container',
            width: '380',
            height: '350',
            dataFormat: 'json',
            dataSource: {
                "chart": {
                    "theme": "fint",
                    "xaxisname": "ABS-02152A",
                    "yaxisname": "",
                    "numberSuffix":"",
                    "caption": "ABS价格变化",
                    "subcaption": "(02152A)",
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
                            { "label": "Jan" },
                            { "label": "Feb" }, 
                            { "label": "Mar" },
                            { "label": "Apl" },
                            { "label": "May" },
                            { "label": "Jun" }, 
                            { "label": "Jul" },
                            { "label": "Aug" },
                            { "label": "Sep" },
                            { "label": "Oct" }, 
                            { "label": "Nov" },
                            { "label": "Dec" }
                        ]
                    }
                ],
                "dataset": [
                    {
                        "seriesname": "定价",
                        "data": [
                            {
                                "value": "16700",
                                "errorvalue": "50"
                            }, 
                            {
                                "value": "16750",
                                "errorvalue": ""
                            }, 
                            {
                                "value": "16700",
                                "errorvalue": ""
                            }, 
                            {
                                "value": "16900",
                                "errorvalue": ""
                            },
                            {
                                "value": "16700",
                                "errorvalue": ""
                            }, 
                            {
                                "value": "16750",
                                "errorvalue": ""
                            }, 
                            {
                                "value": "16700",
                                "errorvalue": ""
                            }, 
                            {
                                "value": "16900",
                                "errorvalue": ""
                            }
                            ,
                            {
                                "value": "16700",
                                "errorvalue": ""
                            }, 
                            {
                                "value": "16750",
                                "errorvalue": ""
                            }, 
                            {
                                "value": "16700",
                                "errorvalue": ""
                            }, 
                            {
                                "value": "16900",
                                "errorvalue": ""
                            }
                        ]
                    }, {
                        "seriesname": "发布价",
                        "data": [
                                 {
                                     "value": "16300",
                                     "errorvalue": ""
                                 }, 
                                 {
                                     "value": "16950",
                                     "errorvalue": ""
                                 }, 
                                 {
                                     "value": "17000",
                                     "errorvalue": ""
                                 }, 
                                 {
                                     "value": "17300",
                                     "errorvalue": ""
                                 },
                                 {
                                     "value": "16300",
                                     "errorvalue": ""
                                 }, 
                                 {
                                     "value": "16950",
                                     "errorvalue": ""
                                 }, 
                                 {
                                     "value": "17000",
                                     "errorvalue": ""
                                 }, 
                                 {
                                     "value": "17300",
                                     "errorvalue": ""
                                 },
                                 {
                                     "value": "16300",
                                     "errorvalue": ""
                                 }, 
                                 {
                                     "value": "16950",
                                     "errorvalue": ""
                                 }, 
                                 {
                                     "value": "17000",
                                     "errorvalue": ""
                                 }, 
                                 {
                                     "value": "17300",
                                     "errorvalue": ""
                                 }
                        ]
                    }
                ]
            }
        }).render();
        
    });
    </script>
</head>
<body>
<div style="padding:10px;padding-top:5px;border-bottom:2px solid #0067B6;position:relative"> 
	<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM" alt="Logo" class="HpLogo" style="display:inline !important;height:35px !important;width:auto !important;float:none;padding:0px;vertical-align:bottom;padding-bottom:10px;">
	<span class="clientSubName" style="font-size:12px;padding-left:7px;color:#333;">市场如水 企业如舟</span>
	<h2 style="color:#333;font-size:18px;padding:0px;padding-left:5px;font-weight:bold;margin-top:5px;font-family:HP Simplified, Arial, Sans-Serif !important;" class="clientName">永佳和塑胶有限公司</h2>
	<p style="position: absolute;top: 1px;right: 10px;font-size: 15px;">欢迎您 </p><img style="border-radius:25px;height:35px;width:35px;position:absolute;top:36px;right:10px;" src="" alt=""/>				
</div>

<div class="row">
  <div id="menu" class="large-12 small-12 columns">
	<p id="ac" class="selected">图文统计</p>
	    <p id="qc">价格趋势</p>
  </div>
  <div id="quotation" style="display:none;">
  <div class="large-12 small-12 columns">
<div id="chart-container"></div>
  </div></div>
  <div id="article">
  <div class="large-12 small-12 columns">
    <div id="svg2"></div>
  </div>

  <div class="large-12 small-12 columns">
  <img class="arrow" src="../Jsp/JS/pizzaChart/img/arrow.png" alt=""/>
    <ul data-pie-id="svg2" data-options='{"donut":"true"}'>
      <li data-value="70"><span class="text-left">Technology (39%)</span><span class="text-right">60</span></li>
      <li data-value="20"><span class="text-left">Market (11%)</span><span class="text-right">20</span></li>
      <li data-value="12"><span class="text-left">Communication (7%)</span><span class="text-right">12</span></li>
      <li data-value="32"><span class="text-left">Quatation(18%)</span><span class="text-right">32</span></li>
    </ul>
  </div>

  <div id="detailPanel" class="large-12 small-12 columns">
    <span class="title">数量排行榜</span>
  <div id="JSdetail" class="commonPanel">
  <div class="singleArticle"><p class="rank">1</p>
  <p class="at">技术文章1</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
    <div class="singleArticle"><p class="rank">2</p>
  <p class="at">技术文章2</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
    <div class="singleArticle"><p class="rank">3</p>
  <p class="at">技术文章3</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
  </div>
    <div id="HQdetail"  class="commonPanel" style="display:none">
  <div class="singleArticle"><p class="rank">1</p>
  <p class="at">行情文章1</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
    <div class="singleArticle"><p class="rank">2</p>
  <p class="at">行情文章2</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
    <div class="singleArticle"><p class="rank">3</p>
  <p class="at">行情文章3</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
  </div>
    <div id="GTdetail"  class="commonPanel" style="display:none">
  <div class="singleArticle"><p class="rank">1</p>
  <p class="at">沟通文章1</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
    <div class="singleArticle"><p class="rank">2</p>
  <p class="at">沟通文章2</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
    <div class="singleArticle"><p class="rank">3</p>
  <p class="at">沟通文章3</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
  </div>
    <div id="BJdetail"  class="commonPanel" style="display:none">
  <div class="singleArticle"><p class="rank">1</p>
  <p class="at">报价文章1</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
    <div class="singleArticle"><p class="rank">2</p>
  <p class="at">报价文章2</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
    <div class="singleArticle"><p class="rank">3</p>
  <p class="at">报价文章3</p><img src="../Jsp/JS/pizzaChart/img/rightArrow.png" alt=""/></div>
  </div>
  </div>
</div>
</div>



<script type="text/javascript" src="../nkang/jquery-1.8.0.js"></script>
<script src="../Jsp/JS/pizzaChart/js/snap.svg.js"></script>
<script src="../Jsp/JS/pizzaChart/js/pizza.js"></script>
<script>
    $(window).load(function() {
      Pizza.init();
$("svg path").live("click",function(){
var i=$(this).index();
	 console.log($(this).index()/2);
	  $("ul li:nth-child("+i/2+")").show();
	  $("ul li:nth-child("+i/2+")").siblings().hide();
	  var j=i/2+1;
	  $("div#detailPanel .commonPanel:nth-child("+j+")").show();
	   $("div#detailPanel .commonPanel:nth-child("+j+")").siblings(".commonPanel").hide();
	  
    });
	$("#qc").click(function(){
	$("#article").hide();
	$("#quotation").show();
	$(this).addClass("selected");
	$(this).siblings().removeClass("selected");

	})
	$("#ac").click(function(){
	$("#article").show();
	$("#quotation").hide();
	$(this).addClass("selected");
	$(this).siblings().removeClass("selected");

	})
	  
	  });

  </script>
</body></html>