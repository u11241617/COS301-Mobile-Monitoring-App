
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
    }).controller('HomeCtrl', function HomeController($scope, $state, $http, store, jwtHelper, Sms, Device, Calls, Sites, CurrentDevice) {

        $scope.jwt = store.get('jwt');
        $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

        $scope.device_logs =  Device.query({userId: $scope.decodedJwt.user_id}, function(data) {

            CurrentDevice.setdeviceId(data[0].deviceId);

            $scope.sms = Sms.query({device: data[0].deviceId}, function(data) {

                drawUserLineChart(data);
            });

            $scope.calls = Calls.query({device: data[0].deviceId}, function(data) {

            });

            $scope.sites = Sites.query({device: data[0].deviceId}, function(data) {

                drawUserDoughnutChart(data);


            });

        });

        $scope.load_data = function(device_id) {

            $scope.sms = Sms.query({device: device_id}, function(data) {

                drawUserLineChart(data);
            });

            $scope.calls = Calls.query({device: device_id}, function(data) {

            });

            $scope.sites = Sites.query({device: device_id}, function(data) {

                drawUserDoughnutChart(data);
            });

            CurrentDevice.setdeviceId(device_id);
            $scope.device_logs =  Device.query({userId: $scope.decodedJwt.user_id}, function(data) {

            });
        }

    });


function drawUserLineChart(data) {


    var numR = 0;
    var numS = 0;
    var smsInMonths = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var smsSent = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var smsRe = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var charLevel =  ["Jan", "Feb", "March", "April", "May", "June", "July", "August"];

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


        console.log(data[i].browsertbByBrowserId.name);
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

    doughnutChart.Doughnut(data);
}


function tour() {

    //$(document).ready(function() {

    localStorage.clear();
        var tour = new Tour({
            steps: [
                {
                    element: ".nav-sidebar",
                    title: "Navigation",
                    content: "This section allows you to navigate the site and view different content"
                },
                {
                    element: "#summary",
                    title: "Logs Summary",
                    content: "A summary of some the the collected data on the current device"
                }
            ]});

// Initialize the tour
        tour.init();

// Start the tour
        tour.start();

        alert("Hello");


   // });

}
