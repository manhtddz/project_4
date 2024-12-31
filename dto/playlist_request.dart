class PlaylistResponse {
  final int id;
  final String title;
  final String userName;

  PlaylistResponse(
      {required this.id,
      required this.title,
      required this.userName});

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'username': userName,
    };
  }
}
