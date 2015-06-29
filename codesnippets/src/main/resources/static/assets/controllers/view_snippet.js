(function() {
	'use strict';
	
	angular.module('codesnippetsApp').controller('ViewSnippetCtrl', Controller);
	
	Controller.$inject = ['$routeParams', 'Snippet'];
	
    function Controller($routeParams, Snippet) {
    	var vm = this;
    	
    	Snippet.view($routeParams['id']).error(errorCallback).success(successCallback);
        
        function errorCallback(data, status, headers, config) {
           
        }

        function successCallback(data, status, headers, config) {
            vm.snippetItem = data;
        }
    }
})();