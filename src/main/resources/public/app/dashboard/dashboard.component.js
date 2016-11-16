(function() {
    'use strict';

    // Global version
    var app = null;

    angular.module('xinity').component('dashboard', {
        templateUrl: 'app/dashboard/dashboard.component.html',

        controller: function($scope, $http, $cookies, $location) {

            if ($cookies.get('apikey') !== undefined) {
                $http.get('/api/v1/' + $cookies.get('apikey') + '/validate')
                    .error(function(r) {
                        if (r.message === 'invalid') {
                            $location.path('/login');
                        }
                    });
            } else {
                $location.path('/login');
            }

            /**
             * Get the application version from app route.
             * Uses the global so we don't make a request every time this controller is loaded.
             */
            if (app == null) {
                $http.get('/api/v1/app')
                    .success(function(response) {
                        app = response;

                        $scope.appVersion = 'v' + app.version;
                    })
                    .error(function() {
                        app = null;
                        $scope.appVersion = '';
                    });
            } else {
                $scope.appVersion = 'v' + app.version;
            }
        }
    });
})();
