(function() {
	angular
		.module("codesnippetsApp")
		.factory("utils", factory);
	
	factory.$inject = ['$timeout', '$location'];	
	
	function factory($timeout, $location) {
		var service =  {
			delayedClear: delayedClear,
			delayedRedirect: delayedRedirect
			
		};
		
		return service;
	
		function delayedClear(obj, time) {
			time = time || 3000;
			$timeout(clear, 3000);
			
			function clear() {
				var key;
				
				for (key in obj) {
					if (obj.hasOwnProperty(key)) {
						obj[key] = null;
					}
				}
			}
		}
		
		function delayedRedirect(path, time) {
			time = time || 2000;
			$timeout(redirect, time);
		    	
		    function redirect() {
		    	$location.path(path);
		    }
		}
	}
})();
