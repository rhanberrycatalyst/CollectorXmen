'use strict';

angular.module('collectorthrdApp')
    .run(function($rootScope, $injector, $location, $state,  Auth, toState, toParams, $window, archive_collectible){
    	
    
    	var Auth = $injector.get('Auth');
        var $state = $injector.get('$state');
        var to = $rootScope.toState;
        var params = $rootScope.toStateParams;
        Auth.logout();
        $rootScope.previousStateName = to;
        $rootScope.previousStateNameParams = params;
        $state.go('login');
        
        $rootScope.$on('$stateChangeSuccess',  function(toState) {
        	$rootScope.previousStateName = to;
            $rootScope.previousStateNameParams = params;
        });
        
        
    	
    });