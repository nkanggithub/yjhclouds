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
<style>

.DetectedPerson{
	width:100%;
	padding:10px 0px;
	border-bottom:1px dashed #999;
}

.myfacevalue{
	float:left;
	width:55%;
	text-align:right;
}
.myfacevalue canvas{
	margin-top:50px;
	float:right;
}
.myfacevalueattribute{
	float:right;
	width:45%;
}
.clear{
	clear:both;
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
<input id="uid" type="hidden" value="<%=uid%>" />											
<div id="text" style="margin-top:150px;width:80%;margin-left:10%;text-align:center;">

</div>

<script>
   $(document).ready(function () {
  	$.ajax({  
	        cache : false,  
	        type : "GET",
			url : "../uploadPicture", 
			data : {openid:$('#uid').val()},
	        timeout: 2000, 
	        success: function(data){
	        	data = '{"results":' + data + '}';
				var jsons = eval('(' + data + ')');
				$("#text").html("");
				var div="";
				if (jsons.results.length > 0) {
					for(var i=0;i<jsons.results.length;i++){
						var temp =  jsons.results[i];
						div+='<div class ="DetectedPerson">'
							+'	<div  class="myfacevalue">'
							+'	<canvas '
							+'			class="myfacevalue" onclick="javascript:alert(\'颜值\');"'
							+'			data-type="radial-gauge"'
							+'	        data-value="'+temp.levelNum+'"'
							+'	        data-width="350"'
							+'	        data-height="350"'
							+'	        data-bar-width="10"'
							+'	        data-bar-shadow="5"'
							+'	        data-color-bar-progress="rgba(50,200,50,.75)"'
							+'	></canvas>'
							+'	</div>'
							+'	<div class="myfacevalueattribute">'
							+'		<table>'
							+'			<tr><td>Smile</td><td>'+temp.smile+'</td></tr>'
							+'			<tr><td>Age</td><td>'+temp.age+'</td></tr>'
							+'			<tr><td>Glasses</td><td>'+temp.glasses+'</td></tr>'
							+'			<tr><td>Gender</td><td>'+temp.gender+'</td></tr>'
							+'			<tr><td>MouStache</td><td>'+temp.moustache+'</td></tr>'
							+'			<tr><td>Beard</td><td>'+temp.beard+'</td></tr>'
							+'			<tr><td>Anger</td><td>'+temp.anger+'</td></tr>'
							+'			<tr><td>Contempt</td><td>'+temp.contempt+'</td></tr>'
							+'			<tr><td>Disgust</td><td>'+temp.disgust+'</td></tr>'
							+'			<tr><td>Fear</td><td>'+temp.fear+'</td></tr>'
							+'			<tr><td>Happiness</td><td>'+temp.happiness+'</td></tr>'
							+'			<tr><td>Sadness</td><td>'+temp.sadness+'</td></tr>'
							+'			<tr><td>Surprise</td><td>'+temp.surprise+'</td></tr>'
							+'		</table>'
							+'	</div>'
							+'	<div class="clear"></div>'
							+'</div>';
					}
				}else{
					div="you don't have one photo..";
				}
				$("#text").html(div);
	        }
	  	});
   });
  	</script>
</body>
</html>
