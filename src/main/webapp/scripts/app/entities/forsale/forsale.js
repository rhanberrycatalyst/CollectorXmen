'use strict';

angular.module('collectorthrdApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('forsale', {
                parent: 'entity',
                url: '/forsales',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Forsales'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/forsale/forsales.html',
                        controller: 'ForsaleController'
                    }
                },
                resolve: {
                }
            })
            .state('forsale.detail', {
                parent: 'entity',
                url: '/forsale/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Forsale'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/forsale/forsale-detail.html',
                        controller: 'ForsaleDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Forsale', function($stateParams, Forsale) {
                        return Forsale.get({id : $stateParams.id});
                    }]
                }
            })
            .state('forsale.new', {
                parent: 'forsale',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/forsale/forsale-dialog.html',
                        controller: 'ForsaleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    forsale: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('forsale', null, { reload: true });
                    }, function() {
                        $state.go('forsale');
                    })
                }]
            })
            .state('forsale.edit', {
                parent: 'forsale',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/forsale/forsale-dialog.html',
                        controller: 'ForsaleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Forsale', function(Forsale) {
                                return Forsale.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('forsale', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('forsale.delete', {
                parent: 'forsale',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/forsale/forsale-delete-dialog.html',
                        controller: 'ForsaleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Forsale', function(Forsale) {
                                return Forsale.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('forsale', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
