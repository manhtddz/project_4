import 'package:flutter/cupertino.dart';
import 'package:pj_demo/dto/keyword_response.dart';

import '../services/keyword_api.dart';

class KeywordProvider with ChangeNotifier {
  final KeywordApi _keywordService = KeywordApi();
  List<KeywordResponse> _keywordList = [];
  bool _isLoading = false;
  String _errorMessage = '';

  bool get isLoading => _isLoading;
  List<KeywordResponse> get keywordList => _keywordList;
  String get errorMessage => _errorMessage;

  Future<void> fetchAllKeyword(BuildContext context) async {
    _isLoading = true;
    _errorMessage = ''; // Reset error message
    notifyListeners();

    try {
      _keywordList = await _keywordService.fetchItems(context);
    } catch (error) {
      _errorMessage = 'Failed to fetch all albums: $error';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }
}