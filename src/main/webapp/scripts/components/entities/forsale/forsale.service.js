'use strict';

angular.module('collectorthrdApp')
    .factory('Forsale', function ($resource, DateUtils) {
        return $resource('api/forsales/:id', {}, {
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
