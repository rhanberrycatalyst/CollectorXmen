'use strict';

angular.module('collectorthrdApp')
    .controller('ForsaleController', function ($scope, $state, Forsale, ForsaleSearch) {

        $scope.forsales = [];
        $scope.loadAll = function() {
            Forsale.query(function(result) {
               $scope.forsales = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ForsaleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.forsales = result;
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
            $scope.forsale = {
                forsale: false,
                id: null
            };
        };
    });
