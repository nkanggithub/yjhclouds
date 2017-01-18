<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>HPE - Signature</title>
  <meta name="description" content="Signature Pad - HTML5 canvas based smooth signature drawing using variable width spline interpolation.">
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
  <link rel="stylesheet" type="text/css" href="../MetroStyleFiles/sweetalert.css"/>
<link rel="stylesheet" type="text/css" href="../nkang/css_athena/profile.css"/>
  <script type="text/javascript" src="../Jsp/JS/jquery-1.8.0.js"></script>
<style>
.icon {
    display: inline-block;
    width: 30px; height: 30px;
    overflow: hidden;
    -overflow: hidden;
}
.icon > img.exit {
    position: relative;
    left: -30px;
    border-right: 30px solid transparent;
    -webkit-filter: drop-shadow(30px 0 #fff);
    filter: drop-shadow(20px 0);   
}
</style>
</head>
<body style="padding:0px;margin:0px;">
<a href="profile.jsp?UID=<%=session.getAttribute("UID")%>">
	<i class="icon" style="position:absolute;top:20px;left:20px;"><img class="exit" src="../MetroStyleFiles//EXIT1.png" style="width: 30px; height: 30px;" /></i>
</a>	
<img style="position:absolute;top:8px;right:20px;" src="" alt="Logo" class="HpLogo">
<div style="width:100%;height:4px;position:absolute;top:70px;"  class="clientTheme"></div>
<div style="width:80%;position:absolute;top:103px;left:10%;font-size: 21px;padding:6px 0;color: #444444;border-bottom:1px solid #ddd;">智能语音</div>
<input id="uid" type="hidden" value="<%=session.getAttribute("UID")%>" />											
<div style="margin-top:150px;width:80%;margin-left:10%;text-align:center;">
<form id="labnol" method="get" action="https://www.bing.com/search">
           <input style="width:100%;line-height:30px;" type="text" name="q" id="transcript" placeholder="Speak" />
       </form>
           <img id="speak" onclick="startDictation()"  alt="" src="../MetroStyleFiles/Microphone.png"/>
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
     			$('.clientTheme').css('background-color',jsons.clientThemeColor);
     			$('.icon > img.exit ').css('-webkit-filter','drop-shadow(30px 0 '+jsons.clientThemeColor+')');
    			
     		}
     	});
                     function startDictation() {
                    	 $('#speak').attr('src','../MetroStyleFiles/Microphone_pressed.png');
                         if (window.hasOwnProperty('webkitSpeechRecognition')) 
                           var recognition = new webkitSpeechRecognition();
                           recognition.continuous = false;
                           recognition.interimResults = false;
                           recognition.lang = "cmn-Hans-CN";
                           recognition.start();
                           recognition.onresult = function(e) {
                        	 $('#speak').attr('src','../MetroStyleFiles/Microphone.png');
                             document.getElementById('transcript').value = e.results[0][0].transcript;
                             recognition.stop();
                             document.getElementById('labnol').submit();
                           };
                           recognition.onerror = function(e) {
                             recognition.stop();
                           }
                  }
         </script>

</body>


</html>
