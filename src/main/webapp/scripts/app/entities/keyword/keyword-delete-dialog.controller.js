'use strict';

angular.module('collectorthrdApp')
	.controller('KeywordDeleteController', function($scope, $uibModalInstance, entity, Keyword) {

        $scope.keyword = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Keyword.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
