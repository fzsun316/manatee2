(function() {
    'use strict';

    angular
        .module('manateeApp')
        .controller('CustomlayoutController', CustomlayoutController);

    CustomlayoutController.$inject = ['Customlayout'];

    function CustomlayoutController(Customlayout) {

        var vm = this;

        vm.customlayouts = [];

        loadAll();

        function loadAll() {
            Customlayout.query(function(result) {
                vm.customlayouts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
