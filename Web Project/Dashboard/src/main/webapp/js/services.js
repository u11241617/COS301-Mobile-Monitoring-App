
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

appServices.factory('Apps', ['$resource',
    function($resource){
        return $resource('http://localhost:8080/Dashboard/resources/:device/apps/', {
            device: '@device'
        }, {
            create: {
                method: 'POST'
            }
        });
    }]);

appServices.factory('CurrentDevice', function () {
    var deviceId = 0;

    return {
        getDeviceId: function () {
            return deviceId;
        },
        setdeviceId: function(d) {
            deviceId = d;
        }
    };
});


appServices.factory('Logout', function () {

    return {
        out: function() {
            alert("Hello");
        }
    };
});