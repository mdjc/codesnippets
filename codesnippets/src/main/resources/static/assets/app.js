(function() {
	'use strict';
	angular
		.module('codesnippetsApp',['ngRoute', 'ngClipboard'])
		.config(config)
		.run(run);
	
	config.$inject = ['ngClipProvider'];
	run.$inject = ['$rootScope', 'authentication']; 
	
	function config(ngClipProvider) {
		ngClipProvider.setPath("assets/vendor/ZeroClipboard.swf");
	}
	
	function run($rootScope, authentication) {
		$rootScope.username = '';
		authentication.principal().success(successCallback);
		
	    function successCallback(data, status, headers, config) {
	    	if (data && data.principal) {        		
	    		$rootScope.username = data.principal;
	    	}
	    }
	}
	
	
})();