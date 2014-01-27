<%@page import="org.proffart.pan.User"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

if(User.isLogined(request)){%>

<%@include file="dashboard/main/index1.html" %>

<%}else{%>

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
	<script src="assets/js/json/jquery.json-2.4.min.js"></script>
	<script src="assets/js/toastr.js"></script>
	<script src="assets/js/main/main.js"></script>

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	<![endif]-->
	
	<!-- TS1390028954: Neon - Responsive Admin Template created by Laborator -->
</head>
<body class="page-body login-page login-form-fall">

<div class="login-container">
	
	<div class="login-header login-caret">
		
		<div class="login-content">
			
			<a href="#" class="logo">
				<img src="assets/images/logo%402x.png" width="120" alt="" />
			</a>
			
			<p class="description">Dear user, log in to access the admin area!</p>
			
			<!-- progress bar indicator -->
			<div class="login-progressbar-indicator">
				<h3>43%</h3>
				<span>logging in...</span>
			</div>
		</div>
		
	</div>
	
	<div class="login-progressbar">
		<div></div>
	</div>
	
	<div class="login-form">
		
		<div class="login-content">
			
			<form method="post" role="form" id="form_login">
				
				<div class="form-group">
					
					<div class="input-group">
						<div class="input-group-addon">
							<i class="entypo-user"></i>
						</div>
						
						<input type="text" class="form-control" name="username" id="username" placeholder="Username" autocomplete="off" />
					</div>
					
				</div>
				
				<div class="form-group">
					
					<div class="input-group">
						<div class="input-group-addon">
							<i class="entypo-key"></i>
						</div>
						
						<input type="password" class="form-control" name="password" id="password" placeholder="Password" autocomplete="off" />
					</div>
				
				</div>
				
				<div class="form-group">
					<button type="submit" class="btn btn-primary btn-block btn-login">
						Login In
						<i class="entypo-login"></i>
					</button>
				</div>
				
			</form>
			
			
			<div class="login-bottom-links">
				
				<a href="#" class="link">Forgot your password?</a>
				
				<br />
				
				<a href="#">ToS</a>  - <a href="#">Privacy Policy</a>
				
			</div>
			
		</div>
		
	</div>
	
</div>


	<script src="assets/js/gsap/main-gsap.js" id="script-resource-1"></script>
	<script src="assets/js/jquery-ui/js/jquery-ui-1.10.3.minimal.min.js" id="script-resource-2"></script>
	<script src="assets/js/bootstrap.min.js" id="script-resource-3"></script>
	<script src="assets/js/joinable.js" id="script-resource-4"></script>
	<script src="assets/js/resizeable.js" id="script-resource-5"></script>
	<script src="assets/js/neon-api.js" id="script-resource-6"></script>
	<script src="assets/js/cookies.min.js" id="script-resource-7"></script>
	<script src="assets/js/jquery.validate.min.js" id="script-resource-8"></script>
	<script src="assets/js/neon-login.js" id="script-resource-9"></script>
	<script src="assets/js/neon-custom.js" id="script-resource-10"></script>
	<script src="assets/js/neon-demo.js" id="script-resource-11"></script>
	<script src="assets/js/neon-skins.js" id="script-resource-12"></script>
	<script src="assets/js/toastr.js" id="script-resource-13"></script>
	
	
</body>

</html>
<%}%>