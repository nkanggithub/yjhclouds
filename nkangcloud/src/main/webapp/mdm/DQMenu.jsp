<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class=" js csstransitions">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MDM Makes it Matter</title> 
	<meta content="" name="hpe" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link rel="icon" type="image/x-icon" href="webitem/hpe.ico"/>
	<link rel="short icon" type="image/x-icon" href="webitem/hpe.ico"/>
	<link rel="stylesheet" type="text/css" href="MetroStyleFiles//CSS/sonhlab-base.css"/>
	<link rel="stylesheet" type="text/css" href="MetroStyleFiles//CSS/metrotab-v2.css"/>
	<link rel="stylesheet" type="text/css" href="MetroStyleFiles//CSS/metro-bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="MetroStyleFiles//CSS/global-demo.css"/>	
	<link rel="stylesheet" type="text/css" href="MetroStyleFiles//CSS/animation-effects.css"/>		
	<link rel="stylesheet" type="text/css" href="MetroStyleFiles//CSS/openmes.css"/>
	<link rel="stylesheet" type="text/css" href="nkang/c3.css"/>
	<link rel="stylesheet" type="text/css" href="MetroStyleFiles//sweetalert.css"/>
	<link rel="stylesheet" type="text/css" href="MetroStyleFiles//CSS/pageLoad.css"/>
	
	<script type="text/javascript" src="Jsp/JS/loader.js"></script>
