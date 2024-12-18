import 'package:flutter/material.dart';
import 'package:pj_demo/models/playlist.dart';
import 'package:pj_demo/pages/song_page2.dart';
import 'package:provider/provider.dart';
import '../models/song_provider.dart';
import '../models/song.dart';

class PlaylistPage extends StatefulWidget {
  final Playlist currentPlaylist;
  PlaylistPage({required this.currentPlaylist});

  @override
  State<PlaylistPage> createState() => _PlaylistPageState();
}

class _PlaylistPageState extends State<PlaylistPage> {
  late final dynamic songProvider;
  List<Song> _favorites = [];

  @override
  void initState() {
    super.initState();
    songProvider = Provider.of<SongProvider>(context, listen: false);
  }

  void goToSong(int songIndex) {
    songProvider.currentSongIndex = songIndex;

    Navigator.push(
        context, MaterialPageRoute(builder: (context) => SongPage2()));
  }

  void addToFavorite(Song so) {
    setState(() {
      if (_favorites.contains(so)) {
        _favorites.remove(so);
      } else {
        _favorites.add(so);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
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
          child: Consumer<SongProvider>(
            builder: (context, value, child) {
              List<Song> playlist = value.playlist;
              return ListView.builder(
                itemCount: playlist.length,
                itemBuilder: (context, index) {
                  final song = playlist[index];
                  return ListTile(
                    onTap: () => goToSong(index),
                    leading: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        SizedBox(
                          width: 2,
                        ),
                        Text('${song.id}',
                            style:
                            TextStyle(fontSize: 16, color: Colors.black54)),
                        SizedBox(
                            width:
                            5.0), // Add some spacing between the number and the avatar
                        CircleAvatar(
                          backgroundImage: NetworkImage(song.albumImagePath!),
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
                      song.artistName! + '   ${value.totalDuration}',
                      style: TextStyle(
                          color: Colors.black54, fontFamily: 'San Francisco'),
                    ),
                    trailing: Row(
                      mainAxisSize: MainAxisSize.min,
                      mainAxisAlignment:
                      MainAxisAlignment.end, // Align to the end
                      children: [
                        CircleAvatar(
                          backgroundColor: Colors.white,
                          child: renderAddToFavoriteButton(playlist[index]),
                        ),
                        IconButton(
                          icon: Icon(Icons.more_vert),
                          onPressed: () {
                            // Handle more options
                          },
                        ),
                      ],
                    ),
                  );
                },
              );
            },
          ),
        ));
  }

  Icon getFavoriteIcon(bool isFavorite) {
    return isFavorite
        ? Icon(
      Icons.favorite, color: Colors.grey, // Set the icon color to grey
      size: 22.0, // Set the icon size to 30 pixels
      semanticLabel: 'Favorite',
    )
        : Icon(
      Icons.favorite_border,
      color: Colors.grey, // Set the icon color to grey
      size: 22.0, // Set the icon size to 30 pixels
      semanticLabel: 'Favorite',
    );
  }

  Widget renderAddToFavoriteButton(Song so) {
    return IconButton(
      icon: getFavoriteIcon(_favorites.contains(so)),
      onPressed: () => addToFavorite(so),
    );
  }
}