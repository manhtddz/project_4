class User {
  final int id;
  final String fullname;
  final String username;
  final String image;
  final String password;
  final String phone;
  final String email;
  final String role; // 'artist' or 'normal'
  final String bio;
  final DateTime dob;
  // final String? artistId; // Nullable, only for artists

  User({
    required this.id,
    required this.fullname,
    required this.username,
    required this.image,
    required this.password,
    required this.phone,
    required this.email,
    required this.role,
    required this.bio,
    required this.dob,
  });
  factory User.fromMap(Map<String, dynamic> map) {
    return User(
      id: map['id'],
      fullname: map['fullname'],
      username: map['username'],
      image: map['image'],
      password: map['password'],
      phone: map['phone'],
      email: map['email'],
      role: map['role'],
      bio: map['bio'],
      dob: DateTime.parse(map['dob']),
      // artistId: map['artist_id'],
    );
  }

  // Convert User to a Map
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'fullname': fullname,
      'username': username,
      'image': image,
      'password': password,
      'phone': phone,
      'email': email,
      'role': role,
      'bio': bio,
      'dob': dob.toIso8601String(),
      // 'artist_id': artistId,
    };
  }
}
