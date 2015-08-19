
angular.module('icrawlerApp.home', [
    'ui.router',
    'angular-jwt',
    'angular-storage',
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
    })
    .controller('HomeCtrl', function HomeController($scope, $http, store, jwtHelper, Sms, Device, Calls, Sites) {

        $scope.jwt = store.get('jwt');
        $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

        $scope.sms = Sms.query({device: $scope.decodedJwt.device_id}, function(data) {

            drawUserLineChart(data);
        });

        $scope.calls = Calls.query({device: $scope.decodedJwt.device_id}, function(data) {

        });

        $scope.sites = Sites.query({device: $scope.decodedJwt.device_id}, function(data) {

            drawUserDoughnutChart(data);
        });


        $scope.devices =  Device.query({userId: $scope.decodedJwt.user_id}, function(data) {

            for(var i = 0; i < data.length; i++)
                console.log(data[i].imeNumber + " " + data[i].model)

            $(".device-make").append(data[0].make);
            $(".device-model").append(data[0].model);
            $(".device-ime").append("IME #" + data[0].imeNumber);
           // drawUserLineChart(data);
        });

        $scope.callAnonymousApi = function() {
            // Just call the API as you'd do using $http
            callApi('Anonymous', 'http://localhost:3001/api/random-quote');
        }

        $scope.callSecuredApi = function() {
            callApi('Secured', 'http://localhost:8080/Dashboard/resources/messages/51');
        }

        function callApi(type, url) {
            $scope.response = null;
            $scope.api = type;
            $http({
                url: url,
                method: 'GET'
            }).then(function(quote) {
                $scope.response = quote.data;
            }, function(error) {
                $scope.response = error.data;
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
            console.log(date.getMonth());
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

    console.log(data[0].source);

    var chrome = 0;
    var default_browser = 0;

    for(var i  = 0; i <  data.length; i++) {


        console.log(data[i].browsertbByBrowserId.name);
        if(data[i].browsertbByBrowserId.name == "Chrome") {

            chrome++;
            console.log("Chone");

        }else {

            default_browser++;
            console.log("default");
        }
    }

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
