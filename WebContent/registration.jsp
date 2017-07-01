<html>
<head>
<title>Registration</title>
<link rel="stylesheet" type="text/css" href="css/general.css">
</head>
<body>
	<%@include file="jspf/navbar.jspf"%>
	<script src='js/jquery.js'></script>
	<script src='js/myjqueryaddons.js'></script>
	<script src='js/skinny.js'></script>
	<script src='js/registration.js'></script>
	
	<h1>Registration</h1>
	<form id='regForm'>
		<label>Username:</label><br/>
		<input type='text' id='username' maxlength='20' required/><br/>
		<label>Password:</label><br/>
		<input type='password' id='password' maxlength='40' required/><br/>
		<label>Confirm password:</label><br/>
		<input type='password' id='confirm' maxlength='40' required/><br/>
		<label>Email address:</label><br/>
		<input type='email' id='email' maxlength='45' required/><br/>
		<label>First name:</label><br/>
		<input type='text' id='firstName' maxlength='40' required/><br/>
		<label>Last name:</label><br/>
		<input type='text' id='lastName' maxlength='40' required/><br/>
		<button type='submit'>Register</button>
		<div id='msgBox'></div>
	</form>
</body>
</html>