class Song {
  final int id;
  final String title;
  final String audioPath;
  final int? likeAmount;
  final int? listenAmount;
  final String? featureArtist;
  final String? lyricsFilePath;
  final bool isActive;
  final String? albumImagePath;
  final String? artistName;
  final String albumTitle;

  Song(
      {required this.id,
      required this.title,
      required this.audioPath,
      this.likeAmount,
      this.listenAmount,
      this.featureArtist,
      this.lyricsFilePath,
      required this.isActive,
      this.albumImagePath,
      this.artistName,
      required this.albumTitle});

  factory Song.fromMap(Map<String, dynamic> map) {
    return Song(
        id: map['id'],
        title: map['title'],
        audioPath: map['audioPath'],
        likeAmount: map['likeAmount'],
        listenAmount: map['listenAmount'],
        featureArtist: map['featureArtist'],
        lyricsFilePath: map['lyricsFilePath'],
        isActive: map['isActive'],
        albumImagePath: map['albumImagePath'],
        artistName: map['artistName'],
        albumTitle: map['albumTitle']);
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'audioPath': audioPath,
      'likeAmount': likeAmount,
      'listenAmount': listenAmount,
      'featureArtist': featureArtist,
      'lyricsFilePath': lyricsFilePath,
      'isActive': isActive,
      'albumImagePath': albumImagePath,
      'artistName': artistName,
      'albumTitle': albumTitle,
    };
  }
}
