<%@ page language="java" pageEncoding="UTF-8"%>
<%
String uid = request.getParameter("UID");
%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>HPE - Signature</title>
  <meta name="description" content="Signature Pad - HTML5 canvas based smooth signature drawing using variable width spline interpolation.">
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
  <link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
  <script type="text/javascript" src="../Jsp/JS/jquery-1.8.0.js"></script>
  <script type="text/javascript" src="../Jsp/JS/gauge.min.js"></script>
   <script>
   $(window).load(function() {
  	$.ajax({  
	        cache : false,  
	        type : "GET",
			url : "../uploadPicture", 
			data : {openid:$('#uid').val()},
	        timeout: 2000, 
	        success: function(data){
	        	$('#text').text(data);
	        }
	  	});
   });
  	</script>
<style>

.DetectedPerson{
	position:relative;
	top:40%;
	left:1%;
	height:300px;
	width:100%;
}
.myfacevalue{
	position:relative;
	top:32px;
}
.myfacevalueattribute{
	position:relative;
	top:-73%;
	left:58%;
	height:280px;
	width:100%;
}

</style>

    
    
</head>
<body style="padding:0px;margin:0px;">





<a href="profile.jsp?UID=<%=uid%>">
	<img src="../MetroStyleFiles//EXIT1.png" style="width: 30px; height: 30px;position:absolute;top:20px;left:20px;" />
</a>	
<img style="position:absolute;top:10px;right:20px;width:130px;height:auto" src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=015900000053FQo&amp;oid=00D90000000pkXM&amp;lastMod=1438220916000" alt="HP Logo" class="HpLogo">
<div style="width:100%;height:4px;background:#56B39D;position:absolute;top:70px;"></div>
<div style="width:80%;position:absolute;top:103px;left:10%;font-size: 21px;padding:6px 0;color: #444444;border-bottom:1px solid #ddd;">看我颜值如何爆表</div>
<input id="uid" type="hidden" value="<%=session.getAttribute("UID")%>" />											
<div id="text" style="margin-top:150px;width:80%;margin-left:10%;text-align:center;">

</div>

<div class ="DetectedPerson">
	<canvas 
			class="myfacevalue" onclick="javascript:alert('okk');"
			data-type="radial-gauge"
	        data-value="80"
	        data-width="350"
	        data-height="350"
	        data-bar-width="10"
	        data-bar-shadow="5"
	        data-color-bar-progress="rgba(50,200,50,.75)"
	></canvas>
	<div class="myfacevalueattribute">
		<table>
			<tr><td>Smile</td><td>0.515</td></tr>
			<tr><td>Age</td><td>21.8</td></tr>
			<tr><td>Glasses</td><td>NoGlasses</td></tr>
			<tr><td>Gender</td><td>Female</td></tr>
			<tr><td>MouStache</td><td>0</td></tr>
			<tr><td>Beard</td><td>0</td></tr>
			<tr><td>Anger</td><td>0.001</td></tr>
			<tr><td>Contempt</td><td>0.0003</td></tr>
			<tr><td>Disgust</td><td>0.001</td></tr>
			<tr><td>Fear</td><td>0.001</td></tr>
			<tr><td>Happiness</td><td>0.001</td></tr>
			<tr><td>Sadness</td><td>0.3</td></tr>
			<tr><td>Surprise</td><td>0.0004</td></tr>
		</table>

	</div>

</div>
<div class ="DetectedPerson">
	<canvas 
			class="myfacevalue" onclick="javascript:alert('okk');"
			data-type="radial-gauge"
	        data-value="80"
	        data-width="350"
	        data-height="350"
	        data-bar-width="10"
	        data-bar-shadow="5"
	        data-color-bar-progress="rgba(50,200,50,.75)"
	></canvas>
	<div class="myfacevalueattribute">
		<table>
			<tr><td>Smile</td><td>0.515</td></tr>
			<tr><td>Age</td><td>21.8</td></tr>
			<tr><td>Glasses</td><td>NoGlasses</td></tr>
			<tr><td>Gender</td><td>Female</td></tr>
			<tr><td>MouStache</td><td>0</td></tr>
			<tr><td>Beard</td><td>0</td></tr>
			<tr><td>Anger</td><td>0.001</td></tr>
			<tr><td>Contempt</td><td>0.0003</td></tr>
			<tr><td>Disgust</td><td>0.001</td></tr>
			<tr><td>Fear</td><td>0.001</td></tr>
			<tr><td>Happiness</td><td>0.001</td></tr>
			<tr><td>Sadness</td><td>0.3</td></tr>
			<tr><td>Surprise</td><td>0.0004</td></tr>
		</table>

	</div>

</div>

<br /><br />
<!-- <button onclick="animateGauges()">开始测试</button>
<button onclick="stopGaugesAnimation()">停止测试</button> -->

</body>
</html>
