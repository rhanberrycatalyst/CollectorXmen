'use strict';

angular.module('collectorthrdApp').controller('ColorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Color', 'Collectible',
        function($scope, $stateParams, $uibModalInstance, entity, Color, Collectible) {

        $scope.color = entity;
        $scope.collectibles = Collectible.query();
        $scope.load = function(id) {
            Color.get({id : id}, function(result) {
                $scope.color = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('collectorthrdApp:colorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.color.id != null) {
                Color.update($scope.color, onSaveSuccess, onSaveError);
            } else {
                Color.save($scope.color, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
