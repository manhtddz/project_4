import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/playlist.dart';
import 'package:pj_demo/services/playlist_api.dart';
import 'package:pj_demo/dto/playlist_response.dart';

class PlaylistProvider with ChangeNotifier {
  final PlaylistApi _playlistService = PlaylistApi();
  List<PlaylistResponse> _playlistList = [];
  bool _isLoading = false;
  String _errorMessage = '';

  List<PlaylistResponse> get playListList => _playlistList;
  bool get isLoading => _isLoading;
  String get errorMessage => _errorMessage;

  Future<void> fetchPlaylistOfUser(int userId, BuildContext context) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _playlistList = await _playlistService.fetchPlaylistOfUser(userId, context);
    } catch (error) {
      _errorMessage = 'Failed to fetch playlists: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }
}
