'use strict';

/**
 * Playlist controller.
 */
angular.module('music').controller('Playlist', function($rootScope, $scope, $state, $stateParams, Restangular, Playlist, NamedPlaylist) {
  // Load playlist
  Restangular.one('playlist', $stateParams.id).get().then(function(data) {
    $scope.playlist = data;

    // console.log(data);
    var tracks = [];
    if ($rootScope.userInfo.username !== "admin") {
    for (var i = 0; i < data.tracks.length; i++) {
        if (data.tracks[i].user_filter === $rootScope.userInfo.username || data.tracks[i].user_filter === "-1") {
          console.log("reached");
          tracks.push(data.tracks[i]);
        }
      }
      $scope.playlist.tracks = tracks;
    }
  });

  // Play a single track
  $scope.playTrack = function(track) {
    Playlist.removeAndPlay(track);
  };

  // Add a single track to the playlist
  $scope.addTrack = function(track) {
    Playlist.add(track, false);
  };

  // Add all tracks to the playlist in a random order
  $scope.shuffleAllTracks = function() {
    Playlist.addAll(_.shuffle(_.pluck($scope.playlist.tracks, 'id')), false);
  };

  // Play all tracks
  $scope.playAllTracks = function() {
    Playlist.removeAndPlayAll(_.pluck($scope.playlist.tracks, 'id'));
  };

  // Add all tracks to the playlist
  $scope.addAllTracks = function() {
    Playlist.addAll(_.pluck($scope.playlist.tracks, 'id'), false);
    
  };

  // Like/unlike a track
  $scope.toggleLikeTrack = function(track) {
    Playlist.likeById(track.id, !track.liked);
  };

  // Remove a track
  $scope.removeTrack = function(order) {
    NamedPlaylist.removeTrack($scope.playlist, order).then(function(data) {
      $scope.playlist = data;
    });
  };

  // Delete the playlist
  $scope.remove = function() {
    NamedPlaylist.remove($scope.playlist).then(function() {
      $state.go('main.default');
    });
  };

  // Update UI on track liked
  $scope.$on('track.liked', function(e, trackId, liked) {
    var track = _.findWhere($scope.playlist.tracks, { id: trackId });
    if (track) {
      track.liked = liked;
    }
  });

  // Configuration for track sorting
  $scope.trackSortableOptions = {
    forceHelperSize: true,
    forcePlaceholderSize: true,
    tolerance: 'pointer',
    handle: '.handle',
    containment: 'parent',
    helper: function(e, ui) {
      ui.children().each(function() {
        $(this).width($(this).width());
      });
      return ui;
    },
    stop: function (e, ui) {
      // Send new positions to server
      $scope.$apply(function () {
        NamedPlaylist.moveTrack($scope.playlist, ui.item.attr('data-order'), ui.item.index());
      });
    }
  };

  $scope.recommend = async function() {
    var tracks = $scope.playlist.tracks;

    if($scope.playlist.tracks.length == 0)
    {
      alert("No tracks in playlist");
      return;
    }

    //get atmost 5 tracks from the playlist
    if($scope.playlist.tracks.length > 5)
    {
      tracks = $scope.playlist.tracks.slice(0, 5);
    }

    //get the names of the tracks
    var trackNames = _.pluck(tracks, 'title');

    //remove duplicates
    trackNames = _.uniq(trackNames);
    
    var trackids = [];

    //get all track ids and wait for all promises to be resolved
    await Promise.all(trackNames.map(async function(trackName) {
      var data = await Restangular.one('user/spotifySearch').get({song: trackName});
      if(data.tracks.length > 0)trackids.push(data.tracks[0].id);
    }));
    
    //get recommendations from user/spotifyRecommendations
    var promise = null;

    //concatenate all track ids to a string
    var trackIds = "";
    _.each(trackids, function(trackid) {
      trackIds += trackid + ",";
    });
    
    promise = Restangular.one('user/spotifyRecommendations').get({seed_tracks: trackIds}).then(function(data) {
      //get name of the tracks
      var names = _.pluck(data.tracks, 'name');
      if(names.length > 10)
      {
        names = names.slice(0, 10);
      }
      $scope.recommendationTracks = names;
    });
  };


  $scope.LastFmRecommendation = async function() {
    var tracks = $scope.playlist.tracks;

    if($scope.playlist.tracks.length == 0)
    {
      alert("No tracks in playlist");
      return;
    }

    //get atmost 5 tracks from the playlist
    if($scope.playlist.tracks.length > 5)
    {
      tracks = $scope.playlist.tracks.slice(0, 5);
    }

    //get the names of the tracks
    var trackNames = _.pluck(tracks, 'title');

    
    var trackmbids = [];

    //get all track ids and wait for all promises to be resolved
    await Promise.all(trackNames.map(async function(trackName) {
      var data = await Restangular.one('user/lastfmSearch').get({song: trackName}); 
      if(data.tracks.length > 0)
      {
        trackmbids.push(data.tracks[0].mbid);
      }
    }));

    //remove duplicates
    trackmbids = _.uniq(trackmbids);
    
    //get recommendations from user/spotifyRecommendations
    var promise = null;

    // concatenate all track ids to a string
    var trackmbIds = "";
    _.each(trackmbids, function(trackmbid) {
      if(trackmbid != "")
      trackmbIds += trackmbid + ",";
    });

    if(trackmbIds == "")
    {
      alert("No Recommendations from lastfm, Please add more tracks to your playlist");
      return;
    }

    promise = Restangular.one('user/lastfmRecommendations').get({seed_mbids: trackmbIds}).then(function(data) {
      var names = _.pluck(data.tracks, 'name');
      //remove duplicates
      names = _.uniq(names);
      if(names.length > 10)
      {
        names = names.slice(0, 10);
      }
      $scope.recommendationTracks = names;
    });

  };

});
