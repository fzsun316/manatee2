(function() {
    'use strict';

    angular
        .module('manateeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('master-log', {
            parent: 'entity',
            url: '/master-log',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Master Log'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/master-log/master-log.html',
                    controller: 'MasterLogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
    }

})();
