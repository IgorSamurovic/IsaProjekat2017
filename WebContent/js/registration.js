$(document).ready(function() {
	$('#regForm').submit(function(e) {
		$('#msgBox').html('');
		e.preventDefault();
		
		if ($('#password').val() !== $('#confirm').val()) {
			$('#msgBox').html('Passwords do not match!');
			return;
		}
		
		const data = {
			active    : false,
			activationString: "",
			username  : $("#username").val(),
			password  : $("#password").val(),
			email     : $("#email").val(),
			firstName : $("#firstName").val(),
			lastName  : $("#lastName").val()
		}; 
		
		$.ajax({
			url    : 'api/user/register',
			method : 'POST',
			data   : JSON.stringify(data),
			contentType: "application/json",
			success: function(data) {
				if (data) {
					$('#msgBox').html(`You have successfully registered, ${data.username}! An email with an activation link has been dispatched to ${data.email}!`)
				} else {
					$('#msgBox').html("Chosen username or email are already taken.");
				}				
			},
			error: function() {
				$('#msgBox').html('The server has encountered an error. Please try again later.');
			}
		});
		
	});
	
});