import 'package:flutter/material.dart';
import '../../models/genre.dart';

import 'general_widget/background_gradiant.dart'; // Assuming you want the gradient background here too

class GenrePage extends StatelessWidget {
  final Genre genre;

  const GenrePage({Key? key, required this.genre}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GradientBackground(
      child: Scaffold(
        backgroundColor: Colors.transparent,
        appBar: AppBar(
          title: Text(
            genre.title,
            style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 24),
          ),
          centerTitle: true,
          backgroundColor: Colors.transparent,
          elevation: 0,
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              // Genre Image (if available)
              if (genre.image.isNotEmpty)
                Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Image.asset(
                    genre.image,
                    height: 150,
                    fit: BoxFit.cover,
                  ),
                ),
              // Genre Title
              Text(
                'Welcome to the ${genre.title} genre!',
                style: const TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 20),
              // Additional information or actions
              const Text(
                'Explore playlists, songs, and more!',
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.white70,
                ),
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 30),
              ElevatedButton(
                onPressed: () {
                  // Navigate back to the genre list
                  Navigator.pop(context);
                },
                style: ElevatedButton.styleFrom(
                  padding:
                      const EdgeInsets.symmetric(vertical: 10, horizontal: 20),
                  backgroundColor: genre.color,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(15),
                  ),
                ),
                child: const Text(
                  'Go Back',
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
