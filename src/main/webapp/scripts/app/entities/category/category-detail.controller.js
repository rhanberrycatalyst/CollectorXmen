'use strict';

angular.module('collectorthrdApp')
    .controller('CategoryDetailController', function ($scope, $rootScope, $stateParams, entity, Category, Collectible) {
        $scope.category = entity;
        $scope.load = function (id) {
            Category.get({id: id}, function(result) {
                $scope.category = result;
            });
        };
        var unsubscribe = $rootScope.$on('collectorthrdApp:categoryUpdate', function(event, result) {
            $scope.category = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
