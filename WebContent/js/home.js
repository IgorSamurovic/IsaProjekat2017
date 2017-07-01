const userId = Cookie.read("user");
var allVisits;

$(document).ready(function() {
	
	$(document).on('click', '[name="cancel"]', function() {
		var id = $(this).data('id');
		var btn = this;
		var visit = allVisits[id];
		$.ajax({
			url   : `api/visit/${visit.id}`,
			method: 'PUT',
			data  : JSON.stringify(visit),
			contentType: "application/json",
			success: function(answer) {
				if (answer) {
					$(btn).closest('tr').remove();
					$("#msgBox").html("");
				} else {
					$("#msgBox").html("You can't cancel a visit when it's less than 30 minutes away!");
				}
			}
		})
	});

	$.ajax({
		url     : `api/user/visits?id=${userId}`,
		method  : 'GET',
		success : function(visits) {
			allVisits = visits;
			if (visits.length>0) {
				$('#visitsTable').html(`
					<tr><th colspan='99'>Visits</th></tr>
					<tr>
						<th>From</th>
						<th>To</th>
						<th>Restaurant</th>
						<th>Cancel</th>
					</tr>`);
				
				for (visit in visits) {
					
					var cancel = '';
					if (visits[visit].canCancel) {
						cancel = `<button type='button' data-id='${visit}' name='cancel'>Cancel</button>`;
					}
					$('#visitsTable').append(`
						<tr>
							<td>${visits[visit].dateFrom}</td>
							<td>${visits[visit].dateTo}</td>
							<td>${visits[visit].restaurantName}</td>
							<td>${cancel}</td>
						</tr>
					`);
				}
			} else {
				$('#visitsTable').html(`<tr><td colspan="99">No visits</td></tr>`);
			}
		}
	})
});