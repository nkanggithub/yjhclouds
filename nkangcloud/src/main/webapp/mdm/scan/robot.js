var userId = 0; //用户ID
function onPageInit() { //页面初始化
		userId = 114529; //随机生成一个用户ID
		document.getElementById('chatContentFootDiv').style.height = document.getElementById('inputDiv').offsetHeight + "px";
	}
//键盘监听
var oInputText = document.getElementById('inputText');
oInputText.onkeydown = function (ev){
	var oEvent = ev || event;
	if (oEvent.keyCode == 13) { //按下ENTER发送消息
		sendMessage();
	}
}

//添加表情
var oBiaoQing = document.getElementById('biaoqing');
var oImg = oBiaoQing.getElementsByTagName('img');

for (var i = 0; i < oImg.length; i++) {
	oImg[i].onclick = function (){
		oInputText.value = this.alt;
	}
};

//发出一个消息
function robot(){  //机器人表情
	var oChatContent = document.getElementById('chatContent')
	var oSpan = oChatContent.getElementsByTagName('span');
	for (var i = 0; i < oSpan.length; i++) {
		var oText = oSpan[i].innerHTML.toLowerCase();  //循环搜索li,并使用toLowerCase()转换为小写
		if (oText.search('mg') != -1) {  //如果 oText 中的值有 bq
			var value = oSpan[i].innerHTML.replace(/[^0-9]/ig,"");  //获取当前li中的数字
			if(value <= 123){
				liContent = oSpan[i].innerHTML.replace('mg' + value, '<img src="http://www.migong.org/wp-content/themes/ming/images/robotSmiles/' + value + '.gif">');
				// alert(liContent)
				oSpan[i].innerHTML = liContent
			} else{};
		} else{};
	};
}	//机器人表情 End

function sendMessage() {
	var text = document.getElementById('inputText').value;
	if (text.length > 0) {
		//添加信息
		document.getElementById('chatContent').innerHTML += '<li class="me"><span class="glyphicon glyphicon-user meImg" aria-hidden="true"></span><span class="meContent">' + text + '</span></li>';
		//机器人表情
		robot()
		//清空输入框
		document.getElementById('inputText').value = "";
		//移动到底端
		scrollBy(0, document.body.scrollHeight);
		sendMessageRequest(text);
	}
}

//接收到信息
function setReceivedMessage(message) {
	if (message.length > 0) {
		//添加信息
		document.getElementById('chatContent').innerHTML += '<li class="robot"><span class="glyphicon glyphicon-baby-formula robotImg" aria-hidden="true"></span><span class="robotContent">' + message + '</span></li>';
		//移动到底端
		scrollBy(0, document.body.scrollHeight);
		//机器人表情
		robot()
	}
}


//发送GET请求给Server

function sendMessageRequest(message) {
	var xmlhttp;
	if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else { // code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			setReceivedMessage(xmlhttp.responseText);
		} else {}
	}
	xmlhttp.open("GET", "robotServer.php?message=" + message + "&userId=" + userId, true);
	xmlhttp.send();

}