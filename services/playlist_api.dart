import 'dart:convert';  // For json.decode
import 'package:flutter/cupertino.dart';
import 'package:pj_demo/dto/playlist_response.dart';
import 'package:pj_demo/services/api.dart';
import 'package:pj_demo/services/urlConsts.dart';  // For http.get

class PlaylistApi extends Api {
  // final String baseUrl = "http://localhost:8080/api/public/playlists/";

  Future<List<PlaylistResponse>> fetchPlaylistOfUser(int uId, BuildContext context) async {
    final response = await get(UrlConsts.PLAYLISTS+'/byUser/display/$uId', context);

    // Check if the response is successful
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((json) => PlaylistResponse.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load playlist with id $uId');
    }
  }
}
