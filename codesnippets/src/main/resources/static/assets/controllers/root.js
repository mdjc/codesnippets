(function() {
	'use strict';
	
	angular.module('codesnippetsApp').controller('RootCtrl', Controller);
	
	Controller.$inject = ['$location', '$rootScope', 'Authentication'];
	
    function Controller($location, $rootScope, Authentication) {
    	Authentication.principal().error(errorCallback).success(successCallback);
        
        function errorCallback(data, status, headers, config) {
            $location.path('/welcome');
            $rootScope.username = '';
        }

        function successCallback(data, status, headers, config) {
            $rootScope.username = data.principal;
            $location.path('/home');
        }
    }
})();