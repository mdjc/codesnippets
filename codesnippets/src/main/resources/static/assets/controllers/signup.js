(function() {
	'use strict';
	
	angular.module('codesnippetsApp').controller('SignUpCtrl', Controller);
	
	Controller.$inject = ['$location', '$rootScope', '$timeout', 'Authentication', 'Alert'];
	
    function Controller($location, $rootScope, $timeout, Authentication, Alert) {
    	var vm = this;
    	
        vm.submit = function() {
        	var user = {"email" : vm.email, 
        				"name": vm.username, 
        				"password" : vm.password};        	
        	Authentication.signup(user).error(errorCallback).success(successCallback);
        }
        
        function errorCallback(data, status, headers, config) {
        	vm.alert = Alert
        		.errorWhen(500, "This username already exists")
        		.defaultError("Unexpected error")
        		.errorInstance(status);
        	$timeout(function() {vm.alert = {};}, 3000);
        }

        function successCallback(data, status, headers, config) {
        	vm.alert = Alert
        		.infoInstance("Your account has been created! Sign in loading...")
            $timeout(function() {$location.path('/signin')}, 4000);
        }
    }
})();