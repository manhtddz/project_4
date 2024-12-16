import 'package:flutter/material.dart';
import 'package:pj_demo/models/user.dart';
import 'package:pj_demo/models/user_api.dart';

class UserProvider with ChangeNotifier {
  final UserApi _userApi = UserApi();

  User? _currentUser;
  List<User> _users = [];
  List<User> _artists = [];

  User? get currentUser => _currentUser;
  List<User> get users => _users;
  List<User> get artists => _artists;

  // Fetch all users
  void fetchUsers() {
    _users = _userApi.getAllUsers();
    notifyListeners();
  }

  // Fetch all artists
  void fetchArtists() {
    _artists = _userApi.getArtists();
    notifyListeners();
  }

  // Find user by ID
  void findUser(String id) {
    final user = _userApi.findUserById(id);
    if (user != null) {
      _currentUser = user;
      notifyListeners();
    }
  }

  // Fetch current user
  Future<User?> fetchCurrentUser() async {
    _currentUser = await _userApi.fetchCurrentUser();
    notifyListeners();
    return _currentUser;
  }

  // Login user
  Future<bool> login(String username, String password) async {
    final success = await _userApi.login(username, password);
    if (success) {
      // Await the result of fetchCurrentUser() to get the actual user data
      _currentUser = await _userApi.fetchCurrentUser();
      notifyListeners();
    }
    return success;
  }

  Future<void> signOut() async {
    // Implement logic to sign out (e.g., remove user from storage, clear session)
    _currentUser = null;
    notifyListeners();
  }
}
