'use strict';

angular.module('collectorthrdApp')
    .controller('KeywordController', function ($scope, $state, Keyword, KeywordSearch) {

        $scope.keywords = [];
        $scope.loadAll = function() {
            Keyword.query(function(result) {
               $scope.keywords = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            KeywordSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.keywords = result;
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
            $scope.keyword = {
                keyword: null,
                id: null
            };
        };
    });
