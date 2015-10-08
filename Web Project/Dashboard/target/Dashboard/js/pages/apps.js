angular.module('icrawlerApp.apps', [
    'ui.router',
    'datatables',
    'angular-jwt',
    'angular-storage',
    'icrawlerServices'
]).config(function($stateProvider) {
        $stateProvider.state('template.apps', {
            url: '/apps',
            controller: 'AppsCtrl',
            templateUrl: 'apps.html',
            data: {
                requiresLogin: true
            }
        });
    })
    .controller('AppsCtrl', function HomeController($scope, $http, store, jwtHelper, Apps, CurrentDevice) {

        $scope.jwt = store.get('jwt');
        $scope.decodedJwt = $scope.jwt && jwtHelper.decodeToken($scope.jwt);

        var device_id = CurrentDevice.getDeviceId();
        $scope.apps_data = Apps.query({device:device_id}, function(data) {

            console.log("I am here: " + data);
        });

        $scope.getPermissions = function(appId) {

                alert(" " + appId);
                $(".md-trigger").click();
        }

    });


