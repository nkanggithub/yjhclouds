<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.nkang.kxmoment.baseobject.CongratulateHistory"%>
<%@ page import="com.nkang.kxmoment.util.RestUtils"%>
<%@ page import="com.nkang.kxmoment.util.MongoDBBasic"%>
<%@ page import="java.util.List"%>
<%	
String uid = request.getParameter("uid");
String num = request.getParameter("num");
List<CongratulateHistory> chList=MongoDBBasic.getRecognitionInfoByOpenID(uid,num);
CongratulateHistory ch=new CongratulateHistory();
if(!chList.isEmpty()){
	ch=chList.get(0);
}
String openid=MongoDBBasic.getRegisterUserByrealName(ch.getFrom());
String signature=MongoDBBasic.getUserWithSignature(openid);


%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>
  <meta charset="utf-8">
  <title>HPE - Recognition</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/style.css"/>
<script type="text/javascript" src="../Jsp/JS/jquery-1.8.0.js"></script>
<script>
$(function(){
	$("#sign").html($("#hiddenSign").html());
})
</script>
</head>
<body style="padding:0px;margin:0px;">
<div style="display:none" id="hiddenSign"><%=signature %></div>
<div id="recognitionCenter" style="position:absolute;width:100%;height:auto;"> 
<div style="height:90px;font-family: HP Simplified, Arial, Sans-Serif;border-bottom:5px solid #56B39D"><img style='position:absolute;top:20px;left:20px;width:130px;height:auto' src='' alt='Logo' class='HpLogo'></div>
<div style="position:absolute;top:120px;width:80%;left:10%;height:100px;">
<p style="float:left;width:110px;font-weight:bold;">Congratulations</p><p id="to" style="float:left;"> <%=ch.getTo() %></p><p style="float:left;">!</p></div>
<div style="position:absolute;top:160px;width:80%;left:10%;height:100px;font-size:14px;font-family: HP Simplified, Arial, Sans-Serif;">
<p style="line-height:22px;">You must have done something amazing! <span id="from"><%=ch.getFrom() %></span> has recognized you: <span id="type"><%=ch.getType() %></span>. <p>
<p style="width:100%;line-height:22px;font-size:16px;font-weight:bold;">Here’s what was said about you</p>
<p style="line-height:22px;"><span id="comments"><%=ch.getComments() %></span></p>
<p style="width:100%;line-height:22px;font-size:16px;font-weight:bold;">Your award</p>
<p style="line-height:22px;"><span id="points"><%=ch.getPoint() %></span> Points have been added to your account. Enjoy surfing the catalogue and finding something that is perfect just for you: merchandise, travel, gift cards or vouchers. <p>

</div>
</div>
<div id="sign" style="position:absolute;top:500px;right:50px;"></div>
	<div id="footer">
		<span class="clientCopyRight"><nobr></nobr></span>
	</div>
	<script>
         jQuery.ajax({
     		type : "GET",
     		url : "../QueryClientMeta",
     		data : {},
     		cache : false,
     		success : function(data) {
     			var jsons = eval(data);
     			$('img.HpLogo').attr('src',jsons.clientLogo);
     			$('span.clientCopyRight').text('©'+jsons.clientCopyRight);
     		}
     	});
         </script>
</body>


</html>
