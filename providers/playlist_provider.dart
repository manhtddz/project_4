import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/playlist.dart';
import 'package:pj_demo/services/playlist_api.dart';
import 'package:pj_demo/dto/playlist_response.dart';

class PlaylistProvider with ChangeNotifier {
  List<PlaylistResponse> _playlistList = [];
  bool _isLoading = false;
  String _errorMessage = '';

  List<PlaylistResponse> get playListList => _playlistList;
  bool get isLoading => _isLoading;
  String get errorMessage => _errorMessage;

  Future<void> fetchPlaylistOfUser(int userId) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _playlistList = await PlaylistApi().fetchPlaylistOfUser(userId);
    } catch (error) {
      _errorMessage = 'Failed to fetch favorite albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }
}
