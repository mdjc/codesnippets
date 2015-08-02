(function() {
	'use strict';
	
	angular
		.module('codesnippetsApp')
		.controller('UpdateSnippetController', Controller);
	
	Controller.$inject = ['$rootScope', '$routeParams', '$location', 'snippet', 'utils'];
	
    function Controller($rootScope, $routeParams, $location, snippet, utils) {
    	var vm = this;
    	
    	vm.update = update;
    	    
    	activate();
    	
    	function activate() {
    		snippet.view($routeParams['id']).success(viewSuccessCallback);
    	}
    		
    	function update() {
    		snippet.update($rootScope.username, vm.snippetItem.snippet).error(updateErrorCallback).success(updateSuccessCallback);
    	}
    	
    	function updateSuccessCallback(data, status, headers, config) {
    		vm.alert = codesnippets.alerts.info("Your snippet has been updated!");
    		utils.delayedClear(vm.alert);
    	}
    	
    	function updateErrorCallback(data, status, headers, config) {
    		vm.alert = new codesnippets.alerts.ErrorBuilder()
        		.when(422, "You already have an snippet under this title")
        		.build(status, "Unexpected error");
    		utils.delayedClear(vm.alert);
    	}
    	
        function viewSuccessCallback(data, status, headers, config) {
        	if (data.username != $rootScope.username) {
        		$location.path('allsnippets/' + data.snippet.id);
        		return;
        	}
            vm.snippetItem = data;
        }
    }
})();