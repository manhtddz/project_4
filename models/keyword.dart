import 'package:flutter/cupertino.dart';
import 'package:provider/provider.dart';
import 'dart:convert';  // For json.decode
import 'package:http/http.dart' as http;  // For http.get

class Keyword {
  final int id;
  final String content;

  Keyword({required this.id, required this.content});

  factory Keyword.fromJson(Map<String,dynamic> json) {
    return Keyword(id: json['id'], content: json['content']);
  }

  // Map<String, dynamic> toJson() {
  //   return {
  //     'id' : id,
  //     'content' : content
  //   };
  // }
}

class KeywordApi {
  final String baseUrl = "http://localhost:8080/api/public/keywords";
  Future<List<Keyword>> fetchItems() async {
    final response = await http.get(Uri.parse(baseUrl));

    // Check if the response is successful
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((json) => Keyword.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load genres');
    }
  }
}

class KeywordProvider with ChangeNotifier {
  List<Keyword> _keywordList = [];
  bool _isLoading = false;
  String _errorMessage = '';

  bool get isLoading => _isLoading;
  List<Keyword> get keywordList => _keywordList;
  String get errorMessage => _errorMessage;

  Future<void> fetchAllKeyword() async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _keywordList = await KeywordApi().fetchItems();
    } catch (error) {
      _errorMessage = 'Failed to fetch all albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }
}