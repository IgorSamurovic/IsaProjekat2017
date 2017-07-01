const userId = Cookie.read("user");
var populateFriendsTable;
var populateRestaurants;

$(document).ready(function() {
	
	var reservation = {
		user : userId,
		tables : {}
	};
	const meta = {};
	
	// Part 1
	
	var restaurants = {};
	var tables = {};
	
	populateRestaurants = function() {
		
		$('#restsTable').removeClass('hidden');
		
		$.ajax({
			url      : 'api/restaurant/search',
			method   : 'GET',
			data     : {
				name : $('#restaurantName').val(),
				type : $('#restaurantType').val(),
			},
			success  : function(data) {
				if (data) {
					$('#restsTable').html(`
					<tr><th colspan="99">Restaurants</th></tr>
					<tr>
						<th>Name</th>
						<th>Type</th>
						<th>Choose</th>
					</tr>`);
					var id = 0;
					for (rest in data) {
						$('#restsTable').append(`<tr>
							<td>${data[rest].name}</td>
							<td>${data[rest].type}</td>
							<td><button name='choose'  data-id='${id}'>Choose</button</td>
						</tr>`);
						id++;
					}
					restaurants = data;
				} else {
					$('#restsTable').html('<tr><td colspan="99">No restaurants with that name/type.</td></tr>');
				}
			},
			error : function() {
				$('#restsTable').html('<tr><td colspan="99">An error has occurred. Please try again later.</td></tr>');
				
			}
		
		});
	};
	
	populateRestaurants();
	
	$('#searchForm').submit(function(e) {
		e.preventDefault();
		populateRestaurants();
	});
	
	$(document).on("click", '[name="choose"]', function() {
		var id = $(this).data('id');
		reservation.restaurant = restaurants[id].id;
		meta.restaurantName = restaurants[id].name;
		$('[name="restaurantName"]').html(meta.restaurantName);
		$('#part1').addClass('hidden');
		$('#part2').removeClass('hidden');
		$('#datetimeInput').val(new Date().toJSON().slice(0,16));
	});
	
	// Part 2
	$('#timeForm').submit(function(e) {
		e.preventDefault();
		
		var val = $('#durationInput').val();
		
		if ( (val >> 0) > 0 && val >= 1 && val <= 6) {
			$('#msgBox').html("");
			
		} else {
			$('#msgBox').html("Invalid duration! Must be a number between 1 and 6.");
			return;
		}
		
		var date = $('#datetimeInput').val();
		var numericDate = new Date(date).getTime();  
		
		var minimumDate = new Date().getTime() + 3600000;
		console.log(numericDate);
		console.log(minimumDate);
		
		if (numericDate < minimumDate) {
			$('#msgBox').html('The time for reservation must be at least one hour from current time!');
			return;
		} else {
			$('#msgBox').html('');
		}
		
		var duration = parseInt($('#durationInput').val(), 10);
		var numericDate2 = numericDate + 3600000 * (1 + duration);
		var date2 = new Date(numericDate2).toISOString().substring(0, 16);
		
		$('[name="datetime"]').html(date.replace('T', ' '));
		$('[name="duration"]').html(duration);
		
		date = date.replace('T', ' ') + ':00';
		date2 = date2.replace('T', ' ') + ':00';
		
		reservation.timeFrom = date;
		reservation.timeTo = date2;
		
		$('#part2').addClass('hidden');
		$('#part3').removeClass('hidden');
		initPart3();
	});
	
	// Part 3
	const initPart3 = function() {
		$('#tableSelect').html(`
			<tr>
				<th colspan='6'>Select the tables</th>
			</tr>`
		);
		
		$.ajax({
			url     : `api/restaurant/tables`,
			method  : 'GET',
			data : reservation,
			success : function(data) {
				var counter = 0;
				tables = data;
				var disabled = "";
				for (table in data) {
					
					disabled = data[table].used ? "disabled" : "";
					
					var cls = data[table].used ? "tableUsed" : "tableUnused";
					
					if (counter === 0) {
						$('#tableSelect').append('<tr>');
					}
					
					$('#tableSelect').append(`
						<td>
							<button ${disabled} class='${cls}' type='button' name='tableBtn' data-id='${data[table].id}'>Table ${table}</button>
						</td>
					`);
					
					if (counter === 2) {
						counter = 0;
						$('#tableSelect').append('</tr>');
					} else {
						counter++;
					}
				}
				
				$('#tableSelect').append(`
					<tr>
						<tr><td colspan='6'><button name='selectTablesBtn'>Select tables</button</td></tr>
					</tr>`
				);
				
			}
		})
	};
	
	$(document).on("click", '[name="tableBtn"]', function() {
		var id = $(this).data('id');
		var table = tables[id];
		
		if (reservation.tables[id]) {
			$(this).removeClass('tableSelected');
			delete reservation.tables[id];
		} else {
			$(this).addClass('tableSelected');
			reservation.tables[id] = true;
		}
	});
	
	$('#tablesForm').submit(function(e) {
		
		e.preventDefault();
		// First we have to reformat reservation.tables
		var tables = [];
		for (tableId in reservation.tables) {
			if (reservation.tables[tableId]) {
				tables.push(tableId);
			}
		}
		
		if (tables.length <= 0) {
			$('#msgBox').html('You need to select at least one table!');
			return;
		} else {
			$('#msgBox').html('');
		}
		
		reservation.tables = tables;
		
		console.log(reservation);
		
		// Now we actually attempt to register this reservation
		$.ajax({
			url     : `api/reservation/create`,
			method  : 'POST',
			data    : JSON.stringify(reservation),
			contentType: "application/json",
			success : function(data) {
				if (data) {
					reservation = data;
					console.log(data);
					reservation.invites = {};
					$('#part3').addClass('hidden');
					$('#part4').removeClass('hidden');
					$('#msgBox').html("");
					populateFriendsTable();
				} else {
					console.log('error');
					$('#msgBox').html("We're sorry, but some of the tables were taken in the meantime!");
				}
			}
		})
	});
	
	// Part 4
	
	$('#invitesForm').submit(function(e) {
		
		e.preventDefault();
		
		var invites = [];
		for (friendId in reservation.invites) {
			if (reservation.invites[friendId]) {
				invites.push(friendId);
			}
		}
		
		reservation.invites = invites;
		
		$.ajax({
			url    : `api/reservation/invite/`,
			method  : 'POST',
			data    : JSON.stringify(reservation),
			contentType: "application/json",
			success: function(succ) {
				order = {
						cancelled: false,
						user : userId,
						reservation: reservation.id,
						ready : true,
					}
				console.log(order);
				$.ajax({
					url    : `api/order/create`,
					method : 'POST',
					data   : JSON.stringify(order),
					contentType: "application/json",
					dataType: "json",
					success: function(order) {
						window.location.href = `order.jsp?id=${order.id}`;
					}
				});
				
			}
		});
	});
	
	$(document).on("click", '[name="invite"]', function() {
		var id = $(this).data('id');
		if (reservation.invites[id]) {
			$(this).removeClass('friendInvited');
			delete reservation.invites[id];
			$(this).html('Invite');
		} else {
			$(this).addClass('friendInvited');
			reservation.invites[id] = true;
			$(this).html('Uninvite');
		}
	});
	
	// Populate friends table	
	populateFriendsTable = function () {
		$('#friendsTable').html(`<tr><th colspan="99">Invite friends</th></tr>`);
		
		$.ajax({
			url    : `api/user/friends/`,
			method : 'GET',
			success: function(freqs) {
				console.log(freqs);
				if (freqs && freqs.length > 0) {
					$('#friendsTable').html(`
					<tr>
						<th>Username</th>
						<th>Email</th>
						<th>First name</th>
						<th>Last name</th>
						<th>Invite</th>
					</tr>
					`);
					for (freq in freqs) {
						console.log(freq);
						$('#friendsTable').append(`<tr>
							<td>${freqs[freq].username}</td>
							<td>${freqs[freq].email}</td>
							<td>${freqs[freq].firstName}</td>
							<td>${freqs[freq].lastName}</td>
							<td><button type='button' name='invite'  data-id='${freqs[freq].id}'>Invite</button</td>
						</tr>`);
					}
				} else {
					$('#friendsTable').html('<tr><td colspan="99">No friends :"(.</tr></td>');
				}
				$('#friendsTable').append(`
					<tr>
						<tr><td colspan='6'><button name='finish'>Finish</button</td></tr>
					</tr>`
				);
			},
			error : function() {
				$('#friendsTable').html('<tr><td colspan="99">An error has occurred. Please try again later.</tr></td>');
				$('#friendsTable').append(`
					<tr>
						<tr><td colspan='6'><button name='finish'>Finish</button</td></tr>
					</tr>`
				);
			}
		})
	}
	
});