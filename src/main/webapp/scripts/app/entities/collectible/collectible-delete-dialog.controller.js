'use strict';

angular.module('collectorthrdApp')
	.controller('CollectibleDeleteController', function($scope, $uibModalInstance, entity, Collectible) {

        $scope.collectible = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Collectible.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
