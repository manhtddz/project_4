import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:pj_demo/models/song.dart';
import 'package:pj_demo/providers/song_provider.dart';
import 'package:pj_demo/services/urlConsts.dart';
import '../dto/song_response.dart';
import 'api.dart';

class SongApi extends Api{
  Future<List<SongResponse>> fetchAllSongOfPlaylist(int playlistId, BuildContext context) async {
    await Future.delayed(const Duration(seconds: 1));
      final response = await get(UrlConsts.SONGS+'/byPlaylist/display/$playlistId', context);
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((item) => SongResponse.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load songs of playlistID $playlistId');
    }
  }

  Future<List<SongResponse>> fetchAllSongOfAlbum(int albumId, BuildContext context) async {
    await Future.delayed(const Duration(seconds: 1));
    final response = await get(UrlConsts.SONGS+'/byAlbum/display/$albumId', context);
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((item) => SongResponse.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load song of albumID $albumId');
    }
  }

  Future<List<SongResponse>> fetchFavSongOfUser(int userId, BuildContext context) async {
    final response = await get(UrlConsts.SONGS+'/byUser/display/$userId',context);

    // Check if the response is successful
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((item) => SongResponse.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load favourite songs of userId $userId');
    }
  }
}
