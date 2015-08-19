
var appServices = angular.module('icrawlerServices', ['ngResource']);
appServices.factory('Sms', ['$resource',
    function($resource){
        return $resource('http://localhost:8080/Dashboard/resources/:device/messages/', {
            device: '@device'
        }, {
            create: {
                method: 'POST'
            }
        });
    }]);

appServices.factory('Device', ['$resource',
    function($resource){
        return $resource('http://localhost:8080/Dashboard/resources/:userId/devices/', {
            userId: '@userId'
        }, {
            create: {
                method: 'POST'
            }
        });
    }]);

appServices.factory('Calls', ['$resource',
    function($resource){
        return $resource('http://localhost:8080/Dashboard/resources/:device/calls/', {
            device: '@device'
        }, {
            create: {
                method: 'POST'
            }
        });
    }]);

appServices.factory('Sites', ['$resource',
    function($resource){
        return $resource('http://localhost:8080/Dashboard/resources/:device/visitedSites/', {
            device: '@device'
        }, {
            create: {
                method: 'POST'
            }
        });
    }]);

/*
appServices.factory('Login', ['$resource',
    function($resource){
        return $resource('http://localhost:8080/Dashboard/resources/signin', {}, {
            login: {
                method: 'POST',
                isArray: false
            }
        });
    }]);
*/
