'use strict';

angular.module('collectorthrdApp').controller('KeywordDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Keyword', 'Collectible',
        function($scope, $stateParams, $uibModalInstance, entity, Keyword, Collectible) {

        $scope.keyword = entity;
        $scope.collectibles = Collectible.query();
        $scope.load = function(id) {
            Keyword.get({id : id}, function(result) {
                $scope.keyword = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('collectorthrdApp:keywordUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.keyword.id != null) {
                Keyword.update($scope.keyword, onSaveSuccess, onSaveError);
            } else {
                Keyword.save($scope.keyword, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
