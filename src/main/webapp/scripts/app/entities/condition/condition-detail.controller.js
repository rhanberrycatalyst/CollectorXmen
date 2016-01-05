'use strict';

angular.module('collectorthrdApp')
    .controller('ConditionDetailController', function ($scope, $rootScope, $stateParams, entity, Condition, Collectible) {
        $scope.condition = entity;
        $scope.load = function (id) {
            Condition.get({id: id}, function(result) {
                $scope.condition = result;
            });
        };
        var unsubscribe = $rootScope.$on('collectorthrdApp:conditionUpdate', function(event, result) {
            $scope.condition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
