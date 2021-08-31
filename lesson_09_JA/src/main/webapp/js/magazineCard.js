$('button.buy-magazine').click(function() {
	var magazineID = jQuery(this).attr('magazineID');

	$.post('subscribe', { 'magazineID': magazineID },
		function(data) {
			if (data == 'Success') {
				//					$('[data-dismiss=modal]').trigger({type: 'click'});
				$('#buyMagazineModal').hide();
				$('.modal-backdrop').remove();
				alert('Журнал успішно добавлений в корзину!');
			}
		});
});