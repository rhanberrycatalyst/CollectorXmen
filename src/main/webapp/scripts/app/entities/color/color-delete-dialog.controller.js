'use strict';

angular.module('collectorthrdApp')
	.controller('ColorDeleteController', function($scope, $uibModalInstance, entity, Color) {

        $scope.color = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Color.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