<script type="text/javascript" src="Jsp/JS/jsapi.js"></script>
	
	
<!-- 	<link rel="stylesheet" type="text/css" href="Jsp/CSS/bootstrap.css"/> -->
	<link rel="stylesheet" type="text/css" href="Jsp/CSS/common.css"/>
	<script type="text/javascript" src="MetroStyleFiles//JS/jquery.min.2.1.1.js"></script>
	<script type="text/javascript" src="MetroStyleFiles//JS/jquery.easing.min.13.js"></script>
	<script type="text/javascript" src="MetroStyleFiles//JS/jquery.mousewheel.min.js"></script>
	<script type="text/javascript" src="MetroStyleFiles//JS/jquery.jscrollpane.min.js"></script>
	<script type="text/javascript" src="MetroStyleFiles//JS/jquery.masonry.min.js"></script>
	<script type="text/javascript" src="MetroStyleFiles//JS/modernizr-transitions.js"></script>
	<script type="text/javascript" src="MetroStyleFiles//JS/metrotab-v2.min.js"></script>
	<script type="text/javascript" src="MetroStyleFiles//JS/openmes.min.js"></script>
	<script type="text/javascript" src="MetroStyleFiles//sweetalert.min.js"></script>
    <script type="text/javascript" src="nkang/liquidFillGauge.js"></script>
	<script type="text/javascript" src="nkang/Chart.js"></script>
	<script type="text/javascript" src="nkang/gauge.js"></script>
	<script type="text/javascript" src="nkang/RadarChart.js"></script>

	<script type="text/javascript" src="nkang/d3.v3.min.js"></script>
	<script type="text/javascript" src="nkang/c3.min.js"></script>
	
	<script type="text/javascript" src="Jsp/JS/bootstrap.min.js"></script>
	<script type="text/javascript">
	 $(document).ajaxStart(function () {
		$(".sk-circle").show();
	    }).ajaxStop(function () {
	    	$(".sk-circle").hide();
	    });
	    google.charts.load('upcoming', {'packages':['geochart']});
        google.charts.setOnLoadCallback(drawRegionsMap);
        function drawRegionsMap() {
          var data = google.visualization.arrayToDataTable([
            ['Country', 'Popularity'],
            ['Germany', 200],
            ['United States', 300],
            ['Brazil', 400],
            ['Canada', 500],
            ['France', 600],
            ['RU', 700],
            ['China',700]
          ]);
          var options = {};
          var chart = new google.visualization.GeoChart(document.getElementById('regions_div'));
          chart.draw(data, options);
        }
	$(document).ready(function() {
		$('.metrotabs').metrotabs({
				outeffect : 'swing',
				ineffect :	'swing',
				moveout : 'BottomOrRight', // TopOrLeft | BottomOrRight
				movein : 'TopOrLeft', // TopOrLeft | BottomOrRight
				outduration : 400,
				induration : 400,
				minibartitle: 'Master Data Lake Quality Grade',
				mtEffect : 'vertical' // vertical | horizontal | fade
			});
		
		$("#openmes").on("click",function(){
			
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "getOpenMes",
				success : function(data) {
					  if (data) {				  
							var htmlcustli = "<div class=\"well well-sm\" style='text-align:center;'><img src='mdm/images/People.png' width='25px' height='25px' alt='icon'/><p style='margin:0;text-align:center;font-size:14px'>"+data.length+" Active Clients in service</p></div>";
							if(data.length!=0){
							for(var i=0;i<data.length;i++){
								htmlcustli = htmlcustli + "<li class=\"active\"><a href=\"#\" onclick=\"javascript:showDetailsForClient('" + data[i].clientID + "','"+data[i].consumedWebService+"');\"><span class=\"badge pull-right\">" + data[i].clientIdentifier + "</span>"+ data[i].clientDescription+"</a></li>";
							}
							}
							$("#openmesChart").html(htmlcustli);
					  }
				},
				error:function(data)
				{
					console.log("failed..."+data.toString());
				}
			
		});
		});
		$("#espresso").on("click",function(){
			var chart2HTML=$("#chart2").html();
			  if(chart2HTML==""){
			
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "getChart2?userState="+$("#userState").html(),
				success : function(data) {
					  if (data) {
						  var chart = c3.generate({
								data: {
									columns: [
									          data[0],
									          data[1],
									         data[2]
									         ]
								},
								axis: {
									x: {
										type: 'category',
										categories: data[3]
									}
								},
								zoom: {
							        enabled: true
							    },
							    bindto : '#chart2'
							});
						 
							console.log("pass by....");
					  }
				},
				error:function(data)
				{
					console.log("failed..."+data.toString());
				}
			
		});
		} 

				   loadChart2();
				   $(".tick text tspan").attr("onclick","hello(this)");
					

			
			 
		});
		
		$("#americano").on("click",function(){
			
			var chartRadarHTML=$("#chart3Radar").html();
			  if(chartRadarHTML==""){

					$.ajax({
						type : "POST",
						dataType : "json",
						url : "getRadarda?userState="+$("#userState").html(),
						success : function(data) {
							  if (data) {	
								//  var temp=eval(data);
								//var temp=[[{axis:" Automotive ",value:3.6345},{axis:" Business Services ",value:15.367},{axis:" Discrete - Machinery ",value:3.359},{axis:" Consumer Packaged Goods ",value:6.2055},{axis:" Construction ",value:3.1815},{axis:" Education: K-12 /School ",value:6.0675},{axis:" Wholesale Trade ",value:16.3635},{axis:" Retail ",value:10.1795},{axis:" Transportation&Trans Services ",value:3.1595},{axis:" Amusement and Recreation ",value:1.817}]];
								  var w = 250, h = 250;
									var colorscale = d3.scale.category10();
									//Legend titles
									var LegendOptions = ['A','B'];
								  var mycfg = {
										  w: w,
										  h: h,
										  maxValue: 0.6,
										  levels: 6,
										  ExtraWidthX: 170
										};
										//Call function to draw the Radar chart
										//Will expect that data is in %'s
										RadarChart.draw("#chart3Radar", data, mycfg);

										var svg = d3.select('#americano_loadChart')
											.selectAll('svg')
											.append('svg')
											.attr("width", w+10)
											.attr("height", h);

										//Initiate Legend	
										var legend = svg.append("g")
											.attr("class", "legend")
											.attr("height", 100)
											.attr("width", 150)
											.attr('transform', 'translate(-220,40)') ;
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
											  .text(function(d) { return d; });	
									
							  
							
						
							  }
							  $("#americano_loadChart").append("<p id='showBtn' style='display:block;position:absolute;top:350px;color:#00b287;font-size:16px;width:100%;text-align:center' onclick='show_american_div()'>load more..</p>")
							  var htmlcustli = "<div id='american_div' style='margin-top:40px;display:none'>";
								if(data[0].length!=0){
								for(var i=0;i<data[0].length;i++){
									htmlcustli = htmlcustli + "<li style='list-style:none;margin-top: 2px;width:100%;' class=\"active\"><a style='position: relative;display: block;padding: 10px 15px;color: #fff;background-color: #428bca;' href=\"#\" \"><span style='color:#428bca;background-color: white;' class=\"badge pull-right\">" + data[0][i].count + "</span>"+ data[0][i].axis+"</a></li>";
								}
								}
								htmlustli=htmlcustli+"</div>";
								$("#americano_loadChart").append(htmlcustli);
								
						},
						error:function(data)
						{
							console.log("failed..."+data.toString());
						}
					
				});
				} 
			loadChartRadar();
		});
		
		$("#openfooter").on("click",function(){
			var chart3HTML=$("#chart3").html();
			  if(chart3HTML==""){
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "getOpenfooterByCountry?country=CN",
				success : function(data) {
					  if (data) {
						  var chart = c3.generate({
								data : {
									columns : [
										          data[0],
										          data[1],
										         data[2],
										         data[3]
								    		  ],
									type : 'pie'
								},
								pie : {
									label : {
										format : function(value, ratio, id) {
											return d3.format('#')(value);
										}
									}
								},
								bindto : '#chart3'
							});
						 
							
					  }
				},
				error:function(data)
				{
					console.log("failed..."+data.toString());
				}
			
		});
		} 
			  
				   loadChart();
			   
			  
		});
		// $("#conporlan").bind("click", loadChart4);
		//document.getElementById("conporlan").addEventListener('click',loadChart4,false);
		//var clickEvent = $("#conporlan").data('events')["click"];
		//alert(JSON.stringify(clickEvent));
		var username= $("#username").text();
		var addressInfo= $("#addressInfo").text();
		
	});
	
	function loadChartRadarWithDetail(obj){
		swal({   
			title: "Segment Area",   
			text: "Retrieving more industry distribution....",   
			type: "info",   
			showCancelButton: true,   
			closeOnConfirm: false,   
			showLoaderOnConfirm: true, }, 
			function(){   
				setTimeout(function(){     
					swal(document.getElementById("username").innerHTML+" Industries Presented within  "+document.getElementById("userState").innerHTML);}, 200); 
				$("#radardetailid").show();
			});
	}
	var country="CN";
	
	function hello(obj)
	{
		console.log($(obj).text());
	}
	function countrySelect(obj){
		var cnName=$("#countrySelect option:selected").val();
		if(cnName=="中国")
			{
			country="CN";
			};
			if(cnName=="马来西亚")
			{
			country="MY";		
			};
			$.ajax({
				type : "POST",
				dataType : "json",
				url : "getCitys?country="+country,
				success : function(data) {
					var text="";
					 for(var i=0;i<data.length;i++){
						  text=text+"<option>"+data[i]+"</option>";
						}
					$("#citySelect").html(text);
				},
				error:function(data)
				{
					console.log("failed..."+data.toString());
				}
			}
			);
	};
	function userlocationsave(obj){
		
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "getCitys?country="+country,
			success : function(data) {
				  if (data) {
					  var text="<p style='position:relative;top:-10px;line-height:30px;height:30px;'>Please select a city</p><select id='countrySelect' onchange='countrySelect(this)' style='height: 30px;width: 100px;border-radius: 8%;'><option>中国</option><option>马来西亚</option></select><select id='citySelect' style='height: 30px;margin-left:10px;width: 100px;border-radius: 8%;'>";
					  for(var i=0;i<data.length;i++){
						  text=text+"<option>"+data[i]+"</option>";
						}
					  text=text+"</select>";
						swal(
								{
									title: "Dear "+document.getElementById("username").innerHTML+"!",   
									text: text,     
									showCancelButton: true,   
									closeOnConfirm: false,  
									html:true,
									animation: "slide-from-top"
								}, 
								function(inputValue){
									if (inputValue === false){ return false; }
									else{
										var city=$("#citySelect option:selected").val();
										
										$("#chart3").html("");
										$("#chart2").html("");
										$("#locationCity").text(city.toLocaleUpperCase());
										$("#userState").text(city);
										swal("Success", "your location saved", "success");
									}							
									 
								}
							);
					 
						
				  }
			},
			error:function(data)
			{
				console.log("failed..."+data.toString());
			}
		
	});


	}
		$(window).load(function() {
			$("#goaway").fadeOut("slow");
			$(".mes-openbt").openmes({ext: 'php'});
		});
		
		function loadChart(){

			 $("#chart3").show();
/* 	 		$("#openfooter_loadC3").html(chartobj);  */
			 $("#chart2").hide();
			 $("#chart4").hide();
			 $("#chart5").hide();
			 
			 $("#chart3Radar").hide();
			
		}
		
		function hideAll(obj){
			 $("#chart3").hide();
			 $("#chart2").hide();
			 $("#chart4").hide();
			 $("#chart5").hide();
			 $("#chart3Radar").hide();
		}
		function loadChart2(){
	 		$("#chart2").show();
/* 	 		$("#openfooter_loadC2").append(chartobj); */
			//$("#openfooter_loadC2").html(chartobj);
			 $("#chart3").hide();
			 $("#chart4").hide();
			 $("#chart5").hide();
			 $("#chart3Radar").hide();
			 

	     }
		function show_american_div()
		{
			 $("#american_div").show();
			 $("#showBtn").hide();
			 
			 
		}
		function loadChartRadar(){
			var chartobj = $("#chart3Radar");
			var detailobj=$("#american_div");
			$("#americano_loadChart").html("");
			$("#americano_loadChart").append(chartobj);
			$("#americano_loadChart").append(detailobj);
			
			$("#chart3").hide();
			 $("#chart4").hide();
			 $("#chart5").hide();
			 $("#chart2").hide();
			chartobj.show();
			
		}
		
		function loadChart4(obj){
			var chartobj = $("#chart4");
			$("#conporlan_loadChart").html(chartobj);
			$("#chart3").hide();
			 $("#chart2").hide();
			 $("#chart5").hide();
			 $("#chart3Radar").hide();
			chartobj.show();
		}
		function loadOpenMe(obj)
		{
			$("#openMeDiv").show();
			$("#chart3").hide();
			 $("#chart2").hide();
		}
		function loadChart5(obj){
			var chartobj = $("#chart5");
//			$("#capuqino_loadChart").html(chartobj);
			$("#chart3").hide();
			 $("#chart4").hide();
			 $("#chart2").hide();
			 $("#chart3Radar").hide();
			chartobj.show();
		}
		function CommentMe(){
			swal(
					{
						title: "Dear Comment US!",   
						text: "Drop us a message and do things better together",   
						type: "input",   
						showCancelButton: true,   
						closeOnConfirm: false,   
						animation: "slide-from-top",   
						inputPlaceholder: "Write something" 
					}, 
					function(inputValue){
						inputVar = inputValue;
						if (inputValue === false) return false;      
						if (inputValue === "") {     
							swal.showInputError("You need to write something!");     
							return false;   
						}
						swal("Thank You!", "We will contact you soon", "success"); 
					}
				);
		}
		function OrganizationInformation(){
			/*swal("200M", document.getElementById("totalOPSI").innerHTML+" Organizations", "success");*/
			window.location.href="http://shenan.duapp.com/mdm/DQNavigate.jsp?UID="+document.getElementById("uid").innerHTML;
		}
		function showDetailsForClient(paraStr,ws){
			/*swal(paraStr,"<span style='color:red'>"+paraStr+"</span>", "success","true");*/
			var datas=ws.split(",");
			var text="";
			for(var i=0;i<datas.length;i++)
			{
				text=text+"<p>"+datas[i]+"</p>"
			}
			   swal({  
          title:paraStr,  
          text:text,
          type:"success",  
		  html:"true",
          showCancelButton:false,  
          showConfirmButton:"true",  
          confirmButtonText:"OK",  
          animation:"slide-from-top"  
        });
		}
	</script>
	  <style type="text/css">

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
    background-color: #333;
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
	

