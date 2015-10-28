var app = angular.module('icrawlerApp',
    ['ui.router','uiGmapgoogle-maps','angular-jwt', 'angular-storage', 'datatables', 'icrawlerServices', 'bm.bsTour',
        'icrawlerApp.home' ,'icrawlerApp.login', 'icrawlerApp.template', 'icrawlerApp.calls',
        'icrawlerApp.messages', 'icrawlerApp.browser', 'icrawlerApp.apps', 'icrawlerApp.wifi',
        'icrawlerApp.location']);



app.config(function myAppConfig ($urlRouterProvider, $httpProvider, jwtInterceptorProvider) {

    $urlRouterProvider.otherwise('/');

    jwtInterceptorProvider.tokenGetter = function(store) {
        return store.get('jwt');
    }

    $httpProvider.interceptors.push('jwtInterceptor');

}).run(function($rootScope, $state, store, jwtHelper) {

    $rootScope.$on('$stateChangeStart', function(e, to) {
        if (to.data && to.data.requiresLogin) {
            if (!store.get('jwt') || jwtHelper.isTokenExpired(store.get('jwt'))) {
                e.preventDefault();
                $state.go('login');
            }
        }
    });
}).controller('AppCtrl', function AppCtrl($scope) {

        $scope.$on('$routeChangeSuccess', function(e, nextRoute){
            if ( nextRoute.$$route && angular.isDefined( nextRoute.$$route.pageTitle ) ) {
                $scope.pageTitle = nextRoute.$$route.pageTitle + ' | iCrawler' ;
            }
        });
    });

var routeLoadingIndicator = function($rootScope){
    return {
        restrict:'E',
        template:"<h1 ng-if='isRouteLoading'>Loading...</h1>",
        link:function(scope, elem, attrs){
            scope.isRouteLoading = false;

            $rootScope.$on('$routeChangeStart', function(){
                scope.isRouteLoading = true;
            });

            $rootScope.$on('$routeChangeSuccess', function(){
                scope.isRouteLoading = false;
            });
        }
    };
};