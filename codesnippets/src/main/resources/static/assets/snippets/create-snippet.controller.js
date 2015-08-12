(function(){
	'use strict';

	angular
		.module('codesnippetsApp')
		.controller('CreateSnippetController', Controller);
	
	Controller.$inject = ['$rootScope', 'snippet', 'utils', 'Languages'];
	
	function Controller($rootScope, snippet, utils, Languages) {
		var vm = this;
		
		vm.mainActionLabel = "Create";
		vm.languages = 	Languages;	
		vm.getCategories = getCategories;
		vm.snippet = {id: 0, title: "", code: "", language: "", description: "", category: ""};
		vm.submit = create;
				
		function getCategories(filter) {
	    	return snippet.allCategories(filter);
	    }
		
		function create() {
			snippet.nw($rootScope.username, vm.snippet).error(createErrorCallback).success(createSuccessCallback);
		}
		
		function createErrorCallback(data, status, headers, config) {
	        vm.alert = new codesnippets.alerts.ErrorBuilder()
	        	.when(422, "You already have an snippet under this title")
	    		.build(status, "Unexpected error");
	        utils.delayedClear(vm.alert);    	
	     }

	    function createSuccessCallback(data, status, headers, config) {
	    	vm.alert = codesnippets.alerts.info("Your snippet has been created!")
	    	utils.delayedRedirect('/mysnippets');
	    }
	}   
})();
