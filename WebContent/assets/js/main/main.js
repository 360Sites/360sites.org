(function( $ ) {
	$.server = function( Cname, Cfunction, args, callBackFunction ) {
		var argsJson = $.toJSON( args );
		console.log(argsJson);
		$.ajax({
			type:		"POST",
			data:		"className="+Cname+"&methodName"+Cfunction+"&args="+argsJson,
			url:		"http://localhost:8080/360sites.org/gateway",
			dataType:	"json",
			success:	function(data)
			{
				if( data["status"] === false ) {
					alert(data["error"]);
					console.log(data);
				}
				else{
					if( callBackFunction !== undefined )
					callBackFunction.call(this,data["result"]);
				}
			}
		});
	};
})(jQuery);