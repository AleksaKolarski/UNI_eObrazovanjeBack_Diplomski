
var stompClient = null;

var button_connect;
var button_disconnect;
var status_connected;
var positionIndicator;
var debug;

var div_questions_parent;
var div_questions = { activeQuestion: -1, activeAnswer: -1, list: [] };
var questions = null;



function setConnected(state) {
    status_connected.css('color', ((state == true) ? 'green' : 'red'));

    if (state == true) {
        button_connect.attr('disabled', true);
        button_disconnect.removeAttr('disabled');
    }
    else if (state == false) {
        button_disconnect.attr('disabled', true);
        button_connect.removeAttr('disabled');
    }
}

// CONNECT
function connect_successful_callback(frame) {
    button_connect.removeAttr('disabled');
    console.log('Connection successful');
    setConnected(true);
    stompClient.subscribe('/topic/gazepoint-data', function (message) {
        if (message.body) // inace je prazno
        {
            var data = JSON.parse(message.body);

            // proveriti da li je flag za zivu konekciju sa gazepoint serverom
            // ako nije diskonektovati se
            if (data.connectionActive == false) {
                disconnect();
            }
            else {
                //console.log(data.fpogx + ' ' + data.fpogy);

                var fpogx = data.fpogx * window.screen.width;
                var fpogy = data.fpogy * window.screen.height;

                //fpogx -= window.screenX;
                //fpogy -= window.screenY;
                //fpogy -= 103;

                var fpogxFinalPosition = fpogx - (fpogx * window.devicePixelRatio - fpogx) / window.devicePixelRatio;
                var fpogyFinalPosition = fpogy - (fpogy * window.devicePixelRatio - fpogy) / window.devicePixelRatio;

                if (debug.is(':checked')) {
                    positionIndicator.css('left', fpogxFinalPosition - 5);
                    positionIndicator.css('top', fpogyFinalPosition - 5);
                }
                else {
                    positionIndicator.css('left', -100);
                    positionIndicator.css('top', -100);
                }


                if (questions != null) {
                    var is_any_question_active = false;
                    var is_any_answer_active = false;
                    div_questions.list.forEach(div_question => {
                        var cr = div_question.get(0).getBoundingClientRect();
                        if (fpogxFinalPosition >= cr.left && fpogxFinalPosition <= cr.right && fpogyFinalPosition >= cr.top && fpogyFinalPosition <= cr.bottom) {
                            is_any_question_active = true;
                            if (div_questions.activeQuestion != div_question.question_id) {
                                console.log('\nleft question: ' + div_questions.activeQuestion);
                                console.log('entered question: ' + div_question.question_id);
                                div_questions.activeQuestion = div_question.question_id;
                            }

                            if (debug.is(':checked')) {
                                div_question.css('border-color', '#F00');
                            }
                        }
                        else {
                            if (debug.is(':checked')) {
                                div_question.css('border-color', 'transparent');
                            }
                        }

                        // odgovori

                        // PROBLEM kad se naglo izadje iz pitanja onda odgovor ostane selektovan. Razlog za to je upravo pozicija ovog koda ispod.
                        // Kod ne sme da se nalazi u gornjoj proveri pozicije misa u pitanju jer kad se izadje iz odgovora vise se ne proveravaju njegova pitanja;

                        
                        div_question.answers.forEach(answer => {
                            var answerCR = answer.get(0).getBoundingClientRect();
                            if (fpogxFinalPosition >= answerCR.left && fpogxFinalPosition <= answerCR.right && fpogyFinalPosition >= answerCR.top && fpogyFinalPosition <= answerCR.bottom) {
                                is_any_answer_active = true;

                                if (div_questions.activeAnswer != answer.id) {
                                    console.log('\nleft answer: ' + div_questions.activeAnswer);
                                    console.log('entered answer: ' + answer.id);
                                    div_questions.activeAnswer = answer.id;
                                }

                                if (debug.is(':checked')) {
                                    answer.css('border-color', '#F00');
                                }
                            }
                            else {
                                if (debug.is(':checked')) {
                                    answer.css('border-color', 'transparent');
                                }
                            }
                        });
                    });
                    if (is_any_question_active == false) {
                        if (div_questions.activeQuestion != -1) {
                            console.log('\nleft question: ' + div_questions.activeQuestion);
                            console.log('entered question: -1');
                        }
                        div_questions.activeQuestion = -1;
                    }
                    if (is_any_answer_active == false) {
                        if (div_questions.activeAnswer != -1) {
                            console.log('\nleft answer: ' + div_questions.activeAnswer);
                            console.log('entered answer: -1');
                        }
                        div_questions.activeAnswer = -1;
                    }


                }
            }
        }
    });
}

