(function() {
	angular
		.module("codesnippetsApp")
		.factory("authentication", factory);
	
	factory.$inject = ['$http'];
	
	function factory($http) {
		var service =  {
			principal: principal,
			signOut: signOut,
			signUp: signUp		
		};
		
		return service;
		
		function principal(config) {
			return  $http.get('/principal', config);
		}
		
		function signOut() {
			return $http.post('/logout', {});
		}
		
		function signUp(user){
			return  $http.post('/users', user);
		}
	}
})();
