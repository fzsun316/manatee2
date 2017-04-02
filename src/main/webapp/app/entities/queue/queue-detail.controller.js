(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('QueueDetailController', QueueDetailController);

    QueueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Queue', 'Patient', 'Team'];

    function QueueDetailController($scope, $rootScope, $stateParams, previousState, entity, Queue, Patient, Team) {
        var vm = this;

        vm.queue = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('manateeApp:queueUpdate', function(event, result) {
            vm.queue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
