'use strict';

angular.module('collectorthrdApp')
    .factory('ColorSearch', function ($resource) {
        return $resource('api/_search/colors/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
