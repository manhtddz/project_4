class PlaylistResponse {
  final int id;
  final String title;

  PlaylistResponse({
    required this.id,
    required this.title,
  });

  factory PlaylistResponse.fromJson(Map<String, dynamic> json) {
    return PlaylistResponse(
        id: json['id'],
        title: json['title'],
    );
  }
}
