import 'package:flutter/material.dart';
import 'package:pj_demo/pages/login_page.dart';
import 'package:pj_demo/pages/profile_detail_page.dart';
import 'package:pj_demo/pages/search_by_keyword_page.dart';
import 'package:pj_demo/providers/album_provider.dart';
import 'package:pj_demo/providers/keyword_provider.dart';
import 'package:pj_demo/providers/playlist_provider.dart';
import 'package:pj_demo/providers/user_favorites_provider.dart';
import 'package:pj_demo/themes/theme_provider.dart';
import 'package:provider/provider.dart';
import 'providers/song_provider.dart';
import 'providers/user_provider.dart';
import 'shared_preference/share_preference_service.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (context) => ThemeProvider(),
        ),
        ChangeNotifierProvider(
          create: (context) => PlaylistProvider(),
        ),
        ChangeNotifierProvider(create: (context) => UserProvider()),
        // ChangeNotifierProvider(create: (context) => GenreProvider(GenreApi())),
        ChangeNotifierProvider(create: (context) => SongProvider()),
        ChangeNotifierProvider(create: (context) => KeywordProvider()),
        ChangeNotifierProvider(create: (context) => AlbumProvider()),
        ChangeNotifierProvider(create: (context) => UserFavoritesProvider()),
        // ChangeNotifierProvider(create: (context) => NewsProvider()),
      ],
      child: const MainApp(),
    ),
  );
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    final SharedPreferencesService prefsService = SharedPreferencesService();

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      // theme: Provider.of<ThemeProvider>(context).themeData,
      home: FutureBuilder<bool>(
        future: prefsService.isTokenValid(), // Kiểm tra token
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            // Hiển thị trạng thái loading
            return const Scaffold(
              body: Center(child: CircularProgressIndicator()),
            );
          } else if (snapshot.hasError) {
            // Hiển thị thông báo lỗi
            return const Scaffold(
              body: Center(child: Text("Lỗi khi tải token.")),
            );
          } else if (snapshot.hasData && snapshot.data == true) {
            // Token hợp lệ -> Điều hướng đến HomePage
            return ProfilePage();
          } else {
            prefsService.clear(); // Xóa token
            return const LoginPage();
          }
        },
      ),
    );
  }
}
