/**
 * % 20-Mar-16.
 */
'use strict';

angular.module('fantasy.lineup', ['ui.router'])
    .controller('LineupCtrl', ['$http','$scope', '$rootScope', '$state', '$stateParams', '$filter', '$timeout', '$mdDialog','$mdMedia', 'contest', 'players',
        function ($http, $scope, $rootScope, $state, $stateParams, $filter, $timeout, $mdDialog, $mdMedia, contest, players) {
            var vm = this;
            var nil = "undefined";
            var contest_id = $stateParams.contest_id;
            console.log('>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>');
            console.log('contest_id', contest_id);

            $rootScope.matches = [];
            $rootScope.filteredPlayers = [];

            vm.players = [];
            vm.search = {primaryPosition: ''};
            vm.contestData = {};

            vm.positions = [
                {pos: 'all', name: 'All', description: 'all'},
                {pos: 'PG', name: 'PG', description: 'Select Point Guard'},
                {pos: 'SG', name: 'SG', description: 'Select Shooting Guard'},
                {pos: 'G', name: 'G', description: 'Select Guard'},
                {pos: 'SF', name: 'SF', description: 'Select Small Forward'},
                {pos: 'PF', name: 'PF', description: 'Select Power Forward'},
                {pos: 'F', name: 'F', description: 'Select Forward'},
                {pos: 'C', name: 'C', description: 'Select Center'},
                {pos: 'UTILS', name: 'Utils', description: 'Select Util'}
            ];

            vm.targetPosition = vm.positions[0].pos;

            vm.lineup = [
                {
                    index: -1,
                    code: 0,
                    image: '',
                    position: 'PG',
                    name: vm.positions[1].description,
                    fppg: '',
                    salary: ''
                },
                {
                    index: -1,
                    code: 0,
                    image: '',
                    position: 'SG',
                    name: vm.positions[2].description,
                    fppg: '',
                    salary: ''
                },
                {index: -1, code: 0, image: '', position: 'G', name: vm.positions[3].description, fppg: '', salary: ''},
                {
                    index: -1,
                    code: 0,
                    image: '',
                    position: 'SF',
                    name: vm.positions[4].description,
                    fppg: '',
                    salary: ''
                },
                {
                    index: -1,
                    code: 0,
                    image: '',
                    position: 'PF',
                    name: vm.positions[5].description,
                    fppg: '',
                    salary: ''
                },
                {index: -1, code: 0, image: '', position: 'F', name: vm.positions[6].description, fppg: '', salary: ''},
                {index: -1, code: 0, image: '', position: 'C', name: vm.positions[7].description, fppg: '', salary: ''},
                {
                    index: -1,
                    code: 0,
                    image: '',
                    position: 'UTILS',
                    name: vm.positions[8].description,
                    fppg: '',
                    salary: ''
                }
            ];

            var posIndex = {pg: 0, sg: 1, g: 2, sf: 3, pf: 4, f: 5, c: 6, utils: 7};
            vm.positionPossibilities = {
                pg: [posIndex.pg, posIndex.g, posIndex.utils],
                sg: [posIndex.sg, posIndex.g, posIndex.utils],
                g: [posIndex.g, posIndex.utils],
                sf: [posIndex.sf, posIndex.f, posIndex.utils],
                pf: [posIndex.pf, posIndex.f, posIndex.utils],
                f: [posIndex.f, posIndex.utils],
                c: [posIndex.c, posIndex.utils],
                utils: [posIndex.utils]
            };

            vm.init = function () {
                // set Constest data
                $rootScope.matches = contest.matches;
                vm.setContestData(contest);

                // set players data
                vm.players = vm.setPlayerList(players);
                vm.applySortingAndFiltering();
            };

            vm.setPlayerList = function (players) {
                var player = {};
                var playersList = [];
                for (var ii = 0; ii < players.length; ii++) {
                    player = players[ii];
                    player.index = ii;
                    player.isAdded = vm.isPlayerAdded(players[ii].code);
                    playersList.push(player);
                }
                return playersList;
            };

            vm.isPlayerAdded = function (code) {
                for (var ii = 0; ii < vm.lineup.length; ii++) {
                    if (vm.lineup[ii].code == code) {
                        return true;
                    }
                }
                return false;
            };

            vm.setContestData = function (data) {
                if (data != undefined && data != null) {
                    vm.contestData.code = data.code;
                    vm.contestData.id = data.id;
                    vm.contestData.title = data.title;
                    vm.contestData.entryLimit = data.entryLimit;
                    vm.contestData.entryCount = data.entryCount;
                    vm.contestData.entryFee = data.entryFee;
                    vm.contestData.startTime = data.startTime;
                    vm.contestData.totalPrize = data.totalPrize;
                    vm.contestData.salaryCap = data.salaryCap;
                    vm.contestData.guaranteed = data.guaranteed;
                    vm.contestData.state = data.state;
                    vm.contestData.type = data.type;
                    vm.contestData.sportCode = data.sportCode;
                    vm.contestData.scope = data.scope;
                    vm.contestData.match = data.match;
                }
            };

            vm.applySortingAndFiltering = function () {
                $rootScope.filteredPlayers = $filter('filter')($filter('orderBy')(vm.players, 'salary', 1), vm.search);
                $timeout(function () {
                    vm.reloadSly('#matchPlayers');
                }, 0, false);
            };

            vm.findByTargetPosition = function (filterPos) {
                vm.targetPosition = filterPos;
                vm.search.primaryPosition = (filterPos === vm.positions[0].pos || filterPos === vm.positions[8].pos) ? '' : filterPos;
                $rootScope.filteredPlayers = $filter('filter')($filter('orderBy')(vm.players, 'salary', 1), vm.search);
                $timeout(function () {
                    vm.reloadSly('#matchPlayers');
                }, 0, false);
            };

            vm.reloadSly = function (id) {
                angular.element(document.querySelector(id)).sly("reload").sly("toStart");
            };

            vm.addToLineup = function (index, player) {
                var isDuplicate = vm.isDuplicate(vm.lineup, player.code);
                if ($rootScope.filteredPlayers[index] && !vm.isLineupFull(8) && !isDuplicate) {
                    var pPos;
                    if (vm.targetPosition === undefined) {
                        pPos = player.primaryPosition.toLowerCase();
                    } else {
                        pPos = vm.targetPosition.toLowerCase();
                    }

                    if (pPos === vm.positions[0].pos) {
                        pPos = player.primaryPosition.toLowerCase();
                    }

                    if (pPos && !vm.checkAndAdd(vm.positionPossibilities[pPos], player.index)) {
                        alert("this position is selected already");
                    }

                } else if (isDuplicate) {
                    alert('duplicate');
                } else {
                    alert('full');
                }
            };

            vm.isAdd = function (event) {
                console.log(event);
            };

            vm.removePlayerFromLineup = function (player) {
                for (var ii = 0; ii < vm.lineup.length; ii++) {
                    if (vm.lineup[ii].code === player.code) {
                        if (vm.players[player.index]) {
                            vm.players[player.index].isAdded = false;
                        }
                        vm.lineup[ii].index = -1;
                        vm.lineup[ii].code = 0;
                        vm.lineup[ii].image = '';
                        vm.lineup[ii].name = vm.positions[ii + 1].description;
                        vm.lineup[ii].fppg = '';
                        vm.lineup[ii].salary = '';
                        break;
                    }
                }
            };

            vm.getPlayersByPos = function (playerArray, posFilter) {
                var filteredPlayers = [];
                for (var ii = 0; ii < playerArray.length; ii++) {
                    if (playerArray[ii].primaryPosition === posFilter) {
                        filteredPlayers.push(playerArray[ii]);
                    }
                }
                return filteredPlayers;
            };

            vm.checkAndAdd = function (pPos, index) {
                var player = vm.players[index];
                var newPlayer = {};
                for (var ii = 0; ii < pPos.length; ii++) {
                    newPlayer = vm.lineup[pPos[ii]];
                    if (newPlayer && newPlayer.index == -1) {
                        newPlayer.index = player.index;
                        newPlayer.code = player.code;
                        newPlayer.image = player['imageUrl'];
                        newPlayer.name = player['firstName'] + ' ' + player['lastName'];
                        newPlayer.fppg = player['fantasyPointsPerGame'];
                        newPlayer.salary = player.salary;
                        vm.players[newPlayer.index].isAdded = true;
                        return true;
                    }
                }
                return false;
            };

            vm.isLineupFull = function (limit) {
                return vm.getLinupSize() >= limit;
            };

            vm.getLinupSize = function () {
                var count = 0;
                for (var ii = 0; ii < vm.lineup.length; ii++) {
                    if (vm.lineup[ii].index > -1)
                        count++;
                }
                return count;
            };

            vm.getTotalSalary = function () {
                var totalSalary = 0;
                for (var ii = 0; ii < vm.lineup.length; ii++) {
                    if (vm.lineup[ii].index > -1) {
                        totalSalary += vm.lineup[ii].salary;
                    }
                }
                return vm.contestData.salaryCap - totalSalary;
            };

            vm.isDuplicate = function (lineup, playerCode) {
                for (var ii = 0; ii < lineup.length; ii++) {
                    if (lineup[ii].code === playerCode)
                        return true;
                }
                return false;
            };

            vm.calcLeftSalary = function () {
                var calc = vm.getTotalSalary() / (vm.lineup.length - vm.getLinupSize());
                if (calc < 0 || calc == Infinity)
                    calc = 0;
                return calc.toFixed();
            };

            vm.avgFppg = function () {
                var avgFppg = 0;
                if (vm.getLinupSize() == 0) {
                    return avgFppg.toFixed(1);
                }
                for (var ii = 0; ii < vm.lineup.length; ii++) {
                    if (vm.lineup[ii].index > -1) {
                        avgFppg += vm.lineup[ii].fppg;
                    }
                }
                return (avgFppg / vm.getLinupSize()).toFixed(1);
            };

            vm.clearLineup = function () {
                for (var ii = 0; ii < vm.lineup.length; ii++) {
                    if (vm.players[vm.lineup[ii].index]) {
                        vm.players[vm.lineup[ii].index].isAdded = false;
                    }
                    vm.lineup[ii].index = -1;
                    vm.lineup[ii].code = 0;
                    vm.lineup[ii].image = '';
                    vm.lineup[ii].name = vm.positions[ii + 1].description;
                    vm.lineup[ii].fppg = '';
                    vm.lineup[ii].salary = '';
                }
            };

            vm.createContestEntry = function () {
                if (vm.contestData.id != 0 && vm.isLineupFull(8)) {
                    $http.post('http://fantasy/api/v1/lineup/enter', {
                        contest_id: vm.contestData.id,
                        pos_pg: vm.lineup[0].code,
                        pos_sg: vm.lineup[1].code,
                        pos_g: vm.lineup[2].code,
                        pos_sf: vm.lineup[3].code,
                        pos_pf: vm.lineup[4].code,
                        pos_f: vm.lineup[5].code,
                        pos_c: vm.lineup[6].code,
                        pos_util: vm.lineup[7].code
                    }).success(function (response) {
                        console.log(response);
                        $state.go('upcomingLineup', {contest_id: vm.contestData.id, entry_id: response.contestLineup.entry_id});
                    }).error(function (error) {
                        console.log(error);
                    });
                } else {
                    if (vm.contestData.id == 0) {
                        alert('contest id is' + vm.contestData.id)
                    } else if (!vm.isLineupFull(8)) {
                        alert('lineup is not full')
                    }
                }
            };

            $rootScope.showConfirm = function(ev) {
                // Appending dialog to document.body to cover sidenav in docs app
                var confirm = $mdDialog.confirm()
                    .title('Confirm Your Entry')
                    .textContent(vm.contestData.title + ", Entry Fee: " + $filter('asFree')(vm.contestData.entryFee))
                    .targetEvent(ev)
                    .ok('Submit')
                    .cancel('Cancel');
                $mdDialog.show(confirm).then(function() {
                    vm.createContestEntry();
                }, function() {
                    $scope.status = 'Cancelled';
                });
            };

            vm.showContestData = function(ev) {
                var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && $scope.customFullscreen;
                $mdDialog.show({
                    controller: DialogController,
                    templateUrl: 'partials/contest_data.html',
                    parent: angular.element(document.body),
                    targetEvent: ev,
                    clickOutsideToClose:true,
                    fullscreen: useFullScreen,
                    resolve: {
                        contestData: function () {
                            return vm.contestData;
                        },
                        matches:function () {
                            return $rootScope.matches;
                        }
                    }
                })
                    .then(function(answer) {
                        $scope.status = 'You said the information was "' + answer + '".';
                    }, function() {
                        $scope.status = 'You cancelled the dialog.';
                    });
                $scope.$watch(function() {
                    return $mdMedia('xs') || $mdMedia('sm');
                }, function(wantsFullScreen) {
                    $scope.customFullscreen = (wantsFullScreen === true);
                });
            };

            function DialogController($scope, $mdDialog, contestData, matches) {
                $scope.users = [];
                $scope.contestData = contestData;
                $scope.matches = matches;

                var getContestUsers = function(contest_id){
                    var users = [];
                    $http.get('http://fantasy/api/v1/lineup/'+contest_id+'/users/0/5')
                        .success(function (response) {
                            $scope.users = response['result'];
                        }).error(function () {
                            console.log("error");
                        });
                };

                $scope.hide = function() {
                    $mdDialog.hide();
                };
                $scope.cancel = function() {
                    $mdDialog.cancel();
                };
                $scope.answer = function(answer) {
                    $mdDialog.hide(answer);
                };
                getContestUsers(contest_id);
            }

            vm.init();
        }]);
    
