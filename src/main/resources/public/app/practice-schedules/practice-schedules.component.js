(function() {
    'use strict';

    // The play worker
    var w;

    // The stack that we're playing from
    var playStack = [];

    angular.module('xinity').component('practiceschedules', {
        templateUrl: 'app/practice-schedules/practice-schedules.component.html',

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

            $scope.loading = true;

            $http.get('/api/v1/' + $cookies.get('apikey') + '/schedules')
                .success(function(data) {
                    $scope.scheduleList = data.schedules;
                })
                .finally(function() {
                    $scope.loading = false;
                });

            function clone(obj) {
                if (null == obj || "object" != typeof obj) return obj;
                var copy = obj.constructor();
                for (var attr in obj) {
                    if (obj.hasOwnProperty(attr)) copy[attr] = obj[attr];
                }
                return copy;
            }

            function updateStar(starId, changeTo) {
                if (changeTo === 'lit') {
                    $('#star' + starId).addClass('lit');
                    $('#star' + starId).children().removeClass('fa-star-o');
                    $('#star' + starId).children().addClass('fa-star');
                } else {
                    $('#star' + starId).removeClass('lit');
                    $('#star' + starId).children().addClass('fa-star-o');
                    $('#star' + starId).children().removeClass('fa-star');
                }
            }

            function makeStars(count) {
                var starHtml = '<ul class="rating">';

                var numUnfilled = 5 - count;

                for (var i = 0; i < count; i++) {
                    starHtml += '<li class="star lit"><i class="fa fa-fw fa-star"></i></li>';
                }

                for (var i = 0; i < numUnfilled; i++) {
                    starHtml += '<li class="star"><i class="fa fa-fw fa-star-o"></i></li>';
                }

                starHtml += '</ul>'

                return starHtml;
            }

            function randomRatingQuestion() {
                var list = [
                    'How\'d that go?',
                    'How was that?',
                    'How\'d you find that?'
                ];

                var randomNumber = Math.floor((Math.random() * list.length));

                console.log('random text string ' + randomNumber);
                console.log(list[randomNumber]);

                return list[randomNumber];
            }

            function randomIntegerUpTo(maxInt) {
                return Math.floor((Math.random() * maxInt) + 1);
            }

            function shuffle(a) {
                var j, x, i;
                for (i = a.length; i; i--) {
                    j = Math.floor(Math.random() * i);
                    x = a[i - 1];
                    a[i - 1] = a[j];
                    a[j] = x;
                }
            }

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

            var isPlaying = false;

            var numberOfQuestions = 1;
            var questionBank = undefined;

            // Create schedule

            $scope.createSchedule = function() {
                $('#addScheduleModal').modal('show');

                $scope.newSchedule = {
                    name: '',
                    difficulty: 'normal',
                    //tempo: 120,
                    random: false
                };

                numberOfQuestions = 1;
                questionBank = {}

                $scope.submissionError = undefined;
                $('#newScheduleQuestions').empty();

                $('#saveNewScheduleButton').removeClass('disabled');
                $('#saveNewScheduleButton').addClass('btn-xinity');
                $('#saveNewScheduleButton').removeClass('btn-success');
                $('#saveNewScheduleButton').html('Save');
            }

            $scope.addScheduleNewQuestion = function(thisType) {
                questionBank[numberOfQuestions] = {
                    type: thisType
                };

                var html = '';

                if (thisType === 'chord') {
                    html = `<div class="scheduleQuestion chord" id="question` + numberOfQuestions + `">
                        <h5>Chord Question <a id="removeQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                        <div class="form-group">
                            <label for="chordNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                            <div class="col-sm-3"><input id="chordNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="5"></div>
                        </div>
                        <div class="form-group">
                            <label for="chordRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                            <div class="col-sm-2"><input id="chordRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="C4"></div>
                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                            <div class="col-sm-2"><input id="chordRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="C5"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Chord Styles</label>
                            <div class="col-sm-4">
                                <div class="checkbox"><label><input type="checkbox" id="chordStyleUnison` + numberOfQuestions + `" value="unison" checked> Unison</label></div>
                                <div class="checkbox"><label><input type="checkbox" id="chordStyleArpeggio` + numberOfQuestions + `" value="arpeggio"> Arpeggio</label></div>
                            </div>
                        </div>
                    </div>`;
                } else if (thisType === 'interval') {
                    html = `<div class="scheduleQuestion interval" id="question` + numberOfQuestions + `">
                        <h5>Interval Questions <a id="removeQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                        <div class="form-group">
                            <label for="intervalNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                            <div class="col-sm-3"><input id="intervalNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="5"></div>
                        </div>
                        <div class="form-group">
                            <label for="intervalRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                            <div class="col-sm-2"><input id="intervalRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="C4"></div>
                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                            <div class="col-sm-2"><input id="intervalRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="C5"></div>
                        </div>
                    </div>`;
                } else if (thisType === 'note') {
                    html = `<div class="scheduleQuestion note" id="question` + numberOfQuestions + `">
                        <h5>Note Questions <a id="removeQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                        <div class="form-group">
                            <label for="noteNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                            <div class="col-sm-3"><input id="noteNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="5"></div>
                        </div>
                        <div class="form-group">
                            <label for="noteRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                            <div class="col-sm-2"><input id="noteRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="C4"></div>
                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                            <div class="col-sm-2"><input id="noteRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="C5"></div>
                        </div>
                    </div>`;
                } else if (thisType === 'scale') {
                    html = `<div class="scheduleQuestion scale" id="question` + numberOfQuestions + `">
                        <h5>Scale Questions <a id="removeQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                        <div class="form-group">
                            <label for="scaleNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                            <div class="col-sm-3"><input id="scaleNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="5"></div>
                        </div>
                        <div class="form-group">
                            <label for="scaleRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                            <div class="col-sm-2"><input id="scaleRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="C4"></div>
                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                            <div class="col-sm-2"><input id="scaleRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="C5"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Scale Direction</label>
                            <div class="col-sm-4">
                                <div class="checkbox"><label><input type="checkbox" id="scaleDirectionUp` + numberOfQuestions + `" checked> Up</label></div>
                                <div class="checkbox"><label><input type="checkbox" id="scaleDirectionDown` + numberOfQuestions + `"> Down</label></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="scaleNumOctaves` + numberOfQuestions + `" class="col-sm-3 control-label"># of Octaves</label>
                            <div class="col-sm-3"><input id="scaleNumOctaves` + numberOfQuestions + `" class="form-control" type="text" value="1"></div>
                        </div>
                        <div class="form-group">
                            <label for="scalePlayStyle` + numberOfQuestions + `" class="col-sm-3 control-label">Play Style</label>
                            <div class="col-sm-4">
                                <select id="scalePlayStyle` + numberOfQuestions + `" class="form-control" type="text">
                                    <option value="straight" selected>Straight</option>
                                    <option value="swing">Swing</option>
                                </select>
                            </div>
                        </div>
                    </div>`;
                }

                $('#newScheduleQuestions').append(html);

                $('#removeQuestion' + numberOfQuestions).click(function(event) {
                    var thisId = this.id;
                    thisId = thisId.match(/\d+/)[0];

                    $(this).parent().parent().remove();
                    delete questionBank[thisId];
                });

                numberOfQuestions++;
            }

            $scope.cancelNewSchedule = function() {
                $('#addScheduleModal').modal('hide');
                $('#newScheduleQuestions').empty();
            }

            $scope.saveNewSchedule = function() {
                var atLeastOneQuestion = false;
                $scope.submissionError = undefined;

                if ($scope.newSchedule.name === "") {
                    $scope.submissionError = "Name cannot be empty";
                    return;
                }

                for (var i = 1; i < numberOfQuestions; i++) {
                    if (questionBank[i] != undefined) {
                        atLeastOneQuestion = true;
                        var type = questionBank[i]['type'];

                        if (type === 'chord') {
                            var quantity = parseInt($('#' + type + 'NumQuestions' + i).val());
                            var unison = $('#' + type + 'StyleUnison' + i).is(':checked');
                            var arpeggio = $('#' + type + 'StyleArpeggio' + i).is(':checked');
                            var rangeLower = $('#' + type + 'RangeLower' + i).val();
                            var rangeUpper = $('#' + type + 'RangeUpper' + i).val();

                            if (quantity < 1 || quantity > 20) {
                                $scope.submissionError = "Invalid number of chord questions. Cannot have more than 20 or less than 1.";
                                return;
                            }

                            if (!unison && !arpeggio) {
                                $scope.submissionError = "A chord question must be either Unison or Arpeggio or both.";
                                return;
                            }

                            questionBank[i]['quantity'] = quantity;
                            questionBank[i]['unison'] = unison;
                            questionBank[i]['arpeggio'] = arpeggio;
                            questionBank[i]['rangeLower'] = rangeLower;
                            questionBank[i]['rangeUpper'] = rangeUpper;
                        } else if (type === 'interval') {
                            var quantity = parseInt($('#' + type + 'NumQuestions' + i).val());
                            var rangeLower = $('#' + type + 'RangeLower' + i).val();
                            var rangeUpper = $('#' + type + 'RangeUpper' + i).val();

                            if (quantity < 1 || quantity > 20) {
                                $scope.submissionError = "Invalid number of interval questions. Cannot have more than 20 or less than 1.";
                                return;
                            }

                            questionBank[i]['quantity'] = quantity;
                            questionBank[i]['rangeLower'] = rangeLower;
                            questionBank[i]['rangeUpper'] = rangeUpper;
                        } else if (type === 'note') {
                            var quantity = parseInt($('#' + type + 'NumQuestions' + i).val());
                            var rangeLower = $('#' + type + 'RangeLower' + i).val();
                            var rangeUpper = $('#' + type + 'RangeUpper' + i).val();

                            if (quantity < 1 || quantity > 20) {
                                $scope.submissionError = "Invalid number of note questions. Cannot have more than 20 or less than 1.";
                                return;
                            }

                            // range checking?

                            questionBank[i]['quantity'] = quantity;
                            questionBank[i]['rangeLower'] = rangeLower;
                            questionBank[i]['rangeUpper'] = rangeUpper;
                        } else if (type === 'scale') {
                            var quantity = parseInt($('#' + type + 'NumQuestions' + i).val());
                            var directionUp = $('#' + type + 'DirectionUp' + i).is(':checked');
                            var directionDown = $('#' + type + 'DirectionDown' + i).is(':checked');
                            var octaves = parseInt($('#' + type + 'NumOctaves' + i).val());
                            var style = $('#' + type + 'PlayStyle' + i).val();
                            var rangeLower = $('#' + type + 'RangeLower' + i).val();
                            var rangeUpper = $('#' + type + 'RangeUpper' + i).val();

                            if (quantity < 1 || quantity > 20) {
                                $scope.submissionError = "Invalid number of scale questions. Cannot have more than 20 or less than 1.";
                                return;
                            }

                            if (!directionUp && !directionDown) {
                                $scope.submissionError = "A scale direction must be either Up or Down or both.";
                                return;
                            }

                            questionBank[i]['quantity'] = quantity;
                            questionBank[i]['directionUp'] = directionUp;
                            questionBank[i]['directionDown'] = directionDown;
                            questionBank[i]['octaves'] = octaves;
                            questionBank[i]['style'] = style;
                            questionBank[i]['rangeLower'] = rangeLower;
                            questionBank[i]['rangeUpper'] = rangeUpper;
                        }
                    }
                }

                if (!atLeastOneQuestion) {
                    $scope.submissionError = "You need to add a question before this schedule can be saved!";
                    return;
                }

                $('#saveNewScheduleButton').addClass('disabled');
                $('#saveNewScheduleButton').html('Saving... <i class="fa fa-fw fa-spinner fa-spin"></i>');

                $scope.newSchedule.questions = questionBank;

                console.log($scope.newSchedule);

                $http.post('/api/v1/' + $cookies.get('apikey') + '/schedule', $scope.newSchedule)
                    .success(function(data) {
                        $('#addScheduleModal').modal('hide');
                        $('#newScheduleQuestions').empty();

                        $scope.loading = true;

                        $http.get('/api/v1/' + $cookies.get('apikey') + '/schedules')
                            .success(function(data) {
                                $scope.scheduleList = data.schedules;
                            })
                            .finally(function() {
                                $scope.loading = false;
                            });

                        $('#saveNewScheduleButton').removeClass('disabled');
                        $('#saveNewScheduleButton').removeClass('btn-xinity');
                        $('#saveNewScheduleButton').addClass('btn-success');
                        $('#saveNewScheduleButton').html('Saved!');
                    })
                    .error(function(data) {
                        if (data.message === 'invalid') {
                            $scope.submissionError = 'Your session has expired. Please reload the page.'
                        } else {
                            $scope.submissionError = data.message
                        }

                        $('#saveNewScheduleButton').removeClass('disabled');
                        $('#saveNewScheduleButton').html('Save');
                    });
            }

            // Delete schedule

            $scope.deleteThisSchedule = function(name, id) {
                // show are you sure modal
                var response = confirm('Are you sure you want to delete "' + name + '"?\nThis cannot be undone.');

                if (response == true) {
                    $http.delete('/api/v1/' + $cookies.get('apikey') + '/schedule/' + id)
                        .success(function(data) {
                            console.log(data);
                        })
                        .error(function(data) {
                            alert(data);
                        })
                        .finally(function() {
                            $scope.loading = true;

                            $http.get('/api/v1/' + $cookies.get('apikey') + '/schedules')
                                .success(function(data) {
                                    $scope.scheduleList = data.schedules;
                                })
                                .finally(function() {
                                    $scope.loading = false;
                                });
                        });
                }
            }

            // Edit schedule

            $scope.editThisSchedule = function(thisId) {
                // wipe the existing schedule edit modal & content
                $scope.editSchedule = {};

                numberOfQuestions = 1;
                questionBank = {}

                $scope.editSubmissionError = undefined;
                $('#editScheduleQuestions').empty();

                $('#saveEditScheduleButton').removeClass('disabled');
                $('#saveEditScheduleButton').addClass('btn-xinity');
                $('#saveEditScheduleButton').removeClass('btn-success');
                $('#saveEditScheduleButton').html('Save');

                // http call for selected sid
                $http.get('/api/v1/' + $cookies.get('apikey') + '/schedule/' + thisId)
                    .success(function(data) {
                        $scope.editSchedule = data;

                        console.log($scope.editSchedule);

                        if ($scope.editSchedule.random === true) {
                            $('#editScheduleRandom').attr('checked');
                        }

                        // set the modal's data from http request
                        for (var key in $scope.editSchedule.questions) {
                            if ($scope.editSchedule.questions.hasOwnProperty(key)) {
                                if ($scope.editSchedule.questions[key].type === 'chord') {
                                    questionBank[numberOfQuestions] = {
                                        type: 'chord'
                                    };

                                    var styleUnison = '';
                                    var styleArpeggio = '';

                                    if ($scope.editSchedule.questions[key].unison) {
                                        styleUnison = ' checked';
                                    }

                                    if ($scope.editSchedule.questions[key].arpeggio) {
                                        styleArpeggio = ' checked';
                                    }

                                    var html = `<div class="scheduleQuestion chord" id="question` + numberOfQuestions + `">
                                        <h5>Chord Question <a id="removeEditQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                                        <div class="form-group">
                                            <label for="chordNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                                            <div class="col-sm-3"><input id="chordNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="` + $scope.editSchedule.questions[key].quantity + `"></div>
                                        </div>
                                        <div class="form-group">
                                            <label for="chordRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                                            <div class="col-sm-2"><input id="chordRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="` + $scope.editSchedule.questions[key].rangeLower + `"></div>
                                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                                            <div class="col-sm-2"><input id="chordRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="` + $scope.editSchedule.questions[key].rangeUpper + `"></div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">Chord Styles</label>
                                            <div class="col-sm-4">
                                                <div class="checkbox"><label><input type="checkbox" id="chordStyleUnison` + numberOfQuestions + `" value="unison"` + styleUnison + `> Unison</label></div>
                                                <div class="checkbox"><label><input type="checkbox" id="chordStyleArpeggio` + numberOfQuestions + `" value="arpeggio"` + styleArpeggio + `> Arpeggio</label></div>
                                            </div>
                                        </div>
                                    </div>`;

                                    $('#editScheduleQuestions').append(html);

                                    $('#removeEditQuestion' + numberOfQuestions).click(function(event) {
                                        var thisId = this.id;
                                        thisId = thisId.match(/\d+/)[0];

                                        $(this).parent().parent().remove();
                                        delete questionBank[thisId];
                                    });

                                    numberOfQuestions++;
                                } else if ($scope.editSchedule.questions[key].type === 'interval') {
                                    questionBank[numberOfQuestions] = {
                                        type: 'interval'
                                    };

                                    var html = `<div class="scheduleQuestion interval" id="question` + numberOfQuestions + `">
                                        <h5>Interval Questions <a id="removeEditQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                                        <div class="form-group">
                                            <label for="intervalNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                                            <div class="col-sm-3"><input id="intervalNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="` + $scope.editSchedule.questions[key].quantity + `"></div>
                                        </div>
                                        <div class="form-group">
                                            <label for="intervalRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                                            <div class="col-sm-2"><input id="intervalRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="` + $scope.editSchedule.questions[key].rangeLower + `"></div>
                                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                                            <div class="col-sm-2"><input id="intervalRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="` + $scope.editSchedule.questions[key].rangeUpper + `"></div>
                                        </div>
                                    </div>`;

                                    $('#editScheduleQuestions').append(html);

                                    $('#removeEditQuestion' + numberOfQuestions).click(function(event) {
                                        var thisId = this.id;
                                        thisId = thisId.match(/\d+/)[0];

                                        $(this).parent().parent().remove();
                                        delete questionBank[thisId];
                                    });

                                    numberOfQuestions++;
                                } else if ($scope.editSchedule.questions[key].type === 'note') {
                                    questionBank[numberOfQuestions] = {
                                        type: 'note'
                                    };

                                    var html = `<div class="scheduleQuestion note" id="question` + numberOfQuestions + `">
                                        <h5>Note Questions <a id="removeEditQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                                        <div class="form-group">
                                            <label for="noteNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                                            <div class="col-sm-3"><input id="noteNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="` + $scope.editSchedule.questions[key].quantity + `"></div>
                                        </div>
                                        <div class="form-group">
                                            <label for="noteRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                                            <div class="col-sm-2"><input id="noteRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="` + $scope.editSchedule.questions[key].rangeLower + `"></div>
                                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                                            <div class="col-sm-2"><input id="noteRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="` + $scope.editSchedule.questions[key].rangeUpper + `"></div>
                                        </div>
                                    </div>`;

                                    $('#editScheduleQuestions').append(html);

                                    $('#removeEditQuestion' + numberOfQuestions).click(function(event) {
                                        var thisId = this.id;
                                        thisId = thisId.match(/\d+/)[0];

                                        $(this).parent().parent().remove();
                                        delete questionBank[thisId];
                                    });

                                    numberOfQuestions++;
                                } else if ($scope.editSchedule.questions[key].type === 'scale') {
                                    questionBank[numberOfQuestions] = {
                                        type: 'scale'
                                    };

                                    var directionUp = '';
                                    var directionDown = '';

                                    if ($scope.editSchedule.questions[key].directionUp) {
                                        directionUp = ' checked';
                                    }

                                    if ($scope.editSchedule.questions[key].directionDown) {
                                        directionDown = ' checked';
                                    }

                                    var straight = '';
                                    var swing = '';

                                    if ($scope.editSchedule.questions[key].style === 'straight') {
                                        straight = ' selected';
                                    }

                                    if ($scope.editSchedule.questions[key].style === 'swing') {
                                        swing = ' selected';
                                    }

                                    var html = `<div class="scheduleQuestion scale" id="question` + numberOfQuestions + `">
                                        <h5>Scale Questions <a id="removeEditQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                                        <div class="form-group">
                                            <label for="scaleNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                                            <div class="col-sm-3"><input id="scaleNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="` + $scope.editSchedule.questions[key].quantity + `"></div>
                                        </div>
                                        <div class="form-group">
                                            <label for="scaleRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                                            <div class="col-sm-2"><input id="scaleRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="` + $scope.editSchedule.questions[key].rangeLower + `"></div>
                                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                                            <div class="col-sm-2"><input id="scaleRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="` + $scope.editSchedule.questions[key].rangeUpper + `"></div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">Scale Direction</label>
                                            <div class="col-sm-4">
                                                <div class="checkbox"><label><input type="checkbox" id="scaleDirectionUp` + numberOfQuestions + `"` + directionUp + `> Up</label></div>
                                                <div class="checkbox"><label><input type="checkbox" id="scaleDirectionDown` + numberOfQuestions + `"` + directionDown + `> Down</label></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="scaleNumOctaves` + numberOfQuestions + `" class="col-sm-3 control-label"># of Octaves</label>
                                            <div class="col-sm-3"><input id="scaleNumOctaves` + numberOfQuestions + `" class="form-control" type="text" value="` + $scope.editSchedule.questions[key].octaves + `"></div>
                                        </div>
                                        <div class="form-group">
                                            <label for="scalePlayStyle` + numberOfQuestions + `" class="col-sm-3 control-label">Play Style</label>
                                            <div class="col-sm-4">
                                                <select id="scalePlayStyle` + numberOfQuestions + `" class="form-control" type="text">
                                                    <option value="straight"` + straight + `>Straight</option>
                                                    <option value="swing"` + swing + `>Swing</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>`;

                                    $('#editScheduleQuestions').append(html);

                                    $('#removeEditQuestion' + numberOfQuestions).click(function(event) {
                                        var thisId = this.id;
                                        thisId = thisId.match(/\d+/)[0];

                                        $(this).parent().parent().remove();
                                        delete questionBank[thisId];
                                    });

                                    numberOfQuestions++;
                                }
                            }
                        }
                    })
                    .error(function(data) {
                        if (data.message === 'invalid') {
                            $scope.editSubmissionError = 'Your session has expired. Please reload the page.'
                        } else {
                            $scope.editSubmissionError = data.message
                        }
                    });

                // show modal
                $('#editScheduleModal').modal('show');
            }

            $scope.editScheduleNewQuestion = function(thisType) {
                questionBank[numberOfQuestions] = {
                    type: thisType
                };

                var html = '';

                if (thisType === 'chord') {
                    html = `<div class="scheduleQuestion chord" id="question` + numberOfQuestions + `">
                        <h5>Chord Question <a id="removeEditQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                        <div class="form-group">
                            <label for="chordNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                            <div class="col-sm-3"><input id="chordNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="5"></div>
                        </div>
                        <div class="form-group">
                            <label for="chordRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                            <div class="col-sm-2"><input id="chordRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="C4"></div>
                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                            <div class="col-sm-2"><input id="chordRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="C5"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Chord Styles</label>
                            <div class="col-sm-4">
                                <div class="checkbox"><label><input type="checkbox" id="chordStyleUnison` + numberOfQuestions + `" value="unison" checked> Unison</label></div>
                                <div class="checkbox"><label><input type="checkbox" id="chordStyleArpeggio` + numberOfQuestions + `" value="arpeggio"> Arpeggio</label></div>
                            </div>
                        </div>
                    </div>`;
                } else if (thisType === 'interval') {
                    html = `<div class="scheduleQuestion interval" id="question` + numberOfQuestions + `">
                        <h5>Interval Questions <a id="removeEditQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                        <div class="form-group">
                            <label for="intervalNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                            <div class="col-sm-3"><input id="intervalNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="5"></div>
                        </div>
                        <div class="form-group">
                            <label for="intervalRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                            <div class="col-sm-2"><input id="intervalRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="C4"></div>
                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                            <div class="col-sm-2"><input id="intervalRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="C5"></div>
                        </div>
                    </div>`;
                } else if (thisType === 'note') {
                    html = `<div class="scheduleQuestion note" id="question` + numberOfQuestions + `">
                        <h5>Note Questions <a id="removeEditQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                        <div class="form-group">
                            <label for="noteNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                            <div class="col-sm-3"><input id="noteNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="5"></div>
                        </div>
                        <div class="form-group">
                            <label for="noteRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                            <div class="col-sm-2"><input id="noteRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="C4"></div>
                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                            <div class="col-sm-2"><input id="noteRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="C5"></div>
                        </div>
                    </div>`;
                } else if (thisType === 'scale') {
                    html = `<div class="scheduleQuestion scale" id="question` + numberOfQuestions + `">
                        <h5>Scale Questions <a id="removeEditQuestion` + numberOfQuestions + `" class="removeQuestion pull-right"><i class="fa fa-fw fa-times"></i></a></h5>
                        <div class="form-group">
                            <label for="scaleNumQuestions` + numberOfQuestions + `" class="col-sm-3 control-label"># of Questions</label>
                            <div class="col-sm-3"><input id="scaleNumQuestions` + numberOfQuestions + `" class="form-control" type="number" value="5"></div>
                        </div>
                        <div class="form-group">
                            <label for="scaleRangeLower` + numberOfQuestions + `" class="col-sm-3 control-label">Note Range</label>
                            <div class="col-sm-2"><input id="scaleRangeLower` + numberOfQuestions + `" class="form-control" type="text" value="C4"></div>
                            <label class="col-sm-1 control-label" style="text-align: center;">to</label>
                            <div class="col-sm-2"><input id="scaleRangeUpper` + numberOfQuestions + `" class="form-control" type="text" value="C5"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Scale Direction</label>
                            <div class="col-sm-4">
                                <div class="checkbox"><label><input type="checkbox" id="scaleDirectionUp` + numberOfQuestions + `" checked> Up</label></div>
                                <div class="checkbox"><label><input type="checkbox" id="scaleDirectionDown` + numberOfQuestions + `"> Down</label></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="scaleNumOctaves` + numberOfQuestions + `" class="col-sm-3 control-label"># of Octaves</label>
                            <div class="col-sm-3"><input id="scaleNumOctaves` + numberOfQuestions + `" class="form-control" type="text" value="1"></div>
                        </div>
                        <div class="form-group">
                            <label for="scalePlayStyle` + numberOfQuestions + `" class="col-sm-3 control-label">Play Style</label>
                            <div class="col-sm-4">
                                <select id="scalePlayStyle` + numberOfQuestions + `" class="form-control" type="text">
                                    <option value="straight" selected>Straight</option>
                                    <option value="swing">Swing</option>
                                </select>
                            </div>
                        </div>
                    </div>`;
                }

                $('#editScheduleQuestions').append(html);

                $('#removeEditQuestion' + numberOfQuestions).click(function(event) {
                    var thisId = this.id;
                    thisId = thisId.match(/\d+/)[0];

                    $(this).parent().parent().remove();
                    delete questionBank[thisId];
                });

                numberOfQuestions++;
            }

            $scope.cancelEditSchedule = function() {
                $('#editScheduleModal').modal('hide');
                $('#editScheduleQuestions').empty();
            }

            $scope.saveEditSchedule = function() {
                var atLeastOneQuestion = false;
                $scope.editSubmissionError = undefined;

                if ($scope.editSchedule.name === "") {
                    $scope.editSubmissionError = "Name cannot be empty";
                    return;
                }

                for (var i = 1; i < numberOfQuestions; i++) {
                    if (questionBank[i] != undefined) {
                        atLeastOneQuestion = true;
                        var type = questionBank[i]['type'];

                        if (type === 'chord') {
                            var quantity = parseInt($('#' + type + 'NumQuestions' + i).val());
                            var unison = $('#' + type + 'StyleUnison' + i).is(':checked');
                            var arpeggio = $('#' + type + 'StyleArpeggio' + i).is(':checked');
                            var rangeLower = $('#' + type + 'RangeLower' + i).val();
                            var rangeUpper = $('#' + type + 'RangeUpper' + i).val();

                            if (quantity < 1 || quantity > 20) {
                                $scope.editSubmissionError = "Invalid number of chord questions. Cannot have more than 20 or less than 1.";
                                return;
                            }

                            if (!unison && !arpeggio) {
                                $scope.editSubmissionError = "A chord question must be either Unison or Arpeggio or both.";
                                return;
                            }

                            questionBank[i]['quantity'] = quantity;
                            questionBank[i]['unison'] = unison;
                            questionBank[i]['arpeggio'] = arpeggio;
                            questionBank[i]['rangeLower'] = rangeLower;
                            questionBank[i]['rangeUpper'] = rangeUpper;
                        } else if (type === 'interval') {
                            var quantity = parseInt($('#' + type + 'NumQuestions' + i).val());
                            var rangeLower = $('#' + type + 'RangeLower' + i).val();
                            var rangeUpper = $('#' + type + 'RangeUpper' + i).val();

                            if (quantity < 1 || quantity > 20) {
                                $scope.editSubmissionError = "Invalid number of interval questions. Cannot have more than 20 or less than 1.";
                                return;
                            }

                            questionBank[i]['quantity'] = quantity;
                            questionBank[i]['rangeLower'] = rangeLower;
                            questionBank[i]['rangeUpper'] = rangeUpper;
                        } else if (type === 'note') {
                            var quantity = parseInt($('#' + type + 'NumQuestions' + i).val());
                            var rangeLower = $('#' + type + 'RangeLower' + i).val();
                            var rangeUpper = $('#' + type + 'RangeUpper' + i).val();

                            if (quantity < 1 || quantity > 20) {
                                $scope.editSubmissionError = "Invalid number of note questions. Cannot have more than 20 or less than 1.";
                                return;
                            }

                            // range checking?

                            questionBank[i]['quantity'] = quantity;
                            questionBank[i]['rangeLower'] = rangeLower;
                            questionBank[i]['rangeUpper'] = rangeUpper;
                        } else if (type === 'scale') {
                            var quantity = parseInt($('#' + type + 'NumQuestions' + i).val());
                            var directionUp = $('#' + type + 'DirectionUp' + i).is(':checked');
                            var directionDown = $('#' + type + 'DirectionDown' + i).is(':checked');
                            var octaves = parseInt($('#' + type + 'NumOctaves' + i).val());
                            var style = $('#' + type + 'PlayStyle' + i).val();
                            var rangeLower = $('#' + type + 'RangeLower' + i).val();
                            var rangeUpper = $('#' + type + 'RangeUpper' + i).val();

                            if (quantity < 1 || quantity > 20) {
                                $scope.editSubmissionError = "Invalid number of scale questions. Cannot have more than 20 or less than 1.";
                                return;
                            }

                            if (!directionUp && !directionDown) {
                                $scope.editSubmissionError = "A scale direction must be either Up or Down or both.";
                                return;
                            }

                            questionBank[i]['quantity'] = quantity;
                            questionBank[i]['directionUp'] = directionUp;
                            questionBank[i]['directionDown'] = directionDown;
                            questionBank[i]['octaves'] = octaves;
                            questionBank[i]['style'] = style;
                            questionBank[i]['rangeLower'] = rangeLower;
                            questionBank[i]['rangeUpper'] = rangeUpper;
                        }
                    }
                }

                if (!atLeastOneQuestion) {
                    $scope.editSubmissionError = "You need to add a question before this schedule can be saved!";
                    return;
                }

                $('#saveEditScheduleButton').addClass('disabled');
                $('#saveEditScheduleButton').html('Saving... <i class="fa fa-fw fa-spinner fa-spin"></i>');

                $scope.editSchedule.questions = questionBank;

                console.log($scope.editSchedule);

                $http.put('/api/v1/' + $cookies.get('apikey') + '/schedule', $scope.editSchedule)
                    .success(function(data) {
                        $('#editScheduleModal').modal('hide');
                        $('#editScheduleQuestions').empty();

                        $scope.loading = true;

                        $http.get('/api/v1/' + $cookies.get('apikey') + '/schedules')
                            .success(function(data) {
                                $scope.scheduleList = data.schedules;
                            })
                            .finally(function() {
                                $scope.loading = false;
                            });

                        $('#saveEditScheduleButton').removeClass('disabled');
                        $('#saveEditScheduleButton').removeClass('btn-xinity');
                        $('#saveEditScheduleButton').addClass('btn-success');
                        $('#saveEditScheduleButton').html('Saved!');
                    })
                    .error(function(data) {
                        if (data.message === 'invalid') {
                            $scope.editSubmissionError = 'Your session has expired. Please reload the page.'
                        } else {
                            $scope.editSubmissionError = data.message
                        }

                        $('#saveEditScheduleButton').removeClass('disabled');
                        $('#saveEditScheduleButton').html('Save');
                    });
            }

            // Play schedule

            var currentlyPlaying = undefined;

            $scope.playScheduleIsLoading = false;
            $scope.isHintShown = false;

            $scope.showResults = false;

            var hasAnswered = false;
            var thisQuestionPlayable = undefined;

            var currentSet = 1;
            var currentQuestion = 1;

            var questionNumber = 0;
            var totalQuestions = 0;
            var totalNumberSets = 0;

            var thisQuestionId;

            var pickedSet;
            var trackingForRandom = undefined;
            var totalRandomQuestion = 0;

            var usersScore = undefined;
            var userScheduleResult = undefined;

            $scope.playSchedule = function(thisId) {
                currentlyPlaying = undefined;
                $scope.playScheduleIsLoading = true;
                $scope.showResults = false;

                $('#scheduleNext').show();

                // make http request for this schedule id's data
                $http.get('/api/v1/' + $cookies.get('apikey') + '/schedule/' + thisId)
                    .success(function(data) {
                        // create the things we need to do a play session
                        currentlyPlaying = data;

                        console.log('schedule we\'re going to play:');
                        console.log(currentlyPlaying);

                        $('#playScheduleModal').modal('show');

                        // So we can load the first question
                        hasAnswered = true;

                        // the number of the question we're on
                        questionNumber = 1;

                        // the total number of questions in this schedule
                        totalQuestions = 0;

                        // total number of sets in the schedule
                        totalNumberSets = 0;

                        // users rating
                        usersScore = undefined;

                        // the object that'll store all questions/ratings
                        userScheduleResult = {};

                        // object for tracking random sets
                        trackingForRandom = {};

                        // total questions = to the sum of all the quanities
                        for (var key in currentlyPlaying.questions) {
                            if (currentlyPlaying.questions.hasOwnProperty(key)) {
                                totalQuestions += currentlyPlaying.questions[key].quantity;

                                totalNumberSets++;

                                trackingForRandom[key] = currentlyPlaying.questions[key].quantity;
                            }
                        }

                        totalRandomQuestion = totalQuestions;

                        // object containing each set as a key with the quanitiy left to play for it

                        if (currentlyPlaying.random) {
                            console.log('random mode');
                            console.log(trackingForRandom);
                            console.log(totalNumberSets);

                            pickedSet = randomIntegerUpTo(totalNumberSets);

                            console.log(pickedSet);

                            currentSet = pickedSet;
                            currentQuestion = 1;
                        } else {
                            currentSet = 1;
                            currentQuestion = 1;
                        }

                        // load first question
                        loadQuestion();
                    })
                    .error(function() {
                        alert('Failed to load schedule id ' + thisId);
                    });
            }

            function loadQuestion() {
                console.log('loadQuestion');
                console.log('hasAnswered = ' + hasAnswered);

                if (hasAnswered) {
                    $('#answerButton').removeClass('disabled');

                    $('#scheduleNext').hide();
                    $('#scheduleDone').hide();

                    $('#scheduleAnswer').hide();

                    // set play details
                    $('#scheduleName').html('\"' + currentlyPlaying.name + '\"');
                    $('#scheduleProgress').html('Question ' + questionNumber + ' of ' + totalQuestions);
                    $('#scheduleQuestionType').html(currentlyPlaying.questions[currentSet].type);

                    $('#ratingQuestionText').html(randomRatingQuestion());

                    thisQuestionId = questionNumber;

                    userScheduleResult[thisQuestionId] = {};
                    userScheduleResult.id = clone(currentlyPlaying.id);
                    userScheduleResult[thisQuestionId].type = clone(currentlyPlaying.questions[currentSet].type);

                    console.log(userScheduleResult);

                    updateStar(1, 'none');
                    updateStar(2, 'none');
                    updateStar(3, 'none');
                    updateStar(4, 'none');
                    updateStar(5, 'none');

                    $('#ratingText').html('');

                    if (usersScore !== undefined) {
                        console.log('saved score ' + usersScore);

                        userScheduleResult[thisQuestionId - 1].rating = usersScore;

                        usersScore = undefined;
                    }

                    console.log('current question set:');
                    console.log(currentlyPlaying.questions[currentSet]);
                    console.log(currentQuestion + ' of ' + currentlyPlaying.questions[currentSet].quantity);

                    thisQuestionPlayable = undefined;
                    $scope.playScheduleIsLoading = true;
                    $scope.isHintShown = false;

                    if (questionNumber === totalQuestions) {
                        console.log('equal? ' + questionNumber + ' | ' + totalQuestions);

                        $('#scheduleDone').addClass('disabled');
                    }

                    var thisQuestionParams = clone(currentlyPlaying.questions[currentSet]);
                    thisQuestionParams.difficulty = clone(currentlyPlaying.difficulty);
                    delete thisQuestionParams.quantity;
                    delete thisQuestionParams.type;

                    console.log(thisQuestionParams);

                    $http.post('/api/v1/' + $cookies.get('apikey') + '/tutor/' + currentlyPlaying.questions[currentSet].type, thisQuestionParams)
                        .success(function(data) {
                            console.log(data);

                            $('#scheduleQuestion').html(data.question);

                            if (data.hint !== undefined) {
                                $('#hintArea').show();
                                $('#scheduleHint').html(data.hint);
                            } else {
                                $('#hintArea').hide();
                            }

                            thisQuestionPlayable = data.play;

                            userScheduleResult[thisQuestionId].question = clone(data.question);
                            userScheduleResult[thisQuestionId].hint = clone(data.hint);
                            userScheduleResult[thisQuestionId].params = clone(thisQuestionParams);

                            console.log(userScheduleResult);

                            $scope.playScheduleIsLoading = false;
                        })
                        .error(function(data) {
                            alert(data.message);

                            $('#playScheduleModal').modal('hide');

                            $scope.playScheduleIsLoading = false;
                        });

                    hasAnswered = false;

                    questionNumber++;

                    // if we are in random mode
                        // pick a random set
                        // if the set has no quanity left
                            // pick another
                        // deincrement the quanity of selected set
                        // load the quesiton

                    if (currentlyPlaying.random) {
                        console.log(trackingForRandom);

                        console.log(pickedSet);

                        trackingForRandom[pickedSet] = trackingForRandom[pickedSet] - 1;
                        totalRandomQuestion--;

                        console.log('total random questions ' + totalRandomQuestion);
                        console.log(trackingForRandom);

                        console.log('picking next set and question');

                        pickedSet = randomIntegerUpTo(Object.keys(trackingForRandom).length);

                        console.log(pickedSet);

                        // while this picked set has 0 questions left
                            // pick a new set

                        while (trackingForRandom[pickedSet] === 0) {
                            if (totalRandomQuestion === 0) {
                                break;
                            } else {
                                console.log(pickedSet + ' has no questions, picking again');
                                pickedSet = randomIntegerUpTo(Object.keys(trackingForRandom).length);
                            }
                        }

                        console.log(pickedSet);

                        currentSet = pickedSet;
                    } else {
                        currentQuestion++;

                        if (currentQuestion > currentlyPlaying.questions[currentSet].quantity) {
                            currentQuestion = 1;
                            currentSet++;
                        }
                    }
                }
            }

            $scope.loadQuestion = loadQuestion;

            $scope.playAnswer = function() {
                // play midi sound here
                handlePlay(thisQuestionPlayable);

                hasAnswered = false;

                $('#scheduleAnswer').show();
            }

            $scope.giveResult = function(score) {
                usersScore = score;

                if (usersScore === 1) {
                    updateStar(1, 'lit');
                    updateStar(2, 'none');
                    updateStar(3, 'none');
                    updateStar(4, 'none');
                    updateStar(5, 'none');

                    $('#ratingText').html('\"Didn\'t know it\"');
                } else if (usersScore === 2) {
                    updateStar(1, 'lit');
                    updateStar(2, 'lit');
                    updateStar(3, 'none');
                    updateStar(4, 'none');
                    updateStar(5, 'none');

                    $('#ratingText').html('\"Struggled a lot\"');
                } else if (usersScore === 3) {
                    updateStar(1, 'lit');
                    updateStar(2, 'lit');
                    updateStar(3, 'lit');
                    updateStar(4, 'none');
                    updateStar(5, 'none');

                    $('#ratingText').html('\"Went okay\"');
                } else if (usersScore === 4) {
                    updateStar(1, 'lit');
                    updateStar(2, 'lit');
                    updateStar(3, 'lit');
                    updateStar(4, 'lit');
                    updateStar(5, 'none');

                    $('#ratingText').html('\"Pretty good\"');
                } else if (usersScore === 5) {
                    updateStar(1, 'lit');
                    updateStar(2, 'lit');
                    updateStar(3, 'lit');
                    updateStar(4, 'lit');
                    updateStar(5, 'lit');

                    $('#ratingText').html('\"Perfect!\"');
                }

                console.log('changed score to ' + usersScore);

                if (!hasAnswered) {
                    // save answer to answer object
                    console.log('giveResult');

                    hasAnswered = true;

                    $('#answerButton').addClass('disabled');
                    $('#scheduleNext').removeClass('disabled');

                    if ((questionNumber - 1) === totalQuestions) {
                        console.log('give answer equal? ' + (questionNumber - 1) + ' | ' + totalQuestions);

                        $('#scheduleDone').show();
                        $('#scheduleDone').removeClass('disabled');
                    } else {
                        $('#scheduleNext').show();
                    }
                }
            }

            $scope.finishSchedule = function() {
                userScheduleResult[questionNumber - 1].rating = usersScore;

                $scope.showResults = true;

                var resultsHtml = `<div class="table-responsive">
                <table class="table resultsTable">
                    <thead>
                        <tr>
                            <th class="qid">#</th>
                            <th class="qtype">Type</th>
                            <th class="question">Question</th>
                            <th class="qrating">Rating</th>
                        </tr>
                    </head>
                    <tbody>`;

                for (var key in userScheduleResult) {
                    if (userScheduleResult.hasOwnProperty(key)) {
                        if (key !== 'id') {
                            resultsHtml += `
                            <tr>
                                <td class="qid">` + key + `</td>
                                <td class="qtype">` + userScheduleResult[key].type + `</td>
                                <td class="question">` + userScheduleResult[key].question + `</td>
                                <td class="qrating">` + makeStars(userScheduleResult[key].rating) + `</td>
                            </tr>`;
                        }
                    }
                }

                resultsHtml += `
                        </tbody>
                    </table>
                </div>`;

                $('#scheduleResultTable').html(resultsHtml);

                $('#scheduleDone').hide();
            }

            $scope.discardScheduleResults = function() {
                $('#playScheduleModal').modal('hide');
            }

            $scope.saveScheduleResults = function() {
                console.log(userScheduleResult);

                $http.post('/api/v1/' + $cookies.get('apikey') + '/result/schedule', userScheduleResult)
                    .success(function(data) {
                        $scope.loading = true;

                        $http.get('/api/v1/' + $cookies.get('apikey') + '/schedules')
                            .success(function(data) {
                                $scope.scheduleList = data.schedules;
                            })
                            .finally(function() {
                                $scope.loading = false;
                            });
                    })
                    .error(function(data) {
                        alert(data.message);
                    });

                $('#playScheduleModal').modal('hide');
            }
        }
    });
})();
