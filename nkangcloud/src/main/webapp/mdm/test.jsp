<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form id="file_upload_id" name="file_upload_name" action="../userProfile/fileUpload" method="post" enctype="multipart/form-data">
<input type="file" name="file">
<input type="submit"/>

</from>
<br/><br/>

<style>
.icon {
    display: inline-block;
    width: 30px; height: 30px;
    overflow: hidden;
}
.icon > img.exit {
    position: relative;
    left: -30px;
    border-right: 30px solid transparent;
    -webkit-filter: drop-shadow(30px 0 #ff0000);
    filter: drop-shadow(20px 0);   
}
</style>

<i class="icon"><img class="exit" src="../MetroStyleFiles/EXIT1.png" style="width: 30px; height: 30px;"></i>


</body>
</html>