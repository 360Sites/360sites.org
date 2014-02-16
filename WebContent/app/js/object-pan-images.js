function runObjectPanImages(params) {
	$.ajax({
		url : APP_HTML_URL+"object-pan-images.html",
		success : function(data) {
			$('#app').html(data);
			initObjectPanImages(params);
		}
	});
}
function initObjectPanImages(params){
	$("#object-name").text(params.objectName);
	$.fileManager("#object-pan-images","UserObject",params);
}