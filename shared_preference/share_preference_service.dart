import 'package:shared_preferences/shared_preferences.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import '../models/user.dart';

class SharedPreferencesService {
  Future<void> saveToken(String token) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('token', token);
  }

  Future<bool> isTokenValid() async {
    final token = await getToken(); // Lấy token từ storage
    if (token == null || token.isEmpty) {
      print("Token không tồn tại hoặc rỗng.");
      return false; // Không có token
    }

    bool isExpired = JwtDecoder.isExpired(token);
    print("Kiểm tra token: ${isExpired ? "Hết hạn" : "Hợp lệ"}");
    return !isExpired; // Trả về true nếu token hợp lệ
  }

  Future<String?> getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString('token');
  }

  Future<void> clear() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.clear();
  }

  Future<void> saveUserToPreferences(User user) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt('id', user.id!);
    await prefs.setString('username', user.username!);
    await prefs.setString('email', user.email!);
    await prefs.setString('phone', user.phone!);
    await prefs.setString(
        'avatar', user.avatar ?? ''); // Store avatar if available
  }

  Future<void> saveUserId(int userId) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt('id', userId); // Save user ID to preferences
  }

  // Retrieve the user ID from SharedPreferences
  Future<int?> getUserId() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getInt('id');
  }

  Future<User?> getUserFromPreferences() async {
    final prefs = await SharedPreferences.getInstance();

    // Lấy thông tin người dùng từ SharedPreferences
    final int? id = prefs.getInt('id');
    final String? username = prefs.getString('username');
    final String? email = prefs.getString('email');
    final String? phone = prefs.getString('phone');
    final String? avatar = prefs.getString('avatar');

    if (id != null && username != null && email != null && phone != null) {
      // Tạo đối tượng User từ dữ liệu lấy được
      return User(
        id: id,
        username: username,
        email: email,
        phone: phone,
        avatar: avatar, // Avatar có thể là null
      );
    }

    // Trả về null nếu không có thông tin người dùng trong SharedPreferences
    return null;
  }
  // Add other methods as needed for user data or other preferences
}
