angular.module('icrawlerApp.wifi', [
    'ui.router',
    'datatables',
    'angular-jwt',
    'angular-storage',
    'icrawlerServices'
]).config(function($stateProvider) {
    $stateProvider.state('template.wifi', {
        url: '/wifi',
        controller: 'wifiCtrl',
        templateUrl: 'wifi.html',
        data: {
            requiresLogin: true
        }
    });
}).controller('wifiCtrl', function HomeController($scope, $http, store, jwtHelper, WIFI, CurrentDevice) {

        $scope.jwt = store.get('jwt');
        $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

        var device_id = CurrentDevice.getDeviceId();
        $scope.wifi_data = WIFI.query({device:device_id}, function(data) {
        });

    });


