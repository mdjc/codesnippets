(function() {
	'use strict';
	
	angular
		.module('codesnippetsApp')
		.controller('ViewSnippetController', Controller);
	
	Controller.$inject = ['$routeParams', '$rootScope', '$location', 'Languages', 'snippet', 'utils'];
	
    function Controller($routeParams, $rootScope, $location, Languages,  snippet, utils) {
    	var vm = this;
    	
    	vm.snippetOwned = false;
    	vm.snippet = {};
    	vm.snippetOwner = "";
    	vm.languages = 	Languages;
    	vm.getCategories = getCategories;
    	vm.mainActionLabel = "Update";
    	vm.submit = update;
    	vm.delt = delt;
    	
    	activate();
    	
    	function activate() {
    		snippet.view($routeParams['id']).success(viewSuccessCallback); 
    	}    	
        
    	function getCategories(filter) {
	    	return snippet.allCategories(filter);
	    }
    	
        function viewSuccessCallback(data, status, headers, config) {
            vm.snippet = data.snippet;
            vm.snippetOwner = data.username;
            vm.snippetOwned = vm.snippetOwner == $rootScope.username;
        }
    	
    	function update() {
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
    		utils.delayedRedirect('mysnippets');
    	}
    	
    	function deleteErrorCallback(data, status, headers, config) {
    		vm.alert = new codesnippets.alerts.ErrorBuilder()
        		.when(404, "This snippet doesn't exists")
        		.build(status, "Unexpected error");
    		utils.delayedClear(vm.alert);
    	}
    }
})();
