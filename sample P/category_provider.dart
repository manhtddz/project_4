import 'package:flutter/material.dart';
import 'package:musicapp/dto/response/category_response.dart';

import 'package:musicapp/services/api/category_api.dart';



class CategoryProvider with ChangeNotifier {
  final CategoryApi _categoryService = CategoryApi();
  List<CategoryResponse>? _categories;
  bool _isLoading = false;

  bool get isLoading => _isLoading;
  List<CategoryResponse>? get categories => _categories;

  Future<void> loadCategories() async {
    _isLoading = true;
    notifyListeners();
    _categories = await _categoryService.fetchCategories();
    _isLoading = false;
    notifyListeners();
  }
}
