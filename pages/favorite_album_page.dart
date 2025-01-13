import 'package:flutter/material.dart';
import 'package:pj_demo/components/common_appbar.dart';
import 'package:pj_demo/dto/user_favourite_request.dart';
import 'package:pj_demo/pages/song_page2.dart';
import 'package:pj_demo/services/urlConsts.dart';
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
  final int userId = 5; // Static user ID for the example

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
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Provider.of<SongProvider>(context, listen: false)
          .fetchFavSongOfUser(userId, context);
    });

    return Scaffold(
        appBar: PreferredSize(
          preferredSize: Size.fromHeight(250.0),
          child: CommonAppBar(
              label1: "Thu Thuy's Favorites",
              label2: "Thu Thuy",
              appBarImg: UrlConsts.FAVORITE_IMAGES,
              isUserFav: true),
        ),
        body: Consumer<SongProvider>(builder: (context, songProvider, child) {
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
              child: songProvider.isLoading
                  ? Center(child: CircularProgressIndicator())
                  : songProvider.favoriteSongs.isEmpty
                      ? Center(child: Text("No favorite songs found."))
                      : _renderListSong(context, songProvider));
        }));
  }

  Widget _renderListSong(BuildContext context, SongProvider songProvider) {
    return ListView.builder(
      itemCount: songProvider.favoriteSongs.length,
      itemBuilder: (context, index) {
        final song = songProvider.favoriteSongs[index];
        LikeRequest requestData = LikeRequest(userId: userId, itemId: song.id);
        final isPlaying = songProvider.currentSongIndex == index && songProvider.isPlaying;

        return ListTile(
          onTap: () => goToSong(context, index),
          leading: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              SizedBox(width: 2),
              Text('${song.id}', style: TextStyle(fontSize: 16, color: Colors.black54)),
              SizedBox(width: 5),
              CircleAvatar(backgroundImage: NetworkImage(song.albumImage)),
            ],
          ),
          title: Text(
            song.title,
            style: TextStyle(fontWeight: FontWeight.bold, color: Colors.black54, fontFamily: 'San Francisco'),
          ),
          subtitle: Text(
            '${song.artistName}   ${song.albumTitle}',
            style: TextStyle(color: Colors.black54, fontFamily: 'San Francisco'),
          ),
          trailing: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              Consumer<UserFavoritesProvider>(
                builder: (context, favProvider, child) {
                  // If the song's favorite status is not cached, fetch it
                  if (!favProvider.isFavouriteSong(requestData)) {
                    favProvider.fetchFavoriteSongStatus(requestData, context); // Fetch data asynchronously once
                  }

                  return CircleAvatar(
                    backgroundColor: Colors.white,
                    child: IconButton(
                      icon: Icon(
                        favProvider.isFavouriteSong(requestData)
                            ? Icons.favorite
                            : Icons.favorite_border,
                        color: favProvider.isFavouriteSong(requestData)
                            ? Colors.red
                            : Colors.grey,
                      ),
                      onPressed: () async {
                        // Handle toggling favorite status
                        if (favProvider.isFavouriteSong(requestData)) {
                          // Remove song from favorites
                          await favProvider.removeUserFavoriteSong(requestData, context);
                        } else {
                          // Add song to favorites
                          await favProvider.addUserFavoriteSong(requestData, context);
                        }

                        // Fetch the updated favorite status and notify listeners
                        await favProvider.fetchFavoriteSongStatus(requestData, context);
                        favProvider.notifyListeners();  // This triggers UI rebuild
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
