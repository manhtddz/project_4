class Song {
  final int id;
  final String title;
  final int artistId;
  final int albumId;
  final String? audioPath;
  final int listenAmount;
  final String? featureArtist;
  final String? lyricsFilePath;
  final bool isPending;
  final DateTime? createdAt;
  final DateTime? modifiedAt;
  final bool isDeleted;

  Song({
    required this.id,
    required this.title,
    required this.albumId,
    required this.artistId,
    this.audioPath,
    required this.listenAmount,
    this.featureArtist,
    this.lyricsFilePath,
    required this.isPending,
    required this.isDeleted,
    required this.createdAt,
    this.modifiedAt,
  });

  // Map<String, dynamic> toMap() {
  //   return {
  //     'id': id,
  //     'title': title,
  //     'album_id': albumId,
  //     'artist_id': artistId,
  //     'audio_path': audioPath,
  //     'listen_amount': listenAmount,
  //     'feature_artist': featureArtist,
  //     'lyric_file_path': lyricsFilePath,
  //     'is_active': isActive,
  //     'albumImagePath':albumImagePath,
  //     'artistName': artistName,
  //     'albumTitle': albumTitle,
  //   };
  // }
}
