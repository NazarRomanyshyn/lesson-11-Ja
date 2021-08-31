$(document).ready(function() {
	$('.leftmenutrigger').on('click', function(e) {
		$('.side-nav').toggleClass('open');
		e.preventDefault();
	});
});

function logout() {
	$.get('loggingOut', function(data) {
		if (typeof (data) != '') {
			var customUrl = '';
			var urlContent = window.location.href.split('/');

			for (var i = 0; i < urlContent.length - 1; i++) {
				customUrl += urlContent[i] + '/';
			}
			window.location = customUrl + data;
		}
	});
}