import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(
    debugShowCheckedModeBanner: false,
    home: ProfilePage(),
  ));
}

class ProfilePage extends StatelessWidget {
  var _txtBio = TextEditingController(text: 'Add a bio');
  var _txtAdd = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Profile'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Profile Picture and Name
            Center(
              child: Column(
                children: [
                  Stack(
                    alignment: Alignment.bottomRight,
                    children: [
                      CircleAvatar(
                        radius: 50,
                        backgroundImage: NetworkImage('https://example.com/profile_pic.jpg'), // Replace with actual image URL
                      ),
                      IconButton(
                        icon: Icon(Icons.edit),
                        onPressed: () {
                          // Implement change profile picture functionality
                        },
                      ),
                    ],
                  ),
                  SizedBox(height: 16),
                  Text('Thu Thuy', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
                ],
              ),
            ),
            SizedBox(height: 24),
            // Personal Information
            _buildSectionTitle('Personal Information'),
            SizedBox(height: 8),
            _buildInfoRow('ID', '16650362'),
            _buildEditableRow('Bio', _txtBio),
            _buildInfoRow('Birthday', '01/01/1970'),
            _buildDropdownRow('Gender', ['Male', 'Female', 'Other'], 'Other'),
            SizedBox(height: 24),
            // Account Information
            _buildSectionTitle('Account Information'),
            SizedBox(height: 8),
            _buildEditableRow('Phone Number', _txtAdd),
          ],
        ),
      ),
    );
  }

  Widget _buildSectionTitle(String title) {
    return Text(
      title,
      style: TextStyle(
        fontSize: 18,
        fontWeight: FontWeight.bold,
      ),
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text('$label:', style: TextStyle(fontWeight: FontWeight.bold)),
        Text(value),
      ],
    );
  }

  Widget _buildEditableRow(String label, TextEditingController controller) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text('$label:', style: TextStyle(fontWeight: FontWeight.bold)),
        Expanded(
          child: TextField(
            controller: controller,
            decoration: InputDecoration(
              border: InputBorder.none,
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildDropdownRow(String label, List<String> options, String initialValue) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text('$label:', style: TextStyle(fontWeight: FontWeight.bold)),
        DropdownButton<String>(
          value: initialValue,
          items: options.map((option) => DropdownMenuItem(value: option, child: Text(option))).toList(),
          onChanged: (value) {
            // Handle gender selection
          },
        ),
      ],
    );
  }
}