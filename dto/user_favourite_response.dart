import 'package:pj_demo/dto/song_response.dart';
import '../models/user.dart';

class UserFavoriteResponse {
  final int songId;
  final int userId;

  // Constructor
  UserFavoriteResponse({required this.userId, required this.songId});

  // Factory method to create a UserFavorites instance from a Map
  factory UserFavoriteResponse.fromJson(Map<String, dynamic> json) {
    return UserFavoriteResponse(
      userId: json['userId'],
      songId: json['songId'],
    );
  }
}
