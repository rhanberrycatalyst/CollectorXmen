'use strict';

angular.module('collectorthrdApp')
    .controller('CollectibleDetailController', function ($scope, $rootScope, $stateParams, entity, Collectible, Category, Condition, Color, Keyword, Forsale) {
        $scope.collectible = entity;
        $scope.load = function (id) {
            Collectible.get({id: id}, function(result) {
                $scope.collectible = result;
            });
        };
        var unsubscribe = $rootScope.$on('collectorthrdApp:collectibleUpdate', function(event, result) {
            $scope.collectible = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
