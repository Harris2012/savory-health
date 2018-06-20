function HealthService($resource, $q) {

    var resource = $resource('api', {}, {

        user_profile: { method: 'POST', url: 'api/user/profile' }
    });

    return {

        user_profile: function () { var d = $q.defer(); resource.user_profile({}, function (result) { d.resolve(result); }, function (result) { d.reject(result); }); return d.promise; }
    }
}