</head>
<body>
<div id="username" style="display:none">${ userInfo.nickname } </div>
<%-- <div id="radarSize" style="display:none">${ radarSize } </div> --%>
<div id="userState" style="display:none">${ userState } </div>
<div id="uid" style="display:none">${ uid }</div>
<div id="address" style="display:none">${ curLoc }</div>
<div id="addressInfo" style="display:none">
<c:forEach items="${userInfo.addressInfo}" var="addressInfo">
 ${addressInfo} |
</c:forEach>
 </div>
	<div class="loader more" id="goaway"></div>
	<div class="demo-content">
		<img class="mes-openbt" data-mesid="message-5" style="width:150px;height:58px;" src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=015900000053FQo&oid=00D90000000pkXM&lastMod=1438220916000" alt="HP Logo"/> 
	</div>

<div id="userInfo">
	<p class="navbar-text pull-right">Welcome <a href="http://shenan.duapp.com/mdm/profile.jsp?UID=${ uid }" class="navbar-link">${ userInfo.nickname }</a><br />
		<a href="http://shenan.duapp.com/mdm/profile.jsp?UID=${ uid }"><img src="${ userInfo.headimgurl }" alt="userImage" class="userImage pull-right"/></a>
	</p>

</div>

 <div id="topinfo">
		<svg id="fillgauge4" width="0%" height="0" onclick="gauge4.update(NewValue());"></svg>
		<svg id="fillgauge5" width="50%" height="220" onclick="Javascript:OrganizationInformation();"></svg>
		<script>
		var percent ="0.66";
		var percent2 = 100*percent;
		  var gauge4 = loadLiquidFillGauge("fillgauge4", 50);
		    var config4 = liquidFillGaugeDefaultSettings();
		    config4.circleThickness = 0.15;
		   config4.circleColor = "#FFFFFF";
		    config4.textColor = "#00b287";
		    config4.waveTextColor = "#00b287";
		    config4.waveColor = "#00b287";
		    config4.textVertPosition = 0.8;
		    config4.waveAnimateTime = 1000;
		    config4.waveHeight = 0.05;
		    config4.waveAnimate = true;
		    config4.waveRise = false;
		    config4.waveHeightScaling = false;
		    config4.waveOffset = 0.25;
		    config4.textSize = 0.75;
		    config4.waveCount = 3;
		  var gauge5 = loadLiquidFillGauge("fillgauge5", percent2 , config4);
		    var config5 = liquidFillGaugeDefaultSettings();
		    config5.circleThickness = 0.4;
		    config5.circleColor = "#FFFFFF";
		    config5.textColor = "#00b287";
		    config5.waveTextColor = "#00b287";
		    config5.waveColor = "#00b287";
		    config5.textVertPosition = 0.52;
		    config5.waveAnimateTime = 5000;
		    config5.waveHeight = 0;
		    config5.waveAnimate = false;
		    config4.waveRise = true;
		    config5.waveCount = 2;
		    config5.waveOffset = 0.25;
		    config5.textSize = 1.2;
		    config5.minValue = 30;
		    config5.maxValue = 150;
		    config5.displayPercent = false;
		    function NewValue(){
		        return 100*0.68;
		    }
		</script>
 </div>


    
