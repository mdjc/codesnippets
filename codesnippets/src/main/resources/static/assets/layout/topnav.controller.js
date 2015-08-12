(function(){
	'use strict';

	angular
		.module('codesnippetsApp')
		.controller('NavController', Controller);
	
	Controller.$inject = ['$rootScope', '$location', 'authentication'];
	
	function Controller($rootScope, $location, authentication) {
		var vm = this;
		
		vm.signOut = signOut;
		
		function signOut() {
			authentication.signOut().success(successCallback); 
		}
	
		function successCallback(data, status, headers, config){
			$rootScope.username = '';
			$location.path('/welcome');
		}	
	}
})();
