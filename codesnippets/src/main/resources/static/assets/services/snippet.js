(function() {
	angular.module("codesnippetsApp")
	.factory("Snippet", ['$http', function CodesnippetFactory($http) {
		return {
			all : function(query) {
				return $http.get('/snippets?query=' + query);
			},
		
			allFor : function(username, query) {
				return $http.get('/users/' + username +'/snippets?query=' + query);
			},
			
			nw: function(username, snippet){
				return $http.post('/users/' + username +'/snippets', snippet);
			},
			
			view: function(id) {
				return $http.get('/snippets/' + id);
			},
			
			update: function(username, snippet) {
				return $http.put('/users/' + username +'/snippets/', snippet);
			}
		};
	}]);
})();