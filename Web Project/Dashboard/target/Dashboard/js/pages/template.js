
var appLogin =  angular.module('icrawlerApp.template', [
    'ui.router',
    'angular-storage',
    'bm.bsTour',
    'icrawlerServices'
]).config(function($stateProvider) {
        $stateProvider.state('template', {
            url: '/template',
            controller: 'TemplateCtrl',
            templateUrl: 'template.html'
        });
});

appLogin.controller('TemplateCtrl',
    function TemplateController($rootScope, $scope, $http, store,
                             $state, $interval, DeviceState) {

       $interval(function() {
            updateDeviceState();
        }, 300);

       function updateDeviceState() {

           $scope.deviceState = DeviceState.query({deviceId: $rootScope.currentDeviceId}, function(data) {

               $rootScope.currentDeviceStatus = data[0].status;

               if($rootScope.currentDeviceStatus == "ON") {

                   angular.element('#currentDeviceStatusId').attr('class', "fa fa-circle text-success");

               }else {

                   angular.element('#currentDeviceStatusId').attr('class', "fa fa-circle text-red");
               }
               console.log(data + "");
           });


       }



        if($state.is("template") || $state.is("template.dashboard")) {

            $state.go('template.dashboard');
        }else if( $state.is('template.calls')) {

            $state.go('template.calls');
        }else if( $state.is('template.messages')) {

            $state.go('template.messages');
        }else if($state.is('template.browser')) {

            $state.go('template.browser');

        }else if($state.is('template.apps')) {

            $state.go('template.apps');
        }

        $scope.showlogoutDialog = function() {

            $(".modal").modal('show');
        };


        $scope.logout = function() {
            $(".modal").modal('hide');
            store.remove('jwt');
            $state.go('login');
        }
});
