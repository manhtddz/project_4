class UserFavorites {
  final int userId;
  final List<int> favoriteSongIds;

  // Constructor
  UserFavorites({required this.userId, required this.favoriteSongIds});

  // Factory method to create a UserFavorites instance from a Map
  factory UserFavorites.fromMap(Map<String, dynamic> map) {
    return UserFavorites(
      userId: map['user_id'],  // Map the user ID from the Map
      favoriteSongIds: List<int>.from(map['favorite_song_ids'] ?? []),  // Map the list of favorite song IDs
    );
  }

  // Method to convert the UserFavorites object into a Map
  Map<String, dynamic> toMap() {
    return {
      'user_id': userId,
      'favorite_song_ids': favoriteSongIds,
    };
  }

  void addFavorite(int songId) {
    if (!favoriteSongIds.contains(songId)) {
      favoriteSongIds.add(songId);
    }
  }

  void removeFavorite(int songId) {
    favoriteSongIds.remove(songId);
  }

  bool isFavorite(int songId) {
    return favoriteSongIds.contains(songId);
  }
}
