import 'package:flutter/material.dart';
import 'package:pj_demo/models/user.dart';

class UserProvider with ChangeNotifier {
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
      artistId: '1',
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
      artistId: null,
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
      artistId: '2',
    )
  ];
  User? _currentUser;
  User? get currentUser => _currentUser;

  List<User> get users => _users;
  List<User> get artists =>
      _users.where((user) => user.role == 'artist').toList();
  void findUserById(String id) {
    _users.firstWhere((user) => user.id == id);
    notifyListeners();
  }

  Future<bool> login(String username, String password) async {
    await Future.delayed(
        const Duration(seconds: 1)); // Simulate a delay (e.g., network request)

    final user = _users.firstWhere(
      (user) => user.username == username && user.password == password,
      // Return null if no matching user found
    );

    _currentUser = user; // Set the current user if login is successful
    notifyListeners(); // Notify listeners about the change
    return true; // Login success
  }
}
