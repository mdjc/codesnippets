(function() {
	'use strict';

	angular
		.module('codesnippetsApp')
		.directive('btnAutoCollapse', directive);
	
	function directive() {
		var dir = {
			restrict: 'A',
		    scope: {},
			link: link
		};
		
		return dir;

		function link(scope, element, attrs) {	
			element.on('click', function(event) {			   
				$(".navbar-collapse.in").collapse('hide');
			 });
		}
	}
})();









