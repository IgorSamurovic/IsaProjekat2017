const params = $.getParams();
const orderId = params['id'];
const userId = Cookie.read("user");

var restaurant;
var articles;
var fillOutMenu;
var calculateBill;
var sum; 

$(document).ready(function() {
	
	// Let's get articles first
	
	$(document).on('keyup', 'input', function() {
     	calculateBill(); 
	});
	
	$(document).on('click', '#accept', function() {
		var ordersToProcess = [];
		$('input').each(function() {
			var id = $(this).data('id');
			ordersToProcess.push({
				ordr    : orderId,
				order   : orderId,
				article: id,
			    amount: $(this).val()
			});
		});
		console.log(ordersToProcess);
		$.ajax({
			url    : `api/order/setarticles`,
			method : 'POST',
			data   : JSON.stringify(ordersToProcess),
			contentType: "application/json",
			dataType: 'json',
			success : function(answer) {
				
				window.location.href='home.jsp';
			}
		});
		
	});
	
	$(document).on('click', '#decline', function() {
		window.location.href='home.jsp';
	});
	
	$.ajax({
		url   :  `api/order/restaurant?id=${orderId}`,
		method: 'GET',
		success: function(getRestaurant) {
			restaurant = getRestaurant;
			console.log(restaurant.id);
			$.ajax({
				url   : `api/restaurant/articles?id=${restaurant.id}`,
				method: 'GET',
				success: function(getArticles) {
					articles = getArticles;
					fillOutMenu();
				}
			});
		}
	});
	
	calculateBill = function() {
		sum = 0;
		$('input').each(function() {
	        sum += Number($(this).val());
	    });
		$('#sum').html(sum);
		return sum;
	};
	
	fillOutMenu = function() {
		$('#menu').html(`
			<tr><th colspan='99'>Menu</th></tr>
			<tr>
				<th>Name</th>
				<th>Price</th>
				<th>Stock</th>
				<th>Type</th>
				<th>Amount</th>
			</tr>
		`);
		
		for (article in articles) {
			var type = articles[article].type == 0 ? 'meal' : 'drink'; 
		
			$('#menu').append(`
				<tr>
					<td>${articles[article].name}</td>
					<td>${articles[article].price}</td>
					<td>${articles[article].stock}</td>
					<td>${type}</td>
					<td><input data-id='${articles[article].id}' name='amount' value='0'/></td>
				</tr>
			`);
			
		}
	};
	
	
});