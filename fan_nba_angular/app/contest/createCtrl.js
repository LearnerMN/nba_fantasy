/**
 * % 20-Mar-16.
 */
'use strict';

angular.module('fantasy.create', ['ui.router'])
    .controller('CreateCtrl', ['$http', '$rootScope', '$scope', '$state', '$timeout', 'holders',
        function ($http, $rootScope, $scope, $state, $timeout, holders) {
            console.log('holders', holders);
            var vm = this;
            $scope.holders = holders;
            $scope.holder = $scope.holders[0];
            $scope.matches = holders;
            $scope.createDetail = {
                yCode: 0,
                entryFee: '0',
                entryLimit: '2',
                entryPrize: '0',
                startTime: '0',
                scope: 'public'
            };

            $scope.reloadSly = function (id) {
                angular.element(document.querySelector(id)).sly("reload").sly("toStart");
            };

            $scope.createContest = function () {
                $http.post('http://fantasy/api/v1/contest/create', {
                    yCode: $scope.holder.yCode,
                    sportCode: 'nba',
                    title: $scope.createDetail.title,
                    scope: $scope.createDetail.scope,
                    entryFee: $scope.createDetail.entryFee,
                    entryLimit: $scope.createDetail.entryLimit,
                    totalPrize: 0,
                    startTime: $scope.holder['start_time']
                }).success(function (response) {
                    $state.go('lineup', {contest_id: response.result});
                }).error(function () {
                    alert("error");
                });
            };
        }]);
