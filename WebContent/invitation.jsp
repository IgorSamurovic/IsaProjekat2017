<html>
<head>
<title>Invitation</title>
<link rel="stylesheet" type="text/css" href="css/general.css">
</head>
<body>
	<%@include file="jspf/navbar.jspf"%>
	<script src='js/jquery.js'></script>
	<script src='js/myjqueryaddons.js'></script>
	<script src='js/skinny.js'></script>
	<script src='js/cookies.js'></script>
	<script src='js/invitation.js'></script>
	
	<h1>Invitation</h1>
	
	<table class='hidden' id="reservationInfo">
		<tr><th colspan='99'>Reservation info</th></tr>
		<tr><td>Reserver       </td><td id='iReserver'></td></tr>
		<tr><td>Restaurant     </td><td id='iRestaurant'></td></tr>
		<tr><td>Type           </td><td id='iType'></td></tr>
		<tr><td>From           </td><td id='iDatetime'></td></tr>
		<tr><td>To             </td><td id='iDatetime2'></td></tr>
		<tr><td><button type='button' id='accept'>Accept</button></td><td><button type='button' id='decline'>Decline</button></td></tr>
	</table>
	
	<div id='msgBox'></div>
</body>
</html>