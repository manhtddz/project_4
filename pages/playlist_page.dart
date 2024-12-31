import 'package:flutter/material.dart';
import 'package:pj_demo/dto/playlist_response.dart';
import 'package:pj_demo/pages/song_page2.dart';
import 'package:provider/provider.dart';
import '../providers/song_provider.dart';
import '../providers/user_favorites_provider.dart';

class PlaylistPage extends StatelessWidget {
  final PlaylistResponse currentPlaylist;
  final int userId = 1; // Example user ID

  PlaylistPage({required this.currentPlaylist});

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
    final favoriteProvider = Provider.of<UserFavoritesProvider>(context);
    if (favoriteProvider.favoriteSongs.isEmpty) {
      favoriteProvider.fetchUserFavorites(userId);
    }

    final songProvider = Provider.of<SongProvider>(context);
    // Fetch the songs of the current album if they are not fetched yet
    if (!songProvider.isLoading && songProvider.songList.isEmpty) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        songProvider.fetchSongOfPlaylist(currentPlaylist.id);
      });
    }

      return Scaffold(
          appBar: AppBar(
            backgroundColor: Color(0xFF314A5EFF),
            leading: IconButton(onPressed: () => {Navigator.pop(context)},
                icon: Icon(Icons.arrow_back)),
            title: Text(
              'Playlist 1',
              // '${widget.currentPlaylist.title}',
              style: TextStyle(
                color: Colors.black, // Or any desired color
                fontSize: 26, // Adjust font size as needed
              ),
            ),
          ),
          // drawer: MyDrawer(),
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
            child:
            // Consumer<SongProvider>(
            //   builder: (context, value, child) {
            //     List<Song> playlist = value.songList;
            //     return ListView.builder(
            //       itemCount: playlist.length,
            //       itemBuilder: (context, index) {
            //         final song = playlist[index];
            //         return ListTile(
            //           onTap: () => goToSong(index),
            //           leading: Row(
            //             mainAxisSize: MainAxisSize.min,
            //             children: [
            //               SizedBox(
            //                 width: 2,
            //               ),
            //               Text('${song.id}',
            //                   style:
            //                   TextStyle(fontSize: 16, color: Colors.black54)),
            //               SizedBox(
            //                   width:
            //                   5.0), // Add some spacing between the number and the avatar
            //               CircleAvatar(
            //                 backgroundImage: NetworkImage(song.albumImagePath!),
            //               ),
            //             ],
            //           ),
            //           title: Text(
            //             song.title,
            //             style: TextStyle(
            //                 fontWeight: FontWeight.bold,
            //                 color: Colors.black54,
            //                 fontFamily: 'San Francisco'),
            //           ),
            //           subtitle: Text(
            //             song.artistName! + '   ${value.totalDuration}',
            //             style: TextStyle(
            //                 color: Colors.black54, fontFamily: 'San Francisco'),
            //           ),
            //           trailing: Row(
            //             mainAxisSize: MainAxisSize.min,
            //             mainAxisAlignment:
            //             MainAxisAlignment.end, // Align to the end
            //             children: [
            //               CircleAvatar(
            //                 backgroundColor: Colors.white,
            //                 child: renderAddToFavoriteButton(playlist[index]),
            //               ),
            //               IconButton(
            //                 icon: Icon(Icons.more_vert),
            //                 onPressed: () {
            //                   // Handle more options
            //                 },
            //               ),
            //             ],
            //           ),
            //         );
            //       },
            //     );
            //   },
            // ),
            songProvider.isLoading
                ? Center(child: CircularProgressIndicator())
                : songProvider.songList.isEmpty
                ? Center(child: Text('No songs found.'))
                : _renderListSong(context, songProvider, favoriteProvider),
          ));
    }

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

  Widget _renderListSong(BuildContext context, SongProvider songProvider,
      UserFavoritesProvider favoriteProvider) {
    return ListView.builder(
      itemCount: songProvider.songList.length,
      itemBuilder: (context, index) {
        final song = songProvider.songList[index];
        final isFavorite = favoriteProvider.isFavorite(song.id);
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
                backgroundImage: NetworkImage(song.albumImagePath),
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
                      favoriteProvider
                          .removeFavorite(song); // Remove from favorites
                    } else {
                      favoriteProvider.addFavorite(song); // Add to favorites
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