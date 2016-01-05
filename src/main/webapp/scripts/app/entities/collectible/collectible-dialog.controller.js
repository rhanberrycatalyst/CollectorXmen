'use strict';

angular.module('collectorthrdApp').controller('CollectibleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Collectible', 'Category', 'Condition', 'Color', 'Keyword', 'Forsale',
        function($scope, $stateParams, $uibModalInstance, entity, Collectible, Category, Condition, Color, Keyword, Forsale) {

        $scope.collectible = entity;
        $scope.categorys = Category.query();
        $scope.conditions = Condition.query();
        $scope.colors = Color.query();
        $scope.keywords = Keyword.query();
        $scope.forsales = Forsale.query();
        $scope.load = function(id) {
            Collectible.get({id : id}, function(result) {
                $scope.collectible = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('collectorthrdApp:collectibleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.collectible.id != null) {
                Collectible.update($scope.collectible, onSaveSuccess, onSaveError);
            } else {
                Collectible.save($scope.collectible, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
