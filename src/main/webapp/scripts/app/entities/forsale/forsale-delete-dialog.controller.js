'use strict';

angular.module('collectorthrdApp')
	.controller('ForsaleDeleteController', function($scope, $uibModalInstance, entity, Forsale) {

        $scope.forsale = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Forsale.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
