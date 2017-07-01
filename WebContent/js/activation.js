$(document).ready(function() {
	const params = $.getParams();
	const activationString = params['activationString'];
	if (activationString) {
		
		$.ajax({
			url     : 'api/user/activate',
			method  : 'PUT',
			data    : JSON.stringify({
				activationString: activationString
			}),
			contentType: "application/json",
			success : function(data) {
				if (data) {
					$('#msgBox').html(`You have activated your account, ${data['username']}, you can proceed to <a href='login.jsp'>log in</a>.`);
				} else {
					$('#msgBox').html(`That activation string doesn't seem to be useful for anyone right now. If we gave it to you, you've already used it.`);
				}
			},
			error: function() {
				$('#msgBox').html('The server has encountered an error. Please try again later.');
			}
		});
	}
	
});