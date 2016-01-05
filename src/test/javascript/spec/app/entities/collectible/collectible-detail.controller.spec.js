'use strict';

describe('Controller Tests', function() {

    describe('Collectible Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCollectible, MockCategory, MockCondition, MockColor, MockKeyword, MockForsale;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCollectible = jasmine.createSpy('MockCollectible');
            MockCategory = jasmine.createSpy('MockCategory');
            MockCondition = jasmine.createSpy('MockCondition');
            MockColor = jasmine.createSpy('MockColor');
            MockKeyword = jasmine.createSpy('MockKeyword');
            MockForsale = jasmine.createSpy('MockForsale');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Collectible': MockCollectible,
                'Category': MockCategory,
                'Condition': MockCondition,
                'Color': MockColor,
                'Keyword': MockKeyword,
                'Forsale': MockForsale
            };
            createController = function() {
                $injector.get('$controller')("CollectibleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'collectorthrdApp:collectibleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
