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
<div id="content">
	<div id="signatureparent">
		<div>jSignature inherits colors from parent element. Text = Pen color. Background = Background. (This works even when Flash-based Canvas emulation is used.)</div>
		<div id="signature"></div></div>
	<div id="tools"></div>
	<div><p>Display Area:</p><div id="displayarea"></div></div>
</div>
<div id="scrollgrabber"></div>
</div>
<script src="../Jsp/JS/jquery-1.8.0.js"></script>
<script type="text/javascript">
jQuery.noConflict()
</script>
<script>
/*  @preserve
jQuery pub/sub plugin by Peter Higgins (dante@dojotoolkit.org)
Loosely based on Dojo publish/subscribe API, limited in scope. Rewritten blindly.
Original is (c) Dojo Foundation 2004-2010. Released under either AFL or new BSD, see:
http://dojofoundation.org/license for more information.
*/
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
	, $tools = $('#tools')
	, $extraarea = $('#displayarea')
	, pubsubprefix = 'jSignature.demo.'
	
	var export_plugins = $sigdiv.jSignature('listPlugins','export')
	, chops = ['<span><b>Extract signature data as: </b></span><select>','<option value="">(select export format)</option>']
	, name
	for(var i in export_plugins){
		if (export_plugins.hasOwnProperty(i)){
			name = export_plugins[i]
			chops.push('<option value="' + name + '">' + name + '</option>')
		}
	}
	chops.push('</select><span><b> or: </b></span>')
	
	$(chops.join('')).bind('change', function(e){
		if (e.target.value !== ''){
			var data = $sigdiv.jSignature('getData', e.target.value)
			$.publish(pubsubprefix + 'formatchanged')
			if (typeof data === 'string'){
				$('textarea', $tools).val(data)
			} else if($.isArray(data) && data.length === 2){
				$('textarea', $tools).val(data.join(','))
				$.publish(pubsubprefix + data[0], data);
			} else {
				try {
					$('textarea', $tools).val(JSON.stringify(data))
				} catch (ex) {
					$('textarea', $tools).val('Not sure how to stringify this, likely binary, format.')
				}
			}
		}
	}).appendTo($tools)

	
	$('<input type="button" value="Reset">').bind('click', function(e){
		$sigdiv.jSignature('reset')
	}).appendTo($tools)
	
	$('<div><textarea style="width:100%;height:7em;"></textarea></div>').appendTo($tools)
	
	$.subscribe(pubsubprefix + 'formatchanged', function(){
		$extraarea.html('')
	})

	$.subscribe(pubsubprefix + 'image/svg+xml', function(data) {

		try{
			var i = new Image()
			i.src = 'data:' + data[0] + ';base64,' + btoa( data[1] )
			$(i).appendTo($extraarea)
		} catch (ex) {

		}
		
		var message = [
			"If you don't see an image immediately above, it means your browser is unable to display in-line (data-url-formatted) SVG."
			, "This is NOT an issue with jSignature, as we can export proper SVG document regardless of browser's ability to display it."
			, "Try this page in a modern browser to see the SVG on the page, or export data as plain SVG, save to disk as text file and view in any SVG-capabale viewer."
           ]
		$( "<div>" + message.join("<br/>") + "</div>" ).appendTo( $extraarea )
	});

	$.subscribe(pubsubprefix + 'image/svg+xml;base64', function(data) {
		var i = new Image()
		i.src = 'data:' + data[0] + ',' + data[1]
		$(i).appendTo($extraarea)
		
		var message = [
			"If you don't see an image immediately above, it means your browser is unable to display in-line (data-url-formatted) SVG."
			, "This is NOT an issue with jSignature, as we can export proper SVG document regardless of browser's ability to display it."
			, "Try this page in a modern browser to see the SVG on the page, or export data as plain SVG, save to disk as text file and view in any SVG-capabale viewer."
           ]
		$( "<div>" + message.join("<br/>") + "</div>" ).appendTo( $extraarea )
	});
	
	$.subscribe(pubsubprefix + 'image/png;base64', function(data) {
		var i = new Image()
		i.src = 'data:' + data[0] + ',' + data[1]
		$('<span><b>As you can see, one of the problems of "image" extraction (besides not working on some old Androids, elsewhere) is that it extracts A LOT OF DATA and includes all the decoration that is not part of the signature.</b></span>').appendTo($extraarea)
		$(i).appendTo($extraarea)
	});
	
	$.subscribe(pubsubprefix + 'image/jsignature;base30', function(data) {
		$('<span><b>This is a vector format not natively render-able by browsers. Format is a compressed "movement coordinates arrays" structure tuned for use server-side. The bonus of this format is its tiny storage footprint and ease of deriving rendering instructions in programmatic, iterative manner.</b></span>').appendTo($extraarea)
	});

	if (Modernizr.touch){
		$('#scrollgrabber').height($('#content').height())		
	}
	
})

})(jQuery)
</script>





