var sparrow = angular.module('sparrow');

sparrow.controller('home', function ($rootScope, $scope, $http) {
    $http.get('/genres').success(function (data) {
        $scope.genres = data;
    });

    $http.get('user').success(function (data) {
        if (data.username) {
            $http.get('recommendations/recommendations/forUser/' + data.username)
                .success(function (recs) {
                    $scope.recommendations = recs;
                });
        }
    });
    
//	$http.get('/resource/').success(function(data) {
//		$scope.greeting = data;
//	});
});
