<html>
<head>
<title>Order</title>
<link rel="stylesheet" type="text/css" href="css/general.css">
</head>
<body>
	<%@include file="jspf/navbar.jspf"%>
	<script src='js/jquery.js'></script>
	<script src='js/myjqueryaddons.js'></script>
	<script src='js/skinny.js'></script>
	<script src='js/cookies.js'></script>
	<script src='js/order.js'></script>
	
	<h1>Order</h1>
	
	<table id="menu"></table>
	
	<br/>
	<br/>
	
	<table id="orderInfo">
		<tr><th colspan='99'>Order info</th></tr>
		<tr><td>Total             </td><td id='sum'></td></tr>
		<tr><td>Ready as I arrive </td><td><select><option value='true'>Yes</option><option value='false'>No</option></select></td></tr>
		<tr><td><button type='button' id='accept'>Accept</button></td><td><button type='button' id='decline'>Decline</button></td></tr>
	</table>
	
	<div id='msgBox'></div>
</body>
</html>