var sparrow = angular.module('sparrow');

sparrow.controller('reviews', function ($rootScope, $scope, $http) {
    $http.get('/reviews')
        .success(function (data) {
            $scope.reviews = data;
        });
});
