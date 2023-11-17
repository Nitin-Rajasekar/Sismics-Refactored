'use strict';

/**
 * Settings user edition page controller.
 */
angular.module('music').controller('Register', function($scope, $dialog, $state, $stateParams, Restangular) {
  /**
   * Saves the current user.
   */
  $scope.save = function(user) {
    console.log(user);
    var promise = null;

    promise = Restangular
    .one('user')
    .put(user);

    promise.then(function() {
      $state.transitionTo('login');
    });
  };
});
