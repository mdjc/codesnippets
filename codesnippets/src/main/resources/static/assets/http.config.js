(function() {
	'use strict';
    
    angular
    	.module('codesnippetsApp')
    	.config(config);
    
    config.$inject = ['$httpProvider'];
    
    function config($httpProvider) {
        //avoid authentication dialog from spring security
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        //send csrf token with header name as expected by spring security
        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
        $httpProvider.interceptors.push(interceptorFactory);
    }
    
    interceptorFactory.$inject = ['$q', '$location', '$rootScope'];

    function interceptorFactory($q, $location, $rootScope) {
        return {
        	responseError: responseError 
        };
        
        function responseError(rejection) {        		
            if (rejection.status === 401 || rejection.status === 403){
            	$rootScope.username = '';
                $location.path('/signin');
            }

            return $q.reject(rejection);
        }
    }
})();