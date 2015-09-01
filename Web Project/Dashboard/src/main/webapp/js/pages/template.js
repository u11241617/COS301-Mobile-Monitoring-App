
var appLogin =  angular.module('icrawlerApp.template', [
    'ui.router',
    'angular-storage'
]).config(function($stateProvider) {
        $stateProvider.state('template', {
            url: '/template',
            controller: 'TemplateCtrl',
            templateUrl: 'template.html'
        });
});

appLogin.controller('TemplateCtrl', function LoginController( $scope, $http, store, $state, Logout) {

    console.log("State: " + $state.get());

    if($state.is("template") || $state.is("template.dashboard")) {

        $state.go('template.dashboard');
    }else if( $state.is('template.calls')) {

        $state.go('template.calls');
    }else if( $state.is('template.messages')) {

        $state.go('template.messages');
    }else if($state.is('template.browser')) {

        $state.go('template.browser');
    }

    $scope.showlogoutDialog = function() {

        $(".modal").modal('show');
    }


    $scope.logout = function() {

        $(".modal").modal('show');
    }
});