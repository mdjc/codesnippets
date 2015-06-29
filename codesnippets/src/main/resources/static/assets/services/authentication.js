(function() {
	angular.module("codesnippetsApp")
	.factory("Authentication", ['$http', function AuthenticationFactory($http) {
		return {
			principal: function(config) {
				return  $http.get('principal', config);
			},
			
			signup: function(user){
				return  $http.post('/users', user);
			},
			
			signOut: function() {
				return $http.post('/logout', {});
			}
		};
	}]);
})();