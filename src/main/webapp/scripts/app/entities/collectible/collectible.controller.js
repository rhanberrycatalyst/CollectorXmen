'use strict';

angular.module('collectorthrdApp')
    .controller('CollectibleController', function ($scope, $state, Collectible, CollectibleSearch) {

    	$scope.collectibles = [];
        var indexList = [];
        $scope.loadIndex = function() {
            Collectible.query(function(results) {
            angular.forEach	(results, function(result, key) {
            	if (result.forsale.forsale == false){
            		indexList.push(result);
            	}
            });
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

        $scope.clear = function () {
            $scope.collectible = {
                name: null,
                description: null,
                age: null,
                cataloguenumber: null,
                id: null
            };
        };
    });