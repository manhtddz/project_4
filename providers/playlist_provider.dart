import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/playlist.dart';
import 'package:pj_demo/services/playlist_api.dart';
import 'package:pj_demo/dto/playlist_response.dart';

class PlaylistProvider with ChangeNotifier {
  final PlaylistApi _playlistService = PlaylistApi();
  List<PlaylistResponse> _playlistList = [];
  // var _playlist;
  bool _isLoading = false;
  String _errorMessage = '';

  List<PlaylistResponse> get playListList => _playlistList;
  // PlaylistResponse get playlist => _playlist;
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

  Future<void> addNewPlaylist(int userId, String title, BuildContext context) async {
    _isLoading = true;
    notifyListeners();

    try {
      await _playlistService.addNewPlaylist(userId, title);

      await fetchPlaylistOfUser(userId, context);
    } catch (error) {
      _errorMessage = 'Failed to add playlist: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Future<void> deletePlaylist(int playlistId) async {
    _isLoading = true;
    notifyListeners(); // Thông báo UI đang trong trạng thái loading

    try {
      await _playlistService.deletePlaylist(playlistId); // Gọi API để xóa playlist

      // Sau khi xóa, ta cần cập nhật lại danh sách playlist
      _playlistList.removeWhere((playlist) => playlist.id == playlistId);
    } catch (error) {
      _errorMessage = 'Failed to delete playlist: $error'; // Xử lý lỗi khi xóa không thành công
    } finally {
      _isLoading = false;
      notifyListeners(); // Thông báo UI khi đã hoàn thành
    }
  }
}
