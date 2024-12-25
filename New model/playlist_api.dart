import 'package:pj_demo/models/playlist.dart';

class PlaylistApi {
  Future<List<Playlist>> findFavouriteAlbumsOfUser(int userId) async {
    final mockResponse = [
      {
        'id': 1,
        'title': 'Sky 1',
        'user_id': 1,
        'userName': 'Thu Thuy',
      },
      {
        'id': 2,
        'title': 'Sky 2',
        'user_id': 1,
        'userName': 'Thu Thuy',
      },
      {
        'id': 3,
        'title': 'Sky 3',
        'user_id': 1,
        'userName': 'Thu Thuy',
      },
    ];
    await Future.delayed(const Duration(seconds: 1));
    try {
      final filteredItems = mockResponse;
      return filteredItems.map((it) {
        return Playlist(
            id: int.parse(it['id'].toString()),
            title: it['title'].toString(),
            userId: int.parse(it['user_id'].toString()),
            userName: it['userName'].toString());
      }).toList();
    } catch (error) {
      print("Error searching for item: $error");
      return [];
    }
  }
}
