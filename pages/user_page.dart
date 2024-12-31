import 'package:flutter/material.dart';
import 'package:pj_demo/pages/favourite_page.dart';
import 'package:pj_demo/pages/playlist_page.dart';
import 'package:pj_demo/pages/profile_detail.dart';
import 'package:pj_demo/pages/user_playlist_list.dart';
import 'package:provider/provider.dart';

import '../models/user.dart';
import '../providers/user_provider.dart';

class Profile extends StatelessWidget {
  const Profile({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: const BoxDecoration(
          gradient: LinearGradient(
            colors: [Colors.black, Color.fromARGB(255, 219, 4, 76)],
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
          ),
        ),
        child: FutureBuilder<User?>(
          // Fetch user data
          future: Provider.of<UserProvider>(context, listen: false)
              .fetchCurrentUser(),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else if (snapshot.hasError) {
              return const Center(
                child: Text(
                  'An error occurred while fetching user data',
                  style: TextStyle(color: Colors.white),
                ),
              );
            } else if (!snapshot.hasData || snapshot.data == null) {
              return const Center(
                child: Text(
                  'No user data available',
                  style: TextStyle(color: Colors.white),
                ),
              );
            } else {
              final user = snapshot.data!;

              return ListView(
                children: [
                  ListTile(
                    leading: CircleAvatar(
                      backgroundImage: user.image.isNotEmpty
                          ? AssetImage(user.image)
                          : const NetworkImage(
                                  'https://via.placeholder.com/150')
                              as ImageProvider,
                      child: user.image.isEmpty
                          ? const Icon(Icons.person, size: 40)
                          : null,
                    ),
                    title: Text(
                      user.fullname,
                      style: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 30,
                        color: Colors.white,
                      ),
                    ),
                    subtitle: const Text(
                      'View Profile',
                      style: TextStyle(color: Colors.blue, fontSize: 14),
                    ),
                    trailing: const Icon(
                      Icons.arrow_forward_ios,
                      color: Colors.white,
                    ),
                    onTap: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => ProfilePage()));
                    },
                  ),
                  const Divider(),
                  _buildSettingItem(context, 'Favourite', () {
                    // Navigate to Playlist Page
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => FavouritePage()),
                    );
                  }),
                  _buildSettingItem(context, 'Playlist', () {
                    // Navigate to Playlist Page
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => PlaylistList()),
                    );
                  }),
                  _buildSettingItem(context, 'Your albums'),
                  _buildSettingItem(context, 'Change Theme'),
                  _buildSettingItem(context, 'Change Password'),
                  // _buildSettingItem(context, 'Sign Out', () async {
                  //   await Provider.of<UserProvider>(context, listen: false).signOut();
                  //   Navigator.pushReplacement(
                  //     context,
                  //     MaterialPageRoute(builder: (context) => const LoginPage()),
                  //   );
                  // }),
                ],
              );
            }
          },
        ),
      ),
    );
  }

  // Build individual setting items
  Widget _buildSettingItem(BuildContext context, String title,
      [Function? onTap]) {
    return ListTile(
      title: Text(
        title,
        style: const TextStyle(
          color: Colors.white,
          fontWeight: FontWeight.bold,
          fontSize: 30,
        ),
      ),
      trailing: const Icon(
        Icons.arrow_forward_ios,
        color: Colors.white,
      ),
      onTap: () {
        if (onTap != null) {
          onTap();
        }
      },
    );
  }
}
