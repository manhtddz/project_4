import 'dart:async';
import 'dart:convert';
import 'package:flutter/cupertino.dart';
import 'package:pj_demo/services/api.dart';
import 'package:pj_demo/services/urlConsts.dart';
import 'package:http/http.dart' as http;

import '../dto/user_favourite_request.dart';

class UserFavoritesAPI extends Api {
  // API to check if a song is already liked by the user
  Future<bool> checkIfSongIsLiked(LikeRequest requestData, BuildContext context) async {
    final String url = '${UrlConsts.FAVOURITE_SONGS}/check'; // Ensure the URL is correct

    try {
      // Sending POST request to check if song is liked
      final response = await post(url, requestData, context); // Correctly calling the `post` method

      if (response.statusCode == 200) {
        // Assuming the response is a JSON containing 'isLike'
        final Map<String, dynamic> responseData = json.decode(response.body);
        return responseData['isLike'] ?? false; // Return the "isLike" status, default to false if null
      } else {
        print("Failed to check if song is liked. Status code: ${response.statusCode}");
        return false; // Return false if there's an error
      }
    } catch (e) {
      print("Error checking song like status: $e");
      return false; // Return false in case of exception
    }
  }


  // API to add a song to the user's favorites
  Future<void> addSongToFavorites(LikeRequest requestData, BuildContext context) async {
    // Check if the song is already liked by the user
    bool isLiked = await checkIfSongIsLiked(requestData, context);

    if (isLiked) {
      // If the song is already liked, we return without adding it again
      print("Song with ID ${requestData.itemId} is already in the favorites list for user ${requestData.userId}.");
      return; // Early return since the song is already in the favorites
    }

    final String url = '${UrlConsts.SONGS}/like';

    try {
      // Sending POST request to add the song to favorites
      final response = await post(url, requestData, context);  // Use the 'post' method from the previous code

      // Check if the response status code is 200 (OK)
      if (response.statusCode == 200) {
        print("Song with ID ${requestData.itemId} added to favorites for user ${requestData.userId}.");
      } else {
        // Handle unexpected response codes (e.g., 4xx, 5xx)
        print("Failed to add song to favorites. Status code: ${response.statusCode}. Error: ${response.body}");
      }
    } catch (e) {
      // Handle network errors or any other exceptions that occur during the API call
      print("Error adding song to favorites: $e");
    }
  }


  // API to remove a song from the user's favorites
  Future<void> removeSongFromFavorites(LikeRequest requestData, BuildContext context) async {
    final String url = '${UrlConsts.SONGS}/unlike';

    final Map<String, dynamic> body = {
      'userId': requestData.userId.toString(), // Convert to string if needed
      'itemId': requestData.itemId.toString(), // Convert to string if needed
    };

    try {
      // Send the DELETE request with the request body
      final response = await delete(url, context, params: body);

      if (response.statusCode == 200) {
        print("Song with ID ${requestData.itemId} removed from favorites for user ${requestData.userId}.");
      } else {
        print("Failed to remove song from favorites. Error: ${response.body}");
      }
    } catch (error) {
      print("Error during removal: $error");
    }
  }


  Future<bool> checkIfAlbumIsLiked(
      LikeRequest requestData, BuildContext context) async {
    final String url = '${UrlConsts.FAVOURITE_ALBUMS}/check';

    // LikeRequest requestData = LikeRequest(userId: userId, itemId: albumId);

    // Sending POST request to check if song is liked
    final response = await post(url, requestData, context);

    // Check the response from API
    if (response.statusCode == 200) {
      final Map<String, dynamic> responseData = json.decode(response.body);
      return responseData['isLike']; // Return the "isLike" status
    } else {
      print('Request Data: ${json.encode(requestData)}');
      print("responseStatus: ${response.statusCode}");
      print("Failed to check if album is liked. Error: ${response.body}");
      return false; // Return false if there's an error
    }
  }

  // API to add a album to the user's favorites
  Future<void> addAlbumToFavorites(
      LikeRequest requestData, BuildContext context) async {
    // Check if the song is already liked by the user
    bool isLiked = await checkIfAlbumIsLiked(requestData, context);

    if (isLiked) {
      print(
          "Album with ID ${requestData.itemId} is already in the favorites list for user ${requestData.userId}.");
      return; // The song is already liked, no need to add again
    }

    final String url = '${UrlConsts.ALBUMS}/like';

    // // Create the request object
    // LikeRequest request = LikeRequest(userId: userId, itemId: albumId);

    // Send POST request to add the song to favorites
    final response = await post(url, requestData, context);

    if (response.statusCode == 200) {
      print(
          "Album with ID ${requestData.itemId} added to favorites for user ${requestData.userId}.");
    } else {
      print("Failed to add album to favorites. Error: ${response.body}");
    }
  }

  Future<void> removeAlbumFromFavorites(LikeRequest requestData, BuildContext context) async {
    final String url = '${UrlConsts.ALBUMS}/unlike'; // Your API endpoint

    // Create the request body (ensure the values are strings where needed)
    final Map<String, dynamic> body = {
      'userId': requestData.userId.toString(), // Convert to string if needed
      'itemId': requestData.itemId.toString(), // Convert to string if needed
    };

    try {
      // Send the DELETE request with the request body
      final response = await delete(url, context, params: body);

      if (response.statusCode == 200) {
        print("Album with ID ${requestData.itemId} removed from favorites for user ${requestData.userId}.");
      } else {
        print("Failed to remove album from favorites. Error: ${response.body}");
      }
    } catch (error) {
      print("Error during removal: $error");
    }
  }

}



  // Future<void> removeAlbumFromFavorites(LikeRequest requestData, BuildContext context) async {
  //   final String url = 'http://${UrlConsts.HOST}${UrlConsts.ALBUMS}/unlike';
  //
  //   // Prepare the request data as a JSON object
  //   final Map<String, dynamic> body = {
  //     'userId': requestData.userId,
  //     'itemId': requestData.itemId,
  //   };
  //
  //   // Send DELETE request to remove the album from favorites
  //   final response = await http.delete(
  //     Uri.parse(url),
  //     headers: {'Content-Type': 'application/json'},
  //     body: json.encode(body), // Encode the body as JSON
  //   );
  //
  //   if (response.statusCode == 200) {
  //     print(
  //         "Album with ID ${requestData.itemId} removed from favorites for user ${requestData.userId}.");
  //   } else {
  //     print("Failed to remove album from favorites. Error: ${response.body}");
  //   }


