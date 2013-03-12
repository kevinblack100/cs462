// Requires jQuery

$(document).ready(function () {
	var driverCheckbox = $('#driver-indicator');
	driverCheckbox.bind('change', toggleDriverDetails);
	driverCheckbox.trigger('change');
});

function toggleDriverDetails(eventObject) {
	// see http://stackoverflow.com/questions/901712/check-checkbox-checked-property-using-jquery
	
	// this = input id="driver-indicator"
	var displayDetails = this.checked;
	$('#driver-details').toggle(displayDetails);
}