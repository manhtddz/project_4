import 'package:pj_demo/sample%20P/genre.dart';

import 'genre_response.dart';

class SongResponse {
  final int id;
  final String title;
  final String audioPath;
  final int listenAmount;
  final int totalFavourite;
  final String? featureArtist;
  final String lyricFilePath;
  final bool isPending;
  final bool isDeleted;
  final DateTime? createdAt;
  final DateTime? modifiedAt;
  final String albumTitle;
  final String albumImage;
  final String artistName;
  final List<String> genreNames;

  SongResponse(
      {required this.id,
        required this.title,
        required this.audioPath,
        required this.listenAmount,
        required this.lyricFilePath,
        required this.genreNames,
        required this.isPending,
        required this.isDeleted,
        this.featureArtist,
        this.createdAt,
        this.modifiedAt,
        required this.totalFavourite,
        required this.albumTitle,
        required this.albumImage,
        required this.artistName,

      });

  factory SongResponse.fromJson(Map<String, dynamic> json) {
    var genreJson = json['genreNames'] as List;
    List<String> genreList = genreJson.map((genreJson) => genreJson.toString() ).toList();

    return SongResponse(
      id: json['id'],
      title: json['title'],
      audioPath: json['audioPath'],
      listenAmount: json['listenAmount'],
      lyricFilePath: json['lyricFilePath'],
      isPending: json['isPending'],
      isDeleted: json['isDeleted'],
      totalFavourite: json['totalFavourite'],
      createdAt: DateTime.parse(json['createdAt']),
      modifiedAt: DateTime.parse(json['modifiedAt']),
      albumImage: json['albumImage'],
      featureArtist: json['featureArtist'],
      artistName: json['artistName'],
      albumTitle: json['albumTitle'],
      genreNames: genreList,
    );
  }
}
