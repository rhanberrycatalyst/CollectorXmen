'use strict';

angular.module('collectorthrdApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


