import 'package:flutter/material.dart';
import 'package:pj_demo/dto/album_response.dart';
import 'package:pj_demo/components/common_appbar.dart';
import 'package:pj_demo/dto/user_favourite_request.dart';
import 'package:pj_demo/pages/song_page2.dart';
import 'package:pj_demo/providers/user_provider.dart';
import 'package:provider/provider.dart';
import '../providers/song_provider.dart';
import '../providers/user_favorites_provider.dart'; // Import the provider

class AlbumPage extends StatelessWidget {
  final AlbumResponse currentAlbum;
  // final int userId = 5; // Example user ID

  AlbumPage({required this.currentAlbum});

  void goToSong(BuildContext context, int songIndex) {
    // Update the current song index in the provider
    Provider.of<SongProvider>(context, listen: false).currentSongIndex =
        songIndex;
    // Navigate to SongPage2
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => SongPage2()));
  }

  @override
  Widget build(BuildContext context) {
    final userProvider = Provider.of<UserProvider>(context, listen: false);

    final songProvider = Provider.of<SongProvider>(context);
    final userFavoritesProvider = Provider.of<UserFavoritesProvider>(context);

    // Fetch the songs of the current album if they are not fetched yet
    if (!songProvider.isLoading && songProvider.songList.isEmpty) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        songProvider.fetchSongOfAlbum(currentAlbum.id, context);
      });
    }

    return Scaffold(
      appBar: PreferredSize(
          preferredSize: Size.fromHeight(250.0),
          child: CommonAppBar(
              label1: currentAlbum.title,
              label2: currentAlbum.artistName,
              appBarImg: currentAlbum.image,
              albumId: currentAlbum.id,
              isUserFav: false)),
      body: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [
              Color(0xFFADDFFF),
              Color(0xFFFDD7E4),
            ],
          ),
        ),
        child: songProvider.isLoading
            ? Center(child: CircularProgressIndicator())
            : songProvider.songList.isEmpty
                ? Center(child: Text('No songs found.'))
                : _renderListSong(
                    context, songProvider, userFavoritesProvider, userProvider),
      ),
    );
  }

  Widget _getFavIcon(UserFavoritesProvider favProvider, LikeRequest requestData) {
    return Icon(
      favProvider.isFavouriteSong(requestData)
          ? Icons.favorite
          : Icons.favorite_border,
      color: favProvider.isFavouriteSong(requestData)
          ? Colors.red
          : Colors.grey,
    );
  }

  Widget _renderListSong(BuildContext context, SongProvider songProvider,
      UserFavoritesProvider userFavoritesProvider, UserProvider userProvider) {
    return ListView.builder(
      itemCount: songProvider.songList.length,
      itemBuilder: (context, index) {
        final song = songProvider.songList[index];
        final currentUser = userProvider.currentUser;
        LikeRequest requestData =
            LikeRequest(userId: currentUser!.id!, itemId: song.id);
        final isPlaying =
            songProvider.currentSongIndex == index && songProvider.isPlaying;

        return ListTile(
          onTap: () => goToSong(context, index),
          leading: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              SizedBox(width: 2),
              Text('${song.id}',
                  style: TextStyle(fontSize: 16, color: Colors.black54)),
              SizedBox(width: 5.0),
              CircleAvatar(
                backgroundImage: NetworkImage(song.albumImage),
              ),
            ],
          ),
          title: Text(
            song.title,
            style: TextStyle(
                fontWeight: FontWeight.bold,
                color: Colors.black54,
                fontFamily: 'San Francisco'),
          ),
          subtitle: Text(
            '${song.artistName}',
            style:
                TextStyle(color: Colors.black54, fontFamily: 'San Francisco'),
          ),
          trailing: Row(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              Consumer<UserFavoritesProvider>(
                builder: (context, favProvider, child) {
                  // If the song's favorite status is not cached, fetch it
                  if (!favProvider.isFavouriteSong(requestData)) {
                    favProvider.fetchFavoriteSongStatus(
                        requestData, context); // Fetch data asynchronously once
                  }

                  return CircleAvatar(
                    backgroundColor: Colors.white,
                    child: IconButton(
                      icon: _getFavIcon(favProvider, requestData),
                      onPressed: () async {
                        // Handle toggling favorite status
                        if (favProvider.isFavouriteSong(requestData)) {
                          // Remove song from favorites
                          await favProvider.removeUserFavoriteSong(
                              requestData, context);
                        } else {
                          // Add song to favorites
                          await favProvider.addUserFavoriteSong(
                              requestData, context);
                        }

                        // After the operation, we fetch the updated favorite status again
                        favProvider.fetchFavoriteSongStatus(
                            requestData, context);

                        // Notify listeners to trigger a UI update
                        favProvider.notifyListeners();
                      },
                    ),
                  );
                },
              ),
              IconButton(
                icon: Icon(
                  isPlaying ? Icons.pause : Icons.play_arrow,
                  color: Colors.black,
                ),
                onPressed: () {
                  if (isPlaying) {
                    songProvider.pause();
                  } else {
                    songProvider.currentSongIndex = index;
                    songProvider.play();
                  }
                },
              ),
              CircleAvatar(
                child: IconButton(
                  onPressed: () {},
                  icon: Icon(Icons.drag_indicator_rounded),
                ),
              ),
            ],
          ),
        );
      },
    );
  }
}
