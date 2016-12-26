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
   <script>
  	$.ajax({  
	        cache : false,  
	        type : "GET",
			url : "../userProfile/getWeather", 
			data : {},
	        timeout: 2000, 
	        success: function(data){
	        	$('#text').text(data);
	        }
	  	});
  	</script>
</head>
<body style="padding:0px;margin:0px;">
<a href="profile.jsp?UID=<%=session.getAttribute("UID")%>">
	<img src="../MetroStyleFiles//EXIT1.png" style="width: 30px; height: 30px;position:absolute;top:20px;left:20px;" />
</a>	
<img style="position:absolute;top:10px;right:20px;width:130px;height:auto" src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=015900000053FQo&amp;oid=00D90000000pkXM&amp;lastMod=1438220916000" alt="HP Logo" class="HpLogo">
<div style="width:100%;height:4px;background:#56B39D;position:absolute;top:70px;"></div>
<div style="width:80%;position:absolute;top:103px;left:10%;font-size: 21px;padding:6px 0;color: #444444;border-bottom:1px solid #ddd;">测颜值</div>
<input id="uid" type="hidden" value="<%=session.getAttribute("UID")%>" />											
<div id="text" style="margin-top:150px;width:80%;margin-left:10%;text-align:center;">


</div>
</body>
</html>
