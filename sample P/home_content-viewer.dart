import 'package:flutter/material.dart';
import 'package:musicapp/dto/response/category_response.dart';
import '../models/categories.dart';
import '../services/api/category_api.dart';
import 'home_widget/category_widget.dart';
import 'home_widget/genre_button.dart';  // Import GenreButton

class HomeContent extends StatelessWidget {
  final CategoryApi categoryApi = CategoryApi();

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
        child: Column(
          children: [
            // Add GenreButton at the top of the screen
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: GenreButton(), // Genre buttons widget
            ),
            // FutureBuilder for categories
            Expanded(
              child: FutureBuilder<List<CategoryResponse>>(
                future: categoryApi.fetchCategories(), // Fetch categories from API
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return const Center(child: CircularProgressIndicator());
                  } else if (snapshot.hasError) {
                    return Center(child: Text('Error: ${snapshot.error}'));
                  } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                    return const Center(child: Text('No categories found'));
                  }

                  final categories = snapshot.data!;
                  return CategoryList(categories: categories ); // Display the category list
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
