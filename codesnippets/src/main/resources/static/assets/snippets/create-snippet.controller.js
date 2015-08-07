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
		vm.snippet = {id: 0, title: "", code: "", language: "", description: ""};
		vm.submit = create;
		
		function create() {
			if (vm.language) {
				vm.snippet.language = vm.language.name;
			}
			
			snippet.nw($rootScope.username, vm.snippet).error(errorCallback).success(successCallback);
		}
		
		function errorCallback(data, status, headers, config) {
	        vm.alert = new codesnippets.alerts.ErrorBuilder()
	        	.when(422, "You already have an snippet under this title")
	    		.build(status, "Unexpected error");
	        utils.delayedClear(vm.alert);    	
	     }

	    function successCallback(data, status, headers, config) {
	    	vm.alert = codesnippets.alerts.info("Your snippet has been created!")
	    	utils.delayedRedirect('/mysnippets');
	    }
	}   
})();
