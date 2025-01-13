import 'package:flutter/material.dart';
import 'package:pj_demo/dto/keyword_response.dart';
import 'package:pj_demo/models/keyword.dart';
import 'package:pj_demo/pages/profile_detail_page.dart';
import 'package:pj_demo/pages/search_result_page.dart';
import 'package:pj_demo/providers/user_provider.dart';
import 'package:pj_demo/themes/theme_provider.dart';
import 'package:provider/provider.dart';

import '../providers/album_provider.dart';
import '../providers/keyword_provider.dart';
import '../providers/song_provider.dart';
import '../providers/user_favorites_provider.dart';

class SearchScreen extends StatelessWidget {
  // final String userName = 'kana';
  var _txtSearch = TextEditingController();

  // Helper method for error state UI
  Widget _buildErrorWidget(Object error) {
    return Center(
      child: Text(
        'Error: $error',
        style: const TextStyle(color: Colors.white),
      ),
    );
  }

  // Helper method for empty state UI
  Widget _buildEmptyWidget() {
    return const Center(
      child: Text(
        'No genres found',
        style: TextStyle(color: Colors.white),
      ),
    );
  }

  // Helper method for loading state UI
  Widget _buildLoadingWidget() {
    return const Center(child: CircularProgressIndicator());
  }

  void _handleSearch(BuildContext context, String val) {
    Navigator.push(context,
        MaterialPageRoute(builder: (context) => SearchResult(txtSearch: val)));
  }

  @override
  Widget build(BuildContext context) {
    //logged in user
    final currentUser = Provider.of<UserProvider>(context).currentUser;
    if (currentUser == null) {
      return const Center(child: Text('User not logged in.'));
    }
    //Fetch keywords
    final keywordProvider =
        Provider.of<KeywordProvider>(context, listen: false);
    WidgetsBinding.instance.addPostFrameCallback((_) {
      keywordProvider.fetchAllKeyword(context);
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
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Text(
            'Chào buổi sáng,\n${currentUser!.username}',
            style: TextStyle(
                fontSize: 24.0,
                color: Colors.black54,
                fontWeight: FontWeight.bold),
          ),
          SizedBox(height: 16.0),
          _buildSearchField(context),
          SizedBox(height: 20.0),
          Text('Từ khóa phổ biến',
              style: TextStyle(
                fontSize: 20.0,
                color: Colors.black54,
              )),
          SizedBox(
            height: 10,
          ),
          _renderKeyword(context, keywordProvider),
        ]),
      ),
    ));
  }

  Widget _buildSearchField(BuildContext context) {
    return TextField(
      controller: _txtSearch,
      decoration: InputDecoration(
        filled: true,
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
            color: Color(0xFF87AFC7),
            width: 1.5,
          ),
        ),
        focusedBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(30.0),
          borderSide: BorderSide(color: Colors.blue, width: 2.0),
        ),
      ),
      onTap: () {
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) =>
                    SearchResult(txtSearch: _txtSearch.text)));
      },
    );
  }

  Widget _renderKeyword(BuildContext context, KeywordProvider keywordProvider) {
    return FutureBuilder<List<KeywordResponse>>(
        future: keywordProvider.fetchAllKeyword(context),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return _buildLoadingWidget();
          } else if (snapshot.hasError) {
            return _buildErrorWidget(snapshot.error!);
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return _buildEmptyWidget();
          } else {
            final items = snapshot.data!;

            return Wrap(
              spacing: 10.0,
              runSpacing: 10.0,
              children: items.map((item) {
                return ElevatedButton(
                    style: ElevatedButton.styleFrom(
                        backgroundColor: Color(0xFF14A3C7)),
                    onPressed: () => _handleSearch(context, item.content),
                    child: Text(
                      item.content,
                      style: TextStyle(color: Colors.white),
                    ));
              }).toList(),
            );
          }
        });
  }
}
