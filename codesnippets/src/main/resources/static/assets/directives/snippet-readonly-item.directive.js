(function() {
	'use strict';

	angular
		.module('codesnippetsApp')
		.directive('snippetReadOnlyItem', directive);
	
	function directive() {
		var dir = {
			restrict: 'E',
		    scope: {snippet : '=snippet', owner: '=owner', hidecategory : '=hidecategory'},
		    templateUrl: 'assets/directives/snippet-readonly-item.html'
		};
		
		return dir;
	}
})();