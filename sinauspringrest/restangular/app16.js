var app = angular
        .module("myModule", [])
        .controller("myController", function ($scope, $http) {

            $http.get("http://localhost:8081/students")
                 .then(function (response) {
                     $scope.students = response.data;
                 });
        });
