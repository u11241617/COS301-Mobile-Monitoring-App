
angular.module('icrawlerApp.home', [
    'ui.router',
    'angular-jwt',
    'angular-storage',
    'bm.bsTour',
    'icrawlerServices'
]).config(function($stateProvider) {
    $stateProvider.state('template.dashboard', {
        url: '/dashboard',
        controller: 'HomeCtrl',
        templateUrl: 'dashboard.html',
        data: {
            requiresLogin: true
        }
    });
}).controller('HomeCtrl', function HomeController($rootScope, $scope, $state, $http, $interval, store,
                                                  jwtHelper, Sms, Device, Calls, Sites, Location,
                                                  DeviceState, CurrentDevice) {

    $scope.jwt = store.get('jwt');
    $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);


    $scope.device_logs =  Device.query({userId: $scope.decodedJwt.user_id}, function(data) {

        CurrentDevice.setdeviceId(data[0].deviceId);
        $rootScope.currentDeviceName = data[0].model;
        $rootScope.currentDeviceStatus = data[0].status;
        $rootScope.currentDeviceId = data[0].deviceId;

        $scope.sms = Sms.query({device: data[0].deviceId}, function(data) {

            drawUserLineChart(data);

        });

        $scope.locations = Location.query({device: data[0].deviceId}, function (data) {

            $scope.lastLocationName = data[data.length - 1].name;
            $scope.lastLocationLocality = data[data.length - 1].localicity;
            $scope.lastLocationCountry = data[data.length - 1].countName;

        })

        $scope.calls = Calls.query({device: data[0].deviceId}, function(data) {

            drawCallLineChart(data);
        });

        $scope.sites = Sites.query({device: data[0].deviceId}, function(data) {

            var browserTotal = drawUserDoughnutChart(data);

            if(data.length != 0) {

                $scope.chromePercentage = roundToTwo((browserTotal.chromeTotal / data.length) * 100);
                $scope.defaultPercentage = roundToTwo((browserTotal.defaultTotal / data.length) * 100);
            }else {

                $scope.chromePercentage = 0;
                $scope.defaultPercentage = 0;
            }


        });

    });



    $scope.load_data = function(device_id) {

        $scope.sms = Sms.query({device: device_id}, function(data) {

            drawUserLineChart(data);
        });

        $scope.calls = Calls.query({device: device_id}, function(data) {

            drawCallLineChart(data);

        });

        $scope.sites = Sites.query({device: device_id}, function(data) {

            drawUserDoughnutChart(data);
        });

        CurrentDevice.setdeviceId(device_id);
        $scope.device_logs =  Device.query({userId: $scope.decodedJwt.user_id}, function(data) {

            for(var i = 0; i < data.length; i++) {

                if(device_id == data[i].deviceId) {
                    $rootScope.currentDeviceName = data[i].model;
                    $rootScope.currentDeviceStatus = data[0].status;
                    $rootScope.currentDeviceId = device_id;
                }
            }
        });

    }
});


