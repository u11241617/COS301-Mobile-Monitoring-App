
var appLogin =  angular.module( 'icrawlerApp.login', [
    'ui.router',
    'angular-storage'
]).config(function($stateProvider) {
        $stateProvider.state('login', {
            url: '/',
            controller: 'LoginCtrl',
            templateUrl: 'login.html'
        });
});

appLogin.controller('LoginCtrl', function LoginController( $scope, $http, store, $state) {

        $scope.user = {};

        $scope.login = function() {
            $http({
                url: 'http://localhost:8080/Dashboard/resources/signin',
                method: 'POST',
                data: $scope.user
            }).then(function (response) {

                if (response.data.status) {

                    store.set('jwt', response.data.access_token);
                    $state.go('template');

                }
                console.log(response.data.access_token);
            }, function (error) {
                alert(error.data);
            });
        }
});