<section id="mainform">
 		<img id="user_location_save" onclick="javascript:userlocationsave(this);" src="MetroStyleFiles//setuplocation.png" alt="userImage" class="userImage pull-right" style="position:relative;top:10px;right:2%; z-index: 8;" /><span id="locationCity" style="position:absolute;top:-6px;right:5px;color:#00b287;z-index:8;font-size:16px">${ userState }</span>
	<!-- START METROTAB -->
    <div class="metrotabs">
        <div class="mt-blocksholder floatleft masonry" style="width: 96%; display: block; position: relative; height: 100%;top:-25px;left:2%" >
            <div id="tileboxjs"  onclick="javascript:hideAll(this);" class="a1 tile-bt-long img-purtywood mt-tab mt-active mt-loadcontent masonry-brick" style="position: absolute; top: 0px; left: 0px;">
                <a href="javascript:void(0);">
	                <img src="MetroStyleFiles//datalake.png" alt="">
	                <span class="light-text"><strong>Data Lake</strong></span>
                </a>
            </div>      
             <div id="conporlan" onclick="javascript:loadChart4(this);" class="a2 tile-bt solid-red mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 200px; left: 200px;">
                <a href="javascript:void(0);" target="_blank">
					<img src="MetroStyleFiles//image/icon.png" alt="">
					<span class="light-text">康宝蓝</span>
                </a>
            </div>
			
			<div id="espresso" class="a3 tile-bt solid-blue-2 mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 300px; left: 200px;">
                <a href="javascript:void(0);">
	                <img src="MetroStyleFiles//location.png" alt="">
	                <span class="light-text">Geolocation</span>
                </a>
            </div>
			
			<div id="openmes"   onclick="javascript:loadOpenMe(this);" class="a4 tile-bt-long solid-green-2 mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 0px; left: 100px;">
                <a href="javascript:void(0);">
	                <img src="MetroStyleFiles//person.png" alt="">
	                <span class="light-text">Show Case</span>
                </a>
            </div>
            
            <div id="capuqino" data-ext="html" onclick="javascript:loadChart5(this);" class="a5 tile-bt-long solid-orange-2 mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 200px; left: 0px;">
                <a href="javascript:void(0);">
	                <img src="MetroStyleFiles//image/icon.png" alt="">
	                <span class="light-text">卡布奇诺</span>
                </a>
            </div>
            
            			<div id="americano" class="a6 tile-bt solid-red-2 mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 400px; left: 0px;">
                <a href="javascript:void(0);">
	                <img src="MetroStyleFiles//industry.png" alt="">
	                <span class="light-text">Industry</span>
                </a>
            </div>
            
            <div id="joinus" class="a7 tile-bt solid-red-2 mt-tab  masonry-brick" style="position: absolute; top: 400px; left: 0px;">
		        <div class="tile-bt solid-green hovershadow-green">
		            <a href="javascript:void(0);" onClick="Javascript:CommentMe();"  title="move together">
		                <img src="MetroStyleFiles//image/documents.png" alt="MetroTab Docs">
		                <span class="light-text" style="margin-left:5px; font-size:16px;">Join US</span>
		            </a>
		        </div>
    		</div>
            
			<div id="openfooter"  class="a8 tile-bt-long img-wildoliva mt-tab mt-loadcontent masonry-brick" style="position: absolute; top: 300px; left: 0px;">
                <a href="javascript:void(0);">
	                <img src="MetroStyleFiles//visualview.png" alt="">
	                <span class="light-text">Business Type</span>
                </a>
            </div>
            
     

			


        <div class="clearspace"></div>
        
        </div>
        
