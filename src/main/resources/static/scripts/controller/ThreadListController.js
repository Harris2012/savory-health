function ThreadListController($scope, HealthService) {

    function java_loadthreadinfo_callback(response) {

        $scope.loading = false;
        if (response.statusCode != 1) {
            swal(response.message);
            return;
        }

        $scope.threadCount = response.threadCount;
        $scope.daemonThreadCount = response.daemonThreadCount;

        $scope.stateMap_RUNNABLE = response.stateMap.RUNNABLE;
        $scope.stateMap_TIMED_WAITING = response.stateMap.RUNNABLE;
        $scope.stateMap_WAITING = response.stateMap.RUNNABLE;

        $scope.threadInfoList = response.threadInfoList;
    }

    HealthService.java_loadthreadinfo().then(java_loadthreadinfo_callback);
}