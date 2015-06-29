(function() {
	'use strict';
	
	angular.module('codesnippetsApp').controller('UpdateSnippetCtrl', Controller);
	
	Controller.$inject = ['$rootScope', '$routeParams', '$timeout', '$location', 'Snippet', 'Alert'];
	
    function Controller($rootScope, $routeParams, $timeout, $location, Snippet, Alert) {
    	var vm = this;
    	
    	vm.update = function() {
    		Snippet.update($rootScope.username, vm.snippetItem.snippet).error(updateErrorCallback).success(updateSuccessCallback);
    	}
    	
    	function updateSuccessCallback(data, status, headers, config) {
    		vm.alert = Alert.infoInstance("Your snippet has been updated!");
    		$timeout(function() {vm.alert = {};}, 3000);
    	}
    	
    	function updateErrorCallback(data, status, headers, config) {
    		vm.alert = Alert
        		.errorWhen(422, "You already have an snippet under this title")
        		.defaultError("Unexpected error")
        		.errorInstance(status);
    		$timeout(function() {vm.alert = {};}, 3000);
    	}
    	
        function viewSuccessCallback(data, status, headers, config) {
        	if (data.username != $rootScope.username) {
        		$location.path('allsnippets/' + data.snippet.id);
        		return;
        	}
            vm.snippetItem = data;
        }
        
        Snippet.view($routeParams['id']).success(viewSuccessCallback);
    }
})();