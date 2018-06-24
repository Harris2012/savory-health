function ThreadListController($scope, HealthService) {

    function java_loadthreadinfo_callback(response) {

        $scope.loading = false;
        if (response.statusCode != 1) {
            swal(response.message);
            return;
        }

        $scope.threadInfoList = response.threadInfoList;
    }

    HealthService.java_loadthreadinfo().then(java_loadthreadinfo_callback);
}