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
            
            /*
             	var name = index.name;
             	var description = index.description;
             	var category = index.category.id;
             	var catalog = index.cataloguenumber;
             	var condition = index.condition.id;
             	var color = index.colors[0].id;
             	var age = index.age;
             	var keyword1 = index.keyword1s[0].id;
             	var keyword2 = index.keyword2s[0].id;
             	var keyword3 = index.keyword3s[0].id;
             	 
             */
            for (var i = 0; i<fieldsList.length; i++){
            	var key = fieldsList[i][0];
            	var field = fieldsList[i][1];
            	
            	for (var j = 0; j<indexList.length; j++){
            		console.log(indexList[j].name);
            		if (indexList[j].name !== field){
            			var check = indexList.indexOf(j)
                		indexList.splice(j,j+1);
                	}	
            	}
                 	 /*else if (index.description == field){
                		resultsList.push(index);
                	}else if (index.category.id == field){
                		resultsList.push(index);
                	}else if (index.cataloguenumber == field){
                		resultsList.push(index);
                	}else if (index.condition.id == field){
                		resultsList.push(index);
                	}else if (index.age == field){
                		resultsList.push(index);
                	}else if (index.colors[0].id == field){
                		resultsList.push(index);
                	}else if (index.keyword1s[0].id == field){
                		resultsList.push(index);
                	}else if (index.keyword2s[0].id == field){
                		resultsList.push(index);
                	}else if (index.keyword3s[0].id == field){
                		resultsList.push(index);
                	}
                });		*/
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
