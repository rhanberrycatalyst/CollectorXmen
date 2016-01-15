'use strict';

angular.module('collectorthrdApp')
    .factory('resultsService', function() {
    	var filters = {}
    	
    	 function set(filter) {
    	   filters = filter;
    	 }
    	 function get() {
    	  return filters;
    	 }

    	 return {
    	  set: set,
    	  get: get
    	 }

    	 /*var filters = {};
    	 
    	 filters.list = [];
    	 
    	 filters.add = function(filter) {
    		 filters.list.push(filter);
    	 }
    	 return filters;
         var searchList = [];
         $scope.search = function() {
         	Collectible.query(function(results) {
                angular.forEach	(results, function(result, key) {
                 	if (result.forsale.forsale == false){
                 		searchList.push(result);
                 	}
                 });
                console.log(searchList);
                 });
         }*/
         //set Name
         // set Description
         // set Age
         // set Catalogue Number
         // set Category
         // set Condition
         // set Color
         // set Keyword 1
         // set Keyword 2
         // set Keyword 3
         	
    	
    });
