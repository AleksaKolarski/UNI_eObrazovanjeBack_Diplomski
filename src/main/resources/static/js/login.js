
var input_ime;
var button_start;

$(document).ready(function(e){

  input_ime = $('#id-input-ime');
  button_start = $('#id-input-start');

  button_start.click(function(e){
    var ime = input_ime.val();
    window.location.href = '/test.html?ime=' + ime;
  });
});