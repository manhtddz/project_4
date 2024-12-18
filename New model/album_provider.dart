import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/album_api.dart';
import 'album.dart';

class AlbumProvider extends ChangeNotifier {
  final AlbumApi _albumApi = AlbumApi();

  Album? _currentAlbum;
  List<Album?>? _searchResults = [];
  List<Album?>? _albumList = [];

  Album? get currentAlbum => _currentAlbum;
  List<Album?>? get albumList => _albumList;
  List<Album?>? get searchResults => _searchResults;

  void fetchAlbums() {
    _albumList = _albumApi.getAllAlbums();
  }

  Future<Album?> fetchCurrentAlbum(int id) async {
    _currentAlbum = await _albumApi.findAlbumById(id);
    notifyListeners();
    return _currentAlbum;
  }

  Future<List<Album>?> findAlbumsByArtistId(int id) async {
    final albumList = await _albumApi.findAlbumsByArtistId(id);
    notifyListeners();
    return albumList;
  }

  Future<List<Album?>?> searchAlbumByKeyWord(String keyword) async {
    if (keyword.isNotEmpty) {
      _searchResults = await _albumApi.searchAlbumsByKeyword(keyword);
      notifyListeners();
    }
    _searchResults = _albumList;
    notifyListeners();
    return _searchResults;
  }
}
