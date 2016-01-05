'use strict';

angular.module('collectorthrdApp')
	.controller('ConditionDeleteController', function($scope, $uibModalInstance, entity, Condition) {

        $scope.condition = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Condition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
