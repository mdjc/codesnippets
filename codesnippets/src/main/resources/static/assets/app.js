(function() {
	'use strict';

	angular.module('codesnippetsApp',[
	   'ngRoute'
	])
	.run(['$rootScope', 'Authentication', function($rootScope, Authentication) {
		$rootScope.username = '';
		Authentication.principal().success(successCallback);
		
        function successCallback(data, status, headers, config) {
            $rootScope.username = data.principal;
        }
		
	}]);
})();