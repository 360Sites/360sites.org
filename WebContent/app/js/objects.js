var isInitApp1 = 0;
$(function() {
	$.ajax({
		url : APP_HTML_URL+"app1.html",
		success : function(data) {
			$('#app').html(data);
			isInitApp1++;
			if(isInitApp1 == 2)
				initApp1();
		}
	});
	$.ajax({
		url : APP_MODAL_URL+"app1.html",
		success : function(data) {
			$('#modal').html(data);
			isInitApp1++;
			if(isInitApp1 == 2)
				initApp1();
		}
	});
});
function initApp1() {
	$("#modal textarea.autogrow").autosize();
}
function newObject(){
	$('#object-info-modal .modal-title').text("New Object");
	$('#object-info-modal-ok').text("Crate");
	$('#object-id').val(0);
	$('#object-name').val("");
	$('#object-address').val("");
	$('#object-description').val("");
	$('#object-info-modal').modal('show', {backdrop: 'static'});
}
function editObject(id){
	var element = $("div[data-object-id='"+id+"']");
	var objectName = element.find(".object-name").text();
	$('#object-info-modal .modal-title').text("Edit Object \""+objectName+"\"");
	$('#object-info-modal-ok').text("Save changes");
	$('#object-id').val(id);
	$('#object-name').val(objectName);
	$('#object-address').val(element.find(".object-address").text());
	$('#object-description').val(element.find(".object-description").text());
	$('#object-info-modal').modal('show', {backdrop: 'static'});
}
function deleteObject(id){
	$.server("","deleteObject",{'id':id},onDeleteObject);
	function onDeleteObject(data) {
		$("div[data-object-id='"+id+"']").remove();
	}
}
function saveObject(){
	var saveData = {
		id:$('#object-id').val(),
		name:$('#object-name').val(),
		address:$('#object-address').val(),
		description:$('#object-description').val()
	};
	if(!saveData.id){
		$.server("","createObject",saveData,onCreateObject);
		function onCreateObject(data) {
			//saveData
		}
	}else{
		$.server("","editObject",saveData,onEditObject);
		function onEditObject(data) {
			var element = $("div[data-object-id='"+id+"']");
			element.find(".object-name").text(saveData.name);
			element.find(".object-address").text(saveData.address);
			element.find(".object-description").text(saveData.description);
		}
	}
}















