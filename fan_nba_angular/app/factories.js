/**
 * % 20-Mar-16.
 */
angular.module('fantasy')
    .factory('contestDetailFactory', function ($http) {
        return {
            getContest: function (contest_id) {
                //console.log('contest_id',contest_id);
                return $http.get('http://fantasy/api/v1/contest/' + contest_id)
                    .then(function successCallback(response) {
                        //console.log(response.data.contest);
                        return response.data.contest;
                    });
            },

            getUsers: function (contest_id) {
                //console.log('contest_id',contest_id);
                return $http.get('http://fantasy/api/v1/lineup/' + contest_id + '/users/0/5')
                    .then(function successCallback(response) {
                        //console.log(response.data.result);
                        return response.data.result;
                    });
            },

            getLineUp: function (entry_id) {
                //console.log('entry_id',entry_id);
                return $http.get('http://fantasy/api/v1/lineup?entry_id=' + entry_id)
                    .then(function successCallback(response) {
                        //console.log(response.data);
                        return response.data;
                    });
            },
            
            getHolders: function (){
                return $http.get('http://fantasy/api/v1/holders')
                    .then(function successCallback(response) {
                        //console.log(response.data.holders);
                        return response.data.holders;
                    }); 
            },
            
            getPlayers: function (contest_id){
                return $http.get('http://fantasy/api/v1/contest/' + contest_id + '/players')
                    .then(function successCallback(response) {
                        //console.log(response.data.players);
                        return response.data.players;
                    });
            },

            getContestCompleted: function () {
                //console.log('entry_id',entry_id);
                return $http.get('http://fantasy/api/v1/lineups?user_id=3&state=completed')
                    .then(function successCallback(response) {
                        //console.log(response);
                        return response.data;
                    });
            },
            getContestUpcoming: function () {
                //console.log('entry_id',entry_id);
                return $http.get('http://fantasy/api/v1/lineups?user_id=3&state=upcoming')
                    .then(function successCallback(response) {
                        //console.log(response);
                        return response.data;
                    });
            },
            getContestLive: function () {
                //console.log('entry_id',entry_id);
                return $http.get('http://fantasy/api/v1/lineups?user_id=3&state=live')
                    .then(function successCallback(response) {
                        //console.log(response);
                        return response.data;
                    });
            },
            getPlayerStats: function (player_code){
                //console.log('entry_id',entry_id);
                return $http.get('http://fantasy/api/v1/playerGameLog?player_code=' + player_code)
                    .then(function successCallback(response) {
                        console.log(response.data['gameLog']);
                        return response.data['gameLog'];
                    });
            },

            DialogController: function ($scope, $mdDialog, contest, users) {
                $scope.contest = contest;
                $scope.users = users;
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
        };
    });