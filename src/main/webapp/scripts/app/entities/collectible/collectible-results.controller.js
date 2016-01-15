'use strict';

angular.module('collectorthrdApp').controller('CollectibleResultsController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Collectible', 'Category', 'Condition', 'Color', 'Keyword', 'Forsale','resultsService',
        function($scope, $stateParams, $uibModalInstance, entity, Collectible, Category, Condition, Color, Keyword, Forsale, resultsService) {


    	$scope.collectibles = [];
    	var fieldsList = resultsService.get();
        var indexList = [];
        var resultsList = [];
        $scope.loadIndex = function() {
            Collectible.query(function(results) {
            angular.forEach	(results, function(result, key) {
            	if (result.forsale.forsale == false){
            		indexList.push(result);
            	}
            });

            for (var i = 0; i<fieldsList.length; i++){
            	var key = fieldsList[i][0];
            	var field = fieldsList[i][1];
            	angular.forEach	(indexList, function(index, testkey) {
                 	if(Object.keys(index)[1] == key) {
                 		if (index.name !== field){
                 			resultsList.push(index);
                     	}
                	} else if (Object.keys(index)[2] == key){
                		if (index.description !== field){
                    		resultsList.push(index);
                    	}
                	}else if (Object.keys(index)[5] == key) {
                		if (index.category.id != field){
                    		resultsList.push(index);
                    	}
                	}else if (Object.keys(index)[4] == key) {
                		if (index.cataloguenumber !== field){
                				resultsList.push(index);
                		}
                	} else if (Object.keys(index)[6] == key) {
                		if (index.condition.id != field){
                    		resultsList.push(index);
                    	}
                	} else if (Object.keys(index)[3] == key){
                		if (index.age != field) {
                			resultsList.push(index);
                		}
                	} else if (Object.keys(index)[7] == key){
                		console.log("reached");
                		if(index.colors[0].id != field){
                    		resultsList.push(index);
                    	}
                	}else if (Object.keys(index)[8] == key) {
                		if(index.keyword1s[0].id != field){
                    		resultsList.push(index);
                    	}
                  	}else if (Object.keys(index)[9] == key) {
                  		if (index.keyword2s[0].id != field){
                    		resultsList.push(index);
                  		}
                	}else if (Object.keys(index)[10] == key) {
                		if (index.keyword3s[0].id != field){
                    		resultsList.push(index);
                    	}
                	}
                });		
             }
            indexList = indexList.filter(function(val) {
            	  return resultsList.indexOf(val) == -1;
            });
            if (indexList.length == 0 ){
            	$("#modalResults").empty();
            	$("#modalResults").append("<p>No search results found. </p>");
            }
             $scope.collectibles = indexList;
            });
        };
        $scope.loadIndex();

        $scope.search = function () {
            CollectibleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.collectibles = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadIndex();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadIndex();
            $scope.clear();
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
