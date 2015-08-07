(function() {
	angular
		.module("codesnippetsApp")
		.factory("utils", factory);
	
	factory.$inject = ['$timeout', '$location', 'Languages'];	
	
	function factory($timeout, $location, Languages) {
		var service =  {
			delayedClear: delayedClear,
			delayedRedirect: delayedRedirect,
			getLanguageObj: getLanguageObj
			
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
			time = time || 3000;
			$timeout(redirect, time);
		    	
		    function redirect() {
		    	$location.path(path);
		    }
		}
		
		function getLanguageObj(languageName) {
			var i;
			for (i in Languages) {				
				if (Languages[i].name === languageName) {
					return Languages[i];
				}  
			}
	    }
	}
})();
