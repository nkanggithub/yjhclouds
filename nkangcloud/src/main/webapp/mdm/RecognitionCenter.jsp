<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>HPE - Recognition</title>
  <meta name="description" content="Signature Pad - HTML5 canvas based smooth signature drawing using variable width spline interpolation.">

  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">

<script src="../Jsp/JS/modernizr.js"></script>

</head>
<body >

<div>
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

	$('<input type="button" name="svg" value="svg">').bind('click', function(e){
		if (e.target.value !== ''){
			var data = $sigdiv.jSignature('getData', e.target.value)
			if($.isArray(data) && data.length === 2){
				alert(data[1]);
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
