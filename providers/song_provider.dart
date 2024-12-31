import 'package:flutter/material.dart';
import 'package:audioplayers/audioplayers.dart';
import 'package:pj_demo/models/song.dart';

import '../services/song_api.dart';

class SongProvider extends ChangeNotifier {
  List<Song> _songList = [];
  bool _isLoading = false;

  bool get isLoading => _isLoading;
  List<Song> get songList => _songList;

  int? _currentSongIndex;
  final AudioPlayer _audioPlayer = AudioPlayer();
  Duration _currentDuration = Duration.zero;
  Duration _totalDuration = Duration.zero;
  bool _isPlaying = false;

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

    final String path = _songList[_currentSongIndex!].audioPath;
    await _audioPlayer.stop();
    await _audioPlayer.play(AssetSource(path));
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
  Future<void> fetchSongOfAlbum(int albumId) async {
    _isLoading = true;
    notifyListeners();

    try {
      final result = await SongApi().filterSongOfAlbum(albumId);
      _songList = result.isNotEmpty ? result : [];
    } catch (error) {
      // Handle error (for example, you can set an error state)
      _songList = [];
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Fetch songs of an playlist
  Future<void> fetchSongOfPlaylist(int playlistId) async {
    _isLoading = true;
    notifyListeners();

    try {
      final result = await SongApi().filterSongOfAlbum(playlistId);
      _songList = result.isNotEmpty ? result : [];
    } catch (error) {
      // Handle error (for example, you can set an error state)
      _songList = [];
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }
}
