'use strict';

describe('Controller Tests', function() {

    describe('Keyword Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKeyword, MockCollectible;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKeyword = jasmine.createSpy('MockKeyword');
            MockCollectible = jasmine.createSpy('MockCollectible');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Keyword': MockKeyword,
                'Collectible': MockCollectible
            };
            createController = function() {
                $injector.get('$controller')("KeywordDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'collectorthrdApp:keywordUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
