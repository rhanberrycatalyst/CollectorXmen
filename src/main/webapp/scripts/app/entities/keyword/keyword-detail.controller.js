'use strict';

angular.module('collectorthrdApp')
    .controller('KeywordDetailController', function ($scope, $rootScope, $stateParams, entity, Keyword, Collectible) {
        $scope.keyword = entity;
        $scope.load = function (id) {
            Keyword.get({id: id}, function(result) {
                $scope.keyword = result;
            });
        };
        var unsubscribe = $rootScope.$on('collectorthrdApp:keywordUpdate', function(event, result) {
            $scope.keyword = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
