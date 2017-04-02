(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('ReferralSourceDetailController', ReferralSourceDetailController);

    ReferralSourceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ReferralSource'];

    function ReferralSourceDetailController($scope, $rootScope, $stateParams, previousState, entity, ReferralSource) {
        var vm = this;

        vm.referralSource = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('manateeApp:referralSourceUpdate', function(event, result) {
            vm.referralSource = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
