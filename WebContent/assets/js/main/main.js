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
				if( typeof data.notifications !== undefined ) {
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
					console.log(data);
				}
				else{
					if( typeof callBackFunction !== undefined )
					callBackFunction.call(this,data["result"]);
				}
			}
		});
	};
})(jQuery);