class UserResponse {
  final int id;
  final String username;
  final String fullName;
  final String avatar;
  final String password;
  final String phone;
  final String email;
  final DateTime? dob;

  UserResponse({
    required this.id,
    required this.username,
    required this.fullName,
    required this.avatar,
    required this.password,
    required this.phone,
    required this.email,
    this.dob,
  });

  factory UserResponse.fromJson(Map<String, dynamic> json) {
    return UserResponse(
      id: json['id'],
      username: json['username'],
      fullName: json['fullName'],
      avatar: json['avatar'],
      password: json['password'],
      phone: json['phone'],
      email: json['email'],
      dob: DateTime.parse(json['dob']),
    );
  }
}
