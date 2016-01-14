'use strict';

angular.module('collectorthrdApp')
    .controller('NavbarController', function ($rootScope, $scope, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
        
        $scope.logarchive = function () {
        	        	
        	Auth.logarchive();
        	$state.go('archive_collectible')
        }
    });
