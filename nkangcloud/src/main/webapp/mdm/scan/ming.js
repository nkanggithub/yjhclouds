$(document).ready(function(){
	//顶部导航背景
    $(".nav a").each(function(){
        $this = $(this);
        if($this[0].href==String(window.location)){
            $this.addClass("hover");
        }
    });
    //tag 随机颜色
    var tags = $(".tagsColor");
	tags.each(function(){
	var x = 5;
	var y = 0;
	var rand = parseInt(Math.random() * (x - y + 1) + y);
	$(this).addClass("tags"+rand);
	});
    //设置自适应图片 width:100%;height:auto;
    $(".wp-post-image,.alignnone").attr("width","100%");
    $(".wp-post-image,.alignnone").attr("height","auto");
});

//Back To Top
$(function(){
    //当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失
    $(function () {
        $(window).scroll(function(){
            if ($(window).scrollTop()>100){
                $("#BackToTop").fadeIn(1500);
            }
            else
            {
                $("#BackToTop").fadeOut(1500);
            }
        });

        //当点击跳转链接后，回到页面顶部位置

        $("#BackToTop").click(function(){
            $('body,html').animate({scrollTop:0},1000);
            return false;
        });
    });
});

//占位符特效
Placeholdem(document.querySelectorAll('[placeholder]'));

var fadeElems = document.body.querySelectorAll('.fade'),
fadeElemsLength = fadeElems.length,
i = 0,
interval = 50;

function incFade() {
    if (i < fadeElemsLength) {
        fadeElems[i].className += ' fade-load';
        i++;
        setTimeout(incFade, interval);
    }
}

setTimeout(incFade, interval);

//搜索框显示关闭

$( ".search-icon" ).click(function() {
    $(this).toggleClass("open");
    var oDisplay = $("#search").css("maxWidth");
    if(oDisplay == '0px'){
        $("#search").css({maxWidth: "100%",paddingLeft:"16px",paddingRight:"16px",borderWidth:"1px"});
    }else{
        $("#search").css({maxWidth: "0px",paddingLeft:"0px",paddingRight:"0px",borderWidth:"0px"});
    }
});

//提示tooltip

$(function () { $("[data-toggle='tooltip']").tooltip(); });

// 可拖拽 iPhone Demo

var oDiv = document.getElementById('frame');
var disX = 0;
var disY = 0;
oDiv.onmousedown = function (ev){
    var oEvent = ev || event;
    disX = oEvent.clientX - oDiv.offsetLeft;  //获取水平距离
    disY = oEvent.clientY - oDiv.offsetTop;  //获取垂直距离
    oDiv.style.cursor = "grabbing";  //修改鼠标指针Style事件
    oDiv.style.cursor = "-webkit-grabbing";  //兼容，修改鼠标指针Style事件
    document.onmousemove = function (ev){  //将鼠标事件对象设置为 document 防止移动速度过快而产生的 bug
        var oEvent = ev || event;
        var l = oEvent.clientX - disX;
        var t = oEvent.clientY - disY;
        //不让 div 移出可视窗口
        if (l < 0) {
            l = 0;  //不让 div 移出左侧可视窗口
        }else if (l > document.documentElement.clientWidth - oDiv.offsetWidth) {
            l = document.documentElement.clientWidth - oDiv.offsetWidth;  //不让 div 移出右侧可视窗口
        };
        if (t < 0) {
            t = 0;  //不让 div 移出顶部可视窗口
        }else if (t > document.documentElement.clientHeight - oDiv.offsetHeight) {
            t = document.documentElement.clientHeight - oDiv.offsetHeight;  //不让 div 移出底部可视窗口
        };
        //不让 div 移出可视窗口 End
        oDiv.style.left = l + 'px';  //当鼠标移动时改变div水平位置
        oDiv.style.top = t + 'px';  //当鼠标移动时改变div垂直位置
    }
    document.onmouseup = function (){  //当 onmouseup 后移除 onmousemove 和 自己本身 onmouseup
        document.onmousemove = null;
        document.onmouseup = null;
        oDiv.style.cursor = "grab";  //修改鼠标指针Style事件
        oDiv.style.cursor = "-webkit-grab";  //兼容，修改鼠标指针Style事件
    }
}

// 点击Home键刷新框架内URL(phoneIframe是iframe的name)
var oPhoneIframe = document.getElementById("phoneIframe").src;
var oRefreshBtn = document.getElementById("refreshBtn");
oRefreshBtn.onclick = function () {
    phoneIframe.location.href="" + oPhoneIframe + "";
}