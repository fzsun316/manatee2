(function() {
    'use strict';

    angular
        .module('manateeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('staff', {
            parent: 'entity',
            url: '/staff',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Staff'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/staff/staff.html',
                    controller: 'StaffController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('staff-detail', {
            parent: 'entity',
            url: '/staff/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Staff'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/staff/staff-detail.html',
                    controller: 'StaffDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Staff', function($stateParams, Staff) {
                    return Staff.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'staff',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('staff-detail.edit', {
            parent: 'staff-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/staff/staff-dialog.html',
                    controller: 'StaffDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Staff', function(Staff) {
                            return Staff.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('staff.new', {
            parent: 'staff',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/staff/staff-dialog.html',
                    controller: 'StaffDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                role: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('staff', null, { reload: 'staff' });
                }, function() {
                    $state.go('staff');
                });
            }]
        })
        .state('staff.edit', {
            parent: 'staff',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/staff/staff-dialog.html',
                    controller: 'StaffDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Staff', function(Staff) {
                            return Staff.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('staff', null, { reload: 'staff' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('staff.delete', {
            parent: 'staff',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/staff/staff-delete-dialog.html',
                    controller: 'StaffDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Staff', function(Staff) {
                            return Staff.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('staff', null, { reload: 'staff' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
