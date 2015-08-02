(function() {
	angular
		.module("codesnippetsApp")
		.factory("snippet", factory);
	
	factory.$inject = ['$http'];	
	
	function factory($http) {
		var service =  {
			all: all,
			allFor: allFor,
			nw: nw,
			update: update,
			view: view
		};
		
		return service;
		
		function all(query) {
			return $http.get('/snippets?query=' + query);
		}
		
		function allFor(username, query) {
			return $http.get('/users/' + username +'/snippets?query=' + query);
		}
		
		function nw(username, snippet) {
			return $http.post('/users/' + username +'/snippets', snippet);
		}
		
		function update(username, snippet) {
			return $http.put('/users/' + username +'/snippets/', snippet);
		}
		
		function view(id) {
			return $http.get('/snippets/' + id);
		}
	}
})();
