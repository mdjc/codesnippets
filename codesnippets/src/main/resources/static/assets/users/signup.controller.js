(function() {
	'use strict';
	
	angular
		.module('codesnippetsApp')
		.controller('SignUpController', Controller);
	
	Controller.$inject = ['authentication', 'utils'];
	
    function Controller(authentication, utils) {
    	var vm = this;
    	
        vm.submit = submit; 
        	
        function submit() {
        	var user = {"email": vm.email, 
        				"name": vm.username, 
        				"password": vm.password};
        	
        	authentication.signUp(user).error(errorCallback).success(successCallback);
        }
        
        function errorCallback(data, status, headers, config) {
        	vm.alert = new codesnippets.alerts.ErrorBuilder()
        		.when(500, "This username already exists")
        		.build(status, "Unexpected error");
        	utils.delayedClear(vm.alert);
        }

        function successCallback(data, status, headers, config) {
        	vm.alert = codesnippets.alerts.info("Your account has been created! Sign in loading...");
        	utils.delayedRedirect('/signin', 4000);
        }
    }
})();
