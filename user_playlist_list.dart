import 'package:flutter/material.dart';
import 'package:pj_demo/models/album_provider.dart';
import 'package:pj_demo/models/playlist.dart';
import 'package:pj_demo/models/playlist_provider.dart';
import 'package:pj_demo/models/user_provider.dart';
import 'package:pj_demo/pages/album_page.dart';
import 'package:pj_demo/pages/playlist_page.dart';
import 'package:provider/provider.dart';
import '../models/album.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (context) => AlbumProvider(),
        ),
        ChangeNotifierProvider(
          create: (context) => PlaylistProvider(),
        ),
        ChangeNotifierProvider(
          create: (context) => UserProvider(),
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
      home: PlaylistList(), // Your main screen widget
    );
  }
}

class PlaylistList extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _PlaylistListState();
}

class _PlaylistListState extends State<PlaylistList> {
  late final dynamic playlistProvider;

  @override
  void initState() {
    super.initState();
    playlistProvider = Provider.of<PlaylistProvider>(context, listen: false);
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
                    "Thu Thuy's Playlists",
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
                        backgroundImage:
                            NetworkImage('assets/images/avatar.png'),
                      ), // Image.asset('${currentUser.image}'),
                      SizedBox(
                        width: 10,
                      ),
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
      body: Consumer<PlaylistProvider>(
        builder: (context, value, child) {
          final playlistList = value.playlistList;
          return Stack(
            children: [
              Container(
                // Gradient container
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
              ),
              Padding(
                // Padding on top of the gradient
                padding: const EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    SizedBox(height: 30),
                    Expanded(
                      child: ListView.separated(
                        itemCount: playlistList.length,
                        itemBuilder: (context, index) {
                          return _renderItems(context, playlistList[index]);
                        },
                        separatorBuilder: (context, index) => const Divider(),
                      ),
                    ),
                  ],
                ),
              )
            ],
          );
        },
      ),
    );
  }

  Widget _renderItems(BuildContext context, Playlist playlist) {
    return ListTile(
        onTap: () => goToPlaylist(playlist),
        leading: Image.asset('assets/images/avatar.png'),
        title: Text(
              playlist.title,
              textAlign: TextAlign.left,
              style: TextStyle(
                fontSize: 18,
                color: Colors.black54,
                fontWeight: FontWeight.bold,
                fontFamily: 'San Francisco',
              ),
            ));
  }

  void goToPlaylist(Playlist playlist) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => PlaylistPage(currentPlaylist: playlist)));
  }
}
