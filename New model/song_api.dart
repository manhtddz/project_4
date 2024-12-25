import 'package:pj_demo/models/song.dart';

class SongApi {
  Future<List<Song>> fetchAllSong() async {
    await Future.delayed(const Duration(seconds: 1));
    return [
      Song(
          id: 1,
          title: 'Limbo',
          artistName: "Keshi",
          artistId: 1,
          listenAmount: 12,
          albumId: 1,
          albumImagePath: "assets/images/2.webp",
          audioPath: "audio/Limbo.mp3",
          lyricsFilePath: "lyrics/Limbo.lrc",
          isActive: true,
          albumTitle: 'No name'),
      Song(
          id: 2,
          title: 'Limbo',
          artistName: "Keshi",
          artistId: 1,
          listenAmount: 12,
          albumId: 1,
          albumImagePath: "assets/images/2.webp",
          audioPath: "audio/Limbo.mp3",
          lyricsFilePath: "lyrics/Limbo.lrc",
          isActive: true,
          albumTitle: 'No name'),
      Song(
          id: 3,
          title: 'Limbo',
          artistName: "Keshi",
          artistId: 1,
          listenAmount: 12,
          albumId: 1,
          albumImagePath: "assets/images/2.webp",
          audioPath: "audio/Limbo.mp3",
          lyricsFilePath: "lyrics/Limbo.lrc",
          isActive: true,
          albumTitle: 'No name'),
      Song(
          id: 4,
          title: 'Limbo',
          artistName: "Keshi",
          artistId: 1,
          listenAmount: 12,
          albumId: 1,
          albumImagePath: "assets/images/2.webp",
          audioPath: "audio/Limbo.mp3",
          lyricsFilePath: "lyrics/Limbo.lrc",
          isActive: true,
          albumTitle: 'No name'),
      Song(
          id: 5,
          title: 'Limbo',
          artistName: "Keshi",
          artistId: 1,
          listenAmount: 12,
          albumId: 1,
          albumImagePath: "assets/images/2.webp",
          audioPath: "audio/Limbo.mp3",
          lyricsFilePath: "lyrics/Limbo.lrc",
          isActive: true,
          albumTitle: 'No name'),
      Song(
          id: 6,
          title: 'Señorita',
          artistId: 1,
          listenAmount: 12,
          albumId: 1,
          artistName: "Shawn Mendes",
          albumImagePath: "assets/images/3.jpg",
          audioPath: "audio/Señorita.mp3",
          lyricsFilePath: "lyrics/Senorita.lrc",
          isActive: true,
          albumTitle: 'No name'),
      Song(
          id: 7,
          title: 'As You Fade Away',
          artistId: 1,
          listenAmount: 12,
          albumId: 1,
          artistName: "NEFFEX",
          albumImagePath: "assets/images/3.jpg",
          audioPath: "audio/music1.mp3",
          lyricsFilePath: "lyrics/Limbo.lrc",
          isActive: true,
          albumTitle: 'No name'),
      Song(
          id: 8,
          title: 'Exit Sign',
          artistName: "HIEUTHUHAI",
          artistId: 1,
          listenAmount: 12,
          albumId: 1,
          albumImagePath: "assets/images/4.jpg",
          audioPath: "audio/Exit Sign.mp3",
          lyricsFilePath: "lyrics/ExitSign.lrc",
          isActive: true,
          albumTitle: 'No name'),
      Song(
          id: 9,
          title: 'Không Thể Say',
          artistName: "HIEUTHUHAI",
          artistId: 1,
          listenAmount: 12,
          albumId: 1,
          albumImagePath: "assets/images/2.jpg",
          audioPath: "audio/Không Thể Say.mp3",
          lyricsFilePath: "lyrics/Limbo.lrc",
          isActive: true,
          albumTitle: 'No name'),
      Song(
          id: 10,
          title: 'NOLOVENOLIFE',
          artistName: "HIEUTHUHAI",
          artistId: 1,
          listenAmount: 12,
          albumId: 1,
          albumImagePath: "assets/images/2.jpg",
          audioPath: "audio/NOLOVENOLIFE.mp3",
          lyricsFilePath: "lyrics/Limbo.lrc",
          isActive: true,
          albumTitle: 'No name'),
    ];
  }

