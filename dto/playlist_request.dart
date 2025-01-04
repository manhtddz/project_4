class PlaylistRequest {
  final int id;
  final String title;
  final String userId;

  PlaylistRequest(
      {required this.id, required this.title, required this.userId});

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'user_id': userId,
    };
  }
}
