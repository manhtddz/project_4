import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/playlist.dart';
import 'package:pj_demo/models/playlist_api.dart';

class PlaylistProvider with ChangeNotifier {
  List<Playlist> _playlistList = [];
  bool _isLoading = false;

  List<Playlist> get playListList => _playlistList;
  bool get isLoading => _isLoading;

  Future<void> fetchUserPlaylist(int userId) async {
    _isLoading = true;
    notifyListeners();
    // Simulating an API request
    final response = await PlaylistApi().findFavouriteAlbumsOfUser(userId);
    // Handle null or empty response
    if (response.isEmpty) {
      _playlistList = [];
    } else {
      // Safely update playlist list
      _playlistList = response;
    }
    _isLoading = false;
    notifyListeners();
  }
}
