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
			data : {},
	        timeout: 2000, 
	        success: function(data){
	        	$('#text').text(data);
	        }
	  	});
  	</script>

    <style>body {
        padding: 0;
        margin: 0;
        background: #fff
    }</style>
    
    
</head>
<body style="padding:0px;margin:0px;">
<button onclick="animateGauges()">开始测试</button>
<button onclick="stopGaugesAnimation()">停止测试</button>
<hr>
<canvas data-type="radial-gauge"
        data-value="-20"
        data-width="400"
        data-height="400"
        data-bar-width="10"
        data-bar-shadow="5"
        data-color-bar-progress="rgba(50,200,50,.75)"
></canvas>
<script>
if (!Array.prototype.forEach) {
    Array.prototype.forEach = function(cb) {
        var i = 0, s = this.length;
        for (; i < s; i++) {
            cb && cb(this[i], i, this);
        }
    }
}

document.fonts && document.fonts.forEach(function(font) {
    font.loaded.then(function() {
        if (font.family.match(/Led/)) {
            document.gauges.forEach(function(gauge) {
                gauge.update();
            });
        }
    });
});

var timers = [];

function animateGauges() {
    document.gauges.forEach(function(gauge) {
        timers.push(setInterval(function() {
            var min = gauge.options.minValue - 20;
            var max = gauge.options.maxValue + 20;

            gauge.value = min + Math.random() * (max - min);
        }, gauge.animation.duration + 50));
    });
}

function stopGaugesAnimation() {
    timers.forEach(function(timer) {
        clearInterval(timer);
    });
}
</script>






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
