
angular.module('icrawlerApp.messages', [
    'ui.router',
    'datatables',
    'angular-jwt',
    'angular-storage',
    'icrawlerServices'
]).config(function($stateProvider) {
        $stateProvider.state('template.messages', {
            url: '/messages',
            controller: 'MessagesCtrl',
            templateUrl: 'messages.html',
            data: {
                requiresLogin: true
            }
        });
    })
    .controller('MessagesCtrl', function HomeController($scope, $http, store, jwtHelper, Sms, CurrentDevice) {

        $scope.jwt = store.get('jwt');
        $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

        $scope.smsHistory = {sent:0,received:0};

        var device_id = CurrentDevice.getDeviceId();
        $scope.sms_data = Sms.query({device: device_id}, function(data) {

            var total = 0;
            for(var i = 0; i < data.length; i++) {

                if(data[i].type == "Sent") {
                    $scope.smsHistory.sent++;
                }else {
                    $scope.smsHistory.received++;
                }
                total++;
            }

            if(total != 0 ) {

                angular.element('#totalSent').attr('value', roundToTwo(( $scope.smsHistory.sent / total) * 100));
                angular.element('#totalMessagesReceived').attr('value',roundToTwo(( $scope.smsHistory.received / total) * 100));

            }else {

                angular.element('#totalSent').attr('value', 0);
                angular.element('#totalMessagesReceived').attr('value',0);
            }

            loadMessagesSummary();

        });


    });

function roundToTwo(value) {
    return(Math.round(value * 100) / 100);
}

function loadMessagesSummary() {

    $(".knob").knob({
        draw: function () {
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


