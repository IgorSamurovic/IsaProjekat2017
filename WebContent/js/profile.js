var fillCurrentData;
var populateFreqTable;
var populateFriendsTable;
var populateSearchTable;

const userId = Cookie.read("user");

$(document).ready(function() {
	
	var user;
	console.log(userId);

	// Let's first fill out the form with data
	
	fillCurrentData = function () {
		$.ajax({
			url   : `api/user/${userId}`,
			method: 'GET',
			success: function(retUser) {
				user = retUser;
				console.log(user);
				$('#username').val(user['username']);
				$('#email').val(user['email']);
				$('#firstName').val(user['firstName']);
				$('#lastName').val(user['lastName']);
				$('#password').val('');
			}
		})
	};
	
	var disabled = true;

	$('#change').click(function(e) {
		e.preventDefault();
		if (disabled) {
			$('#profileForm').find('input').removeAttr('disabled');
			$('#accept').removeAttr('disabled');
			$('#password').val("");
			disabled = false;
		} else {
			$('#profileForm').find('input').attr('disabled', true);
			$('#accept').attr('disabled', true);
			disabled = true;
			fillCurrentData();
		}
	});

	$('#profileForm').submit(function(e) {
		e.preventDefault();
		user = {
			id       : userId,
			username : $('#username').val(),
			email : $('#email').val(),
			firstName : $('#firstName').val(),
			lastName : $('#lastName').val(),
			password : $('#password').val()
		}
		user.id = userId;
		$.ajax({
			url   :  `api/user/${userId}`,
			method : 'PUT',
			data : JSON.stringify(user),
			contentType: "application/json",
			success : function() {
				fillCurrentData();
				$("#change").click();
			}
		});
		
	});
	
	$('#searchForm').submit(function(e) {
		e.preventDefault();
		populateSearchTable();
	});
	
	fillCurrentData();
	
	// Accept button functionality
	$('#freqsTable').on("click", '[name="accept"]', function() {
		var thisButton = this;
		var friendId = $(this).data('id');
		$.ajax({
			url    : `api/user/freqs?friendId=${friendId}&accepted=true`,
			method : 'PUT',
			success: function() {
				$(thisButton).closest('tr').remove();
				populateFriendsTable();
			}
		});
	});
	
	// Decline button functionality
	$('#freqsTable').on("click", '[name="decline"]', function() {
		var thisButton = this;
		var friendId = $(this).data('id');
		$.ajax({
			url    : `api/user/freqs?friendId=${friendId}&accepted=false`,
			method : 'PUT',
			success: function() {
				$(thisButton).closest('tr').remove();
			}
		});
	});
	
	// Add button functionality
	$(document).on("click", '[name="add"]', function() {
		var thisButton = this;
		var friendId = $(this).data('id');
		$.ajax({
			url    : `api/user/friends?friendId=${friendId}`,
			method : 'POST',
			success: function() {
				$(thisButton).remove();
			}
		});
	});
	
	// Remove button functionality
	$(document).on("click", '[name="remove"]', function() {
		var thisButton = this;
		var friendId = $(this).data('id');
		$.ajax({
			url    : `api/user/friends?friendId=${friendId}`,
			method : 'DELETE',
			success: function() {
				$(thisButton).closest('tr').remove();
			}
		});
	});
	
	// Populate friend request table	
	populateFreqTable = function () {
		$('#freqsTable').html(`
		<tr><th colspan="99">Friend requests</th></tr>
		<tr>
			<th>Username</th>
			<th>Email</th>
			<th>First name</th>
			<th>Last name</th>
			<th>Accept</th>
			<th>Decline</th>
		</tr>`);
		
		$.ajax({
			url    : `api/user/freqs/`,
			method : 'GET',
			success: function(freqs) {
				console.log(freqs);
				if (freqs.length > 0) {	
					for (freq in freqs) {
						console.log(freq);
						$('#freqsTable').append(`<tr>
							<td>${freqs[freq].username}</td>
							<td>${freqs[freq].email}</td>
							<td>${freqs[freq].firstName}</td>
							<td>${freqs[freq].lastName}</td>
							<td><button name='accept'  data-id='${freqs[freq].id}'>Accept</button</td>
							<td><button name='decline' data-id='${freqs[freq].id}'>Decline</button</td>
						</tr>`);
					}
				} else {
					$('#freqsTable').html('No friend requests at this time.');
				}
			},
			error : function() {
				$('#freqsTable').html('An error has occurred. Please try again later.');
			}
		})
	}
	
	// Populate friends table	
	populateFriendsTable = function () {
		$('#friendsTable').html(`
		<tr><th colspan="99">Friends</th></tr>
		<tr>
			<th>Username</th>
			<th>Email</th>
			<th>First name</th>
			<th>Last name</th>
			<th>Remove</th>
		</tr>`);
		
		$.ajax({
			url    : `api/user/friends/`,
			method : 'GET',
			success: function(freqs) {
				console.log(freqs);
				if (freqs.length>0) {	
					for (freq in freqs) {
						console.log(freq);
						$('#friendsTable').append(`<tr>
							<td>${freqs[freq].username}</td>
							<td>${freqs[freq].email}</td>
							<td>${freqs[freq].firstName}</td>
							<td>${freqs[freq].lastName}</td>
							<td><button name='remove'  data-id='${freqs[freq].id}'>Remove</button</td>
						</tr>`);
					}
				} else {
					$('#friendsTable').html('No friends :"(.');
				}
			},
			error : function() {
				$('#friendsTable').html('An error has occurred. Please try again later.');
			}
		})
	}
	
	// Populate search table	
	populateSearchTable = function () {
		var firstName = $('#searchFirstName').val();
		var lastName = $('#searchLastName').val();
		var searchFriends = $('#searchFriends').val();
		$('#searchTable').html(`
		<tr><th colspan="99">Users</th></tr>
		<tr>
			<th>Username</th>
			<th>Email</th>
			<th>First name</th>
			<th>Last name</th>
			<th>Add</th>
			<th>Remove</th>
		</tr>`);
		
		$.ajax({
			url    : `api/user/search`,
			method : 'GET',
			data   : {
				firstName : firstName,
				lastName : lastName,
				searchFriends : searchFriends
			}, 
			success: function(data) {
				if (data) {	
					$('#searchTable').removeClass('hidden');
					for (user in data) {
						$('#searchTable').append(`<tr>
							<td>${data[user].username}</td>
							<td>${data[user].email}</td>
							<td>${data[user].firstName}</td>
							<td>${data[user].lastName}</td>
							<td><button name='add'    data-id='${data[user].id}'>Add</button</td>
							<td><button name='remove' data-id='${data[user].id}'>Remove</button</td>
						</tr>`);
					}
				} else {
					$('#searchTable').removeClass('hidden');
					$('#searchTable').html('No results');
				}
			},
			error : function() {
				$('#searchTable').removeClass('hidden');
				$('#searchTable').html('An error has occurred. Please tty again later.');
			}
		})
	}
	
	populateFreqTable();
	populateFriendsTable();
	
});