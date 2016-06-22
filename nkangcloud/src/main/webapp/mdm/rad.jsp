<%@ page language="java" pageEncoding="UTF-8"%> 
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
	<meta charset="utf-8" />
	<title>HPE - MDM Quality DashBoard</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="hpe" />
	<link href="../nkang/assets_athena/bootstrap/css/bootstrap.min.css" 			rel="stylesheet" type="text/css"/>
	<link href="../nkang/assets_athena/bootstrap/css/bootstrap-responsive.min.css" 	rel="stylesheet" type="text/css"/>
	<link href="../nkang/assets_athena/font-awesome/css/font-awesome.css" 			rel="stylesheet" type="text/css"/>
	<link href="../nkang/css_athena/style.css" 										rel="stylesheet" type="text/css"/>
	<link href="../nkang/assets_athena/icomoon/iconMoon.css" 						rel="stylesheet" type="text/css"/>
	<link href="../nkang/css_athena/style-responsive.css" 							rel="stylesheet" type="text/css"/>
	<link href="../nkang/css_athena/style-default.css" 								rel="stylesheet" type="text/css"/>
	<link href="../nkang/assets_athena/data-tables/DT_bootstrap.css" 				rel="stylesheet" type="text/css"/>
	<link href="../nkang/c3.css" 													rel="stylesheet" type="text/css"/>
	<script src="../nkang/js_athena/jquery-1.8.3.min.js"></script>
	<script src="../nkang/assets_athena/bootstrap/js/bootstrap.js"></script>
	<script src="../nkang/assets_athena/jquery-ui/jQuery_UI_1_10_3.js"></script>
	<script src="../nkang/js_athena/typeahead.js"></script>
	<script src="../nkang/js_athena/jquery.feedback_me.js"></script>
	<script src="../nkang/assets_athena/raty/ratyViewJs.js"></script>
	<script src="../nkang/assets_athena/data-tables/jquery.dataTables.js"></script>
	<script src="../nkang/assets_athena/data-tables/DT_bootstrap.js"></script>
	<script src="../nkang/js_athena/common-scripts.js"></script>
	<script src="../nkang/c3.min.js"></script>
    <script src="../nkang/d3.v3.min.js"></script>
    <script src="../nkang/liquidFillGauge.js"></script>
    <script src="../nkang/Donut3D.js"></script>
	<script src="../nkang/Chart.js"></script>
		<script src="../nkang/RadarChart.js"></script>
	
	<script>
		$j = jQuery.noConflict();
		$j(function(){
			var ctx = document.getElementById("myChart").getContext("2d");
			var data = {
			    labels: ["Process Manufacturing", "Real Estate", "Education: K-12 /School", "Construction", "Banking", "Retail", "Legal Services"],
			    datasets: [
			        {
			            label: "My First dataset",
			            fillColor: "rgba(220,220,220,0.2)",
			            strokeColor: "rgba(220,220,220,1)",
			            pointColor: "rgba(220,220,220,1)",
			            pointStrokeColor: "#fff",
			            pointHighlightFill: "#fff",
			            pointHighlightStroke: "rgba(220,220,220,1)",
			            data: [65, 59, 90, 81, 56, 55, 40]
			        },
			        {
			            label: "My Second dataset",
			            fillColor: "rgba(151,187,205,0.2)",
			            strokeColor: "rgba(151,187,205,1)",
			            pointColor: "rgba(151,187,205,1)",
			            pointStrokeColor: "#fff",
			            pointHighlightFill: "#fff",
			            pointHighlightStroke: "rgba(151,187,205,1)",
			            data: [28, 48, 40, 19, 96, 27, 100]
			        }
			    ]
			};
			//Radar.defaults = {
			options = {			
				scaleOverlay : false,
				scaleOverride : false,
				scaleSteps : null,
				scaleStepWidth : null,
				scaleStartValue : null,
				scaleShowLine : true,
				scaleLineColor : "rgba(0,0,0,.1)",
				scaleLineWidth : 1,
				scaleShowLabels : false,
				scaleLabel : "<"+"%=value%"+">",
				scaleFontFamily : "'Arial'",
				scaleFontSize : 12,
				scaleFontStyle : "normal",
				scaleFontColor : "#666",
				scaleShowLabelBackdrop : true,
				scaleBackdropColor : "rgba(255,255,255,0.75)",
				scaleBackdropPaddingY : 2,
				scaleBackdropPaddingX : 2,
				angleShowLineOut : true,
				angleLineColor : "rgba(0,0,0,.1)",
				angleLineWidth : 1,			
				pointLabelFontFamily : "'Arial'",
				pointLabelFontStyle : "normal",
				pointLabelFontSize : 12,
				pointLabelFontColor : "#666",
				pointDot : true,
				pointDotRadius : 3,
				pointDotStrokeWidth : 1,
				datasetStroke : true,
				datasetStrokeWidth : 2,
				datasetFill : true,
				animation : true,
				animationSteps : 60,
				animationEasing : "easeOutQuart",
				onAnimationComplete : null
				
			}
			var myRadarChart = new Chart(ctx).Radar(data, options);
		 });
	</script>
