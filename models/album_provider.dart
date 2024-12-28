import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/album_api.dart';
import 'album.dart';

class AlbumProvider with ChangeNotifier {
  // final AlbumApi apiService;
  List<Album> _albumList = [];
  bool _isLoading = false;
  String _errorMessage = '';

  bool get isLoading => _isLoading;
  List<Album> get albumList => _albumList;
  String get errorMessage => _errorMessage;

  // AlbumProvider(this.apiService);

  // Search albums by keyword
  Future<void> searchAlbumByKeyword(String keyword) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _albumList = await AlbumApi().fetchItemByKeyWord(keyword);
    } catch (error) {
      _errorMessage = 'Failed to fetch albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Fetch favorite albums of a user
  Future<void> getFavAlbumsOfUser(int userId) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      final results = await AlbumApi().fetchFavAlbumOfUser(userId);

      if (results.isNotEmpty) {
        _albumList = results;
      } else {
        _albumList = [];
      }
    } catch (error) {
      _errorMessage = 'Failed to fetch favorite albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Fetch all albums
  Future<void> fetchAllAlbums() async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _albumList = await AlbumApi().fetchItems();
    } catch (error) {
      _errorMessage = 'Failed to fetch all albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Clear album list
  void clearAlbums() {
    _albumList = [];
    notifyListeners();
  }
}

