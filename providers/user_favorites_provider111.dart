import 'package:flutter/cupertino.dart';
import 'package:pj_demo/dto/song_response.dart';
import 'package:pj_demo/models/song.dart';

import '../services/user_favorites_api.dart'; // Make sure you import Song model

class UserFavoritesProvider with ChangeNotifier {
  final UserFavoritesAPI _userFavService = UserFavoritesAPI();
  List<SongResponse> _favoriteSongs = []; // Change this to store Song objects
  bool _isLoading = false;
  String _errorMessage = '';

  List<SongResponse> get favoriteSongs => _favoriteSongs;
  bool get isLoading => _isLoading;
  String get errorMessage => _errorMessage;

  // Example: Fetch user favorites
  Future<void> fetchUserFavorites(int userId, BuildContext context) async {
    _isLoading = true;
    notifyListeners();
    // Simulating an API request
    try {
    _favoriteSongs = await _userFavService.fetchFavoriteSongsOfUser(userId, context);
    // Handle null or empty response
    } catch (error) {
      _errorMessage = 'Failed to fetch albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Function to check if the song is favorited
  // bool isFavorite(int songId) {
  //   // Check if a song with the given ID is in the list of favorite songs
  //   return _favoriteSongs.any((song) => song.id == songId);
  // }
  //
  // void addFavorite(SongResponse song) {
  //   if (!isFavorite(song.id)) {
  //     _favoriteSongs.add(song);
  //     notifyListeners(); // This is important!
  //   }
  // }
  //
  // void removeFavorite(SongResponse song) {
  //   _favoriteSongs.removeWhere((item) => item.id == song.id);
  //   notifyListeners(); // This is important!
  // }
}
