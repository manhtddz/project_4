import 'package:flutter/material.dart';
import 'package:pj_demo/models/keyword.dart';
import 'package:pj_demo/pages/search_result.dart';
import 'package:provider/provider.dart';

import '../models/album_provider.dart';
import '../models/song_provider.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => AlbumProvider()),
        ChangeNotifierProvider(
          create: (context) => SongProvider(),
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
  var _service = KeywordService();
  List<Keyword> _keywordList = [];
  var _txtSearch = TextEditingController();

  @override
  Widget build(BuildContext context) {
    _keywordList = _service.findAllKeywords();

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
                  hintText: 'Nhập từ khóa tìm kiếm',
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
              Expanded(
                  child: ListView.builder(
                      itemCount: _keywordList.length,
                      itemBuilder: (context, idx) {
                        final word = _keywordList[idx];
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
                                        _handleSearchByDefaultKeyword(
                                            context, word.word),
                                    child: Text(
                                      word.word,
                                      style: TextStyle(color: Colors.white),
                                    )),
                              ],
                            )
                          ],
                        );
                      })),
            ],
          ),
        ),
      ),
    );
  }
}

void _handleSearchByDefaultKeyword(BuildContext context, String val) {
  Navigator.push(context,
      MaterialPageRoute(builder: (context) => SearchResult(txtSearch: val)));
}

void _handleSearch(BuildContext context, String val) {
  Navigator.push(context,
      MaterialPageRoute(builder: (context) => SearchResult(txtSearch: val)));
}
