'use strict';

angular.module('collectorthrdApp')
    .controller('ArchiveFilter', function ($scope, $state, Collectible, CollectibleSearch) {

        $scope.collectibles = [];
        var archiveList = [];
        $scope.loadArchive = function() {
            Collectible.query(function(results) {
            angular.forEach	(results, function(result, key) {
            	if (result.forsale.forsale == true){
            		archiveList.push(result);
            	}
            });
               $scope.collectibles = archiveList;
            });
        };
        $scope.loadArchive();


        $scope.search = function () {
            CollectibleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.collectibles = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadArchive();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadArchive();
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
