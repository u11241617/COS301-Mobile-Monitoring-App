
angular.module('icrawlerApp.calls', [
    'ui.router',
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
    .controller('CallsCtrl', function HomeController($scope, $http, store, jwtHelper, Calls) {

        $scope.jwt = store.get('jwt');
        $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

        $scope.calls_data = Calls.query({device: $scope.decodedJwt.device_id}, function(data) {


        });


    });
