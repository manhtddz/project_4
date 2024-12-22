import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/album_api.dart';
import 'album.dart';

class AlbumProvider extends ChangeNotifier {
  List<Album> _albumList = [];
  bool _isLoading = false;

  bool get isLoading => _isLoading;
  List<Album> get albumList => _albumList;

  Future<void> searchAlbums(String keyword) async {
    _isLoading = true;

    await Future.delayed(Duration(milliseconds: 100));
    notifyListeners();

    final searchResults = await AlbumApi().searchAlbums(keyword);

    if (searchResults.isNotEmpty) {
      _albumList = searchResults;
    } else {
      _albumList = [];
    }
    _isLoading = false;

    notifyListeners();
  }

  Future<void> fetchFavouriteAlbumsOfUser(int userId) async {
    _isLoading = true;

    await Future.delayed(Duration(milliseconds: 100));
    notifyListeners();

    final results = await AlbumApi().findFavouriteAlbumsOfUser(userId);
    if (results.isNotEmpty) {
      _albumList = results;
    } else {
      _albumList = [];
    }
    _isLoading = false;
    notifyListeners();
  }

  Future<void> fetchAllAlbums() async {
    _isLoading = true;
    notifyListeners();

    _albumList = await AlbumApi().fetchAllAlbums();
    _isLoading = false;
    notifyListeners();
  }
  void clearAlbums() {
    _albumList = [];
    notifyListeners();
  }
}
