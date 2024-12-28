class PlaylistResponse {
  final int id;
  final String title;
  final String userName;
  final bool isDeleted;

  PlaylistResponse(
      {required this.id,
      required this.title,
      required this.userName,
      required this.isDeleted});

  factory PlaylistResponse.fromJson(Map<String, dynamic> json) {
    return PlaylistResponse(id: json['id'], title: json['title'], userName: json['username'], isDeleted: json['isDeleted']);
  }

  // Map<String, dynamic> toMap() {
  //   return {
  //     'id': id,
  //     'title': title,
  //     'username': userName,
  //     'isDeleted': isDeleted,
  //   };
  // }
}
