class Album {
  final int id;
  final String title;
  final String artistName;
  final String? subjectName;
  final String? imageUrl;
  final bool? isReleased;
  final DateTime? releasedDate;

  Album(
      {required this.id,
      required this.title,
      required this.artistName,
      this.subjectName,
      this.imageUrl,
      this.isReleased,
      this.releasedDate});

  factory Album.fromMap(Map<String, dynamic> map) {
    return Album(
        id: map['id'],
        title: map['title'],
        artistName: map['artistName'],
        subjectName: map['subjectName'],
        imageUrl: map['imageUrl'],
        isReleased: map['isReleased'],
        releasedDate: map['releasedDate']);
  }

  Map<String, dynamic> toMap(){
    return {
      'id': id,
      'title': title,
      'artistName': artistName,
      'subjectName': subjectName,
      'imageUrl': imageUrl,
      'isReleased': isReleased,
      'releasedDate': releasedDate?.toIso8601String(),
    };
  }
}
