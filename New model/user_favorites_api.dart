import 'dart:async';
import 'package:pj_demo/models/song.dart';
import 'package:pj_demo/models/user_favourites.dart';

class UserFavoritesAPI {
  // Method to fetch a user's favorite songs
  Future<UserFavorites> fetchUserFavorites(int userId) async {
    final mockResponse = {
      'user_id': userId,
      'favorite_song_ids': [
        1,
        2,
        3,
        4
      ] // Example favorite song IDs for the user
    };

    await Future.delayed(const Duration(seconds: 1)); // Simulate network delay

    try {
      return UserFavorites.fromMap(mockResponse);
    } catch (error) {
      print("Error fetching user favorites: $error");
      return UserFavorites(userId: userId, favoriteSongIds: []);
    }
  }

  // Method to add a song to the user's favorites
  Future<void> addSongToFavorites(int userId, int songId) async {
    // Simulate a successful API call to add a song to favorites
    await Future.delayed(const Duration(seconds: 1));

    print("Song with ID $songId added to favorites for user $userId.");
    // In a real app, here you would send a POST request to the server to add the song
  }

  // Method to remove a song from the user's favorites
  Future<void> removeSongFromFavorites(int userId, int songId) async {
    // Simulate a successful API call to remove a song from favorites
    await Future.delayed(const Duration(seconds: 1));

    print("Song with ID $songId removed from favorites for user $userId.");
    // In a real app, here you would send a DELETE request to the server to remove the song
  }

  // Method to get all the favorite songs of the user
  Future<List<Song>> getFavoriteSongs(int userId) async {
    final mockResponse = [
      {
        'id': 1,
        'title': 'Không Thể Say',
        'artistName': "HIEUTHUHAI",
        'artist_id': 1,
        'listen_amount': 12,
        'album_id': 1,
        'albumImagePath': "assets/images/2.jpg",
        'audio_path': "audio/Không Thể Say.mp3",
        'lyric_file_path': "lyrics/Limbo.lrc",
        'is_active': true,
        'albumTitle': 'No name',
      },
      {
        'id': 2,
        'title': 'Señorita',
        'artist_id': 1,
        'listenAmount': 12,
        'album_id': 1,
        'artistName': "Shawn Mendes",
        'albumImagePath': "assets/images/3.jpg",
        'audioPath': "audio/Señorita.mp3",
        'lyric_file_path': "lyrics/Senorita.lrc",
        'is_active': true,
        'albumTitle': 'No name'
      },
      // Add more songs as needed
    ];

    await Future.delayed(const Duration(seconds: 1)); // Simulate network delay

    try {
      // Mock the favorite song IDs for user with ID = 1
      final mockFavoriteSongIds = _getMockFavoriteSongs(userId);

      // Filter songs based on the favorite song IDs
      final favoriteSongs = mockResponse.where((song) {
        return mockFavoriteSongIds.contains(song['id']);
      }).map((it) {
        return Song.fromJson(it);  // Using the factory constructor for parsing
      }).toList();

      return favoriteSongs;
    } catch (error) {
      print("Error fetching favorite songs: $error");
      return [];
    }
  }

  // Helper method to mock favorite song IDs for a given user ID
  List<int> _getMockFavoriteSongs(int userId) {
    if (userId == 1) {
      // Mock favorite song IDs for user with ID 1
      return [1, 2, 3]; // These are the song IDs the user has favorited
    }
    // Add mock data for other users if needed
    return [];
  }
}