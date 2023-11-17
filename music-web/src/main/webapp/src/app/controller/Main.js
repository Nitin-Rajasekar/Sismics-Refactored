'use strict';

/**
 * Main controller.
 */
angular.module('music').controller('Main', function($rootScope, $state, $scope, Playlist, Album, Restangular) {
  // $scope.getSome = function(formData){
  //   console.log(formData.name);
  // }

  $scope.partyMode = Playlist.isPartyMode();

  // Keep party mode in sync
  $rootScope.$on('playlist.party', function(e, partyMode) {
    $scope.partyMode = partyMode;
  });

  // Start party mode
  $scope.startPartyMode = function() {
    Playlist.party(true, true);
    $state.transitionTo('main.playing');
  };

  // Stop party mode
  $scope.stopPartyMode = function() {
    Playlist.setPartyMode(false);
  };

  // Clear the albums cache if the previous state is not main.album
  $scope.$on('$stateChangeStart', function (e, to, toParams, from) {
    if (to.name == 'main.music.albums' && from.name != 'main.album') {
      Album.clearCache();
    }
  });

  // Search in Spotify
  $scope.search = function(query) {
    if(!query) {
      alert("Please enter a song name");
      return;
    }
    var promise = null;
    // console.log(query.song);
    // $scope.searcharray = query.song;
    promise = Restangular.one('user/spotifySearch').get({song: query.song}).then(function(data) {
      // console.log(data);
      var tracks = data.tracks;
      var names = [];
      for (var i = 0; i < tracks.length; i++) {
        names.push(tracks[i].name);
      }
      //remove duplicates
      names = names.filter(function(elem, pos) {
        return names.indexOf(elem) == pos;
      });
      if(names.length > 10)
      {
        names = names.slice(0, 10);
      }
      $scope.searcharray = names;
    });
  }  


  //Search in last.fm 
  $scope.searchLastFm = function(query) {
    if(!query) {
      alert("Please enter a song name");
      return;
    }
    var promise = null;
    promise = Restangular.one('user/lastfmSearch').get({song: query.song}).then(function(data) {
      // console.log(data);
      var tracks = data.tracks;
      var names = [];
      for (var i = 0; i < tracks.length; i++) {
        names.push(tracks[i].name);
      }
      //remove duplicates
      names = names.filter(function(elem, pos) {
        return names.indexOf(elem) == pos;
      });
      if(names.length > 10)
      {
        names = names.slice(0, 10);
      }
      $scope.searcharray = names;
    });
  }
});