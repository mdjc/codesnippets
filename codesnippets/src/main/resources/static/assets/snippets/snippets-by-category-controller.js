(function(){
	'use strict';

	angular
		.module('codesnippetsApp')
		.controller('SnippetsByCategoryController', Controller);
	
	Controller.$inject = ['$rootScope', 'snippet'];
	
	function Controller($rootScope, snippet) {
		var vm = this;
		
		vm.categories = [];
		vm.snippetsByCategory = {};
		vm.snippetsFor = snippetsFor;
		vm.selectedCategory = "";
		
		activate();
		
		function activate() {
			loadCategories();
		}
		
		function loadCategories() {
			snippet.allUserCategories($rootScope.username).success(loadCategoriesSuccessCallBack).error(errorCallback);
		}
		
		function loadCategoriesSuccessCallBack(data) {
			vm.categories = data;
		}
		
		function snippetsFor(category) {
			vm.selectedCategory = category;
			
			if (!vm.snippetsByCategory[category]) {
				snippet.allFor($rootScope.username, "", category).success(snippetsForCategorySuccessCallBack).error(errorCallback);			
			}
		}
		
		function snippetsForCategorySuccessCallBack(data) {
			vm.snippetsByCategory[vm.selectedCategory] = data;			
		}
		
		function errorCallback(data) {
		    vm.alert = codesnippets.alerts.error("Unexpected Error");
		}
	}
})();
