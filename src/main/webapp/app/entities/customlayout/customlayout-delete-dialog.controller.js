(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('CustomlayoutDeleteController',CustomlayoutDeleteController);

    CustomlayoutDeleteController.$inject = ['$uibModalInstance', 'entity', 'Customlayout'];

    function CustomlayoutDeleteController($uibModalInstance, entity, Customlayout) {
        var vm = this;

        vm.customlayout = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Customlayout.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
