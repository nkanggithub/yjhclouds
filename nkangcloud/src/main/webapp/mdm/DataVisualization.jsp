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

<div id="chart-container">FusionCharts will render here</div>

</body></html>