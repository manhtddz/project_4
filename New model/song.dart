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

  factory Song.fromJson(Map<String, dynamic> json) {
    return Song(
      id: json['id'] != null ? int.parse(json['id'].toString()) : 0,
      title: json['title']?.toString() ?? '',
      albumId: json['album_id'] != null ? int.parse(json['album_id'].toString()) : 0,
      artistId: json['artist_id'] != null ? int.parse(json['artist_id'].toString()) : 0,
      audioPath: json['audio_path']?.toString() ?? '',
      listenAmount: json['listen_amount'] != null ? int.parse(json['listen_amount'].toString()) : 0,
      featureArtist: json['feature_artist']?.toString(),
      lyricsFilePath: json['lyric_file_path']?.toString() ?? '',
      isActive: json['is_active'] != null ? json['is_active'] : false,
      albumImagePath: json['albumImagePath']?.toString() ?? '',
      artistName: json['artistName']?.toString() ?? '',
      albumTitle: json['albumTitle']?.toString() ?? '',
    );
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
