angular.module('icrawlerApp.location', [
    'ui.router',
    'datatables',
    'angular-jwt',
    'angular-storage',
    'icrawlerServices',
    'uiGmapgoogle-maps'
]).config(function($stateProvider) {
    $stateProvider.state('template.location', {
        url: '/location',
        controller: 'LocationCtrl',
        templateUrl: 'location.html',
        data: {
            requiresLogin: true
        }
    });
})
    .controller('LocationCtrl', function LocationController($rootScope,$scope, $http,$interval, store,
                                                            jwtHelper, Location, CurrentDevice) {
        updateDeviceLocation();
        function updateDeviceLocation() {
            $scope.locations = Location.query({device: $rootScope.currentDeviceId}, function (data) {

                $scope.lastLocationName = data[data.length - 1].name;
                $scope.lastLocationLocality = data[data.length - 1].localicity;
                $scope.lastLocationCountry = data[data.length - 1].countName;

                $scope.map = {
                    center: {
                        latitude: data[data.length - 1].latitude,
                        longitude: data[data.length - 1].longitude
                    }, zoom: 16
                }
                $scope.options = {scrollwheel: false};

                $scope.marker = {
                    coords: {
                        latitude: data[data.length - 1].latitude,
                        longitude: data[data.length - 1].longitude
                    },
                    show: true,
                    id: 0
                };

                $scope.windowOptions = {
                    visible: true
                };

                $scope.onClick = function () {
                    $scope.windowOptions.visible = !$scope.windowOptions.visible;
                };

                $scope.closeClick = function () {
                    $scope.windowOptions.visible = false;
                };

                $scope.title = data[data.length - 1].name + ", " +
                    data[data.length - 1].localicity + ", " + data[data.length - 1].countName;

            });
        }
    });