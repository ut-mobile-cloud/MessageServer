// Monitor

STATUS_CSS_CLASSES = {
	'NEW':'StatusNew',
	'RUNNING' : 'StatusRunning',
	'COMPLETED' : 'StatusCompleted',
	'ERROR' : 'StatusError',
	'UNKNOWN' : 'StatusUnknown'
};

function statusCssFromString(status) {
	cssClass = STATUS_CSS_CLASSES[status];
	if (cssClass == undefined) {
		cssClass = 'StatusUnknown';
	}
	return cssClass;
};

Monitor = function() {
    this.tasks = new Array();
};

Monitor.prototype = {
    constructor : this.Monitor
};

Monitor.prototype.requestStatusUpdates = function() {
	$.getJSON('http://localhost:8084/StatusMonitor', '', this.receiveStatusUpdates);
};

CreateTableRow = function(statusData, rowNr) {
	rowHtml = '';
	rowHtml += '<tr>'
	//rowHtml += '<tr id="task'+ rowNr + '">';
	rowHtml += '<td class="TaskName">' + statusData['name'] +'</td>';
	rowHtml += '<td class="TaskDescription">' + statusData['description'] + '</td>';
	rowHtml += '<td class="' + statusCssFromString(statusData['status']) + '">' + statusData['status'] +'</td>';
	rowHtml += '</tr>';
	return rowHtml;
};

Monitor.prototype.receiveStatusUpdates = function(remoteData) {
	currentTableRows = $('#TaskStatuses tbody tr');
	statusTable = $('#TaskStatuses tbody');
//	for (var rowIndex = 0; rowIndex < currentTableRows.length; rowIndex++) {
//		statusTable[rowIndex] = CreateTableRow(remoteData, rowIndex);
//	}
	
	var stubData = [{name:'Abu', description:'Kaabu', status:'RUNNING'},
	{name:'Kabe', description:'Kaabu', status:'COMPLETED'},
	{name:'Male', description:'Kaabu', status:'NEW'}];
	
	for (var rowIndex = 0; rowIndex < stubData.length; rowIndex++) {
		console.log('Making row ' + stubData[rowIndex]['name']);
		var rowHtml = CreateTableRow(stubData[rowIndex], rowIndex);
		console.log('ROW DATA IS : ' + rowHtml);
		$('#TaskStatuses').append(rowHtml);
	}
	
};

Monitor.prototype.updatePage = function() {

};


Monitor.prototype.updateStatuses = function() {
    this.requestStatusUpdates();
    this.updatePage();
};

function beginMonitoring() {
    statusMonitor = new Monitor();
    // Register for polling
	  $.PeriodicalUpdater('http://localhost:8084/StatusMonitor', {
        method: 'POST',          // method; get or post
        data: '',               // array of values to be passed to the page - e.g. {name: "John", greeting: "hello"}
        minTimeout: 1000,       // starting value for the timeout in milliseconds
        maxTimeout: 8000,       // maximum length of time between requests
        multiplier: 2,          // the amount to expand the timeout by if the response hasn't changed (up to maxTimeout)
        type: 'json',           // response type - text, xml, json, etc.  See $.ajax config options
        maxCalls: 0,            // maximum number of calls. 0 = no limit.
        autoStop: 0             // automatically stop requests after this many returns of the same data. 0 = disabled.
    }, statusMonitor.receiveStatusUpdates);
};

$(document).ready(beginMonitoring);