  Future<List<Song>> filterSongOfPlaylist(int playlistId) async {
    final mockResponse = [
      {
        'id': 1,
        'title': 'Không Thể Say',
        'artistName': "HIEUTHUHAI",
        'artist_id': 1,
        'listen_amount': 12,
        'album_id': 1,
        'albumImagePath': "assets/images/2.jpg",
        'audio_path': "audio/Không Thể Say.mp3",
        'lyric_file_path': "lyrics/Limbo.lrc",
        'is_active': true,
        'albumTitle': 'No name',
      },
      {
        'id': 2,
        'title': 'Señorita',
        'artist_id': 1,
        'listenAmount': 12,
        'album_id': 1,
        'artistName': "Shawn Mendes",
        'albumImagePath': "assets/images/3.jpg",
        'audioPath': "audio/Señorita.mp3",
        'lyric_file_path': "lyrics/Senorita.lrc",
        'is_active': true,
        'albumTitle': 'No name'
      },
      {
        'id': 3,
        'title': 'Không Thể Say',
        'artistName': "HIEUTHUHAI",
        'artist_id': 1,
        'listenAmount': 12,
        'album_id': 1,
        'albumImagePath': "assets/images/2.jpg",
        'audioPath': "audio/Không Thể Say.mp3",
        'lyric_file_path': "lyrics/Limbo.lrc",
        'is_active': true,
        'albumTitle': 'No name'
      },
      {
        'id': 4,
        'title': 'As You Fade Away',
        'artist_id': 1,
        'listen_amount': 12,
        'album_id': 1,
        'artistName': "NEFFEX",
        'albumImagePath': "assets/images/3.jpg",
        'audio_path': "audio/music1.mp3",
        'lyric_file_path': "lyrics/Limbo.lrc",
        'is_active': true,
        'albumTitle': 'No name'
      }
    ];
    await Future.delayed(const Duration(seconds: 1));
    try {
      final filteredItems = mockResponse;
      return filteredItems.map((it) {
        return Song(
            id: int.parse(it['id'].toString()),
            title: it['title'].toString(),
            albumId: int.parse(it['album_id'].toString()),
            artistId: int.parse(it['artist_id'].toString()),
            audioPath: it['audio_path'].toString(),
            listenAmount: int.parse(it['listen_amount'].toString()),
            featureArtist: it['feature_artist'].toString(),
            lyricsFilePath: it['lyric_file_path'].toString(),
            isActive: bool.parse(it['is_active'].toString()),
            albumImagePath: it['albumImagePath'].toString(),
            artistName: it['artistName'].toString(),
            albumTitle: it['albumTitle'].toString());
      }).toList();
    } catch (error) {
      print("Error searching for item: $error");
      return [];
    }
  }

  Future<List<Song>> filterSongOfAlbum(int albumId) async {
    final mockResponse = [
      {
        'id': 5,
        'title': 'Không Thể Say',
        'artistName': "HIEUTHUHAI",
        'artist_id': 1,
        'listen_amount': 12,
        'album_id': 1,
        'albumImagePath': "assets/images/2.jpg",
        'audio_path': "audio/Không Thể Say.mp3",
        'lyric_file_path': "lyrics/Limbo.lrc",
        'is_active': true,
        'albumTitle': 'No name',
      },
      {
        'id': 6,
        'title': 'Señorita',
        'artist_id': 1,
        'listen_amount': 12,
        'album_id': 1,
        'artistName': "Shawn Mendes",
        'albumImagePath': "assets/images/3.jpg",
        'audio_path': "audio/Señorita.mp3",
        'lyric_file_path': "lyrics/Senorita.lrc",
        'is_active': true,
        'albumTitle': 'No name'
      },
    ];
    await Future.delayed(const Duration(seconds: 1));
    try {
      final filteredItems = mockResponse
          .where((it) => (it['album_id']  == albumId)).toList();
      return filteredItems.map((it) {
        return Song(
            id: int.parse(it['id'].toString()),
            title: it['title'].toString(),
            albumId: int.parse(it['album_id'].toString()),
            artistId: int.parse(it['artist_id'].toString()),
            audioPath: it['audio_path'].toString(),
            listenAmount: int.parse(it['listen_amount'].toString()),
            featureArtist: it['feature_artist'].toString(),
            lyricsFilePath: it['lyric_file_path'].toString(),
            isActive: bool.parse(it['is_active'].toString()),
            albumImagePath: it['albumImagePath'].toString(),
            artistName: it['artistName'].toString(),
            albumTitle: it['albumTitle'].toString());
      }).toList();
    } catch (error) {
      print("Error searching for item: $error");
      return [];
    }
  }
}
