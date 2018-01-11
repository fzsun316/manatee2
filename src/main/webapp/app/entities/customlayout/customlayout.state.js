(function() {
    'use strict';

    angular
        .module('manateeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customlayout', {
            parent: 'entity',
            url: '/customlayout',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Customlayouts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customlayout/customlayouts.html',
                    controller: 'CustomlayoutController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('customlayout-detail', {
            parent: 'customlayout',
            url: '/customlayout/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Customlayout'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customlayout/customlayout-detail.html',
                    controller: 'CustomlayoutDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Customlayout', function($stateParams, Customlayout) {
                    return Customlayout.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customlayout',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customlayout-detail.edit', {
            parent: 'customlayout-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customlayout/customlayout-dialog.html',
                    controller: 'CustomlayoutDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Customlayout', function(Customlayout) {
                            return Customlayout.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customlayout.new', {
            parent: 'customlayout',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customlayout/customlayout-dialog.html',
                    controller: 'CustomlayoutDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                timestamp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customlayout', null, { reload: 'customlayout' });
                }, function() {
                    $state.go('customlayout');
                });
            }]
        })
        .state('customlayout.edit', {
            parent: 'customlayout',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customlayout/customlayout-dialog.html',
                    controller: 'CustomlayoutDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Customlayout', function(Customlayout) {
                            return Customlayout.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customlayout', null, { reload: 'customlayout' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customlayout.delete', {
            parent: 'customlayout',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customlayout/customlayout-delete-dialog.html',
                    controller: 'CustomlayoutDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Customlayout', function(Customlayout) {
                            return Customlayout.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customlayout', null, { reload: 'customlayout' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
