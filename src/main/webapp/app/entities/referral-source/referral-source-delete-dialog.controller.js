(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('ReferralSourceDeleteController',ReferralSourceDeleteController);

    ReferralSourceDeleteController.$inject = ['$uibModalInstance', 'entity', 'ReferralSource'];

    function ReferralSourceDeleteController($uibModalInstance, entity, ReferralSource) {
        var vm = this;

        vm.referralSource = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ReferralSource.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
