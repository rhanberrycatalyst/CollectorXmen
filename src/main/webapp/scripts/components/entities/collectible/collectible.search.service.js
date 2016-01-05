'use strict';

angular.module('collectorthrdApp')
    .factory('CollectibleSearch', function ($resource) {
        return $resource('api/_search/collectibles/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
