(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('StaffController', StaffController);

    StaffController.$inject = ['$scope', '$state', 'Staff'];

    function StaffController ($scope, $state, Staff) {
        var vm = this;

        vm.staff = [];

        loadAll();

        function loadAll() {
            Staff.query(function(result) {
                vm.staff = result;
                vm.searchQuery = null;
            });
        }
    }
})();
