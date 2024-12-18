import 'package:flutter/material.dart';
import 'package:pj_demo/models/user.dart';
import 'package:provider/provider.dart';
import '../models/user_provider.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (context) => UserProvider(),
        ),
      ],
      child: MainApp(),
    ),
  );
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: ProfilePage(),
      // theme: Provider.of<ThemeProvider>(context)
      //     .themeData, // Define this or replace with actual theme logic
    );
  }
}

class ProfilePage extends StatefulWidget {
  @override
  State<ProfilePage> createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  late final dynamic userProvider;

  var _txtBio = TextEditingController();
  var _txtPhone = TextEditingController();
  var _txtBirthday = TextEditingController();
  var _txtEmail = TextEditingController();
  var _txtFullname = TextEditingController();

  @override
  void initState() {
    super.initState();
    userProvider = Provider.of<UserProvider>(context, listen: false);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: PreferredSize(
            preferredSize: Size.fromHeight(250.0),
            // child: Consumer<UserProvider>(builder: (context, value, child) {
            //   var currentUser = value.currentUser;
            //   return AppBar(
            child: AppBar(
                flexibleSpace: Stack(children: [
              Align(
                alignment: Alignment.bottomCenter,
                child: Container(
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage('assets/images/profile.png'),
                      fit: BoxFit.cover,
                      opacity: 0.6,
                    ),
                  ),
                ),
              ),
              Align(
                alignment: Alignment.bottomCenter,
                child: Container(
                  padding: EdgeInsets.only(
                    bottom: 100,
                  ), // Adjust padding as needed
                  child: CircleAvatar(
                    radius: 40,
                    backgroundImage: NetworkImage('assets/images/avatar.png'),
                  ), // Image.asset('${currentUser.image}'),
                ),
              ),
              Align(
                alignment: Alignment.topCenter,
                child: Container(
                  padding: EdgeInsets.only(top: 20), // Adjust padding as needed
                  child: Text(
                    // "${currentUser!.username}'s Favourites",
                    "Personal Information",
                    style: TextStyle(
                        color: Colors.black54, // Or any desired color
                        fontSize: 24,
                        fontWeight:
                            FontWeight.bold // Adjust font size as needed
                        ),
                  ),
                ),
              ),
              Align(
                alignment: Alignment.bottomCenter,
                child: Container(
                  padding:
                      EdgeInsets.only(bottom: 40), // Adjust padding as needed
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                        backgroundColor: Color(0xFFF2F2F2)),
                    onPressed: () => {},
                    child: Text(
                      // "${currentUser!.username}'s Favourites",
                      "Change avatar",
                      style: TextStyle(
                        color: Colors.black54, // Or any desired color
                        fontSize: 16,
                      ),
                    ),
                  ),
                ),
              ),
            ]))),
        body: Container(
          color: Colors.white,
          child: Padding(
            padding: const EdgeInsets.all(10.0),
            // child: Consumer<UserProvider>(
            //     builder: (context, value, child) {
            //       User? user = value.currentUser;
            //       return
            child: SizedBox(
    height: 600,
    child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // Profile Picture and Name
                SizedBox(height: 10),
                // Personal Information
                _buildSectionTitle('About you'),
                SizedBox(height: 8),
                _renderAboutField(),
                SizedBox(height: 24),
                // Account Information
                _buildSectionTitle('Account Information'),
                SizedBox(height: 8),
                _renderInfoField(),
              ],
            ),
          ),
        )));
  }

  void _showEditDialog(
      String label, TextEditingController controller, BuildContext context) {
    showDialog(
        context: context,
        builder: (ct) {
          return SizedBox(
              height: 150,
              width: 250, // Adjust the height as needed
              child: AlertDialog(
                title: Text(
                  'Edit Info',
                  style: TextStyle(fontWeight: FontWeight.bold, color: Colors.black54),
                ),
                content: SingleChildScrollView(
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      _buildEditableRow(label, controller, 1),
                      SizedBox(
                          width: 100,
                          child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                minimumSize: Size(50, 40),
                                backgroundColor: Color(0xFFADDFFF),
                              ),
                              onPressed: () => saveInfo(context),
                              child: Text(
                                'Save',
                                style: TextStyle(color: Colors.black54),
                              )))
                    ],
                  ),
                ),
              ));
        });
  }

  void _showEditDialogTextBox(
      String label, TextEditingController controller, BuildContext context) {
    showDialog(
        context: context,
        builder: (ct) {
          return SizedBox(
            height: 150, // Adjust the height as needed
            child: AlertDialog(
              title: Text(
                'Edit Info',
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
              content: SingleChildScrollView(
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    _buildEditableRow(label, controller, 10),
                    SizedBox(
                      width: 100,
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          minimumSize: Size(50, 40),
                          backgroundColor: Color(0xFFADDFFF),
                        ),
                        onPressed: () => saveInfo(context),
                        child: Text(
                          'Save',
                          style: TextStyle(color: Colors.black54),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          );
        });
  }

  Widget _buildSectionTitle(String title) {
    return Padding(
        padding: EdgeInsets.only(left: 20),
        child: Text(
          title,
          style: TextStyle(fontSize: 14, color: Colors.black54),
        ));
  }

  Widget _renderAboutField() {
    return Card(
      color: Color(0xFFF2F2F2),
      child: Column(
        children: [
          _buildInfoRow('Full Name', 'Thu Thuy', _txtFullname),
          _buildInfoTextbox('Bio', 'Bio content', _txtBio),
          _buildInfoRow('Birthday', '01/01/1970', _txtBirthday),
          _buildDropdownRow('Gender', ['Male', 'Female', 'Other'], 'Other'),
        ],
      ),
    );
  }

  Widget _renderInfoField() {
    return Card(
      color: Color(0xFFF2F2F2),
      child: Column(
        children: [
          _buildInfoRow('Phone Number', '09000001111', _txtPhone),
          _buildInfoRow('Email', 'thuy@gmail.com', _txtEmail),
        ],
      ),
    );
  }

  Widget _buildInfoRow(String label, String value, TextEditingController txt) {
    return Padding(
        padding: const EdgeInsets.symmetric(vertical: 6.0, horizontal: 10.0),
        child:
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
          Text('$label:',
              style: TextStyle(
                  fontWeight: FontWeight.bold, color: Colors.black54)),
          Row(
            children: [
              Text(
                value,
                style: TextStyle(color: Colors.black54),
              ),
              IconButton(
                onPressed: () => _showEditDialog(label, txt, context),
                icon: Icon(Icons.arrow_forward),
                color: Colors.blue,
              )
            ],
          )
        ]));
  }

  Widget _buildInfoTextbox(
      String label, String value, TextEditingController txt) {
    return Padding(
        padding: const EdgeInsets.symmetric(vertical: 6.0, horizontal: 10.0),
        child:
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
          Text('$label:',
              style: TextStyle(
                  fontWeight: FontWeight.bold, color: Colors.black54)),
          Row(
            children: [
              Text(
                value,
                style: TextStyle(color: Colors.black54),
              ),
              IconButton(
                onPressed: () => _showEditDialogTextBox(label, txt, context),
                icon: Icon(Icons.arrow_forward),
                color: Colors.blue,
              )
            ],
          )
        ]));
  }

  Widget _buildEditableRow(
      String label, TextEditingController txt, int maxLine) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text(
          '$label : ',
          style: TextStyle(fontWeight: FontWeight.bold, color: Colors.black54),
          textAlign: TextAlign.start,
        ),
        Expanded(
          child: TextField(
            controller: txt,
            maxLines: maxLine,
            decoration: InputDecoration(
              border: InputBorder.none,
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildDropdownRow(
      String label, List<String> options, String initialValue) {
    return Padding(
        padding: const EdgeInsets.symmetric(vertical: 6.0, horizontal: 10.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text('$label:',
                style: TextStyle(
                    fontWeight: FontWeight.bold, color: Colors.black54)),
            DropdownButton<String>(
              value: initialValue,
              items: options
                  .map((option) =>
                      DropdownMenuItem(value: option, child: Text(option)))
                  .toList(),
              onChanged: (value) {
                // Handle gender selection
              },
            ),
          ],
        ));
  }

  void saveInfo(BuildContext context) {
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => ProfilePage()));
  }
}
