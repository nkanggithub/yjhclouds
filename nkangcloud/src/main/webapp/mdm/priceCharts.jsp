<%@ page language="java" pageEncoding="UTF-8"%>
 <%@ page import="java.util.*,org.json.JSONObject"%>
<%@ page import="com.nkang.kxmoment.baseobject.OnlineQuotation"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="com.nkang.kxmoment.baseobject.WeChatUser"%>
<%@ page import="com.nkang.kxmoment.baseobject.ClientMeta"%>
<%	
String item = request.getParameter("itemNo");
pageContext.setAttribute("path", request.getContextPath());
%>
<!Doctype html>
<html>
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<script type="text/javascript" src="../Jsp/JS/fusioncharts.js"></script>
    	<script type="text/javascript" src="../Jsp/JS/fusioncharts.powercharts.js"></script>
	<script type="text/javascript" src="../Jsp/JS/fusioncharts.theme.fint.js"></script>

    <script>
    var params = location.search;
    FusionCharts.ready(function () {
        var estProcChart = new FusionCharts({
            type: 'errorline',
            renderAt: 'chart-container',
            width: '380',
            height: '350',
            dataFormat: 'jsonurl',
            dataSource: '${path}/PlasticItem/priceList'+params
        }).render();
        
    });
    </script>
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
					<img onclick="javascript:history.go(-1);" src="../MetroStyleFiles/EXIT1.png" style="width: 30px; height: 30px;position:absolute;top:20px;left:20px;">

					<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM" alt="Logo" class="HpLogo" style="display:inline !important;top:5px;left: 1%;position: relative;height:50px !important;width:auto !important;float:none;padding:0px;vertical-align:bottom;padding-bottom:10px;margin-right:10px;">
				</div>




<div class="title"><%=item %>的价格趋势分析</div>
	<div class="chart-box">
		<div id="chart-container">FusionCharts will render here</div>
	</div>





</body>
</html>