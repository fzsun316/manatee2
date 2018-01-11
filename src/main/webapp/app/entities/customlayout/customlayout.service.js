(function() {
    'use strict';
    angular
        .module('manateeApp')
        .factory('Customlayout', Customlayout);

    Customlayout.$inject = ['$resource', 'DateUtils'];

    function Customlayout ($resource, DateUtils) {
        var resourceUrl =  'api/customlayouts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.timestamp = DateUtils.convertLocalDateFromServer(data.timestamp);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.timestamp = DateUtils.convertLocalDateToServer(copy.timestamp);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.timestamp = DateUtils.convertLocalDateToServer(copy.timestamp);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
