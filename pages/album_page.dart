import 'package:flutter/material.dart';
import 'package:pj_demo/dto/album_response.dart';
import 'package:pj_demo/general_widget/common_appbar.dart';
import 'package:pj_demo/pages/song_page2.dart';
import 'package:provider/provider.dart';
import '../providers/song_provider.dart';

class AlbumPage extends StatelessWidget {
  final AlbumResponse currentAlbum;
  final int userId = 1; // Example user ID

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
    // Fetch the user's favorite songs only if not fetched
    final favoriteProvider = Provider.of<SongProvider>(context);
    if (favoriteProvider.favoriteSongs.isEmpty) {
      favoriteProvider.fetchFavSongOfUser(userId, context);
    }

    final songProvider = Provider.of<SongProvider>(context);
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
                : _renderListSong(context, songProvider),
      ),
    );
  }

  // Get the favorite icon based on whether the song is in favorites
  Icon getFavoriteIcon(bool isFavorite) {
    return isFavorite
        ? Icon(
            Icons.favorite,
            color: Colors
                .grey, // You might want to change to red to signify it is favorited
            size: 22.0, // Set the icon size to 22 pixels
            semanticLabel: 'Favorite', // Helps with accessibility
          )
        : Icon(
            Icons.favorite_border,
            color: Colors.grey, // Set the icon color to grey
            size: 22.0, // Set the icon size to 22 pixels
            semanticLabel: 'Favorite', // Helps with accessibility
          );
  }

  Widget _renderListSong(BuildContext context, SongProvider songProvider) {
    return ListView.builder(
      itemCount: songProvider.songList.length,
      itemBuilder: (context, index) {
        final song = songProvider.songList[index];
        final isFavorite = songProvider.isFavorite(userId, song.id, context);
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
                backgroundImage: NetworkImage(song.albumImage!),
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
              CircleAvatar(
                backgroundColor: Colors.white,
                child: IconButton(
                  icon: getFavoriteIcon(
                      isFavorite), // Show correct icon based on favorite status
                  onPressed: () {
                    if (isFavorite) {
                      songProvider.removeFavorite(
                          userId, song, context); // Remove from favorites
                    } else {
                      songProvider.addFavorite(
                          userId, song, context); // Add to favorites
                    }
                  },
                ),
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
                  // backgroundColor: Colors.white,
                  child: IconButton(
                onPressed: () {},
                icon: Icon(Icons.drag_indicator_rounded),
              )),
            ],
          ),
        );
      },
    );
  }
}
