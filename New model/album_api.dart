import 'dart:async';
import 'package:pj_demo/models/user.dart';
import 'album.dart';

class AlbumApi {
  List<Album> _albumList = [
    Album(
        id: 1,
        title: 'Son Tung 1',
        artistId: 1,
        artistName: 'ST',
        subjectId: 1,
        subjectName: 'AAA',
        imageUrl: 'assets/images/8.jpg'),
    Album(
        id: 2,
        title: 'Son Tung 2',
        artistId: 1,
        artistName: 'ST',
        subjectId: 1,
        subjectName: 'BBB',
        imageUrl: 'assets/images/8.jpg'),
    // Album(id: 3, title: 'Son Tung 3', artistId: 2,artistName: 'ST', subjectId: 1, subjectName:'AAA', imageUrl: 'assets/images/8.jpg'),
    // Album(id: 4, title: 'SooBin Vo.1', artistId: 1,artistName: 'ST', subjectId: 1, subjectName:'AAA', imageUrl: 'assets/images/8.jpg'),
    // Album(id: 5, title: 'Son Tung 2', artistId: 1,artistName: 'ST', subjectId: 1, subjectName:'AAA', imageUrl: 'assets/images/8.jpg'),
    // Album(id: 6, title: 'SooBin Vo.2', artistId: 1,artistName: 'ST', subjectId: 1, subjectName:'AAA', imageUrl: 'assets/images/8.jpg'),
    // Album(id: 7, title: 'Son Tung 2', artistId: 1,artistName: 'ST', subjectId: 1, subjectName:'AAA', imageUrl: 'assets/images/8.jpg'),
    // Album(id: 8, title: 'SooBin Vo.3', artistId: 1,artistName: 'ST', subjectId: 1, subjectName:'AAA', imageUrl: 'assets/images/8.jpg'),
  ];

  Album? _currentAlbum;

  // Fetch all albums
  List<Album> getAllAlbums() {
    return _albumList;
  }

  // Find album by id
  Future<Album?> findAlbumById(int id) async {
    await Future.delayed(const Duration(seconds: 1)); // Simulate delay
    try {
      final album = _albumList.firstWhere((it) => it.id == id);
      _currentAlbum = album;
    } catch (_) {
      return null;
    }
  }

  // Find album by artistID
  Future<List<Album>?> findAlbumsByArtistId(int id) async {
    await Future.delayed(const Duration(seconds: 1)); // Simulate delay
    try {
      final albumList = _albumList.where((it) => it.artistId == id).toList();
      return albumList;
    } catch (_) {
      return null;
    }
  }

  // Filter albums by keyword
  Future<List<Album?>?> searchAlbumsByKeyword(String keyword) async {
    await Future.delayed(const Duration(seconds: 1));
    try {
      final searchResult = _albumList
          .where((album) =>
              album.title.toLowerCase().contains(keyword.toLowerCase()) ||
              album.artistName.toLowerCase().contains(keyword.toLowerCase()))
          .toList();
      return searchResult;
    } catch (_) {
      return null;
    }
  }
}
