// Requires jQuery

$(document).ready(function() {
	//alert('parsing checkins...');
	var checkinsDataDiv = $('#checkins-data');
	if (checkinsDataDiv.length > 0) {
		var checkinsDataRaw = checkinsDataDiv.html();
		var checkinsData = $.parseJSON(checkinsDataRaw);

		Handlebars.registerHelper('secondsToDate', helpers.secondsToDateHelper);
		
		var templateSource = $('#checkin-list-template').html();
		var template = Handlebars.compile(templateSource);
		var checkins = checkinsData.response.checkins;
		var resultHtml = template(checkins);
		$('#checkins-detail').append(resultHtml);
		
		//alert('parsed checkins successfully');
	}
	else {
		//alert('no checkins data container');
	}
});

var helpers = {
	secondsToDateHelper: function(seconds) {
		var milliseconds = seconds * 1000;
		var asDate = new Date(milliseconds);
		var result = asDate.toLocaleString();
		return new Handlebars.SafeString(result);
	}
};