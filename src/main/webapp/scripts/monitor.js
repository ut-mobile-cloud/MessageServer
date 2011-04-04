// Monitor

function doTheAjaxThing() {
    $.ajax({
        type:'POST',
        url:'http://localhost:8084/StatusMonitor',
        success:function(msg) {
            $('#counter').html(msg);
        }
    });
    console.log('Did the ajax thing');
};

function paintStatuses() {
    $('#task1 .status').css({'background':'green'});
    $('#task2 .status').css({'background':'yellow'});
    $('#task3 .status').css({'background':'red'});
};
function prepareElements() {
    paintStatuses();
    $('#recheck').click(doTheAjaxThing);
  console.log('Prepared elements');
//  $.PeriodicalUpdater('http://localhost:8084/StatusMonitor', {
//        method: 'POST',          // method; get or post
//        data: '',               // array of values to be passed to the page - e.g. {name: "John", greeting: "hello"}
//        minTimeout: 1000,       // starting value for the timeout in milliseconds
//        maxTimeout: 8000,       // maximum length of time between requests
//        multiplier: 2,          // the amount to expand the timeout by if the response hasn't changed (up to maxTimeout)
//        type: 'text',           // response type - text, xml, json, etc.  See $.ajax config options
//        maxCalls: 0,            // maximum number of calls. 0 = no limit.
//        autoStop: 0             // automatically stop requests after this many returns of the same data. 0 = disabled.
//    }, function(remoteData, success, xhr, handle) {
//        $('#counter').html(remoteData);
//    });
};

$(document).ready(prepareElements);