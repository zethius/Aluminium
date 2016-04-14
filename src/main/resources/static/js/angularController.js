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
        $scope.Timer = function (value) {
            $scope.counter = value;
            $scope.minutes = parseInt($scope.counter / 60, 10);
            $scope.seconds = parseInt($scope.counter % 60, 10);

            $scope.onTimeout = function () {
                $scope.counter--;
                $scope.minutes = parseInt($scope.counter / 60, 10);
                $scope.seconds = parseInt($scope.counter % 60, 10);
                if ($scope.counter == 0) alert("test");
                else mytimeout = $timeout($scope.onTimeout, 1000);
            };
            var mytimeout = $timeout($scope.onTimeout, 1000);
        }
    }])
    .controller('RegisterController', ['$scope', function ($scope) {

    }])
    .controller('CreateTestController', function ($scope, $http) {
        $scope.integerval = /^\d*$/;
        $scope.questionNumber = null;
        $scope.selectedValue = null;
        $scope.languagesSet = [
            {id: '1', name: 'CPP'},
            {id: '2', name: 'JAVA'},
            {id: '3', name: 'PYTHON'}
        ];
        $scope.selectedLanguages = [];


        $scope.setQuestionType = function (selectedValue) {
            var element = document.getElementById('selectQuestion');
            element.value = '0';
            $scope.addQuestion(selectedValue);
        };
        $scope.addQuestion = function (questionId) {
            document.getElementById('testform').setAttribute("action", "/test/create/add?questionId=" + questionId);
            var element = document.getElementById('testform');
            element.submit();
        };
        $scope.setLanguages = function (languages) {
            var array = languages.split(",");
            for (var i = 0; i < $scope.languagesSet.length; i++) {
                if (array.indexOf($scope.languagesSet[i].name) > -1) {
                    $scope.selectedLanguages.push($scope.languagesSet[i].name);
                }
            }
        };
        $scope.changeLanguages = function (taskId) {
            var element = document.getElementById('selectedLanguages');
            var selected1 = [];
            for (var i = 0; i < element.length; i++) {
                if (element.options[i].selected)
                    selected1.push(element.options[i].value);
            }
            document.getElementById('testform').setAttribute("action", "/test/create/change?taskId=" + taskId + "&selected=" + selected1);
            document.getElementById('testform').submit();

        };
        $scope.removeQuestion = function (taskId) {
            alert("CHUJ" + taskId);
            document.getElementById('testform').setAttribute("action", "/test/create/remove?taskId=" + taskId);
            document.getElementById('testform').submit();
        }
    })
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
        $scope.setNotificationId = function (aaa) {
            $scope.activeNotification = aaa;
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
        };
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