function drawUserLineChart(data) {


    var numR = 0;
    var numS = 0;
    var smsInMonths = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var smsSent = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var smsRe = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var charLevel =  ["Jan", "Feb", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    var current_month = new Date().getMonth();
    var current_year = new Date().getFullYear();

    for(var i  = 0; i <  data.length; i++) {

        var date = new Date(data[i].datetime);

        smsInMonths[date.getMonth()] = smsInMonths[date.getMonth()] + 1;
        if(data[i].type == "Sent") {

            var date = new Date(data[i].datetime);
            smsSent[date.getMonth()] =  smsSent[date.getMonth()] + 1;
            numS++;
        }else {

            smsRe[date.getMonth()] =  smsRe[date.getMonth()] + 1;
            numR++;
        }
    }

    var label = new Array();
    var sent = new Array();
    var re = new Array();

    for(var j  = current_month - 5; j <= current_month; j++) {

        label.push(charLevel[j]);
        sent.push(smsSent[j]);
        re.push(smsRe[j]);
    }

    var ctx = $("#lineChart").get(0).getContext("2d");
    ctx.clearRect(0, 300, 0, 100);
    var lineChart = new Chart(ctx);

    var data = {
        labels: label,
        datasets: [
            {
                label: "Sent messages",
                fillColor: "rgba(255,255,255,0.0)",
                strokeColor: "rgba(213,0,0,1)",
                pointColor: "rgba(213,0,0,1)",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: sent
            },
            {
                label: "Received messages",
                fillColor: "rgba(255,255,255,0)",
                strokeColor: "rgba(0,150,136,1)",
                pointColor: "rgba(0,150,136,1)",
                pointHighlightStroke: "rgba(151,187,205,1)",
                data: re
            }
        ]
    };


    lineChart.Line(data);

}

function drawUserDoughnutChart(data) {

    var chrome = 0;
    var default_browser = 0;

    for(var i  = 0; i <  data.length; i++) {

        if(data[i].browsertbByBrowserId.name == "Chrome") {

            chrome++;
        }else {

            default_browser++;
        }
    }

    var canvas = $("#doughnutChart").get(0);
    canvas.width = canvas.width
    var ctx = $("#doughnutChart").get(0).getContext("2d");
    var doughnutChart = new Chart(ctx);

    var data = [
        {
            value: chrome,
            color:"#F7464A",
            highlight: "#FF5A5E",
            label: "Chrome"
        },
        {
            value: default_browser,
            color: "#46BFBD",
            highlight: "#5AD3D1",
            label: "Default"
        }
    ]

    var browser = {
        chromeTotal: chrome,
        defaultTotal: default_browser
    }

    doughnutChart.Doughnut(data);

    return browser;
}

function drawCallLineChart(data) {
    var numR = 0;
    var numS = 0;
    var numM = 0;
    var smsInMonths = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var smsSent = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var smsRe = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var callM = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var charLevel =  ["Jan", "Feb", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    var current_month = new Date().getMonth();
    var current_year = new Date().getFullYear();

    for(var i  = 0; i <  data.length; i++) {

        var date = new Date(data[i].datetime);

        smsInMonths[date.getMonth()] = smsInMonths[date.getMonth()] + 1;
        if(data[i].type == "Dialled") {

            smsSent[date.getMonth()] =  smsSent[date.getMonth()] + 1;
            numS++;
        }else if(data[i].type == "Received") {

            smsRe[date.getMonth()] =  smsRe[date.getMonth()] + 1;
            numR++;
        }else {
            callM[date.getMonth()] =  callM[date.getMonth()] + 1;
            numM++;
        }
    }

    var label = new Array();
    var sent = new Array();
    var re = new Array();
    var missed = new Array();

    for(var j  = current_month - 5; j <= current_month; j++) {

        label.push(charLevel[j]);
        sent.push(smsSent[j]);
        re.push(smsRe[j]);
        missed.push(callM[j]);
    }

    var ctx = $("#callsLineChart").get(0).getContext("2d");
    ctx.clearRect(0, 300, 0, 100);
    var lineChart = new Chart(ctx);

    var data = {
        labels: label,
        datasets: [
            {
                label: "Calls Dialled",
                fillColor: "rgba(255,255,255,0.0)",
                strokeColor: "rgba(213,0,0,1)",
                pointColor: "rgba(213,0,0,1)",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: sent
            },
            {
                label: "Received Calls",
                fillColor: "rgba(255,255,255,0)",
                strokeColor: "rgba(0,150,136,1)",
                pointColor: "rgba(0,150,136,1)",
                pointHighlightStroke: "rgba(151,187,205,1)",
                data: re
            },
            {
                label: "Missed Calls",
                fillColor: "rgba(255,255,255,0)",
                strokeColor: "rgba(255,193,7,1)",
                pointColor: "rgba(255,193,7,1)",
                pointHighlightStroke: "rgba(151,187,205,1)",
                data: missed
            }
        ]
    };


    lineChart.Line(data);

}


function roundToTwo(value) {
    return(Math.round(value * 100) / 100);
}
