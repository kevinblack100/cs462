// Requires jQuery

$(document).ready(function() {
	Handlebars.registerHelper('secondsToDate', helpers.secondsToDateHelper);
	Handlebars.registerHelper('entryClass', helpers.entryClassHelper);
	
	//alert('parsing checkins...');
	var checkinsDataDiv = $('#checkins-data');
	if (checkinsDataDiv.length > 0) {
		var checkinsDataRaw = checkinsDataDiv.html();
		var checkinsData = $.parseJSON(checkinsDataRaw);

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
	},
	entryCounter: 0,
	entryClassHelper: function() {
		var evenOrOdd = (helpers.entryCounter % 2 == 0 ? "even" : "odd");
		helpers.entryCounter++;
		return new Handlebars.SafeString(evenOrOdd);
	}
};