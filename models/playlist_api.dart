import 'dart:convert';  // For json.decode
import 'package:http/http.dart' as http;
import 'package:pj_demo/models/playlist_response.dart';  // For http.get

class PlaylistApi {
  final String baseUrl = "http://localhost:8080/api/public/playlists/";

  Future<List<PlaylistResponse>> fetchPlaylistOfUser(int uId) async {
    final response = await http.get(Uri.parse('$baseUrl/byUser/display/$uId'));

    // Check if the response is successful
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((json) => PlaylistResponse.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load playlist with id $uId');
    }
  }
}
