(function( $ ) {
	$.server = function( Cname, Cfunction, args, callBackFunction ) {
		var argsJson = $.toJSON( args );
		console.log(argsJson);
		$.ajax({
			type:		"POST",
			data:		"className="+Cname+"&methodName="+Cfunction+"&args="+argsJson,
			url:		"http://localhost:8080/360sites.org/gateway",
			dataType:	"json",
			success:	function(data)
			{
				if( data.notifications != undefined ) {
					for( var i = 0; i<data.notifications.length; i++ ) {
						var opts = data.notifications[i].opts,
							text = data.notifications[i].text,
							title = data.notifications[i].title;
						switch(data.notifications[i].type) {
							case "error":
								toastr.error(text,title,opts);
								break;
							case "info":
								toastr.info(text,title,opts);
								break;
							case "warning":
								toastr.warning(text,title,opts);
								break;
							case "success":
								toastr.success(text,title,opts);
								break;
						}
					}
				}
				if( data["status"] === false ) {
					if( data.exception != undefined) {
						var opts = {
							"closeButton": false,
							"positionClass": "toast-top-full-width",
							//"toastClass": "black",
							"showDuration": "300",
							"hideDuration": "1000",
							"timeOut": "50000",
							"extendedTimeOut": "1000",
							"showEasing": "swing",
							"hideEasing": "linear",
							"showMethod": "fadeIn",
							"hideMethod": "fadeOut"
						};
						toastr.error(data.exception,"Exception",opts);
					}else{
						console.log(data);
					}
				}
				else{
					if( typeof callBackFunction !== undefined )
					callBackFunction.call(this,data["result"]);
				}
			}
		});
	};
})(jQuery);