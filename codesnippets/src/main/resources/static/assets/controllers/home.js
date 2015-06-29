(function(){
	'use strict';

	angular.module('codesnippetsApp').controller('HomeCtrl', Controller);
	
	Controller.$inject = ['$rootScope','Snippet', 'Alert'];
	
	function Controller($rootScope, Snippet, Alert) {
		var vm = this;
		vm.searchph = "search my snippets";
		vm.searchItems = [];
		vm.query = '';
					
		
		vm.submit = function() {
	        find();
	    };
	    
	    vm.hrefViewSnippet = function(snippetOwner) {
	    	return "home/snippets";
	    }
	    
	    function find() {				
			Snippet.allFor($rootScope.username, vm.query).error(errorCallback).success(successCallback);	
		}
	    
	    function errorCallback(data, status, headers, config) {
	    	vm.alert = Alert
    		.defaultError("Unexpected Error")
    		.errorInstance(status);
	    }
	    
	    function successCallback(data, status, headers, config) {
	    	var items = [];
			
			for(var i = 0; i < data.length; i++) {
				items.push({"snippet" : data[i]});
				console.log("snippet search item " +  {"snippet" : data[i]})
			}
			
			vm.searchItems = items;
	    }

	    find();
	}
	
})();