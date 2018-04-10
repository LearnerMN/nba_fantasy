/**
 * % 20-Mar-16.
 */
angular.module('fantasy')
    .filter("asDate", function () {
        return function (input) {
            return (new Date(input)).getTime();
        }
    })
    .filter('asNo', function () {
        return function (number) {
            if (number == 0) {
                return 'No';
            }
            return number;
        }
    })
    .filter('asFree', function () {
        return function (number) {
            if (number == 0) {
                return 'Free';
            }
            return '$' + number;
        }
    })
    .filter('range', function() {
        return function(val, range) {
            range = parseInt(range);
            for (var i=0; i<range; i++)
                val.push(i);
            return val;
        };
    });
