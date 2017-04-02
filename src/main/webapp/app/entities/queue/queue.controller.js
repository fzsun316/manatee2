(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('QueueController', QueueController);

    QueueController.$inject = ['$scope', '$state', 'Queue'];

    function QueueController ($scope, $state, Queue) {
        var vm = this;

        vm.queues = [];

        loadAll();

        function loadAll() {
            Queue.query(function(result) {
                vm.queues = result;
                vm.searchQuery = null;
            });
        }
    }
})();
