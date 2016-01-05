'use strict';

describe('Controller Tests', function() {

    describe('Forsale Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockForsale, MockCollectible;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockForsale = jasmine.createSpy('MockForsale');
            MockCollectible = jasmine.createSpy('MockCollectible');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Forsale': MockForsale,
                'Collectible': MockCollectible
            };
            createController = function() {
                $injector.get('$controller')("ForsaleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'collectorthrdApp:forsaleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
