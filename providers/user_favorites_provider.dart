import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/song.dart';

import '../services/user_favorites_api.dart'; // Make sure you import Song model

class UserFavoritesProvider with ChangeNotifier {
  List<Song> _favoriteSongs = []; // Change this to store Song objects
  bool _isLoading = false;

  List<Song> get favoriteSongs => _favoriteSongs;
  bool get isLoading => _isLoading;

  // Example: Fetch user favorites
  Future<void> fetchUserFavorites(int userId) async {
    _isLoading = true;
    notifyListeners();
    // Simulating an API request
    final response = await UserFavoritesAPI().getFavoriteSongs(userId);
    // Handle null or empty response
    if (response.isEmpty) {
      _favoriteSongs = [];
    } else {
      // Safely update favorite songs
      _favoriteSongs = response;
    }
    _isLoading = false;
    notifyListeners();
  }

  // Function to check if the song is favorited
  bool isFavorite(int songId) {
    // Check if a song with the given ID is in the list of favorite songs
    return _favoriteSongs.any((song) => song.id == songId);
  }

  void addFavorite(Song song) {
    if (!isFavorite(song.id)) {
      _favoriteSongs.add(song);
      notifyListeners(); // This is important!
    }
  }

  void removeFavorite(Song song) {
    _favoriteSongs.removeWhere((item) => item.id == song.id);
    notifyListeners(); // This is important!
  }
}
