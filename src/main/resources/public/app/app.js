(function() {
    'use strict';

    var app = angular.module('xinity', [
        'ui.router',
        'ngCookies'
    ]);

    app.config(function($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/login');

        var states = [
            {
                name: 'login',
                url: '/login',
                component: 'login'
            },
            {
                name: 'workbench',
                url: '/workbench',
                component: 'workbench'
            },
            {
                name: 'workbench.dashboard',
                url: '/dashboard',
                component: 'dashboard'
            },
            {
                name: 'workbench.commandline',
                url: '/command-line',
                component: 'commandline'
            },
            {
                name: 'workbench.practiceschedules',
                url: '/practice-schedules',
                component: 'practiceschedules'
            }
        ];

        states.forEach(function(state) {
            $stateProvider.state(state);
        });
    });
})();
