import 'dart:async';

import 'package:pj_demo/models/user.dart';

class UserApi {
  final List<User> _users = [
    User(
      id: '1',
      fullname: 'JVKE',
      username: 'jvke',
      image: 'assets/images/6.jpg',
      password: 'password123',
      phone: '1234567890',
      email: 'johndoe@example.com',
      role: 'artist',
      bio: 'An artist who loves to create music.',
      dob: DateTime(2000, 5, 10),
      // artistId: '1',
    ),
    User(
      id: '2',
      fullname: 'Jane Smith',
      username: 'janesmith',
      image: 'https://example.com/images/jane.jpg',
      password: 'password123',
      phone: '9876543210',
      email: 'janesmith@example.com',
      role: 'normal',
      bio: 'A music enthusiast who loves listening to new tunes.',
      dob: DateTime(1995, 8, 22),
      // artistId: null,
    ),
    User(
      id: '3',
      fullname: 'Emily Johnson',
      username: 'emilyj',
      image: 'https://example.com/images/emily.jpg',
      password: 'password123',
      phone: '1231231234',
      email: 'emilyj@example.com',
      role: 'artist',
      bio: 'Singer and songwriter. Exploring the beauty of melodies.',
      dob: DateTime(1992, 3, 18),
      // artistId: '2',
    ),
  ];

  User? _currentUser;

  // Fetch all users
  List<User> getAllUsers() {
    return _users;
  }

  // Fetch all artists
  List<User> getArtists() {
    return _users.where((user) => user.role == 'artist').toList();
  }

  // Find user by ID
  User? findUserById(String id) {
    try {
      return _users.firstWhere((user) => user.id == id);
    } catch (_) {
      return null;
    }
  }

  // Simulate fetching the current user
 Future<User?> fetchCurrentUser() async {
    await Future.delayed(const Duration(seconds: 1)); // Simulate delay
    if (_currentUser != null) {
      return _currentUser; // Return the current user if logged in
    }
    return null; // Return null if no user is logged in
  }

  // Simulate login functionality
  Future<bool> login(String username, String password) async {
    await Future.delayed(const Duration(seconds: 1)); // Simulate delay
    try {
      final user = _users.firstWhere(
        (user) => user.username == username && user.password == password,
        orElse: () => throw Exception("Invalid username or password"),
      );
      _currentUser = user; // Set current user
      return true; // Login success
    } catch (_) {
      return false; // Login failed
    }
  }
  
   void logout() {
    _currentUser = null; // Set current user to null
  }
}
