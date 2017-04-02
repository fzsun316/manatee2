(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('ReferralSourceDialogController', ReferralSourceDialogController);

    ReferralSourceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ReferralSource'];

    function ReferralSourceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ReferralSource) {
        var vm = this;

        vm.referralSource = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.referralSource.id !== null) {
                ReferralSource.update(vm.referralSource, onSaveSuccess, onSaveError);
            } else {
                ReferralSource.save(vm.referralSource, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('manateeApp:referralSourceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
