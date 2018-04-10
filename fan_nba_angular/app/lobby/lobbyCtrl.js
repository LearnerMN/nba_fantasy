/**
 * % 20-Mar-16.
 */
'use strict';

angular.module('fantasy.lobby', ['ui.router'])
    .factory('lobbyFactory', function ($http) {
        return {
            get: function () {
                return $http.get("http://fantasy/api/v1/contest/list")
                    .then(function successCallback(response) {
                        return response;
                    });
            }
        }
    })
    .controller('LobbyCtrl', ['$http', '$rootScope', '$scope', '$state', '$mdDialog', '$mdMedia', 'factory', 'contestDetailFactory',
        function ($http, $rootScope, $scope, $state, $mdDialog, $mdMedia, factory, contestDetailFactory) {

            $scope.contests = [];

            var vm = this;
            vm.init = function () {
                for (var ii = 0; ii < factory.data.contests.length; ii++) {
                    $scope.contests.push(factory.data.contests[ii]);
                    $scope.contests[ii].index = ii;
                }
            };

            vm.init();

            $scope.enterContest = function (event, index) {
                var contest = $scope.contests[index];
                if (contest.entryCount >= contest.entryLimit) {
                    $scope.showalert(event, 'Contest is Full');
                } else {
                    $state.go('lineup', {contest_id: contest.id});
                }
            };

            $scope.showalert = function (event, msg) {
                var alert = $mdDialog.confirm()
                    .title('Sorry')
                    .textContent(msg)
                    .ok('Ok')
                    .targetEvent(event);
                $mdDialog.show(alert);
            };

            $scope.showContestData = function (ev, contest_id) {
                var useFullScreen = ($mdMedia('sm') || $mdMedia('xs')) && $scope.customFullscreen;
                $mdDialog.show({
                    controller: contestDetailFactory.DialogController,
                    templateUrl: 'partials/contestDetail.html',
                    parent: angular.element(document.body),
                    targetEvent: ev,
                    clickOutsideToClose: true,
                    fullscreen: useFullScreen,
                    resolve: {
                        contest: function () {
                            return contestDetailFactory.getContest(contest_id);
                        },
                        users: function () {
                            return contestDetailFactory.getUsers(contest_id);
                        }
                    }
                });

                $scope.$watch(function () {
                    return $mdMedia('xs') || $mdMedia('sm');
                }, function (wantsFullScreen) {
                    $scope.customFullscreen = (wantsFullScreen === true);
                });
            };

        }]);

