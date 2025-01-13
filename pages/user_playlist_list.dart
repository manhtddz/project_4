import 'package:flutter/material.dart';
import 'package:pj_demo/components/common_appbar.dart';
import 'package:pj_demo/providers/album_provider.dart';
import 'package:pj_demo/dto/playlist_response.dart';
import 'package:pj_demo/providers/song_provider.dart';
import 'package:pj_demo/providers/user_favorites_provider.dart';
// import 'package:pj_demo/themes/theme_provider.dart';
import 'package:provider/provider.dart';
import '../providers/playlist_provider.dart';
import '../services/urlConsts.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => AlbumProvider()),
        ChangeNotifierProvider(create: (context) => PlaylistProvider()),
        ChangeNotifierProvider(create: (context) => SongProvider()),
        ChangeNotifierProvider(create: (context) => UserFavoritesProvider()),
      ],
      child: MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(primarySwatch: Colors.blue),
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
          .fetchPlaylistOfUser(userId, context);
    });

    return Scaffold(
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(250.0),
        child: Consumer<PlaylistProvider>(builder: (context, playlistProvider, child) {
          return CommonAppBar(
            label1: "Thu Thuy's Playlists",
            label2: '${playlistProvider.playListList.length} Playlists',
            albumId: 1,
            appBarImg: UrlConsts.FAVORITE_IMAGES,
            isUserFav: true,
          );
        }),
      ),
      body: Stack(
        children: [
          Container(
            decoration: BoxDecoration(
              gradient: LinearGradient(
                begin: Alignment.topLeft,
                end: Alignment.bottomRight,
                colors: [Color(0xFFADDFFF), Color(0xFFFDD7E4)],
              ),
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                GestureDetector(
                  onTap: () => _showAddDialog('Name', _txtName, context),
                  child: Container(
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(15.0),
                      border: Border.all(
                        color: Colors.white,
                        width: 1.0,
                      ),
                    ),
                    width: 30,
                    height: 30,
                    child: Icon(Icons.add),
                  ),
                ),
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
                  },
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _renderListPlaylist(BuildContext context, PlaylistProvider playlistProvider) {
    return ListView.builder(
      itemCount: playlistProvider.playListList.length,
      itemBuilder: (context, index) {
        return Dismissible(
          key: Key(playlistProvider.playListList[index].id.toString()),
          direction: DismissDirection.startToEnd,
          onDismissed: (direction) async {
            // Call delete function if dismissed
            await playlistProvider.deletePlaylist(playlistProvider.playListList[index].id);
          },
          confirmDismiss: (direction) async {
            // Show dialog for confirming deletion
            return _showDeleteDialog(context, playlistProvider.playListList[index].id, playlistProvider);
          },
          background: Container(
            color: Colors.red,
            child: Icon(
              Icons.delete,
              color: Colors.white,
              size: 40.0,
            ),
            alignment: Alignment.centerLeft,
            padding: EdgeInsets.only(left: 20),
          ),
          child: Card(
            color: Color(0xFFE6E6FA),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8.0),
            ),
            margin: EdgeInsets.all(3.0),
            child: Padding(
              padding: const EdgeInsets.all(5.0),
              child: _renderItems(context, playlistProvider.playListList[index], playlistProvider),
            ),
          ),
        );
      },
    );
  }

  Widget _renderItems(BuildContext context, PlaylistResponse playlist, PlaylistProvider playlistProvider) {
    return ListTile(
      onTap: () {},
      leading: Container(
        width: 50,
        height: 50,
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/no_image.png'),
            fit: BoxFit.cover,
          ),
        ),
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
        '${playlist.songQty} songs',
        textAlign: TextAlign.left,
        style: TextStyle(
          fontSize: 14,
          color: Colors.black54,
          fontWeight: FontWeight.bold,
          fontFamily: 'San Francisco',
        ),
      ),
    );
  }

  Future<bool> _showDeleteDialog(
      BuildContext context, int playlistId, PlaylistProvider playlistProvider) async {
    bool confirmDelete = false;
    await showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Xác nhận xóa'),
          content: Text('Bạn có thật sự muốn xóa playlist này?'),
          actions: [
            TextButton(
              onPressed: () {
                confirmDelete = false;
                Navigator.pop(context);
              },
              child: Text('No'),
            ),
            TextButton(
              onPressed: () async {
                await playlistProvider.deletePlaylist(playlistId);
                confirmDelete = true;
                Navigator.pop(context);
              },
              child: Text('Yes'),
            ),
          ],
        );
      },
    );
    return confirmDelete;
  }

  void _showAddDialog(String label, TextEditingController controller, BuildContext context) {
    showDialog(
      context: context,
      builder: (ct) {
        return Consumer<PlaylistProvider>(
          builder: (context, playlistProvider, child) {
            return SizedBox(
              height: 150,
              width: 250,
              child: AlertDialog(
                title: Text('Add Playlist'),
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
                          onPressed: () {
                            String playlistName = controller.text;
                            playlistProvider.addNewPlaylist(userId, playlistName, context);
                            controller.clear();
                            Navigator.pop(context);
                          },
                          child: Text(
                            'Save',
                            style: TextStyle(color: Colors.black54),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            );
          },
        );
      },
    );
  }

  Widget _buildEditableRow(String label, TextEditingController txt, int maxLine) {
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

  // void goToPlaylist(BuildContext context, PlaylistResponse playlist) {
  //   Navigator.push(
  //       context,
  //       MaterialPageRoute(
  //           builder: (context) => PlaylistPage(currentPlaylist: playlist)));
  // }
}
