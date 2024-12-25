import 'package:flutter/material.dart';
import 'package:pj_demo/models/user.dart';
import 'package:pj_demo/models/user_api.dart';

class UserProvider with ChangeNotifier {
  final UserApi _userApi = UserApi();

  User? _currentUser;
  List<User> _users = [];
  List<User> _artists = [];
  bool _isLoading = false;
  String _errorMessage = '';

  bool get isLoading => _isLoading;
  User? get currentUser => _currentUser;
  List<User> get users => _users;
  String get errorMessage => _errorMessage;
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
  Future<void> fetchUserInfo(int userId) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      final result = await UserApi().findUserById(userId);

      _currentUser = result;
    } catch (error) {
      _errorMessage = 'Failed to fetch favorite albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // void updateAvatar(String newImageUrl) {
  //   if (_currentUser != null) {
  //     _currentUser = _currentUser!.copyWith(image: newImageUrl);
  //     notifyListeners();
  //   }
  // }

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
