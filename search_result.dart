import 'package:flutter/material.dart';
import 'package:pj_demo/models/album_provider.dart';
import 'package:pj_demo/pages/album_page.dart';
import 'package:provider/provider.dart';
import '../models/album.dart';

// void main() {
//   runApp(
//     MultiProvider(
//       providers: [
//         ChangeNotifierProvider(
//           create: (context) => AlbumProvider(),
//         ),
//       ],
//       child: MyApp(),
//     ),
//   );
// }
//
// class MyApp extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       theme: ThemeData(
//         primarySwatch: Colors.blue,
//       ),
//       home: SearchResult(txtSearch: ''), // Your main screen widget
//     );
//   }
// }

class SearchResult extends StatefulWidget {
  final String txtSearch;
  SearchResult({super.key, required this.txtSearch});

  @override
  State<StatefulWidget> createState() => _SearchResultState();
}

class _SearchResultState extends State<SearchResult> {
  var _txtSearch = TextEditingController();
  late final dynamic albumProvider;

  @override
  void initState() {
    super.initState();
    albumProvider = Provider.of<AlbumProvider>(context, listen: false);
    albumProvider.searchByKeyWord(widget.txtSearch);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Consumer<AlbumProvider>(
        builder: (context, value, child) {
          final searchedAlbums = value.searchResults;
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
                padding: const EdgeInsets.only(
                  top: 50.0,
                  left: 25.0,
                  right: 25.0,
                  bottom: 50.0,
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      children: [
                        IconButton(
                          onPressed: () {
                            Navigator.pop(context);
                          },
                          icon: Icon(Icons.arrow_back),
                        ),
                        SizedBox(width: 10),
                        SizedBox(
                          width: 280, // Adjust the width as needed
                          height: 40, // Adjust the height as needed
                          child: TextField(
                              controller: _txtSearch,
                              decoration: InputDecoration(
                                filled: true,
                                fillColor: Color(0xFF87AFC7),
                                hintStyle: TextStyle(color: Colors.white),
                                hintText: '${widget.txtSearch}',
                                prefixIcon: Icon(
                                  Icons.search,
                                  color: Colors.white,
                                  size: 30,
                                ),
                                enabledBorder: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(30.0),
                                  borderSide: BorderSide(
                                    color: Color(0xFF87AFC7),
                                    width: 1.5,
                                  ),
                                ),
                                focusedBorder: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(30.0),
                                  borderSide: BorderSide(
                                      color: Colors.blue, width: 2.0),
                                ),
                              ),
                              onChanged: (val) {
                                // Use post-frame callback to avoid calling setState during build
                                WidgetsBinding.instance
                                    .addPostFrameCallback((_) {
                                  albumProvider.searchByKeyWord(val);
                                });
                              }),
                        ),
                      ],
                    ),
                    SizedBox(height: 30),
                    Expanded(
                      child: GridView.builder(
                        itemCount: searchedAlbums.length,
                        gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                          crossAxisCount: 2,
                          mainAxisSpacing: 0,
                          crossAxisSpacing: 15,
                          childAspectRatio: 1,
                        ),
                        itemBuilder: (context, index) {
                          return _renderItems(context, searchedAlbums[index]);
                        },
                      ),
                    ),
                  ],
                ),
              ),
            ],
          );
        },
      ),
    );
  }

  Widget _renderItems(BuildContext context, Album album) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        GestureDetector(
          onTap: () => goToAlbum(album),
          child: Container(
            width: double.infinity,
            height: 100,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(10),
              color: Colors.white,
              boxShadow: [
                BoxShadow(
                  color: Colors.white.withOpacity(0.5),
                  spreadRadius: 5,
                  blurRadius: 7,
                  offset: Offset(0, 3), // changes position of shadow
                ),
              ],
            ),
            child: Container(
              width: 90,
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage(album.imgUrl),
                  fit: BoxFit.cover,
                ),
                borderRadius: BorderRadius.circular(10),
              ),
            ),
          ),
        ),
        SizedBox(height: 5),
        Text(
          album.name,
          textAlign: TextAlign.left,
          style: TextStyle(
            fontSize: 18,
            color: Colors.black54,
            fontWeight: FontWeight.bold,
            fontFamily: 'San Francisco',
          ),
        ),
      ],
    );
  }

  void goToAlbum(Album album) {
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => AlbumPage(currentAlbum: album,)));
  }
}