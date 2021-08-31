var magazines = null;

$.get('magazines', function(data) {
	if (data !== '') {
		magazines = data;
	}
}).done(function() {
	var cardsContent = '';

	jQuery.each(magazines, function(i, value) {
		var publishDateFormatted = ('0' + value.publishDate.day).slice(-2) + "." + ('0' + value.publishDate.month).slice(-2) + "." + value.publishDate.year;

		cardsContent += "<div class='col-3'>"
			+ "<div class='card'>"
			+ "<div class='card-body'>"
			+ "<h5 class='card-title'>" + value.title + "</h5>"
			+ "<h6 class='card-subtitle mb-2 text-muted'>" + value.subscribePrice / 100 + " грн." + "</h6>"
			+ "<p class='card-text'>" + value.description + "</p>"
			+ "<p class='card-text'>" + "Дата публикації: <br>" + publishDateFormatted + " г." + "</p>"
			+ "<a href='magazine?id=" + value.id + "' class='card-link'>Відкрити</a>"
			+ "</div>"
			+ "</div>"
			+ "</div>"
	});

	$('.magazineCards').html(cardsContent);
});