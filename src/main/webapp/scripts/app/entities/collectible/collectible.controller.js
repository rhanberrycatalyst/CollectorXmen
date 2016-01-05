'use strict';

angular.module('collectorthrdApp')
    .controller('CollectibleController', function ($scope, $state, Collectible, CollectibleSearch) {

        $scope.collectibles = [];
        $scope.loadAll = function() {
            Collectible.query(function(result) {
               $scope.collectibles = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            CollectibleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.collectibles = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
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
