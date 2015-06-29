(function() {
	'use strict';
    
    angular.module('codesnippetsApp').config(config);
    
    config.$inject = ['$httpProvider'];
    
    function config($httpProvider) {
        //avoid authentication dialog from spring security
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        //send csrf token with header name as expected by spring security
        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
        $httpProvider.interceptors.push(interceptorFactory);
    }
    
    interceptorFactory.$inject = ['$q', '$location'];

    function interceptorFactory($q, $location) {
        return {
        	'responseError': function(rejection) {
        		console.log("interceptor");
                if (rejection.status == 401 || rejection.status == 403) {
                    $location.path('/signin');
                }

                return $q.reject(rejection);
            }
        };
    }
})();