(function(){
	'use strict';

	angular
		.module('codesnippetsApp')
		.controller('HomeController', Controller);
	
	Controller.$inject = ['$rootScope', 'snippet'];
	
	function Controller($rootScope, snippet) {
		var vm = this;
					
		vm.hrefViewSnippet = hrefViewSnippet;
		vm.searchph = "search my snippets";
		vm.searchItems = [];
		vm.query = '';
		vm.submit = find;

		activate();
	    
	    function hrefViewSnippet(snippetOwner) {
	    	return "home/snippets";
	    }
	    
	    function activate() {				
	    	snippet.allFor($rootScope.username, vm.query).error(errorCallback).success(successCallback);	
		}
	    
	    function errorCallback(data, status, headers, config) {
	    	vm.alert = codesnippets.alerts.error("Unexpected Error");
	    }
	    
	    function successCallback(data, status, headers, config) {
	    	var items = [];
			
			for(var i = 0; i < data.length; i++) {
				items.push({"snippet": data[i]});
			}
			
			vm.searchItems = items;
	    }	    
	}
})();
