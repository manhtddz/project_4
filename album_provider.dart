import 'package:flutter/cupertino.dart';
import 'album.dart';

class AlbumProvider extends ChangeNotifier {
  List<Album> _albumList = [
    Album(name: 'Son Tung 1', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'Son Tung 2', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'Son Tung 3', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'SooBin Vo.1', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'Son Tung 2', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'SooBin Vo.2', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'Son Tung 2', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'SooBin Vo.3', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
  ];

  List<Album> get albumList => _albumList;

  List<Album> _searchResults = [];

  List<Album> get searchResults => _searchResults;

  void searchByKeyWord(String keyword) {
    if (keyword.isEmpty) {
      _searchResults = _albumList;
    } else {
      _searchResults = _albumList
          .where((album) =>
      album.name.toLowerCase().contains(keyword.toLowerCase()) ||
          album.artistName.toLowerCase().contains(keyword.toLowerCase()))
          .toList();
      notifyListeners(); // Notify listeners to update the UI
    }
  }
}