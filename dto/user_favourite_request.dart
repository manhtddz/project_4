class UserFavoriteRequest {
  final int userId;

  // Constructor
  UserFavoriteRequest({required this.userId});

  // Method to convert the UserFavorites object into a Map
  Map<String, dynamic> toJson() {
    return {
      'user_id': userId,
    };
  }

  // void addFavorite(int songId) {
  //   if (!favoriteSongIds.contains(songId)) {
  //     favoriteSongIds.add(songId);
  //   }
  // }
  //
  // void removeFavorite(int songId) {
  //   favoriteSongIds.remove(songId);
  // }
  //
  // bool isFavorite(int songId) {
  //   return favoriteSongIds.contains(songId);
  // }
}
