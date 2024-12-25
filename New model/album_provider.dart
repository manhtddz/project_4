import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/album_api.dart';
import 'album.dart';

class AlbumProvider extends ChangeNotifier {
  List<Album> _albumList = [];
  bool _isLoading = false;
  String _errorMessage = '';

  bool get isLoading => _isLoading;
  List<Album> get albumList => _albumList;
  String get errorMessage => _errorMessage;

  // Search albums by keyword
  Future<void> searchAlbums(String keyword) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      final searchResults = await AlbumApi().searchAlbums(keyword);

      if (searchResults.isNotEmpty) {
        _albumList = searchResults;
      } else {
        _albumList = [];
      }
    } catch (error) {
      _errorMessage = 'Failed to fetch albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Fetch favorite albums of a user
  Future<void> fetchFavouriteAlbumsOfUser(int userId) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      final results = await AlbumApi().findFavouriteAlbumsOfUser(userId);

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
      _albumList = await AlbumApi().fetchAllAlbums();
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

