
var appServices = angular.module('icrawlerServices', ['ngResource']);

var HOST = 'http://localhost:8080'

appServices.factory('Sms', ['$resource',
    function($resource){
        return $resource(HOST + '/Dashboard/resources/:device/messages/', {
            device: '@device'
        }, {
            create: {
                method: 'POST'
            }
        });
    }]);

appServices.factory('Device', ['$resource',
    function($resource){
        return $resource(HOST + '/Dashboard/resources/:userId/devices/', {
            userId: '@userId'
        }, {
            create: {
                method: 'POST'
            }
        });
    }]);

appServices.factory('Calls', ['$resource',
    function($resource){
        return $resource(HOST + '/Dashboard/resources/:device/calls/', {
            device: '@device'
        }, {
            create: {
                method: 'POST'
            }
        });
    }]);

appServices.factory('Sites', ['$resource',
    function($resource){
        return $resource(HOST + '/Dashboard/resources/:device/visitedSites/', {
            device: '@device'
        }, {
            create: {
                method: 'POST'
            }
        });
    }]);

appServices.factory('Apps', ['$resource',
    function($resource){
        return $resource(HOST + '/Dashboard/resources/:device/apps/', {
            device: '@device'
        }, {
            create: {
                method: 'POST'
            }
        });
    }]);

appServices.factory('CurrentDevice', function () {
    var deviceId = 0;
    var name = "n/a"

    return {
        getDeviceId: function () {
            return deviceId;
        },
        setdeviceId: function(d) {
            deviceId = d;
        },
        getDeviceName: function () {
            return name;
        },
        setdeviceName: function(n) {
            name = n;
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