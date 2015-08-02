(function(){
	'use strict';
	
	angular
		.module('codesnippetsApp')
		.config(config);
	
	config.$inject = ['$routeProvider'];
	
	function config($routeProvider) {
		$routeProvider
			.when('/', routeBlank('RootController'))
			.when('/allsnippets', route('AllSnippetsController', 'snippets/snippets-list'))
			.when('/allsnippets/:id', route('ViewSnippetController', 'snippets/view-snippet'))
			.when('/welcome', route('', 'welcome'))
			.when('/signin',route('SignInController', 'users/signin'))
			.when('/signup', route('SignUpController','users/signup'))
			.when('/home', route('HomeController', 'snippets/snippets-list'))
			.when('/home/snippets/:id', route('UpdateSnippetController', 'snippets/update-snippet')) 
			.when('/newsnippet', route('NewSnippetController', 'snippets/new-snippet'))
			.otherwise({redirectTo: '/'});
	}	
		
	function route(controller, template) {
    	return {
    		controller: controller,
    		controllerAs: 'vm',
    		templateUrl: 'assets/' + template + '.html'
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