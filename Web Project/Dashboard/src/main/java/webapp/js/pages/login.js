
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

            $scope.emptyEmail = null;
            $scope.emptyPassword = null;
            $scope.loginError = null;

            if($scope.user.email == null || $scope.user.password == null) {

                if($scope.user.email == null)
                    $scope.emptyEmail = "Please fill out this input field.";

                else if($scope.user.password == null)
                    $scope.emptyPassword = "Please fill out this input field.";

            }else {

                $("#spinner").removeClass("hide");
                $("#spinner").addClass("show");

                $http({
                    url: 'http://localhost:8080/Dashboard/resources/signin',
                    method: 'POST',
                    data: $scope.user
                }).then(function (response) {

                    $("#spinner").removeClass("show");
                    $("#spinner").addClass("hide");

                    if (response.data.status) {

                        store.set('jwt', response.data.access_token);
                        $state.go('template');

                    }else {

                        $scope.loginError = response.data.message;
                        console.log(response.data.message);
                    }
                    //console.log(response.data);
                }, function (error) {
                });

            }
        }




    $scope.recoverPassword = function(){
        $scope.passwordRevocerError = null;

        if($scope.user.emailRecover == null)
            $scope.passwordRecoverError = "Please provide a email address.";
        else
        {
            $http({
                url: 'http://localhost:8080/Dashboard/resources/recoverPassword',
                method: 'POST',
                data: $scope.user
            }).then(function (response) {

                if (response.data.status) {

                    $state.go('login');
                    $scope.passwordRecoverError = null;
                    $scope.passwordRecoverPass = response.data.message;
                    $scope.button = "Ok";

                }else {
                    $scope.passwordRecoverPass = null;
                    $scope.passwordRecoverError = response.data.message;
                    $scope.button = "Cancel";

                }

            }, function (error) {
            });
        }
    }
    $scope.setupRecoverPassword = function(){
        $scope.button = "Cancel";
        $scope.emailRecover=null;
        $scope.passwordRecoverError=null;
        $scope.passwordRecoverPass=null;
    }
});