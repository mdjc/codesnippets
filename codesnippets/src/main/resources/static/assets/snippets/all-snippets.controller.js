(function(){
	'use strict';

	angular
		.module('codesnippetsApp')
		.controller('AllSnippetsController', Controller);
	
	Controller.$inject = ['$rootScope', 'snippet'];
	
	function Controller($rootScope, snippet) {
		var vm = this;
			    
		vm.hrefViewSnippet = hrefViewSnippet;
		vm.searchph = "search all snippets";
		vm.searchItems = [];
		vm.query = "";
		vm.submit = find;
	    	
	    activate();	
	    		
		function hrefViewSnippet(snippetOwner) {
	    	if (snippetOwner === $rootScope.username) {
		    	return "home/snippets";
	    	}
	    	
	    	return "allsnippets";
	    }

		function activate() {				
			snippet.all(vm.query).success(successCallBack);
		}
		
		function successCallBack(data) {
			vm.searchItems = data;
		}
	}
})();
