class Album {
  final int id;
  final String title;
  final String artistName;
  // final String? subjectName;
  final String? image;
  final bool? isReleased;
  final DateTime? releasedDate;
  final DateTime? createdAt;
  final DateTime? modifiedAt;
  final bool isDeleted;

  Album(
      {required this.id,
        required this.title,
        required this.artistName,
        required this.isDeleted,
        this.createdAt,
        this.modifiedAt,
        // this.subjectName,
        this.image,
        this.isReleased,
        this.releasedDate});

  factory Album.fromJson(Map<String, dynamic> json) {
    return Album(
        id: json['id'],
        title: json['title'],
        artistName: json['artistName'],
        // subjectName: map['subjectName'],
        image: json['image'],
        isReleased: json['isReleased'],
        isDeleted: json['isDeleted'],
        releasedDate: json['releasedDate'],
        createdAt: json['createdAt'],
        modifiedAt: json['modifiedAt']);
  }

  Map<String, dynamic> toJson(){
    return {
      'id': id,
      'title': title,
      'artistName': artistName,
      // 'subjectName': subjectName,
      'image': image,
      'isReleased': isReleased,
      'isDeleted': isDeleted,
      'createdAt': createdAt,
      'modifiedAt': modifiedAt,
      'releasedDate': releasedDate?.toIso8601String(),
    };
  }
}