<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="1011" height="246"><path fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" d="M 89 11 c 0.14 -0.09 5.2 -3.78 8 -5 c 4.69 -2.04 10.08 -4.32 15 -5 c 6.53 -0.91 14.52 -0.81 21 0 c 3.61 0.45 7.52 2.51 11 4 c 1.07 0.46 2.2 1.2 3 2 c 1.1 1.1 2.82 2.67 3 4 c 0.39 2.94 -0.01 7.86 -1 11 c -0.85 2.68 -3.05 5.64 -5 8 c -2.63 3.19 -5.74 6.26 -9 9 c -5.12 4.3 -10.34 8.46 -16 12 c -7.72 4.83 -15.59 9.41 -24 13 c -19.12 8.16 -38.2 15.06 -58 22 c -6.27 2.2 -12.46 3.55 -19 5 c -5.74 1.28 -17.49 2.87 -17 3 c 1.26 0.33 29.83 1.54 44 4 c 8.42 1.46 17.31 4.68 25 8 c 4.22 1.83 8.63 5.01 12 8 c 2.33 2.07 4.34 5.23 6 8 c 1.27 2.11 2.51 4.63 3 7 c 1.11 5.33 1.78 11.24 2 17 c 0.46 12.12 -1.15 25.17 0 36 c 0.39 3.63 2.77 7.99 5 11 c 3.13 4.23 7.61 8.64 12 12 c 6.75 5.16 14.28 10.07 22 14 c 11.27 5.73 23.12 11.5 35 15 c 13.65 4.03 28.38 6.17 43 8 c 15.08 1.88 29.66 2.67 45 3 c 16.33 0.35 31.95 0.37 48 -1 c 15.55 -1.32 31.75 -2.57 46 -7 c 19.07 -5.92 38.97 -15.39 57 -25 c 11.65 -6.21 23.2 -14.57 33 -23 c 6.39 -5.5 12.55 -12.98 17 -20 c 3.87 -6.11 7.03 -14.02 9 -21 c 1.56 -5.53 2 -12.11 2 -18 c 0 -5.89 -0.44 -12.47 -2 -18 c -1.97 -6.98 -5.32 -14.69 -9 -21 c -3.1 -5.31 -7.57 -10.43 -12 -15 c -5.88 -6.07 -12.57 -12.18 -19 -17 c -2.48 -1.86 -5.9 -3.05 -9 -4 c -5.49 -1.69 -12.59 -3.79 -17 -4 c -1.26 -0.06 -4.25 2.34 -4 3 c 0.43 1.13 4.5 3.58 7 5 c 7.55 4.28 15.16 9.06 23 12 c 7.77 2.91 16.39 4.34 25 6 c 10.85 2.09 20.93 3.56 32 5 c 12.72 1.66 24.38 3.47 37 4 c 27.81 1.17 54.99 2.15 82 1 c 11.7 -0.5 23.44 -3.3 35 -6 c 8.55 -1.99 16.68 -5 25 -8 c 3.83 -1.38 8.18 -2.84 11 -5 c 2.34 -1.79 4.63 -5.27 6 -8 c 1.13 -2.25 2 -5.54 2 -8 c 0 -2.75 -0.66 -6.72 -2 -9 c -1.65 -2.8 -5.08 -6.19 -8 -8 c -3.59 -2.22 -8.53 -3.66 -13 -5 c -5.6 -1.68 -11.29 -3.18 -17 -4 c -5.9 -0.84 -11.93 -1 -18 -1 c -5.4 0 -11.25 -0.19 -16 1 c -3.97 0.99 -8.57 3.55 -12 6 c -3.25 2.32 -6.74 5.77 -9 9 c -2.15 3.07 -4.28 7.42 -5 11 c -0.83 4.15 -0.73 9.63 0 14 c 0.87 5.23 2.53 11.32 5 16 c 3.6 6.82 8.65 14.22 14 20 c 6.68 7.22 14.73 14.71 23 20 c 11.52 7.37 24.8 13.28 38 19 c 14.94 6.48 29.54 12.36 45 17 c 18.17 5.45 36.29 9.83 55 13 c 18.98 3.22 37.41 4.54 57 6 c 17.1 1.28 33.78 3.25 50 2 c 17.8 -1.37 35.8 -5.89 54 -10 c 13.43 -3.03 26.02 -6.67 39 -11 c 9.34 -3.11 18.58 -6.79 27 -11 c 3.93 -1.97 7.69 -5.06 11 -8 c 2.58 -2.3 5.01 -5.22 7 -8 c 1.25 -1.76 2.14 -3.93 3 -6 c 0.81 -1.95 2 -4.28 2 -6 l -2 -5"/></svg>

</body>


</html>
