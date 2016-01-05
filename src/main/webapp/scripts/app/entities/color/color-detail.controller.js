'use strict';

angular.module('collectorthrdApp')
    .controller('ColorDetailController', function ($scope, $rootScope, $stateParams, entity, Color, Collectible) {
        $scope.color = entity;
        $scope.load = function (id) {
            Color.get({id: id}, function(result) {
                $scope.color = result;
            });
        };
        var unsubscribe = $rootScope.$on('collectorthrdApp:colorUpdate', function(event, result) {
            $scope.color = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
