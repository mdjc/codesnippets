(function() {
	'use strict';
	
	var languages = 
		[
   	      {name:'C'},
   	      {name:'C#'},
   	      {name:'C++'},
   	      {name:'CSS'},
   	      {name:'HTML'},
   	      {name:'CSS'},
   	      {name:'Java'},
   	      {name:'JavaScript'},
   	      {name:'Objective-C'},
   	      {name:'PHP'},
   	      {name:'Python'},
   	      {name:'Ruby'},
   	      {name:'SQL'}
   	    ];
   	
	angular
		.module('codesnippetsApp')
		.constant('Languages', languages);
})();	