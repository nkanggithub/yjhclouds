<%@ page language="java" pageEncoding="UTF-8"%>
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
  	</script>
<style>

.DetectedPerson{
	position:relative;
	top:40%;
	left:1%;
	height:270px;
}
.myfacevalue{
	position:relative;
	top:80%;
}
.myfacevalueattribute{
	position:relative;
	top:-73%;
	left:58%;
	height:260px;
}

</style>

    
    
</head>
<body style="padding:0px;margin:0px;">





<a href="profile.jsp?UID=<%=session.getAttribute("UID")%>">
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
	Smile:0.515 <br />
	Age:21.8 <br />
	Glasses: NoGlasses <br />
	Gender: Female <br />
	MouStache: 0 <br />
	Beard: 0 <br />
	Anger:0.001 <br />
	Contempt:0.0003 <br />
	Disgust: 0.001 <br />
	Fear: 0.001 <br />
	Happiness:0.3 <br />
	Neutral: 0.1 <br />
	Sadness: 0.003 <br />
	Surprise:0.0004 <br />
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
	Smile:0.515 <br />
	Age:21.8 <br />
	Glasses: NoGlasses <br />
	Gender: Female <br />
	MouStache: 0 <br />
	Beard: 0 <br />
	Anger:0.001 <br />
	Contempt:0.0003 <br />
	Disgust: 0.001 <br />
	Fear: 0.001 <br />
	Happiness:0.3 <br />
	Neutral: 0.1 <br />
	Sadness: 0.003 <br />
	Surprise:0.0004 <br />
	</div>

</div>

<br /><br />
<!-- <button onclick="animateGauges()">开始测试</button>
<button onclick="stopGaugesAnimation()">停止测试</button> -->

</body>
</html>
