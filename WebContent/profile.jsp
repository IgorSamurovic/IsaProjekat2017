<html>
<head>
<title>Profile</title>
<link rel="stylesheet" type="text/css" href="css/general.css">
</head>
<body>
	<%@include file="jspf/navbar.jspf"%>
	<script src='js/jquery.js'></script>
	<script src='js/myjqueryaddons.js'></script>
	<script src='js/skinny.js'></script>
	<script src='js/cookies.js'></script>
	<script src='js/profile.js'></script>
	
	<h1>Profile</h1>
	
		<form id='profileForm'>
			<table>
				<tr>
					<th>Field</th>
				    <th>Value</th>
			  	</tr>
				<tr><td>Usernamne</td>     <td><input disabled required id='username'/></td></tr>
				<tr><td>Email</td>         <td><input disabled required id='email' type='email'/></td></tr>
				<tr><td>First name</td>    <td><input disabled id='firstName'/></td></tr>
				<tr><td>Last name</td>     <td><input disabled id='lastName'/></td></tr>
				<tr><td>Password</td>      <td><input disabled required id='password' type='password'/></td></tr>
				<tr><td><button type='button' id='change'>Change</button></td>  <td><button type='submit' id='accept' disabled>Accept</button></td></tr>  
			</table>
		</form>
	
	<br/>
	<br/>
	
	<table id='freqsTable'>
	</table>
	<br/>
	<br/>
	
	<table id='friendsTable'>
	</table>
	<br/>
	<br/>
	
	<form id='searchForm'>
		<table class='friends'>
			<tr>
				<th colspan='2'>Search</th>
		  	</tr>
			<tr><td>First name</td>     <td><input id='searchFirstName'/></td></tr>
			<tr><td>Last name</td>      <td><input id='searchLastName'/></td></tr>
			<tr><td>Search friends</td> <td><select id='searchFriends'><option value=true>Yes</option><option value=false>No</option></select></td></tr>
			<tr><td colspan='2'><button type='submit' id='search'>Search</button></td></tr>  
		</table>
	</form>
	
	<table id='searchTable' class='hidden'>
	</table>
	<br/>
	<br/>
	
</body>
</html>