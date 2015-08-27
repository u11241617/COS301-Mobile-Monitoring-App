
angular.module('icrawlerApp.browser', [
    'ui.router',
    'datatables',
    'angular-jwt',
    'angular-storage',
    'icrawlerServices'
]).config(function($stateProvider) {
        $stateProvider.state('template.browser', {
            url: '/browser',
            controller: 'BrowserCtrl',
            templateUrl: 'browser.html',
            data: {
                requiresLogin: true
            }
        });
    })
    .controller('BrowserCtrl', function HomeController($scope, $http, store, jwtHelper, Sites) {

        $scope.jwt = store.get('jwt');
        $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

        $scope.sites = Sites.query({device: $scope.decodedJwt.device_id}, function(data) {

        });
    });
