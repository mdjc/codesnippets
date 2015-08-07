(function() {
	'use strict';
	
	angular
		.module('codesnippetsApp')
		.controller('UpdateSnippetController', Controller);
	
	Controller.$inject = ['$rootScope', '$routeParams', '$location', 'snippet', 'utils', 'Languages'];
	
    function Controller($rootScope, $routeParams, $location, snippet, utils, Languages) {
    	var vm = this;
    	
    	vm.action = "Update";
    	vm.delt = delt;
    	vm.languages = 	Languages;
    	vm.submit = submit;
    	
    	    
    	activate();
    	
    	function activate() {
    		snippet.view($routeParams['id']).success(viewSuccessCallback);
    	}
    	
    	function delt() {
    		vm.alert = codesnippets.alerts.info("Deleting...");
    		$('#delete-modal-sm').on('hidden.bs.modal', function (e) {
    			$('#delete-modal-sm').on('hidden.bs.modal', function (e) { });
    			console.log("hidden completed");
    			console.log(e);
    			snippet.delt($rootScope.username, vm.snippet).error(deleteErrorCallback).success(deleteSuccessCallback);
    		});
    		
    		$('#delete-modal-sm').modal('hide');
    	}
    	
    	function deleteSuccessCallback(data, status, headers, config) {
    		vm.alert = codesnippets.alerts.info("Your snippet has been deleted!");
    		utils.delayedClear(vm.alert);
    		$location.path('home');
    	}
    	
    	function deleteErrorCallback(data, status, headers, config) {
    		vm.alert = new codesnippets.alerts.ErrorBuilder()
        		.when(404, "This snippet doesn't exists")
        		.build(status, "Unexpected error");
    		utils.delayedClear(vm.alert);
    	}
    	
    		
    	function submit() {
    		if (vm.language) {
				vm.snippet.language = vm.language.name;
			}
    		snippet.update($rootScope.username, vm.snippet).error(updateErrorCallback).success(updateSuccessCallback);
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
            vm.snippet = data.snippet;
            vm.language = utils.getLanguageObj(data.snippet.language);
        }
    }
})();