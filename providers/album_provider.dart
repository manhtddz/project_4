import 'package:flutter/cupertino.dart';
import 'package:pj_demo/dto/album_response.dart';
import 'package:pj_demo/services/album_api.dart';
import '../models/album.dart';

class AlbumProvider with ChangeNotifier {
  final AlbumApi _albumService = AlbumApi();
  List<AlbumResponse> _albumList = [];
  bool _isLoading = false;
  String _errorMessage = '';

  bool get isLoading => _isLoading;
  List<AlbumResponse> get albumList => _albumList;
  String get errorMessage => _errorMessage;

  // AlbumProvider(this.apiService);

  // Search albums by keyword
  Future<void> searchAlbumByKeyword(String keyword, BuildContext context) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _albumList = await _albumService.fetchItemByKeyWord(keyword,context);
    } catch (error) {
      _errorMessage = 'Failed to fetch albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Fetch favorite albums of a user
  Future<void> getFavAlbumsOfUser(int userId, BuildContext context) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _albumList = await _albumService.fetchFavAlbumOfUser(userId, context);
    } catch (error) {
      _errorMessage = 'Failed to fetch favorite albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Fetch all albums
  Future<void> fetchAllAlbums(BuildContext context) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _albumList = await _albumService.fetchItems(context);
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

