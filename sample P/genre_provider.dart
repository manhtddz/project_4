import 'package:flutter/material.dart';
import '../models/genre.dart';
import '../services/api/genre_api.dart';

class ItemProvider with ChangeNotifier {
  final GenreApi apiService;
  List<Genre> _items = [];
  bool _isLoading = false;
  String? _errorMessage;

  // Getter methods to access the state
  List<Genre> get items => _items;
  bool get isLoading => _isLoading;
  String? get errorMessage => _errorMessage;

  // Constructor for injecting the GenreApi service
  ItemProvider(this.apiService);

  // Method to load the list of items (genres)
  Future<void> loadItems() async {
    _isLoading = true;
    _errorMessage = null; // Clear any previous error message
    notifyListeners();

    try {
      _items = await apiService.fetchItems();
    } catch (e) {
      _errorMessage = 'Failed to load genres: $e'; // Error message for UI feedback
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  // Method to get a single genre by its ID
  Future<Genre?> getItemById(int id) async {
    try {
      return await apiService.fetchItemById(id);
    } catch (e) {
      _errorMessage = 'Failed to load genre with ID $id: $e'; // Error message for UI feedback
      notifyListeners();
      return null; // Return null in case of an error
    }
  }
}
