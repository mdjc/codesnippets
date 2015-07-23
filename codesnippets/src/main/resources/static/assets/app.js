(function() {
	'use strict';

	angular.module('codesnippetsApp',[
	   'ngRoute',
	   'ngClipboard'
	])
	
	.config(['ngClipProvider', function(ngClipProvider) {
	    ngClipProvider.setPath("assets/vendor/ZeroClipboard.swf");
	}])
	
	.run(['$rootScope', 'Authentication', function($rootScope, Authentication) {
		$rootScope.username = '';
		Authentication.principal().success(successCallback);
		
        function successCallback(data, status, headers, config) {
        	if (data && data.principal) {        		
        		$rootScope.username = data.principal;
        	}
        }
		
	}]);
})();