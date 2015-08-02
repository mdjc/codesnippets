(function() {
	'use strict';
	
	angular
		.module('codesnippetsApp')
		.controller('ViewSnippetController', Controller);
	
	Controller.$inject = ['$routeParams', 'snippet'];
	
    function Controller($routeParams, snippet) {
    	var vm = this;
    	
    	vm.copythis = copythis; 
    		
    	activate();
    	
    	function activate() {
    		snippet.view($routeParams['id']).success(successCallback);
    	}
    	
    	function copythis() {
    		return vm.snippetItem.snippet.code;
    	}
        
        function successCallback(data, status, headers, config) {
            vm.snippetItem = data;
        }
    }
})();
