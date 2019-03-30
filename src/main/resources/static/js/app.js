

// demonstracija polozaja elemenata na stranici 
var pitanje1;
var pitanje2;
var pitanje3;
var p1;
var p2;
var p3;
$(document).ready(function(e){
    pitanje1 = $('#id-pitanje-1');
    pitanje2 = $('#id-pitanje-2');
    pitanje3 = $('#id-pitanje-3');
    p1 = $('#id-p-1');
    p2 = $('#id-p-2');
    p3 = $('#id-p-3');

    setInterval(function(){
        p1.css('left', pitanje1.get(0).getBoundingClientRect().x);
        p1.css('top', pitanje1.get(0).getBoundingClientRect().y);

        p2.css('left', pitanje2.get(0).getBoundingClientRect().x);
        p2.css('top', pitanje2.get(0).getBoundingClientRect().y);

        p3.css('left', pitanje3.get(0).getBoundingClientRect().x);
        p3.css('top', pitanje3.get(0).getBoundingClientRect().y);
    },700);
});



var stompClient = null;

var button_connect;
var button_disconnect;
var status_connected;
var positionIndicator;


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

                var fpogxFinalPosition = data.fpogx - (data.fpogx*window.devicePixelRatio - data.fpogx)/window.devicePixelRatio;
                var fpogyFinalPosition = data.fpogy - (data.fpogy*window.devicePixelRatio - data.fpogy)/window.devicePixelRatio;

                positionIndicator.css('left', fpogxFinalPosition - 5);
                positionIndicator.css('top', fpogyFinalPosition - 5);
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

    button_connect.click(connect);
    button_disconnect.click(disconnect);
});

