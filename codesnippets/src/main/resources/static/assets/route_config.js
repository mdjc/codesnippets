(function(){
	'use strict';
	
	angular.module('codesnippetsApp')
	.config(['$routeProvider', function($routeProvider) {
		$routeProvider
		.when('/', routeBlank('RootCtrl'))
		.when('/allsnippets', route('AllSnippetsCtrl', 'snippets_list'))
		.when('/allsnippets/:id', route('ViewSnippetCtrl', 'view_snippet'))
		.when('/welcome', route('', 'welcome'))
		.when('/signin',route('SignInCtrl', 'signin'))
		.when('/signup', route('SignUpCtrl','signup'))
		.when('/home', route('HomeCtrl', 'snippets_list'))
		.when('/home/snippets/:id', route('UpdateSnippetCtrl', 'update_snippet')) 
		.when('/newsnippet', route('NewSnippetCtrl', 'new_snippet'))
		.otherwise({redirectTo: '/'});
		
	}]);	
	
	function route(controller, template) {
    	return {
    		controller: controller,
    		controllerAs: 'vm',
    		templateUrl: 'assets/templates/' + template + '.html'
    	};
    }

	function routeBlank(controller) {
    	return {
    		controller: controller,
    		controllerAs: 'vm',
    		template: ''
    	};
    }
	
})();