</head>
<body>
	<canvas id="myChart" width="1000" height="1000" style="border:1px solid #c3c3c3;">
	Your browser does not support the canvas element.
	</canvas>
	
	<div style="overflow: auto; width: auto; max-height:442px;">
     	<div id="chart"> </div>
				<script>
					var chart = c3.generate({
						data: {
							columns: [
								['客户', 10	, 10		, 10, 10, 10, 10, 10, 10],
								['客户A', 10	, 10		, 10, 10, 10, 10, 10, 10],
								['客户B', 10	, 10		, 10, 10, 10, 10, 10, 10]
									]
								
						},
						axis: {
							x: {
								type: 'category',
								categories: ['江北区s', '渝北区', '沙坪坝区', '北碚区', '南岸区', '巴南区','九龙坡区','忠县']
							}
						},
						zoom: {
					        enabled: true
					    }
					});
			    </script>
    </div>
    
    <div id="body">
	  <div id="chart2"></div>
    </div>
    <script>
    var w = 500,
	h = 500;

var colorscale = d3.scale.category10();

//Legend titles
var LegendOptions = ['Smartphone','Tablet'];

//Data
var d = [
		  [
			{axis:"Email",value:0.59},
			{axis:"Social Networks",value:0.56},
			{axis:"Internet Banking",value:0.42},
			{axis:"News Sportsites",value:0.34},
			{axis:"Search Engine",value:0.48},
			{axis:"View Shopping sites",value:0.14},
			{axis:"Paying Online",value:0.11},
			{axis:"Buy Online",value:0.05},
			{axis:"Stream Music",value:0.07},
			{axis:"Online Gaming",value:0.12},
			{axis:"Navigation",value:0.27},
			{axis:"App connected to TV program",value:0.03},
			{axis:"Offline Gaming",value:0.12},
			{axis:"Photo Video",value:0.4},
			{axis:"Reading",value:0.03},
			{axis:"Listen Music",value:0.22},
			{axis:"Watch TV",value:0.03},
			{axis:"TV Movies Streaming",value:0.03},
			{axis:"Listen Radio",value:0.07},
			{axis:"Sending Money",value:0.18},
			{axis:"Other",value:0.07},
			{axis:"Use less Once week",value:0.08}
		  ],[
			{axis:"Email",value:0.48},
			{axis:"Social Networks",value:0.41},
			{axis:"Internet Banking",value:0.27},
			{axis:"News Sportsites",value:0.28},
			{axis:"Search Engine",value:0.46},
			{axis:"View Shopping sites",value:0.29},
			{axis:"Paying Online",value:0.11},
			{axis:"Buy Online",value:0.14},
			{axis:"Stream Music",value:0.05},
			{axis:"Online Gaming",value:0.19},
			{axis:"Navigation",value:0.14},
			{axis:"App connected to TV program",value:0.06},
			{axis:"Offline Gaming",value:0.24},
			{axis:"Photo Video",value:0.17},
			{axis:"Reading",value:0.15},
			{axis:"Listen Music",value:0.12},
			{axis:"Watch TV",value:0.1},
			{axis:"TV Movies Streaming",value:0.14},
			{axis:"Listen Radio",value:0.06},
			{axis:"Sending Money",value:0.16},
			{axis:"Other",value:0.07},
			{axis:"Use less Once week",value:0.17}
		  ]
		];

//Options for the Radar chart, other than default
var mycfg = {
  w: w,
  h: h,
  maxValue: 0.6,
  levels: 6,
  ExtraWidthX: 300
}

//Call function to draw the Radar chart
//Will expect that data is in %'s
RadarChart.draw("#chart2", d, mycfg);

////////////////////////////////////////////
/////////// Initiate legend ////////////////
////////////////////////////////////////////

var svg = d3.select('#body')
	.selectAll('svg')
	.append('svg')
	.attr("width", w+300)
	.attr("height", h)

//Create the title for the legend
var text = svg.append("text")
	.attr("class", "title")
	.attr('transform', 'translate(90,0)') 
	.attr("x", w - 70)
	.attr("y", 10)
	.attr("font-size", "12px")
	.attr("fill", "#404040")
	.text("What % of owners use a specific service in a week");
		
//Initiate Legend	
var legend = svg.append("g")
	.attr("class", "legend")
	.attr("height", -400)
	.attr("width", 20)
	.attr('transform', 'translate(90,20)') 
	;
	//Create colour squares
	legend.selectAll('rect')
	  .data(LegendOptions)
	  .enter()
	  .append("rect")
	  .attr("x", w - 65)
	  .attr("y", function(d, i){ return i * 20;})
	  .attr("width", 10)
	  .attr("height", 10)
	  .style("fill", function(d, i){ return colorscale(i);})
	  ;
	//Create text next to squares
	legend.selectAll('text')
	  .data(LegendOptions)
	  .enter()
	  .append("text")
	  .attr("x", w - 52)
	  .attr("y", function(d, i){ return i * 20 + 9;})
	  .attr("font-size", "11px")
	  .attr("fill", "#737373")
	  .text(function(d) { return d; })
	  ;	
    </script>
</body>
</html>