
angular.module('icrawlerApp.calls', [
    'ui.router',
    'datatables',
    'angular-jwt',
    'angular-storage',
    'icrawlerServices'
]).config(function($stateProvider) {
        $stateProvider.state('template.calls', {
            url: '/calls',
            controller: 'CallsCtrl',
            templateUrl: 'calls.html',
            data: {
                requiresLogin: true
            }
        });
    })
    .controller('CallsCtrl', function HomeController($rootScope, $scope, $http, store,
                                                     jwtHelper, Calls, CurrentDevice) {

        $scope.jwt = store.get('jwt');
        $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

        $scope.dialed = 0;
        $scope.missed = 0;
        $scope.callHistory = {dialed:0, missed:0, received:0};

        var device_id = CurrentDevice.getDeviceId();
        $scope.calls_data = Calls.query({device:$rootScope.currentDeviceId}, function(data) {

            var total = 0;
            for(var i = 0; i < data.length; i++) {

                if(data[i].type == "Dialed") {
                    $scope.callHistory.dialed++;
                }else if(data[i].type == "Missed") {
                    $scope.callHistory.missed++;
                }else {
                    $scope.callHistory.received++;
                }
                total++;
            }

            angular.element('#totalDialed').attr('value', (( $scope.callHistory.dialed / total) * 100));
            angular.element('#totalMissed').attr('value', (( $scope.callHistory.missed / total) * 100));
            angular.element('#totalReceived').attr('value',(( $scope.callHistory.received / total) * 100));
            loadCallsSummary();
        });


    });

function loadCallsSummary() {
$(".knob").knob({
    draw: function () {

        // "tron" case
        if (this.$.data('skin') == 'tron') {

            var a = this.angle(this.cv)  // Angle
                , sa = this.startAngle          // Previous start angle
                , sat = this.startAngle         // Start angle
                , ea                            // Previous end angle
                , eat = sat + a                 // End angle
                , r = true;

            this.g.lineWidth = this.lineWidth;

            this.o.cursor
            && (sat = eat - 0.3)
            && (eat = eat + 0.3);

            if (this.o.displayPrevious) {
                ea = this.startAngle + this.angle(this.value);
                this.o.cursor
                && (sa = ea - 0.3)
                && (ea = ea + 0.3);
                this.g.beginPath();
                this.g.strokeStyle = this.previousColor;
                this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, sa, ea, false);
                this.g.stroke();
            }

            this.g.beginPath();
            this.g.strokeStyle = r ? this.o.fgColor : this.fgColor;
            this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, sat, eat, false);
            this.g.stroke();

            this.g.lineWidth = 2;
            this.g.beginPath();
            this.g.strokeStyle = this.o.fgColor;
            this.g.arc(this.xy, this.xy, this.radius - this.lineWidth + 1 + this.lineWidth * 2 / 3, 0, 2 * Math.PI, false);
            this.g.stroke();

            return false;
        }
    }
});}


