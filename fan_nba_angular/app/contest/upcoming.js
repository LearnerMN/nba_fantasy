/**
 * % 20-Mar-16.
 */
'use strict';

angular.module('fantasy.contest.upcoming', ['ui.router'])
    .controller('ContestUpcomingCtrl', ['$http', '$rootScope', '$scope', '$state', '$mdDialog', '$mdMedia', 'contests',
        function ($http, $rootScope, $scope, $state, $mdDialog, $mdMedia, contests) {

            $scope.line_ups = contests.entryLineups;
            var vm = this;

            $scope.getDetail = function (contest_id, entry_id) {
                $state.go('lineupHistory', {contest_id: contest_id, entry_id: entry_id});
            };

            $scope.showalert = function (event) {
                var alert = $mdDialog.confirm()
                    .title('Sorry')
                    .textContent('this is what you click on')
                    .ok('Ok')
                    .targetEvent(event);
                $mdDialog.show(alert);
            };

        }]);
