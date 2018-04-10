/**
 * % 27-Mar-16.
 */
(function () {
    angular.module('fantasy')
        .directive('countdown', [
            'Util',
            '$interval',
            function (Util, $interval) {
                return {
                    restrict: 'A',
                    scope: {date: '@'},
                    link: function (scope, element) {
                        var future;
                        future = new Date(scope.date);
                        $interval(function () {
                            var diff;
                            diff = Math.floor((future.getTime() - new Date().getTime()) / 1000);
                            return element.text(Util.dhms(diff));
                        }, 1000);
                    }
                };
            }
        ])
        .factory('Util', [function () {
            return {
                dhms: function (t) {
                    var days, hours, minutes, seconds;
                    days = Math.floor(t / 86400);
                    t -= days * 86400;
                    days = days < 10 ? '0' + days : days;

                    hours = Math.floor(t / 3600) % 24;
                    t -= hours * 3600;
                    hours = hours < 10 ? '0' + hours : hours;

                    minutes = Math.floor(t / 60) % 60;
                    t -= minutes * 60;
                    minutes = minutes < 10 ? '0' + minutes : minutes;

                    seconds = t % 60;
                    seconds = seconds < 10 ? '0' + seconds : seconds;
                    return [
                        //days + 'd',
                        hours,
                        minutes,
                        seconds
                    ].join(':');
                }
            };
        }])
        .directive('backgroundImage', function () {
            return function (scope, element, attrs) {
                //restrict: 'A',
                attrs.$observe('backgroundImage', function (value) {
                    if (value) {
                        element.css({'background-image': 'url(' + value + ')'});
                        element.css({'background-size': '150%'});
                    } else {
                        element.css({'background-image': 'url(assets/img/hidden_player.png)'});
                        element.css({'background-size': '100%'});
                    }
                });
            };
        })
        .directive('positionForLineup', function () {
            return {
                scope: {player: '='},
                replace: true,
                restrict: 'AE',
                templateUrl: 'partials/_position_for_lineup.html'
            }
        })
        .directive('positionForOpponentLineup', function () {
            return {
                scope: {player: '='},
                replace: true,
                restrict: 'AE',
                templateUrl: 'partials/_rpfl.html'
            }
        });
}.call(this));