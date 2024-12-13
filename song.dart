class Song {
  final int songId;
  final String songName;
  final String artistName;

  final String albumArtImagePath;
  final String audioPath;
  final String lyricsFile;

  Song(
      {required this.songId,
        required this.songName,
        required this.artistName,
        required this.albumArtImagePath,
        required this.audioPath,
        required this.lyricsFile});
}