<%@ page language="java" pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.baseobject.OnlineQuotation"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%@ page import="com.nkang.kxmoment.baseobject.ClientMeta"%>
<%	
String item = request.getParameter("item");
%>
<!Doctype html>
<html>
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<script type="text/javascript" src="../nkang/js_athena/echarts.min.js"></script>
<script type="text/javascript" src="../nkang/js_athena/zepto.js"></script>
<style>
body{
	padding:0px;
	margin:0px;
}
.chart-box{overflow-x:auto;}
#echarts{height: 350px;width:750px;}
.title{text-align: center;font-size: 18px;color: #333;margin-top: 20px;}
.subtext{text-align: center;color: #666;font-size: 14px;margin-top: 5px;}
.tiptext{text-align: center;color: #666;font-size: 14px;margin-top: 5px;}
</style>
</head>
<body>
<div style="padding:10px;padding-top:5px;border-bottom:2px solid #0067B6;position:relative;text-align:right;"> 
					<img onclick="ToBackPage()" src="../MetroStyleFiles/EXIT1.png" style="width: 30px; height: 30px;position:absolute;top:30px;left:20px;">

					<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM" alt="Logo" class="HpLogo" style="display:inline !important;top: 8px;left: 1%;position: relative;height:35px !important;width:auto !important;float:none;padding:0px;vertical-align:bottom;padding-bottom:10px;">
					<span class="clientSubName" style="font-size:12px;padding-left:7px;color:#333;">市场如水 企业如舟</span>
					<h2 style="color:#333;font-size:18px;padding:0px;padding-left:5px;font-weight:bold;margin-top:5px;font-family:HP Simplified, Arial, Sans-Serif !important;" class="clientName">永佳和塑胶有限公司</h2>
				</div>




<div class="title"><%=item %>的价格趋势分析</div>
	<div class="subtext">2016-12-30 - 2017-02-23</div>
	<div class="chart-box">
		<div id="echarts" _echarts_instance_="ec_1487834284265" style="width: 371px; -webkit-tap-highlight-color: transparent; -webkit-user-select: none; position: relative; background: transparent;"><div style="position: relative; overflow: hidden; width: 371px; height: 350px; cursor: default;"><canvas width="371" height="350" data-zr-dom-id="zr_0" style="position: absolute; left: 0px; top: 0px; width: 371px; height: 350px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas></div><div style="position: absolute; display: none; border: 0px solid rgb(51, 51, 51); white-space: nowrap; z-index: 9999999; transition: left 0.4s cubic-bezier(0.23, 1, 0.32, 1), top 0.4s cubic-bezier(0.23, 1, 0.32, 1); border-radius: 4px; color: rgb(255, 255, 255); font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 14px; font-family: &#39;Microsoft YaHei&#39;; line-height: 21px; padding: 5px; left: 211.95px; top: 122px; background-color: rgba(50, 50, 50, 0.701961);">02-13<br><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#7DB5E8"></span>价格 : 9,840</div></div>
	</div>



<script type="text/javascript">
function ToBackPage(){
	history.go(-1):
}
$(function(){
	var bodyWidth = document.body.offsetWidth;
	$("#echarts").css("width",bodyWidth);

	
		var option = {
				color:['#7DB5E8'],
				/*
			    title: {
			        text:cateName+' '+material+' '+manufacturer,
			        left: 'center',
			        subtext:data.startDate!=""?(data.startDate+' - '+check_time):check_time,
			        textStyle:{
			        	color:'#373737',
			        	fontStyle:'normal',
			        	fontWeight:'normal'
			        },
			        subtextStyle:{
			        	color:'#666'
			        }
			    },*/
			    tooltip: {
			        trigger: 'axis'
			    },
			    xAxis: {
			        type: 'category',
			        boundaryGap: false,
			        data: ["12-30", "01-09", "02-10", "02-13", "02-17", "02-22", "02-23"],
			        axisLine:{
			        	lineStyle:{
			        		color:'#CBD7E5'
			        	}
			        },
			        axisLabel:{
			        	textStyle:{
			        		color:'#333'
			        	}
			        }
			    },
			    grid: { // 控制图的大小，调整下面这些值就可以，
		             x: 50
		        },

			    yAxis: {
			        type: 'value',
			        scale:5,
			        nameTextStyle:{
			        	color:'#333'
			        },
			        axisLine :{
			        	show:false
			        },
			        axisTick:{
			        	show:false
			        	
			        }
			    },
			    series: [
			        {
			            name:'价格',
			            type:'line',
			            stack: '总量',
			            data:[10850, 10130, 9830, 9840, 9580, 9170, 8970]
			        }
			    ]
		};
//		$(".title").html("GE150的价格趋势分析");
		$(".subtext").html("2016-12-30 - 2017-02-23");
		var obj=document.getElementById('echarts');
		var myChart = echarts.init(obj);
			myChart.setOption(option);	
})
</script>


</body>
</html>