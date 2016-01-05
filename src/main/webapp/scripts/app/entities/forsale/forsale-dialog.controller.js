'use strict';

angular.module('collectorthrdApp').controller('ForsaleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Forsale', 'Collectible',
        function($scope, $stateParams, $uibModalInstance, entity, Forsale, Collectible) {

        $scope.forsale = entity;
        $scope.collectibles = Collectible.query();
        $scope.load = function(id) {
            Forsale.get({id : id}, function(result) {
                $scope.forsale = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('collectorthrdApp:forsaleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.forsale.id != null) {
                Forsale.update($scope.forsale, onSaveSuccess, onSaveError);
            } else {
                Forsale.save($scope.forsale, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
