(function(){
	'use strict';

	angular.module('codesnippetsApp').controller('NewSnippetCtrl', Controller);
	Controller.$inject = ['$rootScope', '$timeout', '$location', 'Snippet', 'Alert'];
	
	function Controller($rootScope, $timeout, $location, Snippet, Alert) {
		var vm = this;
		vm.submit = function() {
			var snippet = {id : 0, title : vm.title, code: vm.code};
			Snippet.nw($rootScope.username, snippet).error(errorCallback).success(successCallback);
		}
		
		function errorCallback(data, status, headers, config) {
	        vm.alert = Alert
	        	.errorWhen(422, "You already have an snippet under this title")
	    		.defaultError("Unexpected error")
	    		.errorInstance(status);
	    	$timeout(function() {vm.alert = {};}, 3000);
	     }

	    function successCallback(data, status, headers, config) {
	    	vm.alert = Alert
	    		.infoInstance("Your snippet has been created!")
	        $timeout(function() {$location.path('/home')}, 2000);
	    }

	}   
})();