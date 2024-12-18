class Album {
  final int id;
  final String title;
  final int artistId;
  final int subjectId;
  final String artistName;
  final String subjectName;
  final String? imageUrl;
  final bool? isReleased;
  final DateTime? releasedDate;

  Album(
      {required this.id,
      required this.title,
      required this.artistId,
      required this.artistName,
      required this.subjectId,
      required this.subjectName,
      this.imageUrl,
      this.isReleased,
      this.releasedDate});

  factory Album.fromMap(Map<String, dynamic> map) {
    return Album(
        id: map['id'],
        title: map['title'],
        artistId: map['artist_id'],
        artistName: map['artistName'],
        subjectId: map['subject_id'],
        subjectName: map['subjectName'],
        imageUrl: map['image'],
        isReleased: map['is_released'],
        releasedDate: map['release_date']);
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'artist_id': artistId,
      'artistName': artistName,
      'subject_id': subjectId,
      'subjectName': subjectName,
      'image': imageUrl,
      'is_released': isReleased,
      'release_date': releasedDate?.toIso8601String(),
    };
  }
}
