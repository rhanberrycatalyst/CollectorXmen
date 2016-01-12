'use strict';

angular.module('collectorthrdApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('condition', {
                parent: 'entity',
                url: '/conditions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Conditions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/condition/conditions.html',
                        controller: 'ConditionController'
                    }
                },
                resolve: {
                }
            })
            .state('condition.detail', {
                parent: 'entity',
                url: '/condition/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Condition'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/condition/condition-detail.html',
                        controller: 'ConditionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Condition', function($stateParams, Condition) {
                        return Condition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('condition.new', {
                parent: 'condition',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/condition/condition-dialog.html',
                        controller: 'ConditionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    condition: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('condition', null, { reload: true });
                    }, function() {
                        $state.go('condition');
                    })
                }]
            })
            .state('condition.edit', {
                parent: 'condition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/condition/condition-dialog.html',
                        controller: 'ConditionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Condition', function(Condition) {
                                return Condition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('condition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('condition.delete', {
                parent: 'condition',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/condition/condition-delete-dialog.html',
                        controller: 'ConditionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Condition', function(Condition) {
                                return Condition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('condition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
