angular.module('ngApp', [])
    .filter('range', function () {
        return function (input, min, max) {
            min = parseInt(min);
            max = parseInt(max);
            for (var i = min; i <= max; i += min)
                input.push(i);
            return input;
        };
    })
    .controller('TimerController', ['$scope', '$timeout', function ($scope, $timeout) {
        $scope.Timer = function () {
            $scope.counter = 3600;
            $scope.minutes = parseInt($scope.counter / 60, 10);
            $scope.seconds = parseInt($scope.counter % 60, 10);

            $scope.onTimeout = function () {
                $scope.counter--;
                $scope.minutes = parseInt($scope.counter / 60, 10)
                $scope.seconds = parseInt($scope.counter % 60, 10);
                if($scope.counter==0) alert("test");
                else mytimeout = $timeout($scope.onTimeout,1000);
            }
            var mytimeout = $timeout($scope.onTimeout, 1000);
        }
    }])
    .controller('RegisterController', ['$scope', function ($scope) {

    }])
    .controller('NotificationController', function ($scope, $http) {
        $scope.pageNumber = 0;
        $scope.totalPages = null;
        $scope.activeNotification = null;
        $scope.unreadNotification = 0;


        $scope.getNotifications = function () {
            $http.get('/api/notifications')
                .success(function (data) {
                    $scope.data = data;
                    $http.get('/api/notifications/get/totalPages').success(function (data2) {
                        $scope.totalPages = data2;
                        $scope.pageNumber = 0;
                    });
                });
        };
        $scope.getTop5Notifications = function () {
            $http.get('/api/notifications/top5')
                .success(function (data) {
                    $scope.data3 = data;
                });
        };
        $scope.notificationsFromPage = function (page) {
            $http.get('/api/notifications?page=' + page)
                .success(function (data) {
                    $scope.pageNumber = page - 1;
                    $scope.data = data;
                });
        }
        $scope.getTotalPages = function () {
            $http.get('/api/notifications/get/totalPages?page=' + $scope.pageNumber).success(function (data2) {
                    $scope.totalPages = data2;
                    return $scope.totalPages;
                }
            );
        };
        $scope.getMsgCount = function () {
            $http.get('/api/notifications/get/msgCount').success(function (data4) {
                    $scope.unreadNotification = data4;
                    return $scope.unreadNotification;
                }
            );
        };
        $scope.changeNotificationStatus = function (notificationId) {
            $http.put('/api/notifications/updateStatus?id=' + notificationId)
                .success(function () {
                    $scope.activeNotification = notificationId;
                    $scope.getMsgCount();
                    $scope.notificationsFromPage($scope.pageNumber + 1);
                    $scope.getTop5Notifications();
                });
        };
    });