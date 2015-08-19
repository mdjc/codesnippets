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
			.when('/mysnippets', route('AllSnippetsOfUserController', 'snippets/snippets-list'))
			.when('/newsnippet', route('CreateSnippetController', 'snippets/snippet-form'))
			.when('/mysnippets/categories', route('SnippetsByCategoryController', 'snippets/snippets-by-category'))
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