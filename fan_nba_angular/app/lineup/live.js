/**
 * % 24-Apr-16.
 */

'use strict';

angular.module('fantasy.lineup.live', ['ui.router'])
    .controller('LineupLiveCtrl', ['$http', '$rootScope', '$scope', '$state', '$mdDialog', '$mdMedia', 'contest', 'users', 'lineup', 'contestDetailFactory', '$interval', '$timeout',
        function ($http, $rootScope, $scope, $state, $mdDialog, $mdMedia, contest, users, lineup, contestDetailFactory, $interval, $timeout) {

            $scope.contest = contest;
            $scope.users = users;
            $scope.lineup = lineup.entryLineup;
            $scope.show_opponent = false;
            $scope.opponent = {};
            console.log('contest', contest);
            console.log('lineup', lineup);
            console.log('users', users);
            //var vm = this;
            $scope.show_alert = function (event) {
                var alert = $mdDialog.confirm()
                    .title('Sorry')
                    .textContent('this is what you click on')
                    .ok('Ok')
                    .targetEvent(event);
                $mdDialog.show(alert);
            };

            $scope.clear_opponent = function () {
                $scope.opponent = {};
            };

            $scope.get_opponent = function (entry_id) {
                contestDetailFactory.getLineUp(entry_id).then(function (data) {
                    if (data.error != null) {
                        alert(data.error);
                        return
                    }
                    $scope.opponent = data.entryLineup;
                    $scope.show_opponent = true;
                });
            };

            $scope.get_opponent($scope.users[0].id);

            $scope.showTabDialog = function (ev, player_code) {
                $mdDialog.show({
                    controller: DialogController,
                    templateUrl: 'partials/_player_statistics.html',
                    parent: angular.element(document.body),
                    targetEvent: ev,
                    clickOutsideToClose: true,
                    resolve: {
                        log: function (contestDetailFactory) {
                            return contestDetailFactory.getPlayerStats(player_code);
                        }
                    }
                });
            };
            
            var refresh = function() {
                $http.get('http://fantasy/api/v1/lineups?user_id=3&state=live').then(function(r) {
                    console.log(r.data);
                    if(r.data.entryLineups == null){
                        cancel(refresh);
                        console.log("cancelled");
                    } else {
                        $scope.lineup = r.data.entryLineups[0];
                    }
                    $timeout(refresh, 60000);
                });

            };

            function DialogController($scope, $mdDialog, log) {
                $scope.log = log;
                $scope.hide = function () {
                    $mdDialog.hide();
                };
                $scope.cancel = function () {
                    $mdDialog.cancel();
                };
                $scope.answer = function (answer) {
                    $mdDialog.hide(answer);
                };
            }

            refresh();
        }]);