class LikeRequest {
  final int userId;
  final int itemId;

  // Constructor
  LikeRequest({required this.userId, required this.itemId});

  // Method to convert the UserFavoriteRequest object into a Map
  Map<String, dynamic> toJson() {
    return {
      'userId': userId,
      'itemId': itemId,
    };
  }
}