<!-- START CONTENT -->
        <div id="openMeDiv" class="mt-contentblock  floatleft" style="padding: 5px; height: 100%; width:100%; display: block;">
	        <div class="mt-content jspScrollable" style="height: 100%; top: 0px; overflow: hidden; padding: 0px; width: 100%;" tabindex="0">
	
			</div>
		</div>
<!-- END CONTENT -->
    </div><div class="clearspace"></div>
	<!-- END METROTAB -->
</section>

 <div id="chart3Radar" onclick="javascript:loadChartRadarWithDetail(this);"></div>

 <div id="chart4" >
		<script>
			var chart = c3.generate({
			    data: {
			        columns: [
			            ['data1', 30, 200, 100, 400, 150, 250, 50, 100, 250]
			        ]
			    },
			    axis: {
			        x: {
			            type: 'category',
			            categories: ['cat1', 'cat2', 'cat3', 'cat4', 'cat5', 'cat6', 'cat7', 'cat8', 'cat9']
			        }
			    },
			    bindto : '#chart4'
			});
 			$("#chart4").hide();
		</script>
	</div>
	
	<div id="chart5">
	<svg class="chart"></svg>
	
	<script>
		var data = {
		  labels: [
		    'resilience', 'maintainability', 'accessibility',
		    'uptime', 'functionality', 'impact'
		  ],
		  series: [
		    {
		      label: 'Customer',
		      values: [4, 8, 15, 16, 23, 42]
		    },
		    {
		      label: 'Partner',
		      values: [12, 43, 22, 11, 73, 25]
		    },
		    {
		      label: 'Competitor',
		      values: [31, 28, 14, 8, 15, 21]
		    },]
		};
		
		var chartWidth       = 200,
		    barHeight        = 15,
		    groupHeight      = barHeight * data.series.length,
		    gapBetweenGroups = 10,
		    spaceForLabels   = 100,
		    spaceForLegend   = 100;
		
		// Zip the series data together (first values, second values, etc.)
		var zippedData = [];
		for (var i=0; i<data.labels.length; i++) {
		  for (var j=0; j<data.series.length; j++) {
		    zippedData.push(data.series[j].values[i]);
		  }
		}
		
		// Color scale
		var color = d3.scale.category20();
		var chartHeight = barHeight * zippedData.length + gapBetweenGroups * data.labels.length;
		
		var x = d3.scale.linear()
		    .domain([0, d3.max(zippedData)])
		    .range([0, chartWidth]);
		
		var y = d3.scale.linear()
		    .range([chartHeight + gapBetweenGroups, 0]);
		
		var yAxis = d3.svg.axis()
		    .scale(y)
		    .tickFormat('')
		    .tickSize(0)
		    .orient("left");
		
		// Specify the chart area and dimensions
		var chart = d3.select(".chart")
		    .attr("width", spaceForLabels + chartWidth + spaceForLegend)
		    .attr("height", chartHeight);
		
		// Create bars
		var bar = chart.selectAll("g")
		    .data(zippedData)
		    .enter().append("g")
		    .attr("transform", function(d, i) {
		      return "translate(" + spaceForLabels + "," + (i * barHeight + gapBetweenGroups * (0.5 + Math.floor(i/data.series.length))) + ")";
		    });
		
		// Create rectangles of the correct width
		bar.append("rect")
		    .attr("fill", function(d,i) { return color(i % data.series.length); })
		    .attr("class", "bar")
		    .attr("width", x)
		    .attr("height", barHeight - 1);
		
		// Add text label in bar
		bar.append("text")
		    .attr("x", function(d) { return x(d) - 3; })
		    .attr("y", barHeight / 2)
		    .attr("fill", "red")
		    .attr("dy", ".35em")
		    .text(function(d) { return d; });
		
		// Draw labels
		bar.append("text")
		    .attr("class", "label")
		    .attr("x", function(d) { return - 10; })
		    .attr("y", groupHeight / 2)
		    .attr("dy", ".35em")
		    .text(function(d,i) {
		      if (i % data.series.length === 0)
		        return data.labels[Math.floor(i/data.series.length)];
		      else
		        return ""});
		
		chart.append("g")
		      .attr("class", "y axis")
		      .attr("transform", "translate(" + spaceForLabels + ", " + -gapBetweenGroups/2 + ")")
		      .call(yAxis);
		
		// Draw legend
		var legendRectSize = 18,
		    legendSpacing  = 4;
		
		var legend = chart.selectAll('.legend')
		    .data(data.series)
		    .enter()
		    .append('g')
		    .attr('transform', function (d, i) {
		        var height = legendRectSize + legendSpacing;
		        var offset = -gapBetweenGroups/2;
		        var horz = spaceForLabels + chartWidth + 40 - legendRectSize;
		        var vert = i * height - offset;
		        return 'translate(' + horz + ',' + vert + ')';
		    });
		
		legend.append('rect')
		    .attr('width', legendRectSize)
		    .attr('height', legendRectSize)
		    .style('fill', function (d, i) { return color(i); })
		    .style('stroke', function (d, i) { return color(i); });
		
		legend.append('text')
		    .attr('class', 'legend')
		    .attr('x', legendRectSize + legendSpacing)
		    .attr('y', legendRectSize - legendSpacing)
		    .text(function (d) { return d.label; });
		
		$("#chart5").hide();
		</script>
	</div>
 <!-- -------------------------------------------------------------------------------------------------------------- -->
 <!-- -------------------------------------------------------------------------------------------------------------- -->
