class Song {
  final int id;
  final String title;
  final int artistId;
  final int albumId;
  final String audioPath;
  final int listenAmount;
  final String? featureArtist;
  final String? lyricsFilePath;
  final bool isActive;
  final String albumImagePath;
  final String artistName;
  final String albumTitle;

  Song(
      {required this.id,
      required this.title,
      required this.albumId,
      required this.artistId,
      required this.audioPath,
      required this.listenAmount,
      this.featureArtist,
      this.lyricsFilePath,
      required this.isActive,
      required this.albumImagePath,
      required this.artistName,
      required this.albumTitle});

  factory Song.fromMap(Map<String, dynamic> map) {
    return Song(
        id: map['id'],
        title: map['title'],
        albumId: map['album_id'],
        artistId: map['artist_id'],
        audioPath: map['audio_path'],
        listenAmount: map['listen_amount'],
        featureArtist: map['feature_artist'],
        lyricsFilePath: map['lyric_file_path'],
        isActive: map['is_active'],
        albumImagePath: map['albumImagePath'],
        artistName: map['artistName'],
        albumTitle: map['albumTitle']);
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'album_id': albumId,
      'artist_id': artistId,
      'audio_path': audioPath,
      'listen_amount': listenAmount,
      'feature_artist': featureArtist,
      'lyric_file_path': lyricsFilePath,
      'is_active': isActive,
      'albumImagePath':albumImagePath,
      'artistName': artistName,
      'albumTitle': albumTitle,
    };
  }
}
