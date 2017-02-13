<%@ page language="java" pageEncoding="UTF-8"%>
<!Doctype html>
<html>
<head>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<style>
*{margin:0;padding:0;}
#quoteList
{
position:absolute;
top:90px;
width:100%;
}
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
font-size:22px;
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
width:68%;
height:100%;
float:left;
}
.quotePrice
{

width:28%;
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
margin-left:5%;
width:24%;
border:1px solid #347F78;
background:#2BD2CA;
}
.soldOutOfPay
{
width:30%;
border:1px solid #676767;
background:#CBCCCE;
}
.onDelivery
{
width:20%;
border:1px solid #B06B5C;
background:#FF896F;
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
  height: 50px;
    margin-left: 5px !important;
}
</style>
</head>
<body>
<img style="position:absolute;top:15px;right:20px;" class="HpLogo" src="https://c.ap1.content.force.com/servlet/servlet.ImageServer?id=0159000000DkptH&oid=00D90000000pkXM" alt="Logo"><div style="width:100%;height:4px;background:#0067B6;position:absolute;top:80px;"></div>
<div id="quoteList">
<div class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">0215A/ABC</p><p class="quotePrice"><span style="color:red">￥16700</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>179.86</p></div>
<div class="shape soldOutOfPay"><p>已售未下载</p><p>14.175</p></div>
<div class="shape onDelivery"><p>在途</p><p>0</p></div>
</div>
<div class="rightPanel">
<p>重庆 194.035</p>
<p>成都 0</p>
</div>
</div>
</div>
<div class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">PA-757K/ABC/一般级</p><p class="quotePrice"><span style="color:red">￥18200</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>148.6327</p></div>
<div class="shape soldOutOfPay"><p>已售未下载</p><p>99</p></div>
<div class="shape onDelivery"><p>在途</p><p>194</p></div>
</div>
<div class="rightPanel">
<p>重庆 18.9977</p>
<p>成都 34.635</p>
</div>
</div>
</div>
<div class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">PC/ABS-385KZ</p><p class="quotePrice"><span style="color:green">￥25500</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>1</p></div>
<div class="shape soldOutOfPay"><p>已售未下载</p><p>0</p></div>
<div class="shape onDelivery"><p>在途</p><p>0</p></div>
</div>
<div class="rightPanel">
<p>重庆 0</p>
<p>成都 1</p>
</div>
</div>
</div>

<div class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">PA-749SK/ABS/押出级</p><p class="quotePrice"><span style="color:red">￥18600</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>49.6755</p></div>
<div class="shape soldOutOfPay"><p>已售未下载</p><p>19.25</p></div>
<div class="shape onDelivery"><p>在途</p><p>57</p></div>
</div>
<div class="rightPanel">
<p>重庆 9.0005</p>
<p>成都 2.925</p>
</div>
</div>
</div>
<div class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">PA-726M/ABS/电镀级</p><p class="quotePrice"><span style="color:black">￥18800</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>84.1005</p></div>
<div class="shape soldOutOfPay"><p>已售未下载</p><p>20.275</p></div>
<div class="shape onDelivery"><p>在途</p><p>84</p></div>
</div>
<div class="rightPanel">
<p>重庆 20.3755</p>
<p>成都 0</p>
</div>
</div>
</div>
<div class="singleQuote">
<div class="firstLayer"><p class="quoteTitle">PC/1100</p><p class="quotePrice"><span style="color:black">￥23800</span></p></div>
<div class="secondLayer">
<div class="leftPanel">
<div class="shape quoteInventory "><p>可用库存</p><p>7.325</p></div>
<div class="shape soldOutOfPay"><p>已售未下载</p><p>0</p></div>
<div class="shape onDelivery"><p>在途</p><p>0</p></div>
</div>
<div class="rightPanel">
<p>重庆 7.325</p>
<p>成都 0</p>
</div>
</div>
</div>
</div>
</body>
</html>
