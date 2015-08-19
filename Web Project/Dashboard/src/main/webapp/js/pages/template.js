
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

appLogin.controller('TemplateCtrl', function LoginController( $scope, $http, store, $state) {

    console.log("State: " + $state.get());

    if($state.is("template") || $state.is("template.dashboard")) {

        $state.go('template.dashboard');
    }else if( $state.go('template.calls')) {

        $state.go('template.calls');
    }

});