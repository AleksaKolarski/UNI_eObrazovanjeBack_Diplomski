
var stompClient = null;

var button_connect;
var button_disconnect;
var status_connected;
var positionIndicator;
var debug;

var div_questions_parent;
var div_questions = [];
var question_active = -1;
var questions = null;


function setConnected(state){
    status_connected.css('color', ((state==true)?'green':'red'));

    if(state == true){
        button_connect.attr('disabled', true);
        button_disconnect.removeAttr('disabled');
    }
    else if(state == false){
        button_disconnect.attr('disabled', true);
        button_connect.removeAttr('disabled');
    }
}

// CONNECT
function connect_successful_callback(frame){
    button_connect.removeAttr('disabled');
    console.log('Connection successful');
    setConnected(true);
    stompClient.subscribe('/topic/gazepoint-data', function (message) {
        if(message.body) // inace je prazno
        {
            var data = JSON.parse(message.body);

            // proveriti da li je flag za zivu konekciju sa gazepoint serverom
            // ako nije diskonektovati se
            if(data.connectionActive == false){
                disconnect();
            }
            else{
                //console.log(data.fpogx + ' ' + data.fpogy);

                var fpogx = data.fpogx;
                var fpogy = data.fpogy;


                //fpogx -= window.screenX;
                //fpogy -= window.screenY;
                //fpogy -= 103;


                var fpogxFinalPosition = fpogx - (fpogx*window.devicePixelRatio - fpogx)/window.devicePixelRatio;
                var fpogyFinalPosition = fpogy - (fpogy*window.devicePixelRatio - fpogy)/window.devicePixelRatio;

                if(debug.is(':checked')){
                    positionIndicator.css('left', fpogxFinalPosition - 5);
                    positionIndicator.css('top', fpogyFinalPosition - 5);
                }
                else{
                    positionIndicator.css('left', -100);
                    positionIndicator.css('top', -100);
                }


                if(questions != null){
                    var is_any_question_active = false;
                    div_questions.forEach(div_question => {
                        var cr = div_question.get(0).getBoundingClientRect();
                        if(fpogxFinalPosition >= cr.left && fpogxFinalPosition <= cr.right && fpogyFinalPosition >= cr.top && fpogyFinalPosition <= cr.bottom){
                            is_any_question_active = true;
                            if(question_active != div_question.question_id){
                                console.log('\nleft question: ' + question_active);
                                console.log('entered question: ' + div_question.question_id);
                                question_active = div_question.question_id;
                            }

                            if(debug.is(':checked')){
                                div_question.css('border-color', '#F00');
                            }
                        }
                        else{
                            if(debug.is(':checked')){
                                div_question.css('border-color', 'transparent');
                            }
                        }
                    });
                    if(is_any_question_active == false){
                        if(question_active != -1){
                            console.log('\nleft question: ' + question_active);
                            console.log('entered question: -1');
                        }
                        question_active = -1;
                    }
                }

                //console.log('window ' + window.screenX + ' ' + window.screenTop);
            }
        }
    });
}

function connect_unsuccessful_callback(error){
    button_connect.removeAttr('disabled');
    console.log('Connection unsuccessful');
    if(error !== null){
        console.log('error: ' + error);
    }
    setConnected(false);
    stompClient = null;
}

function connect(){
    if(stompClient == null){
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
function disconnect_successful_callback(){
    button_disconnect.removeAttr('disabled');
    console.log('Disconnected');
    setConnected(false);
    stompClient = null;
}

function disconnect(){
    if (stompClient !== null) {
        button_disconnect.attr('disabled', 'true');
        stompClient.disconnect(disconnect_successful_callback);
        stompClient = null;
    }
}

//function send_data(data){
    //stompClient.send("/app/connect", {}, data);
//}





$(document).ready(function(e){

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
        success: function(data, status, xhr){
            questions = data;
            var html = '';
            var i = 1;
            questions.forEach(question => {
                html += '<div id="id-pitanje-'+ question.id +'" class="class-pitanje col-lg-8 col-md-8 col-sm-12">' + 
                            '<div class="class-pitanje-pitanje">' + 
                                '<p class="class-pitanje-redni-broj">'+ i +'. </p>' + 
                                '<p class="class-pitanje-tekst">'+ question.body +'</p>' + 
                            '</div>';
                question.answers.forEach(answer => {
                    html += '<input type="radio" name="'+ question.id +'-radio" value="'+ answer.id +'"><p class="class-pitanje-odgovor">'+ answer.body +'</p><br>';
                });
                html += '</div>';
                i++;
            });
            div_questions_parent.html(html);
            questions.forEach(question => {
                var tmp = $('#id-pitanje-' + question.id);
                tmp['question_id'] = question.id;
                div_questions.push(tmp);
            });
        }
    });


});

