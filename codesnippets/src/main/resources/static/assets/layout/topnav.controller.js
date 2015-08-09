(function(){
	'use strict';

	angular
		.module('codesnippetsApp')
		.controller('NavController', Controller);
	
	Controller.$inject = ['$rootScope', '$location', 'authentication'];
	
	function Controller($rootScope, $location, authentication) {
		var vm = this;
		
		vm.signOut = signOut;
		
		activate();
		
		function activate() {
			//automatically collapse navbar when link clicked AND navbar is compacted (because of small screen)
		    //see: https://github.com/twbs/bootstrap/issues/12852
		    $(document).on('click', '.navbar-collapse.in', function(e) {
		        if ($(e.target).is('a') && ($(e.target).attr('class') != 'dropdown-toggle')) {
		            $(this).collapse('hide');
		        }
		    });
		}
		
		function signOut() {
			authentication.signOut().success(successCallback); 
		}
	
		function successCallback(data, status, headers, config){
			$rootScope.username = '';
			$location.path('/welcome');
		}	
	}
})();
