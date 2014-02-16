var isInitAppObjects = 0;
$(function() {
	$.ajax({
		url : APP_HTML_URL+"objects.html",
		success : function(data) {
			$('#app').html(data);
			isInitAppObjects++;
			if(isInitAppObjects == 2)
				initAppObjects();
		}
	});
	$.ajax({
		url : APP_MODAL_URL+"m-objects.html",
		success : function(data) {
			$('#modal').html(data);
			isInitAppObjects++;
			if(isInitAppObjects == 2)
				initAppObjects();
		}
	});
});
function initAppObjects() {
	$("#modal textarea.autogrow").autosize();
	$.server("UserObject","getObjects",{},function(data){
		$('#object-tmpl').tmpl(data).appendTo('#object-data');
	});
	
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
	$.server("UserObject","deleteObject",{'ID':id},onDeleteObject);
	function onDeleteObject(data) {
		$("div[data-object-id='"+id+"']").remove();
	}
}
function saveObject(){
	var saveData = {
		fields:{
			name:$('#object-name').val(),
			location:$('#object-address').val(),
			description:$('#object-description').val()
		}
	};
	var ID = parseInt($('#object-id').val());
	if(!ID){
		$.server("UserObject","createObject",saveData,onCreateObject);
		function onCreateObject(id) {
			saveData.fields.id = id;
			$('#object-tmpl').tmpl(saveData.fields).appendTo('#object-data');
			$('#object-info-modal').modal('hide');
			
			//saveData
		}
	}else{
		saveData.fields.id = ID;
		$.server("UserObject","editObject",saveData,onEditObject);
		function onEditObject(data) {
			var element = $("div[data-object-id='"+ID+"']");
			element.find(".object-name").text(saveData.fields.name);
			element.find(".object-address").text(saveData.fields.address);
			element.find(".object-description").text(saveData.fields.description);
			$('#object-info-modal').modal('hide');
		}
	}
}















