(function() {
	angular.module("codesnippetsApp")
	.factory("Alert", function AlertFactory() {
		var errorType = 'danger';
		var infoType = 'info';
		
		var factory = this;
		factory.alerts = [];
		factory.defaultAlert = {};
		
		return {	
			errorWhen : function(status, message) {
				factory.alerts.push({status : status, type : errorType, message: message})
				return this;
			},
			
			defaultError: function(message) {
				factory.defaultAlert = {type : errorType, message: message};
				return this;
			},
			
			errorInstance : function(status) {
				for (i = 0; i < factory.alerts.length; i++) {
					if (factory.alerts[i].status === status) {
						return {type : factory.alerts[i].type, message: factory.alerts[i].message};
					} 
				}
				
									
				return {type : factory.defaultAlert.type, message: factory.defaultAlert.message};			
			},
			
			infoInstance: function(message) {
				return {type : infoType, message: message};
			}
		};
	});
})();