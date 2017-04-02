(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('QueueDeleteController',QueueDeleteController);

    QueueDeleteController.$inject = ['$uibModalInstance', 'entity', 'Queue'];

    function QueueDeleteController($uibModalInstance, entity, Queue) {
        var vm = this;

        vm.queue = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Queue.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
