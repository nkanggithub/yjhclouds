<!Doctype html>
<html>
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<link rel="stylesheet" href="../nkang/jquery.mobile.min.css" />
<script type="text/javascript" src="../nkang/jquery-1.8.0.js"></script>
<script type="text/javascript" src="../nkang/jquery.mobile.min.js"></script>
<!--	<link href='css/horsey.css' rel='stylesheet' type='text/css' />
	<link href='css/example.css' rel='stylesheet' type='text/css' />-->
<style>
*{margin:0;padding:0;}
.singleQuote
{
height:90px;
width:100%;
border-bottom:1px solid gray;
}
.firstLayer{
line-height:40px;
width:100%;
height:35%;
font-size:18px;
font-weight:bold;
}
.secondLayer
{
height:60%;
font-size:14px;
margin-top: 2px;
}
.quoteTitle
{
padding-left:4%;
width:45%;
height:100%;
float:left;
}
.quotePrice
{

width:27%;
margin-left:1%;
height:100%;
float:left;
}
.shape{
margin-top:2%;

padding:1%;
margin-left:2%;
float:left;
}
.quoteInventory
{
margin-left: 5%;
    width: 24%;
    border: 1px solid #2BD2CA;
    color: #2BD2CA;
}
.soldOutOfPay
{
width: 30%;
    border: 1px solid #CBCCCE;
    color: #CBCCCE;
}
.onDelivery
{
    width: 20%;
    border: 1px solid #FF896F;
    color: #FF896F;
}
.shape p
{
text-align:center;}
.leftPanel
{
width:73%;
height:100%;
float:left;
}
.rightPanel
{
margin-top:1%;
width:27%;
height:99%;
float:left;
}
.rightPanel p
{
padding-right:5%;
width:95%;
font-size:13px;
line-height:22px;}
.HpLogo {
    position: relative;
    top: 8px;
    left: 1%;
    width: 150px;
    height: 58px;
}
.searchBox
{    border: none;
    outline: none;
    background-color: #ecf0f1;
    padding-top:10px;
	padding-bottom:10px;
	padding-left:3%;
    color: #14204f;
    border: 0;
    margin: 5px 0;
    display: block;
    width: 97%;}
	.tag
	{    width: 16%;
    margin-left: 1%;
    margin-right: 5%;
    float: left;
    height: 25px;
    margin-top: 5px;
    text-align: center;
    line-height: 26px;
    font-size: 14px;
    font-weight: normal;
    color: black;}
	.tagStyle{
	 border: 1px solid #2BD2CA;
    background: #2BD2CA;
	}
</style>
</head>
<body>
<div style="padding:10px;border-bottom:2px solid #0067B6"> 
					<img src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&amp;oid=00D90000000pkXM" alt="Logo" class="HpLogo" style="display:inline !important;height:35px !important;width:auto !important;float:none;padding:0px;vertical-align:bottom;padding-bottom:10px;">
					<span class="clientSubName" style="font-size:12px;padding-left:7px;color:#333;">市场如水 企业如舟</span>
					<h2 style="color:#333;font-size:18px;padding:0px;padding-left:5px;font-weight:bold;margin-top:5px;font-family:HP Simplified, Arial, Sans-Serif !important;" class="clientName">永佳和塑胶有限公司</h2>
				</div>
<!--<input class="searchBox" id='hy' />-->
<div  style="position: absolute; top: 120px;" data-role="page" style="padding-top:15px" data-theme="c">
 <ul id="QuoteList" data-role="listview" data-autodividers="false" data-filter="true" data-filter-placeholder="输入牌号" data-inset="true" style="margin-top:15px">
<li class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">0215A [ABS]</p><p class="tag"></p><p class="quotePrice"><span style="color:red">￥16700</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>179.86</p></div>
<div class="shape soldOutOfPay"><p>已售未下账</p><p>14.175</p></div>
<div class="shape onDelivery"><p>在途</p><p>0</p></div>
</div>
<div class="rightPanel">
<p>重庆 194.035</p>
<p>成都 0</p>
</div>
</div>
</li>
<li class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">PA-757K [ABS]</p><p class="tag tagStyle">一般级</p><p class="quotePrice"><span style="color:red">￥18200</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>148.6327</p></div>
<div class="shape soldOutOfPay"><p>已售未下账</p><p>99</p></div>
<div class="shape onDelivery"><p>在途</p><p>194</p></div>
</div>
<div class="rightPanel">
<p>重庆 18.9977</p>
<p>成都 34.635</p>
</div>
</div>
</li>
<li class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">PC/ABS-385KZ</p><p class="tag"></p><p class="quotePrice"><span style="color:green">￥25500</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>1</p></div>
<div class="shape soldOutOfPay"><p>已售未下账</p><p>0</p></div>
<div class="shape onDelivery"><p>在途</p><p>0</p></div>
</div>
<div class="rightPanel">
<p>重庆 0</p>
<p>成都 1</p>
</div>
</div>
</li>

<li class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">PA-749SK [ABS]</p><p class="tag tagStyle">押出级</p><p class="quotePrice"><span style="color:red">￥18600</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>49.6755</p></div>
<div class="shape soldOutOfPay"><p>已售未下账</p><p>19.25</p></div>
<div class="shape onDelivery"><p>在途</p><p>57</p></div>
</div>
<div class="rightPanel">
<p>重庆 9.0005</p>
<p>成都 2.925</p>
</div>
</div>
</li>
<li class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">PA-726M [ABS]</p><p class="tag tagStyle">电镀级</p><p class="quotePrice"><span style="color:black">￥18800</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>84.1005</p></div>
<div class="shape soldOutOfPay"><p>已售未下账</p><p>20.275</p></div>
<div class="shape onDelivery"><p>在途</p><p>84</p></div>
</div>
<div class="rightPanel">
<p>重庆 20.3755</p>
<p>成都 0</p>
</div>
</div>
</li>
<li class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">PC/1100</p><p class="tag"></p><p class="quotePrice"><span style="color:black">￥23800</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>7.325</p></div>
<div class="shape soldOutOfPay"><p>已售未下账</p><p>0</p></div>
<div class="shape onDelivery"><p>在途</p><p>0</p></div>
</div>
<div class="rightPanel">
<p>重庆 7.325</p>
<p>成都 0</p>
</div>
</div>
</li>
</ul>
</div>
<!--
	<script src='js/horsey.js'></script>
	<script src='js/example.js'></script>-->
</body>
</html>
