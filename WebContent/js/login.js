$(document).ready(function() {
	$('#loginForm').submit(function(e) {
		$('#msgBox').html('');
		e.preventDefault();
		
		const data = {
			username  : $("#username").val(),
			password  : $("#password").val(),
		}; 
		console.log(JSON.stringify(data));
		$.ajax({
			url    : 'api/user/login',
			method : 'GET',
			data   : data,
			success: function(retData) {
				if (retData) {
					$('#msgBox').html(`You have successfully logged in, ${data.username}! You'll be redirected to home page now.`)
					setTimeout(function() {
						window.location.href = 'home.jsp';
					}, 2500 );
					Cookie.create("user", retData['id']);
				} else {
					$('#msgBox').html('That combination of username and password does not exist in our user database.');
				}
			},
			error: function() {
				$('#msgBox').html('The server has encountered an error. Please try again later.');
			}
		});
		
	});
	
});