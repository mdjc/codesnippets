(function() {
	'use strict';
	
	angular
		.module('codesnippetsApp')
		.controller('SignInController', Controller);
	
	Controller.$inject = ['$location', '$rootScope', 'authentication', 'utils'];
    
    function Controller($location, $rootScope, authentication, utils) {
    	var vm = this;
        
        vm.password = '';
        vm.signIn = signIn;         	
        vm.username = '';
        	
        function signIn() {
        	if ($rootScope.username) {
        		$location.path('/mysnippets');
        		return;
        	}
        	
            var authorization = 'Basic ' + btoa(vm.username + ':' + vm.password);
            var config = {
            	headers: {
                    'Authorization': authorization
                }
            };
            authentication.principal(config).error(errorCallback).success(successCallback);
        }
        
        function errorCallback(data, status, headers, config) {
        	vm.alert = new codesnippets.alerts.ErrorBuilder()
        		.when(401, "Incorrect username or password")
        		.build(status, "Unexpected Error");
        	utils.delayedClear(vm.alert);
        }
        
        function successCallback(data, status, headers, config) {
            $rootScope.username = data.principal;
            $location.path('/mysnippets');
        }
    }
})();
