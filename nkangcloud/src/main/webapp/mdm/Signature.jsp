<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>HPE - Signature</title>
  <meta name="description" content="Signature Pad - HTML5 canvas based smooth signature drawing using variable width spline interpolation.">

  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">

<script src="../Jsp/JS/modernizr.js"></script>
<style>
#signature canvas{
	border:3px #56B39D solid;
}
</style>
</head>
<body style="padding:0px;margin:0px;">
<a href="profile.jsp">
	<img src="../MetroStyleFiles//EXIT1.png" style="width: 30px; height: 30px;position:absolute;top:20px;left:20px;" />
</a>	
<img style="position:absolute;top:10px;right:20px;width:130px;height:auto" src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=015900000053FQo&amp;oid=00D90000000pkXM&amp;lastMod=1438220916000" alt="HP Logo" class="HpLogo">
<div style="width:100%;height:4px;background:#56B39D;position:absolute;top:70px;"></div>
<div style="width:80%;position:absolute;top:103px;left:10%;font-size: 21px;padding:6px 0;color: #444444;border-bottom:1px solid #ddd;">电子签名</div>
						
											
<div id="content">		<br /><br /><br /><br /><br /><br />
	<div id="signatureparent">
		<div id="signature"></div></div>

	<div id="tools"></div>
	
</div>

<script src="../Jsp/JS/jquery-1.8.0.js"></script>
<script type="text/javascript">
	jQuery.noConflict()
</script>
<script>

(function($) {
	var topics = {};
	$.publish = function(topic, args) {
	    if (topics[topic]) {
	        var currentTopic = topics[topic],
	        args = args || {};
	
	        for (var i = 0, j = currentTopic.length; i < j; i++) {
	            currentTopic[i].call($, args);
	        }
	    }
	};
	$.subscribe = function(topic, callback) {
	    if (!topics[topic]) {
	        topics[topic] = [];
	    }
	    topics[topic].push(callback);
	    return {
	        "topic": topic,
	        "callback": callback
	    };
	};
	$.unsubscribe = function(handle) {
	    var topic = handle.topic;
	    if (topics[topic]) {
	        var currentTopic = topics[topic];
	
	        for (var i = 0, j = currentTopic.length; i < j; i++) {
	            if (currentTopic[i] === handle.callback) {
	                currentTopic.splice(i, 1);
	            }
	        }
	    }
	};
})(jQuery);

</script>
<script src="../Jsp/JS/jSignature.min.noconflict.js"></script>
<script>
(function($){

$(document).ready(function() {
	// This is the part where jSignature is initialized.
	var $sigdiv = $("#signature").jSignature({'UndoButton':true})
	// All the code below is just code driving the demo. 
	var $tools = $('#tools')

	$('<input type="button" name="svg" value="保存签名"  style="float:right;margin-right:10%;color:#fff;background-color:#56B39D;border:none;padding:10px;font-size:18px;margin-top:10px;">').bind('click', function(e){
		if (e.target.value !== ''){
			//var data = $sigdiv.jSignature('getData', e.target.value)
			var data = $sigdiv.jSignature('getData', 'svg')
			if($.isArray(data) && data.length === 2){
				alert(data[1]);
				jQuery.ajax({
					type : "GET",
					url : "../userProfile/setRecognition",
					data : {
						UID : "11111111",
						svg : "2222222"
					},
					cache : false,
					success : function(data) {
						var jsons = eval('(' + data + ')');
						$("#levelcalc").text(jsons.levelcalc);
						$("#nolevelcalc").text(jsons.nolevelcalc);
						swal("Calculate successfully!", "Congratulations!", "success"); 
					}
				});
			}
		}
	}).appendTo($tools)
	
	if (Modernizr.touch){
		$('#scrollgrabber').height($('#content').height())		
	}
	
})

})(jQuery)
</script>

</body>


</html>
