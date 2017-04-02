(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('StaffDeleteController',StaffDeleteController);

    StaffDeleteController.$inject = ['$uibModalInstance', 'entity', 'Staff'];

    function StaffDeleteController($uibModalInstance, entity, Staff) {
        var vm = this;

        vm.staff = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Staff.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
