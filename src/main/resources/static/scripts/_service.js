function HealthService($resource, $q) {

    var resource = $resource('api', {}, {

        user_profile: { method: 'POST', url: 'api/user/profile' },
        java_checkdeadlock: { method: 'POST', url: 'api/java/checkdeadlock' },
        java_dumpthread: { method: 'POST', url: 'api/java/dumpthread' },
        java_gc: { method: 'POST', url: 'api/java/gc' },
        java_heapdump: { method: 'POST', url: 'api/java/heapdump' },
        java_loadruntimeinfo: { method: 'POST', url: 'api/java/loadruntimeinfo' },
        java_loadthreadinfo: { method: 'POST', url: 'api/java/loadthreadinfo' },

    });

    return {

        user_profile: function () { var d = $q.defer(); resource.user_profile({}, function (result) { d.resolve(result); }, function (result) { d.reject(result); }); return d.promise; },
        java_checkdeadlock: function () { var d = $q.defer(); resource.java_checkdeadlock({}, function (result) { d.resolve(result); }, function (result) { d.reject(result); }); return d.promise; },
        java_dumpthread: function () { var d = $q.defer(); resource.java_dumpthread({}, function (result) { d.resolve(result); }, function (result) { d.reject(result); }); return d.promise; },
        java_gc: function () { var d = $q.defer(); resource.java_gc({}, function (result) { d.resolve(result); }, function (result) { d.reject(result); }); return d.promise; },
        java_heapdump: function () { var d = $q.defer(); resource.java_heapdump({}, function (result) { d.resolve(result); }, function (result) { d.reject(result); }); return d.promise; },
        java_loadruntimeinfo: function () { var d = $q.defer(); resource.java_loadruntimeinfo({}, function (result) { d.resolve(result); }, function (result) { d.reject(result); }); return d.promise; },
        java_loadthreadinfo: function () { var d = $q.defer(); resource.java_loadthreadinfo({}, function (result) { d.resolve(result); }, function (result) { d.reject(result); }); return d.promise; },

    }
}
