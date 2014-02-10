$(function() {
	$.ajax({
		url : APP_HTML_URL+"upload.html",
		success : function(data) {
			$('#app').html(data);
			initAppObjects();
		}
	});
});
function initAppObjects() {
	replaceCheckboxes();
	//$("#modal textarea.autogrow").autosize();
}