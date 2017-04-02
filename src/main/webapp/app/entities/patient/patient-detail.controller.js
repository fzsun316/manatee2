(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('PatientDetailController', PatientDetailController);

    PatientDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Patient', 'ReferralSource'];

    function PatientDetailController($scope, $rootScope, $stateParams, previousState, entity, Patient, ReferralSource) {
        var vm = this;

        vm.patient = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('manateeApp:patientUpdate', function(event, result) {
            vm.patient = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
