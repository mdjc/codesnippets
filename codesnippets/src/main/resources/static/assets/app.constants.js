(function() {
	'use strict';
	
	var languages = 
		[ 'C', 
		  'C#',
   	      'C++',
   	      'CSS',
   	      'HTML',
   	      'CSS',
   	      'Java',
   	      'JavaScript',
   	      'Objective-C',
   	      'PHP',
   	      'Python',
   	      'Ruby',
   	      'SQL'
   	    ];
   	
	angular
		.module('codesnippetsApp')
		.constant('Languages', languages);
})();	