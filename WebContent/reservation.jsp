<html>
<head>
<title>Reservation</title>
<link rel="stylesheet" type="text/css" href="css/general.css">
</head>
<body>
	<%@include file="jspf/navbar.jspf"%>
	<script src='js/jquery.js'></script>
	<script src='js/myjqueryaddons.js'></script>
	<script src='js/skinny.js'></script>
	<script src='js/cookies.js'></script>
	<script src='js/reservation.js'></script>
	
	<h1>Reservation</h1>
	<div id='part1'>
		<form id='searchForm'>
			<table>
				<tr>
					<th colspan='2'>Find a restaurant by name and/or type</th>
			  	</tr>
				<tr><td>Name</td>     <td><input id='restaurantName'/></td></tr>
				<tr><td>Type</td>      <td><input id='restaurantType'/></td></tr>
				<tr><td colspan='2'><button type='submit' id='restaurantSearch'>Search</button></td></tr>  
			</table>
		</form>
		
		<table id='restsTable' class='hidden'>
		</table>
	</div>
	
	<div id='part2' class='hidden'>
		<form id='timeForm'>
			<table>
				<tr>
					<th colspan='2'>Find a restaurant by name and/or type</th>
			  	</tr>
				<tr><td>Name</td>            <td name='restaurantName'></td></tr>
				<tr><td>Date and time</td>   <td><input required id='datetimeInput' type="datetime-local"/></td></tr>
				<tr><td>Duration</td>        <td><input required id='durationInput' type='text'/></td></tr>
				<tr><td colspan='2'><button type='submit'>Accept</button></td></tr>  
			</table>
		</form>
	</div>
	
	<div id='part3' class='hidden'>
		
		<form id='tablesForm'>
			<table>
				<tr>
					<th colspan='2'>Info</th>
			  	</tr>
				<tr><td>Name</td>            <td name='restaurantName'></td></tr>
				<tr><td>Date and time</td>   <td name='datetime'></td></tr>
				<tr><td>Duration</td>        <td name='duration'></td></tr>
			</table>
			
			<br/>
			<br/>
			
			<table id='tableSelect'>
			</table>
			
		</form>
	</div>
	
	<div id='part4' class='hidden'>
		<h2>Invite some friends?</h2>
		<form id='invitesForm'>
			<table id='friendsTable'>
			</table>
		</form>
	</div>
	
	
	
	
	<div id='msgBox'></div>
</body>
</html>