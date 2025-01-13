class PlaylistRequest {
  final String title;
  final int userId;

  PlaylistRequest(
      {required this.userId, required this.title});

  Map<String, dynamic> toJson() {
    return {
      'title': title,
      'userId': userId,
    };
  }
}
