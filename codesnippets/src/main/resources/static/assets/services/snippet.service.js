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
			delt: delt,
			view: view,
			allCategories:allCategories ,
			allUserCategories: allUserCategories
		};
		
		return service;
		
		function all(query) {			
			return $http.get('/snippets?query=' + query);
		}
		
		function allFor(username, query, category) {
			category = category || "";
			return $http.get('/users/' + username +'/snippets?query=' + query + '&category=' + category);
		}
		
		function nw(username, snippet) {
			return $http.post('/users/' + username +'/snippets', snippet);
		}
		
		function update(username, snippet) {
			return $http.put('/users/' + username +'/snippets/', snippet);
		}
		
		function delt(username, snippet) {
			var config = { 
						data : {'id': snippet.id, 
									'title': snippet.title, 
									'code' : snippet.code, 
									'language': snippet.language, 
									'description': snippet.description
								},
								
						headers: { 'Content-Type': 'application/json;charset=UTF-8'}
					};
			return $http.delete('/users/' + username +'/snippets/', config);
		}
		
		function view(id) {
			return $http.get('/snippets/' + id);
		}
		
		function allCategories(filter) {
			return $http.get('/snippets/categories?filter=' + filter).then(thenCallBack);;
			
			function thenCallBack(response) {
		    	return response.data;
		    }
		}
		
		function allUserCategories(username) {
			return $http.get('users/' + username + '/snippets/categories');
		}
	}
})();
