(function() {
	'use strict';
	
	angular
		.module('codesnippetsApp')
		.controller('RootController', Controller);
	
	Controller.$inject = ['$location', '$rootScope', 'authentication'];
	
    function Controller($location, $rootScope, authentication) {
    	var vm = this;
        
    	activate();
    	
    	function activate() {
    		authentication.principal().success(successCallback);    		
    	}
    	
        function successCallback(data, status, headers, config) {
        	if (data && data.principal) {          		
        		$rootScope.username = data.principal;
                $location.path('/home');
                return;
        	}
        	
        	$location.path('/welcome');
        }
    }
})();