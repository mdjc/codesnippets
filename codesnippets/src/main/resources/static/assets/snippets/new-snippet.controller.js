(function(){
	'use strict';

	angular
		.module('codesnippetsApp')
		.controller('NewSnippetController', Controller);
	
	Controller.$inject = ['$rootScope', 'snippet', 'utils'];
	
	function Controller($rootScope, snippet, utils) {
		var vm = this;
		
		vm.submit = submit;
			
		function submit() {
			var snippetObj = {id: 0, title: vm.title, code: vm.code};
			snippet.nw($rootScope.username, snippetObj).error(errorCallback).success(successCallback);
		}
		
		function errorCallback(data, status, headers, config) {
	        vm.alert = new codesnippets.alerts.ErrorBuilder()
	        	.when(422, "You already have an snippet under this title")
	    		.build(status, "Unexpected error");
	        utils.delayedClear(vm.alert);    	
	     }

	    function successCallback(data, status, headers, config) {
	    	vm.alert = codesnippets.alerts.info("Your snippet has been created!")
	    	utils.delayedRedirect('/home');
	    }
	}   
})();
