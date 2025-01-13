import 'dart:async';
import 'package:flutter/cupertino.dart';
import 'package:pj_demo/dto/album_response.dart';
import 'package:pj_demo/services/api.dart';
import 'dart:convert';  // For json.decode
import 'urlConsts.dart';  // For http.get

class AlbumApi extends Api {
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
    final response = await getSearch('${UrlConsts.ALBUMS}/search/$keyword',context);

    // Check if the response is successful
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((item) => AlbumResponse.fromJson(item)).toList();
    } else {
      return [];
      // throw Exception('Failed to load album with keyword $keyword');
    }
  }

  Future<List<AlbumResponse>> fetchFavAlbumOfUser(int uId, BuildContext context) async {
    final response = await get('${UrlConsts.ALBUMS}/byUser/display/$uId', context);

    // Check if the response is successful
    if (response.statusCode == 200) {
      List<dynamic> data = json.decode(response.body);
      return data.map((item) => AlbumResponse.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load albums with id $uId');
    }
  }
}
