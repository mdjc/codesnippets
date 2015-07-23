(function(){
	'use strict';

	angular.module('codesnippetsApp').controller('AllSnippetsCtrl', Controller);
	
	Controller.$inject = ['$scope', '$rootScope', 'Snippet'];
	
	function Controller($scope, $rootScope, Snippet) {
		var vm = this;
		vm.searchph = "search all snippets";
		vm.searchItems = [];
		vm.query = "";
					
		vm.submit = function() {
	        find();
	    };
	    
	    vm.hrefViewSnippet = function(snippetOwner) {
	    	if (snippetOwner === $rootScope.username) {
		    	return "home/snippets";
	    	}
	    	
	    	return "allsnippets";
	    }
	    
		function find() {				
			Snippet.all(vm.query).success(function(data) {
				vm.searchItems = data;
			});
		}

	    find();
	}
})();