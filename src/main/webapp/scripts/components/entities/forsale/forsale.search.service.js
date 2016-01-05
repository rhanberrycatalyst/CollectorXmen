'use strict';

angular.module('collectorthrdApp')
    .factory('ForsaleSearch', function ($resource) {
        return $resource('api/_search/forsales/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
