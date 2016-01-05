 'use strict';

angular.module('collectorthrdApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-collectorthrdApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-collectorthrdApp-params')});
                }
                return response;
            }
        };
    });
