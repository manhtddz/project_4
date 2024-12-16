class Playlist {
  final int id;
  final String title;
  final int userId;
  final String? userName;

  Playlist(
      {required this.id,
      required this.title,
      required this.userId,
      this.userName});

  factory Playlist.fromMap(Map<String, dynamic> map) {
    return Playlist(id: map['id'], title: map['title'], userId: map['userId']);
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'userId': userId,
      'userName': userName,
    };
  }
}
