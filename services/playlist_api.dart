import 'dart:convert';  // For json.decode
import 'package:flutter/cupertino.dart';
import 'package:pj_demo/dto/playlist_request.dart';
import 'package:pj_demo/dto/playlist_response.dart';
import 'package:pj_demo/services/api.dart';
import 'package:pj_demo/services/urlConsts.dart';
import 'package:http/http.dart' as http;

class PlaylistApi extends Api {
  // final String baseUrl = "http://localhost:8080/api/public/playlists/";

  Future<List<PlaylistResponse>> fetchPlaylistOfUser(int uId,
      BuildContext context) async {
    final response = await get(
        '${UrlConsts.PLAYLISTS}/byUser/display/$uId', context);

    // Check if the response is successful
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((json) => PlaylistResponse.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load playlist with id $uId');
    }
  }


  Future<void> addNewPlaylist(int userId, String title) async {
    final String url = 'http://${UrlConsts.HOST}${UrlConsts.PLAYLISTS}';

    // Create the request object
    PlaylistRequest request = PlaylistRequest(userId: userId, title: title);

    // Send POST request to add the song to favorites
    final response = await http.post(
      Uri.parse(url),
      headers: {
        'Content-Type': 'application/json',
      },
      body: json.encode(request), // Convert request object to JSON
    );

    if (response.statusCode == 200) {
      print("Playlist add successfully.");
    } else {
      print("Failed to add Playlist. Error: ${response.body}");
    }
  }

  Future<void> deletePlaylist(int playlistId) async {
    final url = Uri.parse('http://${UrlConsts.HOST}${UrlConsts.PLAYLISTS}/$playlistId'); //

    final response = await http.delete(url);

    if (response.statusCode == 200) {
      print("Playlist add successfully.");
    } else {
      print("Failed to delete Playlist. Error: ${response.body}");
    }
  }
}
