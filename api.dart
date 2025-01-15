import 'dart:async';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../../pages/login_page.dart';
import '../../shared_preference/share_preference_service.dart';
import 'urlConsts.dart';

class Api {
  final SharedPreferencesService _prefsService = SharedPreferencesService();

  Future<http.Response> post(String path, dynamic request, BuildContext context) async {
    var uri = Uri.http(UrlConsts.HOST, path); // Build the URI for the request

    // Convert request to JSON string
    var body = jsonEncode(request);

    // Assuming _makeHeader is a method that returns necessary headers (like Authorization)
    var headers = await _makeHeader(path);

    try {
      // Send the POST request
      var response = await http.post(
        uri,
        headers: headers,
        body: body,
      );

      if (response.statusCode != 200) {
        throw Exception(
            'Failed to load data. Status code: ${response.statusCode}');
      }

      if (response.statusCode == 401) {
        print("Unauthorized: Token expired");
      }

      _handleSecurityError(response, context);

      return response;
    } catch (e) {
      print("Error in POST request: $e");
      rethrow; // Re-throw the error so the caller can handle it
    }
  }

  // void _handleSecurityError(http.Response response, BuildContext context) {
  //   if (response.statusCode == 401) {
  //     // Optionally clear stored user data
  //     _prefsService.clear();

  //     // Navigate to the Login Page
  //     Navigator.pushReplacement(
  //       context,
  //       MaterialPageRoute(builder: (context) => LoginPage()),
  //     );
  //   }
  // }
  void _handleSecurityError(http.Response response, BuildContext context) {
    if (response.statusCode == 401) {
      print("Phát hiện trạng thái 401 - Token hết hạn");
      print("Body: ${response.body}"); // In nội dung body trả về từ API

      // Hiển thị Dialog thông báo
      showDialog(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return AlertDialog(
            title: const Text("Thông báo"),
            content: const Text(
                "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại."),
            actions: [
              TextButton(
                onPressed: () async {
                  Navigator.of(context).pop(); // Đóng Dialog

                  // Xóa thông tin lưu trữ
                  await _prefsService.clear();

                  // Điều hướng đến trang đăng nhập
                  Navigator.pushReplacement(
                    context,
                    MaterialPageRoute(builder: (context) => const LoginPage()),
                  );
                },
                child: const Text("OK"),
              ),
            ],
          );
        },
      );
    }
  }

  Future<Map<String, String>> _makeHeader(String path) async {
    var map = {
      'Content-Type':
          'application/json', // Set the Content-Type to application/json
    };

    if (!UrlConsts.isInWhilteList(path)) {
      var token = await _prefsService.getToken();
      map['Authorization'] = 'Bearer $token';
    }

    return map;
  }

  Future<http.Response> get(String path, BuildContext context,
      {Map<String, String>? params}) async {
    var headers = await _makeHeader(path);
    var uri = Uri.http(UrlConsts.HOST, path,
        params); // Build the URI with optional query parameters

    // Send the GET request
    var response = await http.get(
      uri,
      headers: headers,
    );
    if (response.statusCode == 401) {
      print("Phát hiện trạng thái 401 - Token hết hạn");
    }
    // Handle security errors or other response status codes
    _handleSecurityError(response, context);

    return response;
  }

  // common get method without Auth. Comment out later.
  Future<http.Response> getNoAuth(String path, BuildContext context,
      {Map<String, String>? params}) async {
    var uri = Uri.http(UrlConsts.HOST, path,
        params); // Build the URI with optional query parameters

    // Send the GET request
    var response = await http.get(
      uri,
      // headers: headers,
    );
    if (response.statusCode == 401) {
      print("Phát hiện trạng thái 401 - Token hết hạn");
    }
    // Handle security errors or other response status codes
    _handleSecurityError(response, context);

    return response;
  }

  Future<http.Response> put(String path, dynamic request, BuildContext context) async {
    var uri = Uri.http(UrlConsts.HOST, path); // Build the URI for the request

    // Convert request to JSON string
    var body = jsonEncode(request);

    // Assuming _makeHeader is a method that returns necessary headers (like Authorization)
    var headers = await _makeHeader(path);

    try {
      // Send the POST request
      var response = await http.put(
        uri,
        headers: headers,
        body: body,
      );

      if (response.statusCode != 200) {
        throw Exception(
            'Failed to load data. Status code: ${response.statusCode}');
      }

      if (response.statusCode == 401) {
        print("Unauthorized: Token expired");
      }

      _handleSecurityError(response, context);

      return response;
    } catch (e) {
      print("Error in POST request: $e");
      rethrow; // Re-throw the error so the caller can handle it
    }
  }

  // common put method without Auth. Comment out later.
  Future<http.Response> putNoAuth(String path, Map<String, dynamic> body, BuildContext context, {Map<String, String>? params}) async {
    try {
      // Build the URI with optional query parameters
      var uri = Uri.http(UrlConsts.HOST, path, params);

      // Prepare the request body (convert the body map to JSON)
      String jsonBody = jsonEncode(body);

      // Send the PUT request
      var response = await http.put(
        uri,
        headers: {
          'Content-Type': 'application/json', // Ensure the content type is JSON
          // Add other headers if necessary (e.g., authorization tokens)
        },
        body: jsonBody,
      );

      // Handle response status and errors
      if (response.statusCode == 401) {
        print("Phát hiện trạng thái 401 - Token hết hạn");
      }

      // Handle security errors or other response status codes
      _handleSecurityError(response, context);

      return response; // Return the response object
    } catch (e) {
      print('Error in PUT request: $e');
      rethrow; // You can handle the error or throw it again to be caught elsewhere
    }
  }


  Future<http.Response> getSearch(String path, BuildContext context,
      {Map<String, String>? params}) async {
    var headers = await _makeHeader(path);
    var uri = Uri.http(UrlConsts.HOST, path,
        params); // Build the URI with optional query parameters

    // Send the GET request
    var response = await http.get(
      uri,
      headers: headers,
    );
    if (response.statusCode == 401) {
      print("Phát hiện trạng thái 401 - Token hết hạn");
    }
    // Handle security errors or other response status codes

    return response;
  }

  Future<http.Response> delete(String path, BuildContext context,
      {Map<String, dynamic>? params}) async {
    var headers = await _makeHeader(path); // Get necessary headers, including authorization
    var uri = Uri.http(UrlConsts.HOST, path); // API endpoint

    try {
      var response = await http.delete(
        uri,
        headers: headers,
        body: params != null ? jsonEncode(params) : null, // Send the params as JSON
      );

      // Log for debugging
      print("DELETE Request to: $uri");
      print("Response Status: ${response.statusCode}");
      print("Response Body: ${response.body}");

      if (response.statusCode == 401) {
        print("Phát hiện trạng thái 401 - Token hết hạn");
        _handleSecurityError(response, context);
      }

      if (response.statusCode != 200 && response.statusCode != 204) {
        throw Exception(
            'Failed to delete data. Status code: ${response.statusCode}, Response body: ${response.body}');
      }

      return response;
    } catch (e) {
      print("Error during DELETE request: $e");
      rethrow; // Re-throw the error to be handled by the caller
    }
  }

}
