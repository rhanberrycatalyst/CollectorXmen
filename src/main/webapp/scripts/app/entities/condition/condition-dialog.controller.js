'use strict';

angular.module('collectorthrdApp').controller('ConditionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Condition', 'Collectible',
        function($scope, $stateParams, $uibModalInstance, entity, Condition, Collectible) {

        $scope.condition = entity;
        $scope.collectibles = Collectible.query();
        $scope.load = function(id) {
            Condition.get({id : id}, function(result) {
                $scope.condition = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('collectorthrdApp:conditionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.condition.id != null) {
                Condition.update($scope.condition, onSaveSuccess, onSaveError);
            } else {
                Condition.save($scope.condition, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
