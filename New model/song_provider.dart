import 'package:flutter/material.dart';
import 'package:audioplayers/audioplayers.dart';
import 'package:pj_demo/models/song.dart';

class SongProvider extends ChangeNotifier {
  final List<Song> _album = [
    Song(
        id: 1,
        title: 'Limbo',
        artistId: 1,
        albumTitle: 'ABC',
        artistName: "Keshi",
        listenAmount: 12,
        albumImagePath: "assets/images/2.webp",
        audioPath: "audio/Limbo.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumId: 1),
    Song(
        id: 2,
        title: 'Limbo',
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        artistName: "Keshi",
        albumImagePath: "assets/images/2.webp",
        audioPath: "audio/Limbo.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 3,
        title: 'Limbo',
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        artistName: "Keshi",
        albumImagePath: "assets/images/2.webp",
        audioPath: "audio/Limbo.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 4,
        title: 'Limbo',
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        artistName: "Keshi",
        albumImagePath: "assets/images/2.webp",
        audioPath: "audio/Limbo.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 5,
        title: 'Limbo',
        artistName: "Keshi",
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        albumImagePath: "assets/images/2.webp",
        audioPath: "audio/Limbo.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
  ];

  final List<Song> _playlist = [
    Song(
        id: 1,
        title: 'Limbo',
        artistName: "Keshi",
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        albumImagePath: "assets/images/2.webp",
        audioPath: "audio/Limbo.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 2,
        title: 'Limbo',
        artistName: "Keshi",
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        albumImagePath: "assets/images/2.webp",
        audioPath: "audio/Limbo.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 3,
        title: 'Limbo',
        artistName: "Keshi",
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        albumImagePath: "assets/images/2.webp",
        audioPath: "audio/Limbo.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 4,
        title: 'Limbo',
        artistName: "Keshi",
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        albumImagePath: "assets/images/2.webp",
        audioPath: "audio/Limbo.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 5,
        title: 'Limbo',
        artistName: "Keshi",
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        albumImagePath: "assets/images/2.webp",
        audioPath: "audio/Limbo.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 6,
        title: 'Señorita',
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        artistName: "Shawn Mendes",
        albumImagePath: "assets/images/3.jpg",
        audioPath: "audio/Señorita.mp3",
        lyricsFilePath: "lyrics/Senorita.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 7,
        title: 'As You Fade Away',
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        artistName: "NEFFEX",
        albumImagePath: "assets/images/3.jpg",
        audioPath: "audio/music1.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 8,
        title: 'Exit Sign',
        artistName: "HIEUTHUHAI",
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        albumImagePath: "assets/images/4.jpg",
        audioPath: "audio/Exit Sign.mp3",
        lyricsFilePath: "lyrics/ExitSign.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 9,
        title: 'Không Thể Say',
        artistName: "HIEUTHUHAI",
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        albumImagePath: "assets/images/2.jpg",
        audioPath: "audio/Không Thể Say.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
    Song(
        id: 10,
        title: 'NOLOVENOLIFE',
        artistName: "HIEUTHUHAI",
        artistId: 1,
        listenAmount: 12,
        albumId: 1,
        albumImagePath: "assets/images/2.jpg",
        audioPath: "audio/NOLOVENOLIFE.mp3",
        lyricsFilePath: "lyrics/Limbo.lrc",
        isActive: true,
        albumTitle: 'No name'),
  ];
  int? _currentSongIndex;
  /* Audio Player*/
//audio player
  final AudioPlayer _audioPlayer = AudioPlayer();
  get audioPlayer => this._audioPlayer;

//duration
  Duration _currentDuration = Duration.zero;
  Duration _totalDuration = Duration.zero;
//constructor
  PlaylistProvider() {
    listenToDuration();
  }
//initiallity not playing

  bool _isPlaying = false;

//play the song
  void play() async {
    final String path = _playlist[currentSongIndex!].audioPath;
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
      if (_currentSongIndex! < _playlist.length - 1) {
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
        currentSongIndex = _playlist.length - 1;
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

  List<Song> get playlist => _playlist;
  // List<Song> get album => _album;

  Future<List<Song>?> fetchAlbum() async {
    final album = await _album;
    notifyListeners();
  }

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
}
