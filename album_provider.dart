import 'package:flutter/cupertino.dart';
import 'album.dart';

class AlbumProvider extends ChangeNotifier {
  List<Album> _albumList = [
    Album(id: 1, title: 'Son Tung 1', artistName: 'ST', imageUrl: 'assets/images/8.jpg'),
    Album(id: 2, title: 'Son Tung 2', artistName: 'ST', imageUrl: 'assets/images/8.jpg'),
    Album(id: 3, title: 'Son Tung 3', artistName: 'ST', imageUrl: 'assets/images/8.jpg'),
    Album(id: 4, title: 'SooBin Vo.1', artistName: 'ST', imageUrl: 'assets/images/8.jpg'),
    Album(id: 5, title: 'Son Tung 2', artistName: 'ST', imageUrl: 'assets/images/8.jpg'),
    Album(id: 6, title: 'SooBin Vo.2', artistName: 'ST', imageUrl: 'assets/images/8.jpg'),
    Album(id: 7, title: 'Son Tung 2', artistName: 'ST', imageUrl: 'assets/images/8.jpg'),
    Album(id: 8, title: 'SooBin Vo.3', artistName: 'ST', imageUrl: 'assets/images/8.jpg'),
  ];

  List<Album> get albumList => _albumList;

  List<Album> _searchResults = [];

  List<Album> get searchResults => _searchResults;

  Album? _currentAlbum;
  Album? get currentAlbum => _currentAlbum;

  void findCurrentAlbum(int id) {
    _currentAlbum = _albumList.where((it) => it.id ==id).first;
    notifyListeners();
  }

  void searchByKeyWord(String keyword) {
    if (keyword.isEmpty) {
      _searchResults = _albumList;
    } else {
      _searchResults = _albumList
          .where((album) =>
      album.title.toLowerCase().contains(keyword.toLowerCase()) ||
          album.artistName.toLowerCase().contains(keyword.toLowerCase()))
          .toList();
      notifyListeners(); // Notify listeners to update the UI
    }
  }
}