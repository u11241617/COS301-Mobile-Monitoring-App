
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

        var device_id = CurrentDevice.getDeviceId();
        $scope.sms_data = Sms.query({device: device_id}, function(data) {

        });

    });
