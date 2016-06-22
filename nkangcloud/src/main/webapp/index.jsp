
<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ page import="java.util.*" %>
<%@ page import="com.nkang.kxmoment.baseobject.MdmDataQualityView" %> 
<%@ page import="com.nkang.kxmoment.util.DBUtils"%>
<%					
MdmDataQualityView mqv = new MdmDataQualityView();
mqv= DBUtils.getDataQualityReport();
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head lang="en">
	<meta name="baidu-site-verification" content="VEOBGv7rlp" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="DS MY UI Team">
     <meta content="" name="description" />
      <meta content="" name="hpe" />
    <title>MDM Data Quality</title>
    <script src="../Jsp/JS/d3.v3.min.js"></script>
    <script src="../Jsp/JS/liquidFillGauge.js" language="JavaScript"></script>
    <script src="../Jsp/JS/Donut3D.js" language="JavaScript"></script>
<!--     <script src="d3.v3.min.js"></script>
    <script src="liquidFillGauge.js" language="JavaScript"></script>
    <script src="Donut3D.js" language="JavaScript"></script> -->
    <style>
        .liquidFillGaugeText { font-family: Helvetica; font-weight: bold; }
        
        #fillgauge4{
        	position:relative;
        	left:25%;
        	visibility:hidden;
        }
        #fillgauge5{
        	position:relative;
        	left:25%;
        }
        #svgDistribution{
            position:relative;
        	left:15%;
        }
        
		path.slice{
			stroke-width:2px;
		}
		polyline{
			opacity: .3;
			stroke: black;
			stroke-width: 2px;
			fill: none;
		} 
		svg text.percent{
			fill:white;
			text-anchor:middle;
			font-size:15px;
		}
    </style>
</head>
<body>
<!-- <svg id="fillgauge1" width="10%" height="250" onclick="gauge1.update(NewValue());"></svg>
<svg id="fillgauge2" width="19%" height="200" onclick="gauge2.update(NewValue());"></svg>
<svg id="fillgauge3" width="19%" height="200" onclick="gauge3.update(NewValue());"></svg> -->
<svg id="fillgauge4" width="0%" height="0" onclick="gauge4.update(NewValue());"></svg>
<svg id="fillgauge5" width="50%" height="220" onclick="gauge5.update(NewValue());"></svg>
<!-- <svg id="fillgauge6" width="19%" height="200" onclick="gauge6.update(NewValue());"></svg> -->
<script language="JavaScript">
/*  var gauge1 = loadLiquidFillGauge("fillgauge1", 43);
    var config1 = liquidFillGaugeDefaultSettings();
    config1.circleColor = "#FF7777";
    config1.textColor = "#FF4444";
    config1.waveTextColor = "#FFAAAA";
    config1.waveColor = "#FFDDDD";
    config1.circleThickness = 0.2;
    config1.textVertPosition = 0.2;
    config1.waveAnimateTime = 1000;  */
/*  var gauge2= loadLiquidFillGauge("fillgauge2", 28, config1);
    var config2 = liquidFillGaugeDefaultSettings();
    config2.circleColor = "#D4AB6A";
    config2.textColor = "#553300";
    config2.waveTextColor = "#805615";
    config2.waveColor = "#AA7D39";
    config2.circleThickness = 0.1;
    config2.circleFillGap = 0.2;
    config2.textVertPosition = 0.8;
    config2.waveAnimateTime = 2000;
    config2.waveHeight = 0.3;
    config2.waveCount = 1; */
/*   var gauge3 = loadLiquidFillGauge("fillgauge3", 60.1, config2);
    var config3 = liquidFillGaugeDefaultSettings();
    config3.textVertPosition = 0.8;
    config3.waveAnimateTime = 5000;
    config3.waveHeight = 0.15;
    config3.waveAnimate = false;
    config3.waveOffset = 0.25;
    config3.valueCountUp = false;
    config3.displayPercent = false; */
  var gauge4 = loadLiquidFillGauge("fillgauge4", 50);
    var config4 = liquidFillGaugeDefaultSettings();
    config4.circleThickness = 0.15;
    config4.circleColor = "#808015";
    config4.textColor = "#555500";
    config4.waveTextColor = "#FFFFAA";
    config4.waveColor = "#AAAA39";
    config4.textVertPosition = 0.8;
    config4.waveAnimateTime = 1000;
    config4.waveHeight = 0.05;
    config4.waveAnimate = true;
    config4.waveRise = false;
    config4.waveHeightScaling = false;
    config4.waveOffset = 0.25;
    config4.textSize = 0.75;
    config4.waveCount = 3;
  var gauge5 = loadLiquidFillGauge("fillgauge5", 43.64, config4);
    var config5 = liquidFillGaugeDefaultSettings();
    config5.circleThickness = 0.4;
    config5.circleColor = "#6DA398";
    config5.textColor = "#0E5144";
    config5.waveTextColor = "#6DA398";
    config5.waveColor = "#246D5F";
    config5.textVertPosition = 0.52;
    config5.waveAnimateTime = 5000;
    config5.waveHeight = 0;
    config5.waveAnimate = false;
    config5.waveCount = 2;
    config5.waveOffset = 0.25;
    config5.textSize = 1.2;
    config5.minValue = 30;
    config5.maxValue = 150
    config5.displayPercent = false;
  //var gauge6 = loadLiquidFillGauge("fillgauge6", 120, config5); 

    function NewValue(){
        if(Math.random() > .5){
            return Math.round(Math.random()*100);
        } else {
            return (Math.random()*100).toFixed(1);
        }
    }
</script>
<div><h3><center>Master Data Quality Grade</center></h3></div>

<script>
var salesData=[
	{label:"Basic", color:"#3366CC"},
	{label:"Plus", color:"#DC3912"},
	{label:"Lite", color:"#FF9900"}
];

var svg = d3.select("body").append("svg").attr("id","svgDistribution").attr("width",300).attr("height",280);

svg.append("g").attr("id","salesDonut");
/* svg.append("g").attr("id","quotesDonut"); */

Donut3D.draw("salesDonut", randomData(), 150, 150, 130, 100, 30, 0.4);
/* Donut3D.draw("quotesDonut", randomData(), 450, 150, 130, 100, 30, 0); */
	
function changeData(){
	Donut3D.transition("salesDonut", randomData(), 130, 100, 30, 0.4);
/* 	Donut3D.transition("quotesDonut", randomData(), 130, 100, 30, 0); */
}

function randomData(){
	return salesData.map(function(d){ 
		return {label:d.label, value:1000*Math.random(), color:d.color};});
}
</script>
<div><h3><center>Master Data Distribution</center></h3></div>


</body>
</html>