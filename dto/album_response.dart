class AlbumResponse {
  final int id;
  final String title;
  final String artistName;
  final String image;

  AlbumResponse(
      {required this.id,
      required this.title,
      required this.artistName,
      required this.image});

  factory AlbumResponse.fromJson(Map<String, dynamic> json) {
    return AlbumResponse(
        id: json['id'],
        title: json['title'],
        artistName: json['artistName'],
        image: json['image']);
  }
}
