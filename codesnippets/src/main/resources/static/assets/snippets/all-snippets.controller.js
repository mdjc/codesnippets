(function(){
	'use strict';

	angular
		.module('codesnippetsApp')
		.controller('AllSnippetsController', Controller);
	
	Controller.$inject = ['snippet'];
	
	function Controller(snippet) {
		var vm = this;
		
		vm.query = "";
		vm.searchItems = [];
		vm.searchph = "search all snippets";
		vm.submit = find;
	    	
	    activate();	
	    		
	    function activate() {				
	    	find();
	    }

		function find() {
			snippet.all(vm.query).success(successCallBack);
		}
		
		function successCallBack(data) {
			vm.searchItems = data;
		}
	}
})();
