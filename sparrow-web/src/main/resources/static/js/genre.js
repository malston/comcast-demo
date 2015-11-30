var sparrow = angular.module('sparrow');

sparrow.controller('genres', function ($scope, $http, $routeParams) {
    $http.get('/genres/' + $routeParams.genreMlId)
        .success(function (data) {
            $scope.genre = data;
        });

    $http.get('/movies/genre/' + $routeParams.genreMlId)
        .success(function (data) {
            $scope.movies = data;
        });
});
