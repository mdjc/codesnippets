(function() {
	'use strict';
	
	angular.module('codesnippetsApp').controller('SignInCtrl', Controller);
	
	Controller.$inject = ['$location', '$rootScope', '$timeout', 'Authentication', 'Alert'];
    
    function Controller($location, $rootScope, $timeout, Authentication, Alert) {
    	var vm = this;
        vm.username = '';
        vm.password = '';

        vm.signIn = function() {
        	if ($rootScope.username) {
        		$location.path('/home');
        		return;
        	}
        	
            var authorization = 'Basic ' + btoa(vm.username + ':' + vm.password);
            var config = {
            	headers: {
                    'Authorization': authorization
                }
            };
            Authentication.principal(config).error(errorCallback).success(successCallback);
        };
        
        function errorCallback(data, status, headers, config) {
        	vm.alert = Alert
        		.errorWhen(401, "Incorrect username or password")
        		.defaultError("Unexpected Error")
        		.errorInstance(status);
        	$timeout(function() {vm.alert = {};}, 3000);
        }
        
        function successCallback(data, status, headers, config) {
            $rootScope.username = data.principal;
            $location.path('/home');
        }
    }
})();