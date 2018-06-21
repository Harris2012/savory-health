function HeaderController($scope, HealthService) {

    function user_profile_callback(response) {

        if (response.statusCode == 1) {
            $scope.userName = response.userName;
        }
    }

    HealthService.user_profile().then(user_profile_callback);
}