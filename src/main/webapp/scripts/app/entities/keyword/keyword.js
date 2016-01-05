'use strict';

angular.module('collectorthrdApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('keyword', {
                parent: 'entity',
                url: '/keywords',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Keywords'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/keyword/keywords.html',
                        controller: 'KeywordController'
                    }
                },
                resolve: {
                }
            })
            .state('keyword.detail', {
                parent: 'entity',
                url: '/keyword/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Keyword'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/keyword/keyword-detail.html',
                        controller: 'KeywordDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Keyword', function($stateParams, Keyword) {
                        return Keyword.get({id : $stateParams.id});
                    }]
                }
            })
            .state('keyword.new', {
                parent: 'keyword',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/keyword/keyword-dialog.html',
                        controller: 'KeywordDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    keyword: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('keyword', null, { reload: true });
                    }, function() {
                        $state.go('keyword');
                    })
                }]
            })
            .state('keyword.edit', {
                parent: 'keyword',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/keyword/keyword-dialog.html',
                        controller: 'KeywordDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Keyword', function(Keyword) {
                                return Keyword.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('keyword', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('keyword.delete', {
                parent: 'keyword',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/keyword/keyword-delete-dialog.html',
                        controller: 'KeywordDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Keyword', function(Keyword) {
                                return Keyword.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('keyword', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
