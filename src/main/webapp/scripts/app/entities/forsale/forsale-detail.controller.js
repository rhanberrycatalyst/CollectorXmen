'use strict';

angular.module('collectorthrdApp')
    .controller('ForsaleDetailController', function ($scope, $rootScope, $stateParams, entity, Forsale, Collectible) {
        $scope.forsale = entity;
        $scope.load = function (id) {
            Forsale.get({id: id}, function(result) {
                $scope.forsale = result;
            });
        };
        var unsubscribe = $rootScope.$on('collectorthrdApp:forsaleUpdate', function(event, result) {
            $scope.forsale = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
