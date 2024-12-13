import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/playlist.dart';

class PlaylistProvider extends ChangeNotifier {
  List<Playlist> _playlistList = [
    Playlist(id: 1, title: 'Playlist A', userId: 1),
    Playlist(id: 2, title: 'Playlist B', userId: 2),
    Playlist(id: 3, title: 'Playlist C', userId: 3),
    Playlist(id: 4, title: 'Playlist D', userId: 1),
    Playlist(id: 5, title: 'Playlist E', userId: 1),
    Playlist(id: 6, title: 'Playlist F', userId: 1),
  ];

  List<Playlist> _playlistOfUser = [];
  List<Playlist> get playlistOfUser => _playlistOfUser;

  void getPlaylistByUserId(int uId) {
    _playlistOfUser = _playlistList.where((it) => it.userId == uId).toList();
    notifyListeners();
  }
}
