'use strict';

angular.module('collectorthrdApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('collectible', {
                parent: 'entity',
                url: '/collectibles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Collectibles'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/collectible/collectibles.html',
                        controller: 'CollectibleController'
                    }
                },
                resolve: {
                }
            })
            .state('collectible.detail', {
                parent: 'entity',
                url: '/collectible/{id}',
                data: {
                    authorities: ['ROLE_USER'],
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
            .state('collectible.new', {
                parent: 'collectible',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/collectible/collectible-dialog.html',
                        controller: 'CollectibleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    age: null,
                                    cataloguenumber: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('collectible', null, { reload: true });
                    }, function() {
                        $state.go('collectible');
                    })
                }]
            })
            .state('collectible.search', {
                parent: 'collectible',
                url: '/search',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/collectible/collectible-search.html',
                        controller: 'CollectibleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    age: null,
                                    cataloguenumber: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('collectible', null, { reload: true });
                    }, function() {
                        $state.go('collectible');
                    })
                }]
            })
            .state('collectible.edit', {
                parent: 'collectible',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/collectible/collectible-dialog.html',
                        controller: 'CollectibleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Collectible', function(Collectible) {
                                return Collectible.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('collectible', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('collectible.delete', {
                parent: 'collectible',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/collectible/collectible-delete-dialog.html',
                        controller: 'CollectibleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Collectible', function(Collectible) {
                                return Collectible.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('collectible', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
