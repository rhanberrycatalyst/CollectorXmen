'use strict';

angular.module('collectorthrdApp').controller('CollectibleSearchController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Collectible', 'Category', 'Condition', 'Color', 'Keyword', 'Forsale','resultsService',
        function($scope, $stateParams, $uibModalInstance, entity, Collectible, Category, Condition, Color, Keyword, Forsale, resultsService) {

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

        $scope.save = function () {
        	var fields = [];
        	var nameInput = $('#field_name').val();
        	if (nameInput.length > 0 ) {
        		fields.push(["name",nameInput]);
        	}
        	var descriptionInput = $('#field_description').val();
        	if (descriptionInput.length > 0) {
        		fields.push(["description",descriptionInput]);
        	}
        	var ageInput = $('#field_age').val();
        	if (ageInput.length > 0) {
          		fields.push(["age",ageInput]);
        	}
        	var catalogInput = $('#field_cataloguenumber').val();
        	if (catalogInput.length > 0 ) {
        		fields.push(["catalogenumber",catalogInput]);
        	}
        	var categoryInput = $('#field_category').val();
        	if (categoryInput > 0) {
        		fields.push(["category",categoryInput]);
        	}
        	var conditionInput = $('#field_condition').val();
        	if (conditionInput > 0) {
        		fields.push(["condition",conditionInput]);
        	}
        	var colorInput = $('#field_color').val();
        	if (colorInput !== null) {
        		fields.push(["colors",colorInput]);
        	}
        	var keyword1Input = $('#field_keyword1').val();
        	if (keyword1Input !== null) {
        		
        		fields.push(["keyword1s",keyword1Input]);
        	}
        	var keyword2Input = $('#field_keyword2').val();
        	if (keyword2Input !== null) {
        		fields.push(["keyword2s",keyword2Input]);
        	}
        	var keyword3Input = $('#field_keyword3').val();
        	if (keyword3Input !== null) {
        		fields.push(["keyword3s", keyword3Input]);
        	}
        	if(fields.length == 0){
        		alert("No search criteria entered");
        	}
        	//grab fields
        		// make sure fields is not 0.
        	//send to service
        	resultsService.set(fields);
        	
        }
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
