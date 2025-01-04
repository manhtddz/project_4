import 'package:flutter/material.dart';
import 'package:audioplayers/audioplayers.dart';
import 'package:pj_demo/dto/song_response.dart';
import 'package:pj_demo/models/song.dart';

import '../services/song_api.dart';

class SongProvider extends ChangeNotifier {
  final SongApi _songService = SongApi();
  List<SongResponse> _favoriteSongs = []; // to store favourite songs of user logging in
  List<SongResponse> _songList = []; // to store other songs (of playlist or album)
  bool _isLoading = false;
  String _errorMessage = '';

  bool get isLoading => _isLoading;
  List<SongResponse> get songList => _songList;
  List<SongResponse> get favoriteSongs => _favoriteSongs;

  int? _currentSongIndex;
  final AudioPlayer _audioPlayer = AudioPlayer();
  Duration _currentDuration = Duration.zero;
  Duration _totalDuration = Duration.zero;
  bool _isPlaying = false;
  String get errorMessage => _errorMessage;

  SongProvider() {
    listenToDuration();
  }

  // Audio player controls
  get audioPlayer => _audioPlayer;

  bool get isPlaying => _isPlaying;
  Duration get currentDuration => _currentDuration;
  Duration get totalDuration => _totalDuration;
  int? get currentSongIndex => _currentSongIndex;

  // Set current song index
  set currentSongIndex(int? newIndex) {
    _currentSongIndex = newIndex;
    if (newIndex != null && _songList.isNotEmpty) {
      play();
    }
    notifyListeners();
  }

  // Play the song
  Future<void> play() async {
    if (_currentSongIndex == null || _songList.isEmpty) return;

    final String? path = _songList[_currentSongIndex!].audioPath;
    await _audioPlayer.stop();
    await _audioPlayer.play(AssetSource(path!));
    _isPlaying = true;
    notifyListeners();
  }

  // Pause current song
  Future<void> pause() async {
    await _audioPlayer.pause();
    _isPlaying = false;
    notifyListeners();
  }

  // Resume current song
  Future<void> resume() async {
    await _audioPlayer.resume();
    _isPlaying = true;
    notifyListeners();
  }

  // Pause or resume based on the current state
  Future<void> pauseOrResume() async {
    if (_isPlaying) {
      await pause();
    } else {
      await resume();
    }
    notifyListeners();
  }

  // Seek to a specific position in the current song
  Future<void> seek(Duration position) async {
    await _audioPlayer.seek(position);
  }

  // Play next song
  void playNextSong() {
    if (_currentSongIndex != null && _songList.isNotEmpty) {
      _currentSongIndex = (_currentSongIndex! + 1) % _songList.length;
      play();
    }
  }

  // Play previous song
  Future<void> playPreviousSong() async {
    if (_currentDuration.inSeconds > 2) {
      await seek(Duration.zero);
    } else {
      if (_currentSongIndex != null && _songList.isNotEmpty) {
        _currentSongIndex = (_currentSongIndex! - 1) % _songList.length;
        play();
      }
    }
  }

  // Listen to the audio player duration changes
  void listenToDuration() {
    _audioPlayer.onDurationChanged.listen((newDuration) {
      _totalDuration = newDuration;
      notifyListeners();
    });

    _audioPlayer.onPositionChanged.listen((newPosition) {
      _currentDuration = newPosition;
      notifyListeners();
    });

    _audioPlayer.onPlayerComplete.listen((event) {
      playNextSong();
    });
  }

  // Fetch songs of an album
  Future<void> fetchSongOfAlbum(int albumId, BuildContext context) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _songList = await _songService.fetchAllSongOfAlbum(albumId, context);
      // _songList = result.isNotEmpty ? result : [];
    } catch (error) {
      _errorMessage = 'Failed to fetch albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Fetch songs of an playlist
  // Future<void> fetchSongOfPlaylist(int playlistId, BuildContext context) async {
  //   _isLoading = true;
  //   _errorMessage = ''; // Reset error message
  //   // Do not call notifyListeners() here, it should be done after the build phase
  //
  //   try {
  //     _songList = await _songService.fetchAllSongOfPlaylist(playlistId, context);
  //     // Make sure to call notifyListeners after the frame is built
  //     WidgetsBinding.instance.addPostFrameCallback((_) {
  //       notifyListeners();
  //     });
  //   } catch (error) {
  //     _errorMessage = 'Failed to fetch songs: $error';
  //   } finally {
  //     _isLoading = false;
  //     // Call notifyListeners after the frame is built
  //     WidgetsBinding.instance.addPostFrameCallback((_) {
  //       notifyListeners();
  //     });
  //   }
  // }

  Future<List<SongResponse>> fetchSongOfPlaylist(
      int playlistId, BuildContext context) async {
    _isLoading = true;

    try {
      _songList = await _songService.fetchAllSongOfPlaylist(playlistId, context);
      return _songList; // Explicitly return the list of songs
    } catch (e) {
      print('Error fetching songs: $e');
      return []; // Return an empty list in case of an error
    } finally {
      _isLoading = false;
    }
  }

  Future<void> fetchFavSongOfUser(int userId, BuildContext context) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _favoriteSongs = await _songService.fetchFavSongOfUser(userId, context);
      // _favoriteSongs = result.isNotEmpty ? result : [];
    } catch (error) {
      _errorMessage = 'Failed to fetch albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  bool isFavorite(int userId, int songId, BuildContext context) {
    fetchFavSongOfUser(userId, context);
    // Check if a song with the given ID is in the list of favorite songs
    return _favoriteSongs.any((song) => song.id == songId);
  }

  void addFavorite(int userId, SongResponse song, BuildContext context) {
    if (!isFavorite(userId, song.id, context)) {
      _favoriteSongs.add(song);
      notifyListeners(); // This is important!
    }
  }

  void removeFavorite(int userId, SongResponse song, BuildContext context) {
    _favoriteSongs.removeWhere((item) => item.id == song.id);
    notifyListeners(); // This is important!
  }
}
