class PlaylistResponse {
  final int id;
  final String title;
  final int songQty;

  PlaylistResponse({
    required this.id,
    required this.title,
    required this.songQty,
  });

  factory PlaylistResponse.fromJson(Map<String, dynamic> json) {
    return PlaylistResponse(
      id: json['id'],
      title: json['title'],
      songQty: json['songQty'],
    );
  }
}
