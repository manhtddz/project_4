class AlbumRequest {
  final int id;
  final String title;
  final String artistName;
  final String image;
  // final bool? isReleased;
  final DateTime? releasedDate;
  final DateTime? createdAt;
  final DateTime? modifiedAt;
  final bool isDeleted;

  AlbumRequest(
      {required this.id,
        required this.title,
        required this.artistName,
        required this.isDeleted,
        this.createdAt,
        this.modifiedAt,
        required this.image,
        // this.isReleased,
        this.releasedDate});

  Map<String, dynamic> toJson(){
    return {
      'id': id,
      'title': title,
      'artistName': artistName,
      // 'subjectName': subjectName,
      'image': image,
      // 'isReleased': isReleased,
      'isDeleted': isDeleted,
      'createdAt': createdAt,
      'modifiedAt': modifiedAt,
      'releasedDate': releasedDate?.toIso8601String(),
    };
  }
}