'use strict';

angular.module('collectorthrdApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('archivelogin', {
                parent: 'account',
                url: '/archivelogin',
                data: {
                    authorities: [],
                    pageTitle: 'ARCHIVE Sign in'
                },
                views: {
                    'content@': {
                        templateUrl: '\scripts\components\archive\archivelogin.html',
                        controller: 'ArchiveLoginController'
                    }
                },
                });
    });
