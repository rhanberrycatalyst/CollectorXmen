'use strict';

angular.module('collectorthrdApp')
    .factory('ConditionSearch', function ($resource) {
        return $resource('api/_search/conditions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
