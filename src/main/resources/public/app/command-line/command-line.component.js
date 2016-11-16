(function() {
    'use strict';

    // Global for storing the command line history so that it's present when the view changes
    var commandHistory = [];
    var commandHistoryPointer = 0;

    // Global for storing the command line contents when view changes
    var commandLineContents = '';

    // The play worker
    var w;

    // The stack that we're playing from
    var playStack = [];

    angular.module('xinity').component('commandline', {
        templateUrl: 'app/command-line/command-line.component.html',

        controller: function($scope, $http, $cookies, $location) {

            if ($cookies.get('apikey') !== undefined) {
                $http.get('/api/v1/' + $cookies.get('apikey') + '/validate')
                    .error(function(r) {
                        if (r.message === 'invalid') {
                            $location.path('/login');
                        } else {
                            console.log('api key is valid :D');
                        }
                    });
            } else {
                $location.path('/login');
            }

            var commandOutput = $('#command-output');
            var outputArea = $('#app-output');
            var inputArea = $('#user-input');
            var statusOutput = $('#command-status');

            // Move focus to the input field on load.
            inputArea.focus();

            // Load the previous command line contents into the output area
            outputArea.append(commandLineContents);

            var isPlaying = false;

            function waitToSend() {
                if (isPlaying) {
                    console.log('Waiting to post next message...')
                    setTimeout(function(){waitToSend()}, 100);
                } else {
                    w.postMessage({"one" : playStack[0], "two" : playStack[0]});
                }
            }

            function startWorker() {
                // Check for web worker support
                if (typeof(Worker) !== "undefined") {

                    // If we haven't already defined a web worker, create a new one.
                    if (typeof(w) == "undefined") {
                        w = new Worker("app/services/play.service.js");

                        isPlaying = true;

                        w.postMessage({"one" : playStack[0], "two" : playStack[0]});
                    } else {
                        waitToSend();
                    }

                    // When we recieve a worker message, remove the item it just dealt with from the stack
                    // and then check that there's not more to do
                    w.onmessage = function(event) {
                        var lastPlayed = playStack.shift();
                        console.log("Just played: " + lastPlayed[0] + " " + lastPlayed[1] + " for " + lastPlayed[2]);

                        if (event.data[0] == 'single') {
                            MIDI.noteOn(0, event.data[1], 127);
                            MIDI.noteOff(0, event.data[1], event.data[2] / 1000);
                        } else if (event.data[0] == 'chord') {
                            for (var j = 0; j < event.data[1].length; j++) {
                                MIDI.noteOn(0, event.data[1][j], 127);
                                MIDI.noteOff(0, event.data[1][j], event.data[2] / 1000);
                            }
                            //MIDI.chordOn(0, event.data[1], 127);
                            //MIDI.noteOff(0, event.data[1], event.data[2] / 1000);
                        }

                        if (playStack.length != 0) {
                            isPlaying = true;

                            w.postMessage({"one" : playStack[0], "two" : lastPlayed});
                        } else {
                            isPlaying = false;
                        }
                    };
                } else {
                    alert("Sorry! No Web Worker support.\nSounds will not play on this device :(");
                }
            }

            function handlePlay(object) {
                if (object.type == "single") {
                    if (object.note !== undefined) {
                        for (var i = 0; i < object.note.length; i++) {
                            playStack.push(["single", object.note[i][0], object.note[i][1]]);
                        }
                    } else if (object.silent !== undefined) {
                        for (var i = 0; i < object.note.length; i++) {
                            playStack.push(["single", object.note[i][0], object.note[i][1]]);
                        }
                    } else {
                        console.log("Unknown single play type");
                    }
                } else if (object.type == "straight") {
                    if (object.scale !== undefined) {
                        for (var i = 0; i < object.scale.length; i++) {
                            playStack.push(["single", object.scale[i][0], object.scale[i][1]]);
                        }
                    }
                } else if (object.type == "swing") {
                    if (object.scale !== undefined) {
                        for (var i = 0; i < object.scale.length; i++) {
                            playStack.push(["single", object.scale[i][0], object.scale[i][1]]);
                        }
                    }
                } else if (object.type == "interval") {
                    if (object.interval !== undefined) {
                        for (var i = 0; i < object.interval.length; i++) {
                            playStack.push(["single", object.interval[i][0], object.interval[i][1]]);
                        }
                    }
                } else if (object.type == "unison") {
                    if (object.chord !== undefined) {
                        var noteList = [];

                        for (var i = 0; i < object.chord.length; i++) {
                            noteList.push(object.chord[i][0]);
                        }

                        playStack.push(["chord", noteList, object.chord[0][1]]);
                    } else {
                        console.log("Unknown chord play type");
                    }
                } else if (object.type == "arpeggio") {
                    if (object.chord !== undefined) {
                        for (var i = 0; i < object.chord.length; i++) {
                            playStack.push(["single", object.chord[i][0], object.chord[i][1]]);
                        }
                    }
                } else {
                    console.log('Unknown object.type: ' + object.type);
                }

                // invoke the worker which will manage the play stack
                startWorker();
            }

            // Html Entity map
            var htmlMap = {
                "&": "&amp;",
                "<": "&lt;",
                ">": "&gt;",
                '"': '&quot;',
                "'": '&#39;',
                "/": '&#x2F;'
            };

            /**
             * Escape any Html in a string.
             */
            function escapeHtml(string) {
                return String(string).replace(/[&<>\/]/g, function (s) {
                    return htmlMap[s];
                });
            };

            /**
             * Update the scroll position in the output area to be the bottom.
             */
            function updateScroll() {
                commandOutput.scrollTop(commandOutput.prop('scrollHeight'));
            };

            /**
             * Tokenize's a command so that if syntax highlighting is turned on it gets fancy markup.
             */
            function tokenize(command) {
                var output = '';

                var tokenized = command.match(/[^(),]+|\(|\,|\)|"[^"]+"/g);

                output += '<span class="method">' + tokenized[0] + '</span>'

                for (var i = 1; i < tokenized.length; i++) {
                    var thistoken = tokenized[i];

                    // braces
                    if (thistoken.match(/[\(\)]/g)) {
                        output += '<span class="brace">' + thistoken + '</span>';

                    // commas
                    } else if (thistoken.match(/[,]/g)) {
                        output += '<span class="comma">' + thistoken + '</span>';

                    // string
                    } else if (thistoken.match(/"[^"]+"/g)) {
                        output += '<span class="string">' + thistoken + '</span>';

                    // digit
                    } else if (thistoken.match(/[0-9]+/g)) {
                        output += '<span class="digit">' + thistoken + '</span>';

                    // variable
                    } else {
                        output += '<span class="variable">' + thistoken + '</span>';
                    }
                }

                return '<li><span class="invoke">&gt;</span> ' + output + '</li>';
            };

            /**
             * Updates the status area for the command line so the user gets feedback
             */
            function updateStatus(status) {
                if (status == 'sending') {
                    statusOutput.html('<i class="fa fa-fw fa-refresh fa-spin text-primary"></i> Sending...');
                } else if (status == 'success') {
                    statusOutput.html('<i class="fa fa-fw fa-check text-success"></i> Success.');
                } else if (status == 'error') {
                    statusOutput.html('<i class="fa fa-fw fa-times text-danger"></i> Failed to contact server.');
                } else {
                    statusOutput.html('<i class="fa fa-fw fa-exclamation text-warning"></i> Something\'s gone wrong!');
                }
            };

            /**
             * These will be used for story W9 (command playback)
             */
            $scope.showPlaybackModal = function() {
                // no ops
            };

            $scope.playbackMoveToStart = function() {
                // no ops
            };

            $scope.playbackPlayOne = function() {
                // no ops
            };

            $scope.playbackPlayAll = function() {
                // no ops
            };

            $scope.playbackMoveToEnd = function() {
                // no ops
            };

            /**
             * Turn on/off syntax highlighting
             */
            $scope.toggleSyntax = function() {
                if (outputArea.is('.syntax')) {
                    outputArea.removeClass('syntax');
                } else {
                    outputArea.addClass('syntax');
                }
            };

            /**
             * Clear the output area
             */
            $scope.clearOutputArea = function () {
                outputArea.empty();
                commandLineContents = '';
            };

            /**
             * Show the command reference interface
             */
            $scope.showReference = function() {
                // no ops
            };

            /**
             * On arrow up function. Moves command history pointer up.
             */
            $scope.commandUp = function() {
                if (commandHistoryPointer != 0) {
                    commandHistoryPointer -= 1;
                }

                inputArea.val(commandHistory[commandHistoryPointer]);
            };

            /**
             * On arrow down function. Moves command history pointer down.
             */
            $scope.commandDown = function() {
                if (commandHistoryPointer < commandHistory.length) {
                    commandHistoryPointer += 1;
                }

                inputArea.val(commandHistory[commandHistoryPointer]);
            };

            /**
             * On enter key function.
             */
            $scope.executeCommand = function() {
                // Get the value of the input field
                var userCommand = inputArea.val();

                // If the input field wasn't empty
                if (userCommand.length > 0) {
                    // Escape characters that'll break our json object
                    var escapedCommand = userCommand.replace(/["'\/\\\{\}\[\]]/g, "\\$&");

                    // Package the escaped string it into a json object
                    var commandJson = '{"command": "' + escapedCommand + '"}';

                    // Append the command to the output area, update scroll and status
                    outputArea.append(tokenize(escapeHtml(userCommand)));
                    updateScroll();
                    updateStatus('sending');

                    // Push the command onto the command history
                    commandHistory.push(escapeHtml(userCommand));
                    commandHistoryPointer = commandHistory.length;

                    // Make API POST with the json
                    $http.post('/api/v1/' + $cookies.get('apikey') + '/command', commandJson)
                        .success(function(response) {
                            // Get the response result from the json
                            // ($http has already processed it into an object at this point)
                            var result = response.result;
                            var toPlay = response.play;

                            // If we got a play object then we need to make some noise!
                            if (toPlay !== undefined) {
                                handlePlay(response.play);
                            }

                            // Append response result to the output area, update scroll and status
                            outputArea.append('<li><span class="string">' + result + '</span></li>');
                            updateScroll();
                            updateStatus('success');

                            // Update our persistent command line output area
                            commandLineContents = outputArea.html();
                        })
                        .error(function(response) {
                            if (response == null) {
                                // If we get a null response then we'll assume that the connection was lost
                                updateStatus('error');
                            } else {
                                // If we get an API error back (which we shouldn't but is possible), then output it
                                outputArea.append('<li><span class="string">' + response.message + '</span></li>');
                                updateScroll();
                                updateStatus('other');
                            }

                            // Update our persistent command line output area
                            commandLineContents = outputArea.html();
                        });
                }

                // Reset the input field to be empty
                inputArea.val('');
            };
        }
    });
})();
