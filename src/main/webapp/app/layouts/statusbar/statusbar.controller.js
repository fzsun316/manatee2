(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('StatusbarController', StatusbarController);

    StatusbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', 'ChatService', '$scope',  '$timeout'];

    function StatusbarController ($state, Auth, Principal, ProfileService, LoginService, ChatService, $scope, $timeout) {
        var vm = this;
        $scope.isRefreshing = false;

        ChatService.receive().then(null, null, function(message) {
            $scope.isRefreshing = true;
            $timeout(function(){$scope.isRefreshing = false; console.log("back");}, 1000);  
        });

        // vm.isNavbarCollapsed = true;
        // vm.isAuthenticated = Principal.isAuthenticated;

        // ProfileService.getProfileInfo().then(function(response) {
        //     vm.inProduction = response.inProduction;
        //     vm.swaggerEnabled = response.swaggerEnabled;
        // });

        // vm.login = login;
        // vm.logout = logout;
        // vm.toggleNavbar = toggleNavbar;
        // vm.collapseNavbar = collapseNavbar;
        // vm.$state = $state;

        // function login() {
        //     collapseNavbar();
        //     LoginService.open();
        // }

        // function logout() {
        //     collapseNavbar();
        //     Auth.logout();
        //     $state.go('home');
        // }

        // function toggleNavbar() {
        //     vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        // }

        // function collapseNavbar() {
        //     vm.isNavbarCollapsed = true;
        // }
    }
})();
