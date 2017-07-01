<html>
<head>
<title>Login</title>
<link rel="stylesheet" type="text/css" href="css/general.css">
</head>
<body>
	<%@include file="jspf/navbar.jspf"%>
	<script src='js/jquery.js'></script>
	<script src='js/myjqueryaddons.js'></script>
	<script src='js/skinny.js'></script>
	<script src='js/cookies.js'></script>
	<script src='js/login.js'></script>
	
	<h1>Login</h1>
	<form id='loginForm'>
		<label>Username:</label><br/>
		<input type='text' id='username' required/><br/>
		<label>Password:</label><br/>
		<input type='password' id='password' required/><br/>
		<button type='submit'>Login</button>
		<div id='msgBox'></div>
	</form>
</body>
</html>