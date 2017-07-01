const params = $.getParams();
const invId = params['id'];
const userId = Cookie.read("user");
var data;
var reservation;

$(document).ready(function() {
	
	// Set up buttons
	$(document).on('click', "#accept", function() {
		invite.accepted = true;
		$.ajax({
			url    : `api/invitation/${invite.id}`,
			method : 'PUT',
			data    : JSON.stringify(invite),
			contentType: "application/json",
			success: function() {
				
				var order = {
					cancelled : false,
					reservation : reservation.id,
					user        : userId,
					ready       : true,
				}
				
				var visit = {
						reservation : reservation.id,
						user        : userId,
						coming       : true,
					}
				
				$.ajax({
					url : `api/visit/create`,
					method : 'POST',
					data   : JSON.stringify(visit),
					contentType: "application/json",
					success : function() {
						$.ajax({
							url : `api/order/create`,
							method : 'POST',
							data   : JSON.stringify(order),
							contentType: "application/json",
							success : function(getOrder) {
								window.location.href=`order.jsp?id=${getOrder.id}`;
							}
						});
					}
				});
				
				var visit = {
						reservation : reservation.id,
						user        : userId,
						coming       : true,
					}
				
				
			}
		});
	});
	
	$(document).on('click', "#decline", function() {
		invite.accepted = false;
		$.ajax({
			url    : `api/invitation/${invite.id}`,
			method : 'PUT',
			data    : JSON.stringify(invite),
			contentType: "application/json",
			success: function() {
				window.location.href=`home.jsp`;
			}
		});
	});
	
	// First let's find a reservation from this invitation id
	if (invId) {
		
		$.ajax({
			url    : `api/invitation/${invId}`,
			method : 'GET',
			success: function(getInvite) {
				invite = getInvite;
				console.log(getInvite.user);
				console.log(userId);
				if (getInvite.user != userId || getInvite.accepted) {
					$('#msgBox').html('Invalid invitation id.');
				} else {
					inviteValid();
				}
			}
		});
	}
	
	inviteValid = function() {
		$('#reservationInfo').removeClass('hidden');
		$.ajax({
			url     : `api/reservation/frominv?invId=${invId}`,
			method  : 'GET',
			success : function(getReservation) {
				reservation = getReservation;
				if (reservation) {
					
					// Get user data 
					$.ajax({
						url     : `api/user/${reservation.user}`,
						method  : 'GET',
						success : function (user) {
							$('#iReserver').html(user.username);
							console.log(user);
						}
					});
							
					$.ajax({
						url    : `api/restaurant/${reservation.restaurant}`,
						method : 'GET',
						success: function (restaurant) {
							$('#iRestaurant').html(restaurant.name);
							$('#iType').html(restaurant.type);
						}
					});
					
					$('#iDatetime').html(reservation.timeFrom);
					$('#iDatetime2').html(reservation.timeTo);
				
				} else {
					$('#msgBox').html('Invalid invitation id.');
				}
			},
			error: function() {
				$('#msgBox').html('The server has encountered an error. Please try again later.');
			}
		});
	}
	
});