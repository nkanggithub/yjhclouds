<%@ page language="java" pageEncoding="UTF-8"%>
<%
String uid = request.getParameter("UID");
%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>HPE - Face Recognition</title>
  <meta name="description" content="Signature Pad - HTML5 canvas based smooth signature drawing using variable width spline interpolation.">
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
  <link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
  <script type="text/javascript" src="../Jsp/JS/jquery-1.8.0.js"></script>
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
table tr td:nth-child(odd) {
	text-align: left;
}
table tr td:nth-child(even) {
	text-align: left;
}
</style>

    
    
</head>
<body style="padding:0px;margin:0px;">
<a href="profile.jsp?UID=<%=uid%>">
	<img src="../MetroStyleFiles//EXIT1.png" style="width: 30px; height: 30px;position:absolute;top:20px;left:20px;" />
</a>	
<img style="position:absolute;top:10px;right:20px;width:130px;height:auto" src="" alt="Logo" class="HpLogo">
<div style="width:100%;height:4px;background:#56B39D;position:absolute;top:70px;"></div>
<div style="width:80%;position:absolute;top:103px;left:10%;font-size: 21px;padding:6px 0;color: #444444;border-bottom:1px solid #ddd;"><a id="imgCurrentpic">看我颜值如何爆表</a></div>
<input id="uid" type="hidden" value="<%=uid%>" />											
<div id="text" style="margin-top:150px;width:80%;margin-left:10%;text-align:center;">
</div>
<script>
	jQuery.ajax({
		type : "GET",
		url : "../QueryClientMeta",
		data : {},
		cache : false,
		success : function(data) {
			var jsons = eval(data);
			$('img.HpLogo').attr('src',HpLogoSrc);
			$('span.clientCopyRight').text(copyRight);
		}
	});
  	$.ajax({  
	        cache : false,  
	        type : "GET",
			url : "../uploadPicture", 
			async: false,  
			data : {openid:$('#uid').val()},
	        timeout: 2000, 
	        success: function(data){
				$("#text").html("");
				var div="";
				if (data.length > 0) {
					for(var i=0;i<data.length;i++){
						var temp =  data[i];
						$("#imgCurrentpic").attr("href",temp.currentUrl);
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
							+'			<tr><td>Anger</td><td>'+temp.anger.substr(0,9)+'</td></tr>'
							+'			<tr><td>Contempt</td><td>'+temp.contempt.substr(0,9)+'</td></tr>'
							+'			<tr><td>Disgust</td><td>'+temp.disgust.substr(0,9)+'</td></tr>'
							+'			<tr><td>Fear</td><td>'+temp.fear.substr(0,9)+'</td></tr>'
							+'			<tr><td>Happiness</td><td>'+temp.happiness.substr(0,9)+'</td></tr>'
							+'			<tr><td>Sadness</td><td>'+temp.sadness.substr(0,9)+'</td></tr>'
							+'			<tr><td>Surprise</td><td>'+temp.surprise.substr(0,9)+'</td></tr>'
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
  	</script>
  <script type="text/javascript" src="../Jsp/JS/gauge.min.js"></script>
</body>
</html>
