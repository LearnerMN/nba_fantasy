/**
 * % 20-Mar-16.
 */

'use strict';

angular.module('fantasy.lineup.history', ['ui.router'])
    .controller('LineupHistoryCtrl', ['$http', '$rootScope', '$scope', '$state', '$mdDialog', '$mdMedia', 'contest', 'users', 'lineup', 'contestDetailFactory',
        function ($http, $rootScope, $scope, $state, $mdDialog, $mdMedia, contest, users, lineup, contestDetailFactory) {

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

            $scope.showTabDialog = function (ev, player) {
                $mdDialog.show({
                    controller: DialogController,
                    templateUrl: 'partials/_player_statistics.html',
                    parent: angular.element(document.body),
                    targetEvent: ev,
                    clickOutsideToClose: true,
                    resolve: {
                        log: function (contestDetailFactory) {
                            return contestDetailFactory.getPlayerStats(player.code);
                        },
                        player: function () {
                            return player;
                        }
                    }
                });
            };

            function DialogController($scope, $mdDialog, log, player) {
                $scope.position = {'PG': 'Point Gaurd', 'SG': 'Shooting Gaurd', 'G': 'Gaurd', 'PF': 'Power Forward', 'SF': 'Small Forward', 'C': 'Center'};
                $scope.logs = log;
                $scope.player = player;
                console.log('player: ', $scope.player);
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
        }]);