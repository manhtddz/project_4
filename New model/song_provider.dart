import 'package:flutter/material.dart';
import 'package:audioplayers/audioplayers.dart';
import 'package:pj_demo/models/song.dart';
import 'package:pj_demo/models/song_api.dart';

class SongProvider extends ChangeNotifier {
  List<Song> _songList = [];
  bool _isLoading = false;

  bool get isLoading => _isLoading;
  List<Song> get songList => _songList;

  int? _currentSongIndex;
  /* Audio Player*/
//audio player
  final AudioPlayer _audioPlayer = AudioPlayer();
  get audioPlayer => this._audioPlayer;

//duration
  Duration _currentDuration = Duration.zero;
  Duration _totalDuration = Duration.zero;
//constructor
  SongProvider() {
    listenToDuration();
  }
//initiallity not playing
  bool _isPlaying = false;

//play the song
  void play() async {
    final String path = _songList[currentSongIndex!].audioPath;
    await _audioPlayer.stop();
    await _audioPlayer.play(AssetSource(path));
    _isPlaying = true;
    notifyListeners();
  }
// pause current song

  void pause() async {
    await _audioPlayer.pause();
    _isPlaying = false;
    notifyListeners();
  }

//resume playing
  void resume() async {
    await _audioPlayer.resume();
    _isPlaying = true;
    notifyListeners();
  }

//pause or resume
  void pauseOrResume() async {
    if (_isPlaying) {
      pause();
    } else {
      resume();
    }
    notifyListeners();
  }

//seek to a specific position in the current song
  void seek(Duration position) async {
    await _audioPlayer.seek(position);
  }

//play next song
  void playNextSong() {
    if (_currentSongIndex != null) {
      if (_currentSongIndex! < _songList.length - 1) {
        currentSongIndex = _currentSongIndex! + 1;
      } else {
        currentSongIndex = 0;
      }
    }
  }

//play previous song
  void playPreviousSong() async {
    if (_currentDuration.inSeconds > 2) {
      seek(Duration.zero);
    } else {
      if (_currentSongIndex! > 0) {
        currentSongIndex = _currentSongIndex! - 1;
      } else {
        currentSongIndex = _songList.length - 1;
      }
    }
  }

// list to duration
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

//display audio player

  int? get currentSongIndex => _currentSongIndex;

  bool get isPlaying => _isPlaying;
  Duration get currentDuration => _currentDuration;
  Duration get totalDuration => _totalDuration;

  set currentSongIndex(int? newIndex) {
    _currentSongIndex = newIndex;
    if (newIndex != null) {
      play();
    }
    notifyListeners();
  }

  Future<List<Song>> fetchSongOfAlbum(int albumId) async {
    _isLoading = true;

    await Future.delayed(Duration(milliseconds: 100));
    notifyListeners();

    return await SongApi().filterSongOfAlbum(albumId);

    // if (result.isNotEmpty) {
    //   _songList = result;
    // } else {
    //   _songList = [];
    // }
    // _isLoading = false;
    //
    // notifyListeners();
  }

  Future<void> fetchFavouriteSongOfUser(int userId) async {
    _isLoading = true;

    await Future.delayed(Duration(milliseconds: 100));
    notifyListeners();

    final result = await SongApi().filterFavouriteSongOfUser(userId);

    if (result.isNotEmpty) {
      _songList = result;
    } else {
      _songList = [];
    }
    _isLoading = false;

    notifyListeners();
  }
}