<div id="mt-station">

<!-- MetroTab Content 卡布奇诺-->
<div data-mtid="capuqino" id="capuqino_loadChart">

</div>
<!-- End MetroTab Content  卡布奇诺-->

<!-- MetroTab Content 美式咖啡 Industry-->
<div data-mtid="americano"> 
	<div id="americano_loadChart"></div>
	<div id="radardetailid">
<%--    		<%
			for(String i : lst){
				String cnt =  RestUtils.CallgetFilterCountOnCriteriaFromMongo(i,"","重庆","");
				i = i.replaceAll("\\s+","");
				i = i.replaceAll("-", "");
				double n = Double.valueOf(cnt);
				double m = Double.valueOf(totalcntwithRegion);
				double percent = n/m;
				String mywidth = percent*100 + "%";
				out.print("<div class=\"progress\"><div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuenow=\""+ cnt +"\" aria-valuemin=\"0\" aria-valuemax=\""+ totalcntwithRegion +"\" style=\"width: "+ mywidth +";\"><span style=\"text-align:center; color:#000; \"> "+ i +" </span></div><span style=\"float:right;\">"+ cnt +"</span></div>");
			}
		%> --%>
</div>

</div>
<!-- End MetroTab Content  美式咖啡-->


<!-- MetroTab Content espresso浓缩-->
<div data-mtid="espresso" id="openfooter_loadC22" style="display:none"> 
	<div id="openfooter_loadC2"></div>
