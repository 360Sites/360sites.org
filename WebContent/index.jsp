<%@page import="org.proffart.pan.User"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"><![endif]-->
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="360sites admin panel" />
	
	<title>Neon | Login</title>

	<link rel="stylesheet" href="assets/js/jquery-ui/css/no-theme/jquery-ui-1.10.3.custom.min.css"  id="style-resource-1">
	<link rel="stylesheet" href="assets/css/font-icons/entypo/css/entypo.css"  id="style-resource-2">
	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Noto+Sans:400,700,400italic"  id="style-resource-3">
	<link rel="stylesheet" href="assets/css/neon.css"  id="style-resource-4">
	<link rel="stylesheet" href="assets/css/custom.css"  id="style-resource-5">

	<script src="assets/js/jquery-1.10.2.min.js"></script>
	<script src="assets/js/toastr.js"></script>
	<script src="assets/js/main/main.js"></script>

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
	
	<!-- TS1390028954: Neon - Responsive Admin Template created by Laborator -->
</head>
<%
if(User.isLogined(request)){%>
<body class="page-body page-fade">
	<div class="page-container">
		<%@include file="system/menu.jsp" %>
			<div class="main-content">
				<%@include file="system/header.jsp" %>
				<%@include file="system/content.jsp" %>
				
				
				
				<%@include file="system/footer.jsp" %>
			</div>
		<%@include file="system/chat.jsp" %>
	</div>
	<div id="modal" style="width: 0px; height: 0px; z-index: 1040;"></div>
	<%@include file="system/scripts.jsp" %>
</body>

<%}else{%>
<%@include file="system/login.jsp" %>
<%}%>
</html>