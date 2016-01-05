'use strict';

angular.module('collectorthrdApp')
    .controller('ConditionController', function ($scope, $state, Condition, ConditionSearch) {

        $scope.conditions = [];
        $scope.loadAll = function() {
            Condition.query(function(result) {
               $scope.conditions = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ConditionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.conditions = result;
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
            $scope.condition = {
                condition: null,
                id: null
            };
        };
    });
