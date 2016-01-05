'use strict';

angular.module('collectorthrdApp')
    .controller('ColorController', function ($scope, $state, Color, ColorSearch) {

        $scope.colors = [];
        $scope.loadAll = function() {
            Color.query(function(result) {
               $scope.colors = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ColorSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.colors = result;
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
            $scope.color = {
                color: null,
                id: null
            };
        };
    });
