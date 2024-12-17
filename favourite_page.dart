import 'package:flutter/material.dart';
import 'package:pj_demo/models/playlist_provider.dart';
import 'package:pj_demo/models/user_provider.dart';
import 'package:pj_demo/pages/song_page2.dart';
import 'package:provider/provider.dart';
import '../models/song_provider.dart';
import '../models/song.dart';
import '../themes/theme_provider.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        // ChangeNotifierProvider(create: (context) => AlbumProvider()),
        ChangeNotifierProvider(
          create: (context) => SongProvider(),
        ),
        ChangeNotifierProvider(
          create: (context) => PlaylistProvider(),
        ),
        ChangeNotifierProvider(
          create: (context) => UserProvider(),
        ),
      ],
      child: MainApp(),
    ),
  );
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: FavouritePage(),
      // theme: Provider.of<ThemeProvider>(context)
      //     .themeData, // Define this or replace with actual theme logic
    );
  }
}

class FavouritePage extends StatefulWidget {
  // final Album currentAlbum;
  // AlbumPage({required this.currentAlbum});

  @override
  State<FavouritePage> createState() => _FavouritePageState();
}

class _FavouritePageState extends State<FavouritePage> {
  late final dynamic songProvider;
  late final dynamic userProvider;
  List<Song> _favorites = [];

  @override
  void initState() {
    super.initState();
    songProvider = Provider.of<SongProvider>(context, listen: false);
    userProvider = Provider.of<UserProvider>(context, listen: false);
  }

  void goToSong(int songIndex) {
    songProvider.currentSongIndex = songIndex;

    Navigator.push(
        context, MaterialPageRoute(builder: (context) => SongPage2()));
  }

  void removeFavourite(Song so) {
    setState(() {
      _favorites.remove(so);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: PreferredSize(
          preferredSize: Size.fromHeight(250.0),
          // child: Consumer<UserProvider>(builder: (context, value, child) {
          //   var currentUser = value.currentUser;
          //   return AppBar(
          child: AppBar(
            flexibleSpace: Stack(
              children: [
                Align(
                  alignment: Alignment.bottomCenter,
                  child: Container(
                    decoration: BoxDecoration(
                      image: DecorationImage(
                        image: AssetImage('assets/images/favourite.png'),
                        fit: BoxFit.cover,
                        opacity: 0.7,
                      ),
                    ),
                  ),
                ),
                Align(
                  alignment: Alignment.bottomLeft,
                  child: Container(
                    padding: EdgeInsets.only(
                        left: 16, bottom: 50), // Adjust padding as needed
                    child: Text(
                      // "${currentUser!.username}'s Favourites",
                      "Thu Thuy's Favourites",
                      style: TextStyle(
                          color: Colors.white, // Or any desired color
                          fontSize: 26,
                          fontWeight:
                              FontWeight.bold // Adjust font size as needed
                          ),
                    ),
                  ),
                ),
                Align(
                  alignment: Alignment.bottomLeft,
                  child: Container(
                    padding: EdgeInsets.only(
                        left: 16,
                        bottom: 10,
                    ), // Adjust padding as needed
                    child: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        CircleAvatar(
                          backgroundImage: NetworkImage('assets/images/avatar.png'),
                        ), // Image.asset('${currentUser.image}'),
                        SizedBox(width: 10,),
                        Text(
                          // '${currentUser.username}',
                          'Thu Thuy',
                          style: TextStyle(
                            color: Colors.white, // Or any desired color
                            fontSize: 16, // Adjust font size as needed
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
            // );
            // }),
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
                          onPressed: () => renderAddToFavoriteButton(playlist[index]),
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
      onPressed: () => removeFavourite(so),
    );
  }
}
