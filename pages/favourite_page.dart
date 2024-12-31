import 'package:flutter/material.dart';
import 'package:pj_demo/pages/song_page2.dart';
import 'package:provider/provider.dart';
import '../providers/song_provider.dart';
import '../providers/user_favorites_provider.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (context) => UserFavoritesProvider(),
        ),
        ChangeNotifierProvider(
          create: (context) => SongProvider(),
        ),
      ],
      child: MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: FavouritePage(), // Your main screen widget
    );
  }
}

class FavouritePage extends StatelessWidget {
  final int userId = 1; // Static user ID for the example

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
    // Fetch providers for UserFavorites and Song
    final userFavoritesProvider = Provider.of<UserFavoritesProvider>(context);
    final songProvider = Provider.of<SongProvider>(context);

    return Scaffold(
        appBar: PreferredSize(
          preferredSize: Size.fromHeight(250.0),
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
                    padding: EdgeInsets.only(left: 16, bottom: 50),
                    child: Text(
                      "Thu Thuy's Favourites",
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 26,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                ),
                Align(
                  alignment: Alignment.bottomLeft,
                  child: Container(
                    padding: EdgeInsets.only(left: 16, bottom: 10),
                    child: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        CircleAvatar(
                          backgroundImage:
                              AssetImage('assets/images/avatar.png'),
                        ),
                        SizedBox(width: 10),
                        Text(
                          'Thu Thuy',
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 16,
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
        body: Consumer<UserFavoritesProvider>(
            builder: (context, userFavoritesProvider, child) {
          // Fetch user favorites when the page is first created
          if (userFavoritesProvider.favoriteSongs.isEmpty &&
              !userFavoritesProvider.isLoading) {
            userFavoritesProvider.fetchUserFavorites(userId);
          }
          return Container(
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
            child: userFavoritesProvider.isLoading
                ? Center(child: CircularProgressIndicator())
                : userFavoritesProvider.favoriteSongs.isEmpty
                    ? Center(child: Text("No favorite songs found."))
                    : _renderListSong(context, songProvider, userFavoritesProvider)
          );
        }));
  }
  
  Widget _renderListSong(BuildContext context, SongProvider songProvider, UserFavoritesProvider userFavoritesProvider) {
    return ListView.builder(
      itemCount: userFavoritesProvider.favoriteSongs.length,
      itemBuilder: (context, index) {
        final song =
        userFavoritesProvider.favoriteSongs[index];
        final isPlaying = songProvider.currentSongIndex == index && songProvider.isPlaying;
        return ListTile(
          onTap: () => goToSong(context, index),
          leading: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              SizedBox(width: 2),
              Text('${song.id}',
                  style: TextStyle(
                      fontSize: 16, color: Colors.black54)),
              SizedBox(width: 5),
              CircleAvatar(
                backgroundImage:
                NetworkImage(song.albumImagePath),
              ),
            ],
          ),
          title: Text(
            song.title,
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: Colors.black54,
              fontFamily: 'San Francisco',
            ),
          ),
          subtitle: Text(
            '${song.artistName}   ${song.albumTitle}',
            style: TextStyle(
                color: Colors.black54,
                fontFamily: 'San Francisco'),
          ),
          trailing: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              CircleAvatar(
                backgroundColor: Colors.white,
                child: IconButton(
                  icon: Icon(
                    userFavoritesProvider.isFavorite(song.id)
                        ? Icons.favorite
                        : Icons.favorite_border,
                    color: userFavoritesProvider
                        .isFavorite(song.id)
                        ? Colors.red
                        : Colors.grey,
                  ),
                  onPressed: () {
                    // Toggle favorite status
                    if (userFavoritesProvider
                        .isFavorite(song.id)) {
                      userFavoritesProvider
                          .removeFavorite(song);
                    } else {
                      userFavoritesProvider.addFavorite(song);
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
            ],
          ),
        );
      },
    );
  }
}
