'use strict';

/**
 * Named playlist service.
 */
angular.module('music').factory('NamedPlaylist', function($rootScope, $modal, Restangular, toaster) {
  $rootScope.playlists = [];
  var service = {};
  service = {
    update: function() {
      Restangular.one('playlist').get({
        limit: 1000
      }).then(function(data) {
        // $rootScope.playlists = data.items;
        var name = $rootScope.userInfo.username;
        // console.log(data.items);
        console.log(name);
        for(var i = 0; i < data.items.length; i++) {
          console.log("reached");
          if(data.items[i].user_filter !== null && (name === "admin" || (data.items[i].user_filter === name || data.items[i].user_filter === "-1"))) {
            $rootScope.playlists.push(data.items[i]);
          }
        }
      });
    },

    addToPlaylist: function(playlist, tracks) {
      Restangular.one('playlist/' + playlist.id + '/multiple').put({
        ids: _.pluck(tracks, 'id'),
        clear: false
      }).then(function() {
        toaster.pop('success', 'Track' + (tracks.length > 1 ? 's' : '') + ' added to ' + playlist.name,
          _.pluck(tracks, 'title').join('\n'));
      });
    },

    removeTrack: function(playlist, order) {
      return Restangular.one('playlist/' + playlist.id, order).remove();
    },

    remove: function(playlist) {
      return Restangular.one('playlist/' + playlist.id).remove().then(function() {
        $rootScope.playlists = _.reject($rootScope.playlists, function(p) {
          return p.id === playlist.id;
        });
      });
    },

    moveTrack: function(playlist, order, neworder) {
      return Restangular.one('playlist/' + playlist.id, order).post('move', {
        neworder: neworder
      });
    },

    createPlaylist: function(tracks) {
      $modal.open({
        templateUrl: 'partial/modal.createplaylist.html',
        controller: function($scope, $modalInstance) {
          'ngInject';
          $scope.ok = function (name, user_filter) {
            console.log(user_filter);
            if(user_filter === undefined) {
              user_filter = false;
            }
            if(user_filter == true){
              user_filter = $rootScope.userInfo.username;
            }
            else {
              user_filter = "-1";
            }
            $modalInstance.close({name, user_filter});
          };

          $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
          };
        }
      }).result.then(function(name) {
        console.log(name);
        // console.log(user_filter);
        Restangular.one('playlist').put({
          name: name.name,
          user_filter : name.user_filter
        }).then(function(data) {
          console.log(data);
          $rootScope.playlists.push(data.item);
          service.addToPlaylist(data.item, tracks);
          toaster.pop('success', 'Playlist created', name.name);
        });
      });
    }
  };

  return service;
});