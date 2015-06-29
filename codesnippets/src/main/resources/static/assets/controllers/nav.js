(function(){
	'use strict';

	angular.module('codesnippetsApp').controller('NavCtrl', Controller);
	
	Controller.$inject = ['$rootScope', '$location', 'Authentication'];
	
	function Controller($rootScope, $location, Authentication) {
		var vm = this;
		
		vm.signOut = function() {
			Authentication.signOut().success(successCallback); 
		}
	
		function successCallback(data, status, headers, config){
			$rootScope.username = '';
			$location.path('/welcome');
		}		
			
	}
	
})();