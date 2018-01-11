(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('CustomlayoutDetailController', CustomlayoutDetailController);

    CustomlayoutDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Customlayout', 'User'];

    function CustomlayoutDetailController($scope, $rootScope, $stateParams, previousState, entity, Customlayout, User) {
        var vm = this;

        vm.customlayout = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('manateeApp:customlayoutUpdate', function(event, result) {
            vm.customlayout = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
