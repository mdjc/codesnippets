(function(){
	'use strict';

	angular
		.module('codesnippetsApp')
		.controller('AllSnippetsController', Controller);
	
	Controller.$inject = ['$rootScope', 'snippet'];
	
	function Controller($rootScope, snippet) {
		var vm = this;
			    
		vm.viewSnippetPathPrefix = viewSnippetPathPrefix;
		vm.query = "";
		vm.searchItems = [];
		vm.searchph = "search all snippets";
		vm.submit = find;
	    	
	    activate();	
	    		
	    function activate() {				
	    	find();
	    }
		
	    function viewSnippetPathPrefix(snippetOwner) {
	    	if (snippetOwner === $rootScope.username) {
		    	return "home/snippets";
	    	}
	    	
	    	return "allsnippets";
	    }

		function find() {
			snippet.all(vm.query).success(successCallBack);
		}
		
		function successCallBack(data) {
			vm.searchItems = data;
		}
	}
})();
