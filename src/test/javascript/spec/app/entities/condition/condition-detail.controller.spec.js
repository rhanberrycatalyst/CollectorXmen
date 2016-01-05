'use strict';

describe('Controller Tests', function() {

    describe('Condition Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCondition, MockCollectible;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCondition = jasmine.createSpy('MockCondition');
            MockCollectible = jasmine.createSpy('MockCollectible');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Condition': MockCondition,
                'Collectible': MockCollectible
            };
            createController = function() {
                $injector.get('$controller')("ConditionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'collectorthrdApp:conditionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
