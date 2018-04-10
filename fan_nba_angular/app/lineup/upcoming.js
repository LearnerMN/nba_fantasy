/**
 * % 20-Mar-16.
 */
'use strict';

angular.module('fantasy.lineup.upcoming', ['ui.router'])
    .controller('UpcomingLineup', ['$http', '$scope', '$rootScope', '$state', '$stateParams', '$filter', '$timeout', '$mdDialog', '$mdMedia', 'contest', 'users', 'lineup',
        function ($http, $scope, $rootScope, $state, $stateParams, $filter, $timeout, $mdDialog, $mdMedia, contest, users, lineup) {

            var ma = this;
            ma.error = contest.error;
            console.log('contest',contest);
            console.log('lineup',lineup);
            console.log('users',users);
            $scope.entryLineup = lineup.entryLineup;
            $scope.matches = contest.matches;
            $scope.users = users;
        }]);