</div>
<!-- End MetroTab Content  espresso浓缩-->

<!-- Start MetroTab Content 康宝蓝-->
<div data-mtid="conporlan">
	<div >
		<div  style="position:relative; width:500px; height:500px;">
			<div id="conporlan_loadChart" style="position:absolute;"></div>
		</div>
	</div> 
	<!-- <div id="conporlan_loadChart"></div> -->
</div>
<!-- End MetroTab Content  康宝蓝-->

<!-- MetroTab Content 摩卡-->
<div data-mtid="openmes">
	<br />
 	<ul  id="openmesChart" class="nav nav-pills nav-stacked">
	</ul> 

</div>
<!-- End MetroTab Content 摩卡-->


<!-- Start MetroTab Content 玛琪雅朵-->
 <div data-mtid="openfooter" id="openfooter_loadC33" style="display:none">
	<div id="openfooter_loadC3">

</div>
</div> 
<!-- End MetroTab Content 玛琪雅朵-->


<!-- Start MetroTab Content 拿铁-->
<div data-mtid="tileboxjs">
	 <div id="regions_div" style="width: 360px; height: 300px;position:relative;top:30px;"></div>
	
</div>
<!-- End MetroTab Content 拿铁-->
</div>




<!-- START MESSAGE STATION -->
	<div id="mes-station">
		<div class="mes-container item-profileview transparent-black"
			data-mesid="message-5">
			<!-- Start Content Holder -->
			<div class="mes-contentholder">
				<div class="item-profilebody">
					<!-- Start Background -->
					<div class="mes-content item-profilebg solid-smoke"
						data-show="hmove" data-start="-100" data-showdura="400"></div>
					<!-- End Background -->

					<!-- Start Control Bar -->
					<div class="mes-content item-ctrlbar-5" data-show="fade"
						data-showdura="200">
						<div class="mes-closebt light-text floatleft">
							<img src="MetroStyleFiles//exit.png"
								style="width: 40px; height: 40px;" />
						</div>
						<div class="clearspace"></div>
					</div>
					<!-- End Control Bar -->

					<!-- Start Header Photo -->
					<div class="mes-content item-headerphoto" data-show="bounceInDown">
						<img style="width: 100%; height: 200px;"
							src="MetroStyleFiles//reallake.jpg" alt="demo-headphoto">
					</div>
					<!-- End Header Photo -->

					<!-- Start Social Connection -->
					<div class="mes-content item-pfconnect" data-show="hmove"
						data-start="-100" data-showdura="400">
						<div class="social-badges">
							<i class="fa fa-facebook-square"></i>
							<i class="fa fa-google-plus-square"></i>
							<i class="fa fa-twitter-square"></i>
						</div>
					</div>
					<!-- End Social Connection -->
					<img  src="MetroStyleFiles//image/datalakepure.jpg" alt="demo-headphoto">
					<!-- Start Info -->
					<div  data-show="fadeInDown">
						<img  src="MetroStyleFiles//image/sitemaintenance_robot_animation.gif" alt="demo-headphoto">a
						<img  src="MetroStyleFiles//image/datalakepure.jpg" alt="demo-headphoto">
					</div>

				</div>
				<img  src="MetroStyleFiles//image/sitemaintenance_robot_animation.gif" alt="demo-headphoto">
			</div>
		</div>
		<!-- End Content Holder -->
	</div>
	
	
	
	
<!--  <div id="chart6" >
		<script>
			var chart = c3.generate({
			    data: {
			        columns: [
			            ['data1', 30, 200, 100, 400, 150, 250, 50, 100, 250]
			        ]
			    },
			    axis: {
			        x: {
			            type: 'category',
			            categories: ['cat1', 'cat2', 'cat3', 'cat4', 'cat5', 'cat6', 'cat7', 'cat8', 'cat9']
			        }
			    },
			    bindto : '#chart6'
			});
			$("#chart6").hide();
		</script>
</div> -->
<!-- END MESSAGE STATION -->

	<div id="chart3" style="display:none"></div>
	<div id="chart2" style="display:none"></div>
<div class="panel-footer"><span>©</span> 2016 Hewlett-Packard Enterprise Development Company, L.P.</div>
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
</body></html>