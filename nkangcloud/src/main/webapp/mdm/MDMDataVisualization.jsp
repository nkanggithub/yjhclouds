<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.*"%>
<%
String uid = request.getParameter("UID");
MongoDBBasic.updateUser(uid);
SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd"); 
Date date=new Date();
String currentDate = format.format(date);
HashMap<String, String> res=MongoDBBasic.getWeChatUserFromOpenID(uid);
MongoDBBasic.updateVisited(uid,currentDate,"MDMDataVisualization",res.get("HeadUrl"),res.get("NickName"));
MongoDBBasic.addSkimNum();
Map map = (HashMap<String,List>)request.getAttribute("map");
List<Integer> apj=(List<Integer>)map.get("APJ");
List<Integer> usa=(List<Integer>)map.get("USA");
List<Integer> mexico=(List<Integer>)map.get("MEXICO");
List<Integer> emea=(List<Integer>)map.get("EMEA");
%>
<!DOCTYPE html>
<html><head lang="en"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>数据可视化</title>
    <link href="../Jsp/JS/pizzaChart/css/app.css" media="screen, projector, print" rel="stylesheet" type="text/css" />
<script src="../Jsp/JS/pizzaChart/js/custom.modernizr.js"></script>

</style>
    	<script type="text/javascript" src="../Jsp/JS/fusioncharts.js"></script>
    	<script type="text/javascript" src="../Jsp/JS/fusioncharts.powercharts.js"></script>
	<script type="text/javascript" src="../Jsp/JS/fusioncharts.theme.fint.js"></script>

     <script>
    FusionCharts.ready(function () {
        var estProcChart = new FusionCharts({
            type: 'errorline',
            renderAt: 'chart-container',
            width: '880',
            height: '550',
            dataFormat: 'json',
            dataSource: {
                "chart": {
                    "theme": "fint",
                    "xaxisname": "",
                    "yaxisname": "",
                    "numberSuffix":"",
                    "caption": "Run & Maintain Break Fix By Region",
                    "subcaption": "",
                    "showvalues": "0",
                    "plottooltext": "$seriesname, $value",
                    //Error bar configuration
                    "halferrorbar": "0",
                    "errorBarColor": "#990000",
                    "errorBarAlpha": "0",
                    "errorBarThickness": "4",
                    "errorBarWidth": "8"
                },
                "categories": [
                    {
                        "category": [
                            { "label": "Done" },
                            { "label": "Work In Progress" }, 
                            { "label": "In Planning" }
                        ]
                    }
                ],
                "dataset": [
                    {
                        "seriesname": "APJ",
                        "data": [
<%  for(int i=0;i<apj.size();i++){ 
	 if(i==apj.size()-1){%>
	 {
        "value": "<%=apj.get(i)%>",
        "errorvalue": ""
    }
	 <%}else{%>
	 {
        "value": "<%=apj.get(i)%>",
        "errorvalue": ""
    },
<%}%>
<%}%>
                        ]
                    }, {
                        "seriesname": "USA",
                        "data": [
<%  for(int i=0;i<usa.size();i++){ 
	 if(i==usa.size()-1){%>
	 {
       "value": "<%=usa.get(i)%>",
       "errorvalue": ""
   }
	 <%}else{%>
	 {
       "value": "<%=usa.get(i)%>",
       "errorvalue": ""
   },
<%}%>
<%}%>
                        ]
                    }, {
                        "seriesname": "Mexico",
                        "data": [
<%  for(int i=0;i<mexico.size();i++){ 
	 if(i==mexico.size()-1){%>
	 {
       "value": "<%=mexico.get(i)%>",
       "errorvalue": ""
   }
	 <%}else{%>
	 {
       "value": "<%=mexico.get(i)%>",
       "errorvalue": ""
   },
<%}%>
<%}%>
                        ]
                    }, {
                        "seriesname": "EMEA",
                        "data": [
<%  for(int i=0;i<emea.size();i++){ 
	 if(i==mexico.size()-1){%>
	 {
      "value": "<%=emea.get(i)%>",
      "errorvalue": ""
  }
	 <%}else{%>
	 {
      "value": "<%=emea.get(i)%>",
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
    </script> 
</head>
<body>
<div id="chart-container" style="text-align:center;margin-top: 20px;"></div>
</body></html>