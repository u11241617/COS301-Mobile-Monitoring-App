
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
    .controller('CallsCtrl', function HomeController($scope, $http, store, jwtHelper, Calls, CurrentDevice) {

        $scope.jwt = store.get('jwt');
        $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

        var device_id = CurrentDevice.getDeviceId();
        $scope.calls_data = Calls.query({device:device_id}, function(data) {

        });


    });
