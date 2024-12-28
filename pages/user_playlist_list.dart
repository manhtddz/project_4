import 'package:flutter/material.dart';
import 'package:pj_demo/models/album_provider.dart';
import 'package:pj_demo/models/playlist.dart';
import 'package:pj_demo/models/playlist_provider.dart';
import 'package:pj_demo/models/playlist_response.dart';
import 'package:pj_demo/models/user_provider.dart';
import 'package:pj_demo/pages/album_page.dart';
import 'package:pj_demo/pages/playlist_page.dart';
import 'package:pj_demo/pages/profile_detail.dart';
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

class PlaylistList extends StatelessWidget {
  final int userId = 1; // Static user ID for the example
  final TextEditingController _txtName = TextEditingController();

  @override
  Widget build(BuildContext context) {
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Provider.of<PlaylistProvider>(context, listen: false)
          .fetchPlaylistOfUser(userId);
    });

    return Scaffold(
        appBar: PreferredSize(
          preferredSize: Size.fromHeight(250.0),
          child: Consumer<PlaylistProvider>(
              builder: (context, playlistProvider, child) {
            if (playlistProvider.isLoading) {
              return Center(child: CircularProgressIndicator());
            }
            return AppBar(
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
                            '${playlistProvider.playListList.length} Playlists',
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
            );
          }),
        ),
        body: Stack(
          children: [
            Container(
              // color: Colors.white,
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
              padding: const EdgeInsets.all(10),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  GestureDetector(
                    onTap: () => _showAddDialog('Name', _txtName, context),
                    child: Container(
                        // color: Colors.white,
                        decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius:
                              BorderRadius.circular(5.0), // Set border radius
                          border: Border.all(
                            color: Colors.white, // Set border color
                            width: 1.0, // Set border width
                          ),
                        ),
                        width: 40,
                        height: 40,
                        child: Icon(Icons.add)),
                  ),
                  // ElevatedButton(
                  //   style: ElevatedButton.styleFrom(
                  //     backgroundColor: Colors.white,
                  //   ),
                  //     onPressed: () =>
                  //         _showAddDialog('name', _txtName, context),
                  //     child: Icon(Icons.add)),
                  SizedBox(height: 5),
                  Consumer<PlaylistProvider>(
                      builder: (context, playlistProvider, child) {
                    if (playlistProvider.isLoading) {
                      return Center(child: CircularProgressIndicator());
                    }
                    return Expanded(
                      child: playlistProvider.isLoading
                          ? Center(child: CircularProgressIndicator())
                          : playlistProvider.playListList.isEmpty
                              ? Center(child: Text('No playlist found.'))
                              : _renderListPlaylist(context, playlistProvider),
                    );
                  })
                ],
              ),
            )
          ],
        ));
  }

  Widget _renderListPlaylist(
      BuildContext context, PlaylistProvider playlistProvider) {
    return ListView.builder(
      itemCount: playlistProvider.playListList.length,
      itemBuilder: (context, index) {
        return Card(
          color: Color(0xFFF2F2F2),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(8.0),
          ),
          margin: EdgeInsets.all(3.0),
          child: Padding(
            padding: const EdgeInsets.all(5.0), // Adjust padding as needed
            child: _renderItems(context, playlistProvider.playListList[index]),
          ),
        );
      },
      // separatorBuilder: (context, index) => const Divider(),
    );
  }

  Widget _buildEditableRow(
      String label, TextEditingController txt, int maxLine) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text(
          '$label : ',
          style: TextStyle(fontWeight: FontWeight.bold, color: Colors.black54),
          textAlign: TextAlign.start,
        ),
        Expanded(
          child: TextField(
            controller: txt,
            maxLines: maxLine,
            decoration: InputDecoration(
              border: InputBorder.none,
            ),
          ),
        ),
      ],
    );
  }

  void saveInfo(BuildContext context) {
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => PlaylistList()));
  }

  void _showAddDialog(
      String label, TextEditingController controller, BuildContext context) {
    showDialog(
        context: context,
        builder: (ct) {
          return SizedBox(
              height: 150,
              width: 250, // Adjust the height as needed
              child: AlertDialog(
                title: Text(
                  'Add Playlist',
                  style: TextStyle(
                      fontWeight: FontWeight.bold, color: Colors.black54),
                ),
                content: SingleChildScrollView(
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      _buildEditableRow(label, controller, 1),
                      SizedBox(
                          width: 100,
                          child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                minimumSize: Size(50, 40),
                                backgroundColor: Color(0xFFADDFFF),
                              ),
                              onPressed: () => saveInfo(context),
                              child: Text(
                                'Save',
                                style: TextStyle(color: Colors.black54),
                              )))
                    ],
                  ),
                ),
              ));
        });
  }

  Widget _renderItems(BuildContext context, PlaylistResponse playlist) {
    return ListTile(
        onTap: () => goToPlaylist(context, playlist),
        leading: Container(
          color: Colors.black54,
          width: 50,
          height: 50,
        ),
        title: Text(
          playlist.title,
          textAlign: TextAlign.left,
          style: TextStyle(
            fontSize: 16,
            color: Colors.black54,
            fontWeight: FontWeight.bold,
            fontFamily: 'San Francisco',
          ),
        ),
        subtitle: Text(
          '5 songs',
          textAlign: TextAlign.left,
          style: TextStyle(
            fontSize: 14,
            color: Colors.black54,
            fontWeight: FontWeight.bold,
            fontFamily: 'San Francisco',
          ),
        ));
  }

  void goToPlaylist(BuildContext context, PlaylistResponse playlist) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => PlaylistPage(currentPlaylist: playlist)));
  }
}
