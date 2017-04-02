(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('StaffDetailController', StaffDetailController);

    StaffDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Staff', 'Team'];

    function StaffDetailController($scope, $rootScope, $stateParams, previousState, entity, Staff, Team) {
        var vm = this;

        vm.staff = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('manateeApp:staffUpdate', function(event, result) {
            vm.staff = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
