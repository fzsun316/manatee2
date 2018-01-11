(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('CustomlayoutDialogController', CustomlayoutDialogController);

    CustomlayoutDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Customlayout', 'User'];

    function CustomlayoutDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Customlayout, User) {
        var vm = this;

        vm.customlayout = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customlayout.id !== null) {
                Customlayout.update(vm.customlayout, onSaveSuccess, onSaveError);
            } else {
                Customlayout.save(vm.customlayout, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('manateeApp:customlayoutUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.timestamp = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
