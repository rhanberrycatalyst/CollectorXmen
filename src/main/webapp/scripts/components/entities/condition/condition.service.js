'use strict';

angular.module('collectorthrdApp')
    .factory('Condition', function ($resource, DateUtils) {
        return $resource('api/conditions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
