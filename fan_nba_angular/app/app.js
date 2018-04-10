'use strict';

// Declare app level module which depends on views, and components
angular.module('fantasy', [
    'ui.router',
    'fantasy.lobby',
    'fantasy.create',
    'fantasy.contest.history',
    'fantasy.contest.upcoming',
    'fantasy.lineup',
    'fantasy.lineup.history',
    'fantasy.lineup.upcoming',
    'fantasy.lineup.live',
    'angular-sly',
    'ngMaterial',
    'angular-loading-bar'
]).
    config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        //$urlRouterProvider.otherwise('/contest/create');
        $stateProvider
            .state('index', {
                url: '/',
                views: {
                    'homeContent': {
                        templateUrl: 'lobby/index.html',
                        controller: 'LobbyCtrl as lobby',
                        resolve: {
                            factory: function (lobbyFactory) {
                                return lobbyFactory.get();
                            }
                        }
                    }
                }
            })
            .state('create', {
                url: '/contest/create',
                views: {
                    'homeContent': {
                        templateUrl: 'contest/index.html',
                        controller: 'CreateCtrl as create',
                        resolve: {
                            holders: function (contestDetailFactory) {
                                return contestDetailFactory.getHolders();
                            }
                        }
                    }
                }
            })
            .state('contestHistory', {
                url: '/contest/history',
                views: {
                    'homeContent': {
                        templateUrl: 'contest/history.html',
                        controller: 'ContestHistoryCtrl as contest_history',
                        resolve: {
                            contests: function (contestDetailFactory) {
                                return contestDetailFactory.getContestCompleted();
                            }
                        }
                    }
                }
            })
            .state('lineupHistory', {
                url: '/lineup/:contest_id/:entry_id',
                views: {
                    'homeContent': {
                        templateUrl: 'lineup/history.html',
                        controller: 'LineupHistoryCtrl as line_history',
                        resolve: {
                            contest: function (contestDetailFactory, $stateParams) {
                                return contestDetailFactory.getContest($stateParams['contest_id']);
                            },
                            users: function (contestDetailFactory, $stateParams) {
                                return contestDetailFactory.getUsers($stateParams['contest_id']);
                            },
                            lineup: function (contestDetailFactory, $stateParams) {
                                return contestDetailFactory.getLineUp($stateParams['entry_id']);
                            }
                        }
                    }
                }
            })
            .state('lineupLive', {
                url: '/:contest_id/:entry_id',
                views: {
                    'homeContent': {
                        templateUrl: 'lineup/live.html',
                        controller: 'LineupLiveCtrl as line_live',
                        resolve: {
                            contest: function (contestDetailFactory, $stateParams) {
                                return contestDetailFactory.getContestLive($stateParams['contest_id']);
                            },
                            users: function (contestDetailFactory, $stateParams) {
                                return contestDetailFactory.getUsers($stateParams['contest_id']);
                            },
                            lineup: function (contestDetailFactory, $stateParams) {
                                return contestDetailFactory.getLineUp($stateParams['entry_id']);
                            }
                        }
                    }
                }
            })
            .state('lineup', {
                url: '/lineup/:contest_id',
                views: {
                    'homeContent': {
                        templateUrl: 'lineup/index.html',
                        controller: 'LineupCtrl as lineup',
                        resolve: {
                            contest: function(contestDetailFactory, $stateParams){
                                return contestDetailFactory.getContest($stateParams.contest_id);
                            },
                            players: function(contestDetailFactory, $stateParams){
                                return contestDetailFactory.getPlayers($stateParams.contest_id);
                            }
                        }
                    }
                }
            }).state('upcomingContest', {
                url: '/contest/upcoming',
                views: {
                    'homeContent': {
                        templateUrl: 'contest/upcoming.html',
                        controller: 'ContestUpcomingCtrl as upcomingContest',
                        resolve: {
                            contests: function (contestDetailFactory) {
                                return contestDetailFactory.getContestUpcoming();
                            }
                        }
                    }
                }
            })
            .state('upcomingLineup', {
                url: '/lineup/upcoming/:contest_id/:entry_id',
                views: {
                    'homeContent': {
                        templateUrl: 'lineup/upcoming.html',
                        controller: 'UpcomingLineup as upcomingLineup',
                        resolve: {
                            contest: function (contestDetailFactory, $stateParams) {
                                return contestDetailFactory.getContest($stateParams['contest_id']);
                            },
                            users: function (contestDetailFactory, $stateParams) {
                                return contestDetailFactory.getUsers($stateParams['contest_id']);
                            },
                            lineup: function (contestDetailFactory, $stateParams) {
                                return contestDetailFactory.getLineUp($stateParams['entry_id']);
                            }
                        }
                    }
                }
            });
    }]);

