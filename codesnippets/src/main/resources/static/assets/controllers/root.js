(function() {
	'use strict';
	
	angular.module('codesnippetsApp').controller('RootCtrl', Controller);
	
	Controller.$inject = ['$location', '$rootScope', 'Authentication'];
	
    function Controller($location, $rootScope, Authentication) {
    	Authentication.principal().success(successCallback);
        
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