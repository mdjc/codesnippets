var codesnippets;

(function(codesnippets) {
	var alerts;
	
	(function(alerts) {		
		alerts.ErrorBuilder = ErrorBuilder;
		alerts.info = info;
		alerts.error = error;
		
		
		function info(message) {
			return {type: 'info', message: message};
		}
		
		function error(message) {
			return {type: 'danger', message: message};
		}
		
		function ErrorBuilder() {		
			var messages = {};
			
			this.build = build;
			this.when = when;

			function build(status, defaultMessage) {
				var message = messages[status];
				
				if (!message) {
					return error(defaultMessage);
				}
											
				return error(message);			
			}

			function when(status, message) {
				messages[status] = message;
				return this;
			}						
		}	
		
	})(alerts = codesnippets.alerts || (codesnippets.alerts = {}));
})(codesnippets || (codesnippets = {}));
