class Playlist {
  final int id;
  final String title;
  final int userId;
  final bool isDeleted;
  final DateTime createdAt;
  final DateTime? modifiedAt;

  Playlist(
      {required this.id,
      required this.title,
      required this.userId,
      required this.isDeleted,
      required this.createdAt,
      this.modifiedAt});
}
