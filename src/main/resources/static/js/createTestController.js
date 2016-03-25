var createTestApp = angular.module('createTestApp', []);
createTestApp.controller('createTestCtrl', function ($scope, $http) {
    $scope.questionNumber=null;

    $scope.setQuestionType = function () {
        var idQuestion = document.getElementById('selectQuestion').selectedIndex;
        alert(idQuestion);
        addQuestionById();
    };
});