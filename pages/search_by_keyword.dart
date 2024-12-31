import 'package:flutter/material.dart';
import 'package:pj_demo/models/keyword.dart';
import 'package:pj_demo/pages/search_result.dart';
import 'package:pj_demo/themes/theme_provider.dart';
import 'package:provider/provider.dart';

import '../providers/album_provider.dart';
import '../providers/song_provider.dart';
import '../providers/user_favorites_provider.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => AlbumProvider()),
        ChangeNotifierProvider(create: (context) => KeywordProvider()),
        ChangeNotifierProvider(
          create: (context) => SongProvider(),
        ),
        ChangeNotifierProvider(
          create: (context) => ThemeProvider(),
        ),
        ChangeNotifierProvider(
          create: (context) => UserFavoritesProvider(),
        ),
      ],
      child: const MainApp(),
    ),
  );
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: SearchScreen(),
      // theme: Provider.of<ThemeProvider>(context)
      //     .themeData, // Define this or replace with actual theme logic
    );
  }
}

class SearchScreen extends StatelessWidget {
  final String userName = 'kana'; // Replace with actual user name
  var _txtSearch = TextEditingController();

  @override
  Widget build(BuildContext context) {
    WidgetsBinding.instance.addPostFrameCallback((_) {
      Provider.of<KeywordProvider>(context, listen: false)
          .fetchAllKeyword();
    });
    return Scaffold(
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
        child: Padding(
          padding: const EdgeInsets.only(
              top: 50.0, left: 20.0, right: 20.0, bottom: 50.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Chào buổi sáng,\n${userName}',
                style: TextStyle(
                    fontSize: 24.0,
                    color: Colors.black54,
                    fontWeight: FontWeight.bold),
              ),
              SizedBox(height: 16.0),
              TextField(
                controller: _txtSearch,
                decoration: InputDecoration(
                  filled: true, // Sets the background to be filled
                  fillColor: Color(0xFF87AFC7),
                  hintStyle: TextStyle(color: Colors.white),
                  hintText: 'Nhập từ khóa tìm kiếm album',
                  prefixIcon: Icon(
                    Icons.search,
                    color: Colors.white,
                    size: 30,
                  ),
                  enabledBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(30.0),
                    borderSide: BorderSide(
                      color: Color(0xFF87AFC7), // Enabled border color
                      width: 1.5,
                    ),
                  ),
                  focusedBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(30.0),
                    borderSide: BorderSide(color: Colors.blue, width: 2.0),
                  ),
                ),
                onSubmitted: (val) => _handleSearch(context, _txtSearch.text),
              ),
              SizedBox(height: 20.0),
              Text('Từ khóa phổ biến',
                  style: TextStyle(
                    fontSize: 20.0,
                    color: Colors.black54,
                  )),
              Consumer<KeywordProvider>(
                builder: (context, keywordProvider, child) {
                  if (keywordProvider.isLoading) {
                    return Center(child: CircularProgressIndicator());
                  } else if (keywordProvider.keywordList.isEmpty) {
                    return Center(child: Text('No items found.'));
                  } else {
                    return _renderKeyword(context, keywordProvider);
                  }
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}

Widget _renderKeyword (BuildContext context, KeywordProvider keywordProvider) {
  return Expanded(
      child: ListView.builder(
          itemCount: keywordProvider.keywordList.length,
          itemBuilder: (context, idx) {
            final word = keywordProvider.keywordList[idx];
            return Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                SizedBox(
                  height: 10,
                ),
                Row(
                  children: [
                    SizedBox(
                      height: 10,
                    ),
                    ElevatedButton(
                        style: ElevatedButton.styleFrom(
                            backgroundColor: Color(0xFF14A3C7)),
                        onPressed: () =>
                            _handleSearch(
                                context, word.content),
                        child: Text(
                          word.content,
                          style: TextStyle(color: Colors.white),
                        )),
                  ],
                )
              ],
            );
          }));
}

void _handleSearch(BuildContext context, String val) {
  Navigator.push(context,
      MaterialPageRoute(builder: (context) => SearchResult(txtSearch: val)));
}