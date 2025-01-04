import 'dart:async';
import 'package:flutter/cupertino.dart';
import 'package:pj_demo/dto/album_response.dart';
import 'package:pj_demo/services/api.dart';
import 'dart:convert';  // For json.decode
import 'urlConsts.dart';  // For http.get

class AlbumApi extends Api {
  // final String baseUrl = "http://localhost:8080/api/public/albums";
  Future<List<AlbumResponse>> fetchItems(BuildContext context) async {
    final response = await get(UrlConsts.ALBUMS, context);

    // Check if the response is successful
    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((item) => AlbumResponse.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load albums');
    }
  }

  Future<List<AlbumResponse>> fetchItemByKeyWord(String keyword, BuildContext context) async {
    final response = await get(UrlConsts.ALBUMS+'/search/$keyword',context);

    // Check if the response is successful
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((item) => AlbumResponse.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load album with keyword $keyword');
    }
  }

  Future<List<AlbumResponse>> fetchFavAlbumOfUser(int uId, BuildContext context) async {
    final response = await get(UrlConsts.ALBUMS+'/byUser/display/$uId', context);

    // Check if the response is successful
    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((item) => AlbumResponse.fromJson(item)).toList();
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
