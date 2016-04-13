angular.module('ngApp', [])
    .controller('CreateTestController', ['$scope', function ($scope) {
        $scope.integerval = /^\d*$/;
        $scope.questionNumber=null;

        $scope.setQuestionType = function () {
            var idQuestion = document.getElementById('selectQuestion').selectedIndex;
            alert(idQuestion);
            addQuestionById();
        };

    }]);
