import 'package:flutter/cupertino.dart';
import 'dart:convert';  // For json.decode
import 'package:http/http.dart' as http;  // For http.get

class Keyword {
  final int id;
  final String content;

  Keyword({required this.id, required this.content});
}