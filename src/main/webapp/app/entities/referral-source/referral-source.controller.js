(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('ReferralSourceController', ReferralSourceController);

    ReferralSourceController.$inject = ['$scope', '$state', 'ReferralSource'];

    function ReferralSourceController ($scope, $state, ReferralSource) {
        var vm = this;

        vm.referralSources = [];

        loadAll();

        function loadAll() {
            ReferralSource.query(function(result) {
                vm.referralSources = result;
                vm.searchQuery = null;
            });
        }
    }
})();
