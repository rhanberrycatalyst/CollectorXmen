'use strict';

angular.module('collectorthrdApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('archive_collectible', {
                parent: 'entity',
                url: '/collectibles',
                data: {
                    authorities: ['ROLE_OWNER'],
                    pageTitle: 'Collectibles'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/components/archive/archive_collectibles.html',
                        controller: 'CollectibleController'
                    }
                },
                resolve: {
                }
            })
            .state('archive_collectible.detail', {
                parent: 'entity',
                url: '/collectible/{id}',
                data: {
                    authorities: ['ROLE_OWNER'],
                    pageTitle: 'Collectible'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/collectible/collectible-detail.html',
                        controller: 'CollectibleDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Collectible', function($stateParams, Collectible) {
                        return Collectible.get({id : $stateParams.id});
                    }]
                }
            })
    });
