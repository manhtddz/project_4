import 'dart:async';
import '../models/album.dart';
import 'dart:convert';  // For json.decode
import 'package:http/http.dart' as http;  // For http.get

class AlbumApi {
  final String baseUrl = "http://localhost:8080/api/public/albums";
  Future<List<Album>> fetchItems() async {
    final response = await http.get(Uri.parse(baseUrl));

    // Check if the response is successful
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((json) => Album.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load genres');
    }
  }
  // Future<List<Album>> fetchAllAlbums() async {
  //   await Future.delayed(const Duration(seconds: 1));
  //   return [
  //     Album(
  //         id: 1,
  //         title: 'BBB 3',
  //         artistId: 1,
  //         artistName: 'ST',
  //         subjectId: 1,
  //         subjectName: 'AAA',
  //         imageUrl: 'assets/images/1.jpg'),
  //     Album(
  //         id: 2,
  //         title: 'BBB 2',
  //         artistId: 1,
  //         artistName: 'ST',
  //         subjectId: 1,
  //         subjectName: 'BBB',
  //         imageUrl: 'assets/images/4.jpg'),
  //     Album(
  //         id: 3,
  //         title: 'BBB 3',
  //         artistId: 1,
  //         artistName: 'ST',
  //         subjectId: 1,
  //         subjectName: 'CCC',
  //         imageUrl: 'assets/images/5.jpg'),
  //     Album(
  //         id: 4,
  //         title: 'AHH 2',
  //         artistId: 1,
  //         artistName: 'Thuy Linh',
  //         subjectId: 1,
  //         subjectName: 'BBB',
  //         imageUrl: 'assets/images/3.jpg'),
  //   ];
  // }

  Future<List<Album>> fetchItemByKeyWord(String keyword) async {
    final response = await http.get(Uri.parse('$baseUrl/search/$keyword'));

    // Check if the response is successful
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((json) => Album.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load album with keyword $keyword');
    }
  }

  Future<List<Album>> fetchFavAlbumOfUser(int uId) async {
    final response = await http.get(Uri.parse('$baseUrl/byUser/display/$uId'));

    // Check if the response is successful
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((json) => Album.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load albums with id $uId');
    }
  }

  // Future<List<Album>> searchAlbums(String keyword) async {
  //   final mockResponse = [
  //     {
  //       'id': 1,
  //       'title': 'Son Tung 1',
  //       'artist_id': 1,
  //       'artistName': 'ST',
  //       'subject_id': 1,
  //       'subjectName': 'AAA',
  //       'image': 'assets/images/3.jpg'
  //     },
  //     {
  //       'id': 2,
  //       'title': 'Thuy Linh 1',
  //       'artist_id': 1,
  //       'artistName': 'Hoang Thuy Linh',
  //       'subject_id': 1,
  //       'subjectName': 'BBB',
  //       'image': 'assets/images/2.jpg'
  //     },
  //   ];
  //
  //   await Future.delayed(const Duration(seconds: 1));
  //   try {
  //     final filteredItems = mockResponse
  //         .where((item) =>
  //             (item['title'] as String)
  //                 .toLowerCase()
  //                 .contains(keyword.toLowerCase()) ||
  //             (item['artistName'] as String).toLowerCase().contains(keyword.toLowerCase()))
  //         .toList();
  //     return filteredItems.map((it) {
  //       return Album(
  //           id: int.parse(it['id'].toString()),
  //           title: it['title'].toString(),
  //           artistId: int.parse(it['artist_id'].toString()),
  //           imageUrl: it['image'].toString(),
  //           artistName: it['artistName'].toString(),
  //           subjectId: int.parse(it['subject_id'].toString()),
  //           subjectName: it['subjectName'].toString());
  //     }).toList();
  //   } catch (error) {
  //     print("Error searching for item: $error");
  //     return [];
  //   }
  // }

  // Future<List<Album>> findFavouriteAlbumsOfUser(int userId) async {
  //   final mockResponse = [
  //     {
  //       'id': 3,
  //       'title': 'Sky 1',
  //       'artist_id': 1,
  //       'artistName': 'Son Tung',
  //       'subject_id': 1,
  //       'subjectName': 'AAA',
  //       'image': 'assets/images/3.jpg'
  //     },
  //     {
  //       'id': 3,
  //       'title': 'Sky 2',
  //       'artist_id': 1,
  //       'artistName': 'Son Tung',
  //       'subject_id': 1,
  //       'subjectName': 'BBB',
  //       'image': 'assets/images/2.jpg'
  //     },
  //   ];
  //   await Future.delayed(const Duration(seconds: 1));
  //   try {
  //     final filteredItems = mockResponse;
  //     return filteredItems.map((it) {
  //       return Album(
  //           id: int.parse(it['id'].toString()),
  //           title: it['title'].toString(),
  //           artistId: int.parse(it['artist_id'].toString()),
  //           artistName: it['artistName'].toString(),
  //           subjectId: int.parse(it['subject_id'].toString()),
  //           subjectName: it['subjectName'].toString());
  //     }).toList();
  //   } catch (error) {
  //     print("Error searching for item: $error");
  //     return [];
  //   }
  // }
}
