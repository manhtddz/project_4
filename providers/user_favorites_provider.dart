import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:http/http.dart';
import 'package:pj_demo/dto/song_response.dart';
import 'package:pj_demo/dto/user_favourite_request.dart';
import 'package:pj_demo/models/song.dart';
import '../services/urlConsts.dart';
import '../services/user_favorites_api.dart'; // Ensure you import UserFavoritesAPI

class UserFavoritesProvider with ChangeNotifier {
  final UserFavoritesAPI _userFavService = UserFavoritesAPI();
  bool _isLoading = false;
  String _errorMessage = '';
  List<int> _favoriteSongIds = []; // Store the favorite song IDs
  List<int> _favoriteAlbumIds = []; // Store the favorite song IDs

  bool get isLoading => _isLoading;
  String get errorMessage => _errorMessage;
  List<int> get favoriteSongIds => _favoriteSongIds;
  List<int> get favoriteAlbumIds => _favoriteAlbumIds;

  // Add a song to user favorites
  Future<void> addUserFavoriteSong(LikeRequest requestData, BuildContext context) async {
    _isLoading = true;
    notifyListeners();

    try {
      // Check if the song is already in favorites before adding
      bool isLiked = await _userFavService.checkIfSongIsLiked(requestData, context);

      if (!isLiked) {
        // If song is not already liked, add to favorites
        await _userFavService.addSongToFavorites(requestData,context);
        _favoriteSongIds.add(requestData.itemId); // Update local list
      } else {
        _errorMessage = 'Song is already in your favorites!';
      }
    } catch (error) {
      _errorMessage = 'Failed to add song to favorites: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Remove a song from user favorites
  Future<void> removeUserFavoriteSong(LikeRequest requestData, BuildContext context) async {
    _isLoading = true;
    notifyListeners();

    try {
      // Call API to remove from favorites
      await _userFavService.removeSongFromFavorites(requestData, context);
      _favoriteAlbumIds.remove(requestData.itemId); // Update local list
    } catch (error) {
      _errorMessage = 'Failed to remove song from favorites: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Map<int, bool> _likedSongs = {}; // Cache for liked songs

  // Method to check if a song is liked (synchronously)
  bool isFavouriteSong(LikeRequest requestData) {
    return _likedSongs[requestData.itemId] ?? false; // Return cached value or false if not found
  }

  // Fetch favorite status of a song asynchronously and update the cache
  Future<void> fetchFavoriteSongStatus(LikeRequest requestData, BuildContext context) async {
    // Use the API function to check if the song is liked
    bool isLiked = await _userFavService.checkIfSongIsLiked(requestData, context);

    // Update the cache with the result
    _likedSongs[requestData.itemId] = isLiked;

    // Notify listeners that the data has changed
    notifyListeners();
  }

  // Optionally, a method to clear the cache
  void clearSongCache() {
    _likedSongs.clear();
    notifyListeners();
  }

  Future<void> addUserFavoriteAlbum(LikeRequest requestData, BuildContext context) async {
    _isLoading = true;
    notifyListeners();

    try {
      // Check if the song is already in favorites before adding
      bool isLiked = await _userFavService.checkIfAlbumIsLiked(requestData,context);

      if (!isLiked) {
        // If song is not already liked, add to favorites
        await _userFavService.addAlbumToFavorites(requestData, context);
        _favoriteAlbumIds.add(requestData.itemId); // Update local list
      } else {
        _errorMessage = 'Album is already in your favorites!';
      }
    } catch (error) {
      _errorMessage = 'Failed to add album to favorites: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Remove a song from user favorites
  Future<void> removeUserFavoriteAlbum(LikeRequest requestData, BuildContext context) async {
    _isLoading = true;
    notifyListeners();

    try {
      // Call API to remove from favorites
      await _userFavService.removeAlbumFromFavorites(requestData, context);
      _favoriteAlbumIds.remove(requestData.itemId); // Update local list
    } catch (error) {
      _errorMessage = 'Failed to remove album from favorites: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  Map<int, bool> _likedAlbums = {}; // Cache for liked songs

  // Method to check if a song is liked (synchronously)
  bool isFavouriteAlbum(LikeRequest requestData) {
    return _likedAlbums[requestData.itemId] ?? false; // Return cached value or false if not found
  }

  // Fetch favorite status of a song asynchronously and update the cache
  Future<void> fetchFavoriteAlbumStatus(LikeRequest requestData, BuildContext context) async {
    // Use the API function to check if the song is liked
    bool isLiked = await _userFavService.checkIfAlbumIsLiked(requestData, context);

    // Update the cache with the result
    _likedAlbums[requestData.itemId] = isLiked;

    // Notify listeners that the data has changed
    notifyListeners();
  }

  // Optionally, a method to clear the cache
  void clearAlbumCache() {
    _likedAlbums.clear();
    notifyListeners();
  }

}
