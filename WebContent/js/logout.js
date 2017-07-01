$(document).ready(function() {
	
	$.ajax({
		url    : 'api/user/logout',
		method : 'GET',
		success: function() {
			$('#msgBox').html(`You have successfully logged out. You'll be redirected to the login page now.`);
			setTimeout(function() {
				window.location.href = 'login.jsp';
			}, 2500 );
			Cookie.erase("user");
		}
	});

});