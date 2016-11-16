(function() {
    'use strict';

    angular.module('xinity').component('workbench', {
        templateUrl: 'app/workbench/workbench.component.html',

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

            $http.get('/api/v1/' + $cookies.get('apikey') + '/user')
                .success(function(data) {
                    $scope.user = data;

                    // Manually create this otherwise the objects are all linked together which creates issues...
                    $scope.profileSettings = {
                        name: data.name,
                        email: data.email,
                        location: data.location,
                        bio: data.bio,
                        imgUrl: data.imgUrl,
                        theme: data.theme,
                        //tempo: data.tempo,
                        apikey: $cookies.get('apikey')
                    };

                    // If we don't have a profile picture set, then give em the default one.
                    if ($scope.user.imgUrl === undefined) {
                        $scope.user.imgUrl = 'content/img/unknown_user.png';
                    }

                    appTheme.removeClass('theme-dark');

                    if ($scope.user.theme === 'dark') {
                        appTheme.addClass('theme-dark');
                    } else if ($scope.user.theme === 'light') {
                        appTheme.addClass('theme-light');
                    } else {
                        appTheme.addClass('theme-dark');
                    }
                });

            var appTheme = $('#app-theme');
            var appSidebar = $('#app-sidebar');
            var appTopbar = $('#app-topbar');
            var appView = $('#app-view');

            var sidebarShown = true;

            appTheme.addClass('theme-dark');
            hideSidebarIfMobile();

            function hideSidebarIfMobile() {
                if ($(document).width() <= 720) {
                    sidebarHide();
                }
            }

            $scope.hideSidebarIfMobile = hideSidebarIfMobile;

            $scope.hideSidebar = function() {
                if (sidebarShown) {
                    sidebarHide();
                } else {
                    sidebarShow();
                }
            }

            function sidebarHide() {
                appSidebar.hide();

                appTheme.removeClass('bump-out');
                appTopbar.css('width', '100%');
                appView.css('width', '100%');

                sidebarShown = false;
            }

            function sidebarShow() {
                appSidebar.show();

                appTheme.addClass('bump-out');
                appTopbar.css('width', 'calc(100% - 100px)');
                appView.css('width', 'calc(100% - 100px)');

                sidebarShown = true;
            }

            $scope.sidebarItems = [
                {
                    sref: 'workbench.dashboard',
                    icon: 'home',
                    title: 'Dashboard'
                },
                {
                    sref: 'workbench.commandline',
                    icon: 'terminal',
                    title: 'Command Line'
                },
                {
                    sref: 'workbench.practiceschedules',
                    icon: 'book',
                    title: 'Practice Schedules'
                },/*
                {
                    sref: 'workbench.tutors',
                    icon: 'graduation-cap',
                    title: 'Tutors'
                },
                {
                    sref: 'workbench.scores',
                    icon: 'line-chart',
                    title: 'Scores and Progress'
                }*/
            ];

            $scope.showProfile = function() {
                $('#profileModal').modal('show');
            }

            $scope.settingsSidebarMenu = [
                {
                    name: 'Profile',
                    id: 'profile'
                },
                {
                    name: 'Application',
                    id: 'app'
                },
                {
                    name: 'Theme',
                    id: 'theme'
                },
                {
                    name: 'API',
                    id: 'api'
                }
            ];

            $scope.showSettings = function() {
                $('#settingsModal').modal('show');

                $('#viewProfileTab').addClass('active');
                $('#viewApplicationTab').removeClass('active');
                $('#viewThemesTab').removeClass('active');
                $('#viewAPITab').removeClass('active');

                $('#tabProfile').show();
                $('#tabApplication').hide();
                $('#tabThemes').hide();
                $('#tabAPI').hide();
            }

            $scope.showTab = function(event, tabName) {
                if (tabName === 'profile') {
                    $('#viewProfileTab').addClass('active');
                    $('#viewApplicationTab').removeClass('active');
                    $('#viewThemesTab').removeClass('active');
                    $('#viewAPITab').removeClass('active');

                    $('#tabProfile').show();
                    $('#tabApplication').hide();
                    $('#tabThemes').hide();
                    $('#tabAPI').hide();
                } else if (tabName === 'application') {
                    $('#viewProfileTab').removeClass('active');
                    $('#viewApplicationTab').addClass('active');
                    $('#viewThemesTab').removeClass('active');
                    $('#viewAPITab').removeClass('active');

                    $('#tabProfile').hide();
                    $('#tabApplication').show();
                    $('#tabThemes').hide();
                    $('#tabAPI').hide();
                } else if (tabName === 'theme') {
                    $('#viewProfileTab').removeClass('active');
                    $('#viewApplicationTab').removeClass('active');
                    $('#viewThemesTab').addClass('active');
                    $('#viewAPITab').removeClass('active');

                    $('#tabProfile').hide();
                    $('#tabApplication').hide();
                    $('#tabThemes').show();
                    $('#tabAPI').hide();
                } else if (tabName === 'api') {
                    $('#viewProfileTab').removeClass('active');
                    $('#viewApplicationTab').removeClass('active');
                    $('#viewThemesTab').removeClass('active');
                    $('#viewAPITab').addClass('active');

                    $('#tabProfile').hide();
                    $('#tabApplication').hide();
                    $('#tabThemes').hide();
                    $('#tabAPI').show();
                }
            }

            $scope.resetApiKey = function() {
                var response = confirm('Are you sure you wish to reset your API key?\nYou\'ll be logged out and have to log in again.');

                if (response == true) {
                    // reset api key
                    $http.post('/api/v1/' + $cookies.get('apikey') + '/resetApiKey');

                    $cookies.remove('apikey');

                    location.reload();
                }
            }

            $scope.saveSettings = function() {
                var newName = $scope.profileSettings.name;
                var newEmail = $scope.profileSettings.email;
                var newLocation = $scope.profileSettings.location;
                var newBio = $scope.profileSettings.bio;
                var newImgUrl = $scope.profileSettings.imgUrl;
                var newTempo = $scope.profileSettings.tempo;
                var newTheme = $scope.profileSettings.theme;

                var postBack = {
                    name: newName,
                    email: newEmail,
                    location: newLocation,
                    bio: newBio,
                    imgUrl: newImgUrl,
                    tempo: newTempo,
                    theme: newTheme
                };

                // Remove special characters that cause problems
                // Don't worry; the API is also doing sanity checking...
                for (var key in postBack) {
                    if (postBack.hasOwnProperty(key)) {
                        var oldValue = postBack[key];
                        if (oldValue !== undefined && typeof oldValue === 'string') {
                            postBack[key] = (oldValue).replace(/["'\/\\\{\}\[\]=`;]/g, "");
                        }
                    }
                }

                $http.post('/api/v1/' + $cookies.get('apikey') + '/user', postBack)
                    .then(function(data) {
                        location.reload();
                    });
            }

            $scope.doLogout = function() {
                var response = confirm('Are you sure you want to log out?\nAny unsaved data or progress will be lost!');

                if (response == true) {
                    $cookies.remove('apikey');
                    $location.path('/login');
                }
            }

            $scope.switchTheme = function() {
                if (appTheme.is('.theme-dark')) {
                    appTheme.removeClass('theme-dark');
                    appTheme.addClass('theme-light');
                } else {
                    appTheme.removeClass('theme-light');
                    appTheme.addClass('theme-dark');
                }
            }
        }
    });
})();