function connect_unsuccessful_callback(error) {
    button_connect.removeAttr('disabled');
    console.log('Connection unsuccessful');
    if (error !== null) {
        console.log('error: ' + error);
    }
    setConnected(false);
    stompClient = null;
}

function connect() {
    if (stompClient == null) {
        button_connect.attr('disabled', 'true');

        // ako koristimo SockJS
        //var socket = new SockJS('/websocket-endpoint');
        //stompClient = Stomp.over(socket);

        // direktno koristimo websocket
        stompClient = Stomp.client('ws://localhost:8080/websocket-endpoint');

        stompClient.debug = null;
        stompClient.connect({}, connect_successful_callback, connect_unsuccessful_callback);
    }
}

// DISCONNECT
function disconnect_successful_callback() {
    button_disconnect.removeAttr('disabled');
    console.log('Disconnected');
    setConnected(false);
    stompClient = null;
}

function disconnect() {
    if (stompClient !== null) {
        button_disconnect.attr('disabled', 'true');
        stompClient.disconnect(disconnect_successful_callback);
        stompClient = null;
    }
}

//function send_data(data){
//stompClient.send("/app/connect", {}, data);
//}





$(document).ready(function (e) {

    button_connect = $('#id-button-connect');
    button_disconnect = $('#id-button-disconnect');
    status_connected = $('#id-status-connected');
    positionIndicator = $('#id-position');
    debug = $('#id-debug');

    button_connect.click(connect);
    button_disconnect.click(disconnect);

    div_questions_parent = $('#id-div-pitanja');

    $.ajax({
        method: 'GET',
        url: '/questions/all',
        success: function (data, status, xhr) {
            questions = data;
            var html = '';
            var i = 1;
            questions.forEach(question => {
                html += '<div id="id-pitanje-' + question.id + '" class="class-pitanje col-lg-8 col-md-8 col-sm-12">' +
                    '<div class="class-pitanje-pitanje">' +
                    '<p class="class-pitanje-redni-broj">' + i + '. </p>' +
                    '<p class="class-pitanje-tekst">' + question.body + '</p>' +
                    '</div>';

                //question.answers.forEach(answer => {
                //html += '<input type="radio" name="'+ question.id +'-radio" value="'+ answer.id +'"><p class="class-pitanje-odgovor">'+ answer.body +'</p><br>';
                //});

                html += '<div class="row">' +
                    '<div id="id-odgovor-' + question.answers[0].id + '" class="div-pitanje-odgovor col-5 offset-1">' +
                    '<input type="radio" name="' + question.id + '-radio" value="' + question.answers[0].id + '"><p class="class-pitanje-odgovor">' + question.answers[0].body + '</p><br>' +
                    '</div>' +
                    '<div id="id-odgovor-' + question.answers[1].id + '" class="div-pitanje-odgovor col-5 offset-1">' +
                    '<input type="radio" name="' + question.id + '-radio" value="' + question.answers[1].id + '"><p class="class-pitanje-odgovor">' + question.answers[1].body + '</p><br>' +
                    '</div>' +
                    '</div>' +
                    '<div class="row">' +
                    '<div id="id-odgovor-' + question.answers[2].id + '" class="div-pitanje-odgovor col-5 offset-1">' +
                    '<input type="radio" name="' + question.id + '-radio" value="' + question.answers[2].id + '"><p class="class-pitanje-odgovor">' + question.answers[2].body + '</p><br>' +
                    '</div>' +
                    '<div id="id-odgovor-' + question.answers[3].id + '" class="div-pitanje-odgovor col-5 offset-1">' +
                    '<input type="radio" name="' + question.id + '-radio" value="' + question.answers[3].id + '"><p class="class-pitanje-odgovor">' + question.answers[3].body + '</p><br>' +
                    '</div>' +
                    '</div>';


                html += '</div>';
                i++;
            });
            div_questions_parent.html(html);
            questions.forEach(question => {
                var tmp = $('#id-pitanje-' + question.id);
                tmp['question_id'] = question.id;
                div_questions.list.push(tmp);

                tmp['answers'] = [];

                for (var i = 0; i < 4; i++) {
                    var tmpAnswer = $('#id-odgovor-' + question.answers[i].id);
                    tmpAnswer['id'] = question.answers[i].id;
                    tmp.answers.push(tmpAnswer);
                }

            });
        }
    });


});

