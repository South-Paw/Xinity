(function() {
    'use strict';

    angular.module('xinity').component('login', {
        templateUrl: 'app/login/login.component.html',

        controller: function($scope, $http, $cookies, $location) {
            var loggingIn = false;
            var registering = false;

            var signinForm = $('#signinForm');
            var signinButton = $('#signinButton');

            var registrationForm = $('#registrationForm');
            var registrationExtra = $('#extraDetails');
            var registerButton = $('#registrationButton');

            $('#app-theme').removeClass('theme-light');
            $('#app-theme').removeClass('theme-dark');

            $('#app-theme').addClass('theme-dark');

            $scope.user = {};
            $scope.signup = {};

            if ($cookies.get('apikey') !== undefined) {
                $http.get('/api/v1/' + $cookies.get('apikey') + '/validate')
                    .success(function(r) {
                        if (r.message === 'valid') {
                            $location.path('/workbench/dashboard');
                        }
                    })
                    .error(function(r) {
                        if (r.message === 'invalid') {
                            $cookies.remove('apikey');
                        }
                    });
            }

            $scope.doLogin = function() {
                if (!loggingIn) {
                    $scope.loginError = undefined;

                    signinButton.addClass('disabled');
                    signinButton.html('Signing in... <i class="fa fa-fw fa-spinner fa-spin"></i>');

                    $http.post('/api/v1/login', $scope.user)
                        .success(function(data) {
                            loggingIn = true;

                            var currentTime = new Date();
                            var expiryTime = (currentTime.getTime() + 60 * 60000);
                            $cookies.put('apikey', data.success, {'expires': new Date(expiryTime)});

                            $location.path('/workbench/dashboard');
                        })
                        .error(function(data) {
                            if (data.message) {
                                loggingIn = false;
                                $scope.loginError = data.message;
                                signinButton.removeClass('disabled');
                                signinButton.html('Sign in');
                            }
                        });
                }
            }

            $scope.doRegistration = function() {
                if (!registering) {
                    $scope.registrationError = undefined;

                    registerButton.addClass('disabled');
                    registerButton.html('Registering... <i class="fa fa-fw fa-spinner fa-spin"></i>');

                    $http.post('/api/v1/register', $scope.signup)
                        .success(function(data) {
                            registering = true;

                            $scope.user = {}
                            $scope.user.username = $scope.signup.username

                            registerButton.removeClass('btn-xinity');
                            registerButton.addClass('btn-success');
                            registerButton.html('Registed! <i class="fa fa-fw fa-check"></i>');

                            setTimeout($scope.hideRegistration, 1000);

                            registering = false;
                        })
                        .error(function(data) {
                            if (data.message) {
                                registering = false;

                                $scope.registrationError = data.message;

                                registerButton.removeClass('disabled');
                                registerButton.html('Register');
                            }
                        });
                }
            }

            $scope.showRegistration = function() {
                registerButton.addClass('btn-xinity');
                registerButton.removeClass('btn-success');
                registerButton.removeClass('disabled');
                registerButton.html('Register');

                $scope.signup = {}
                $scope.registrationError = undefined;

                signinForm.fadeOut(200, function(){
                    registrationForm.fadeIn(200);
                });
            }

            $scope.hideRegistration = function() {
                $scope.loginError = undefined;

                registrationForm.fadeOut(200, function(){
                    signinForm.fadeIn(200);
                });
            }

            $scope.showExtraDetails = function() {
                registrationExtra.slideToggle();
            }
        }
    });
})();
