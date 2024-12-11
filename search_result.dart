import 'package:flutter/material.dart';
import 'package:pj_demo/components/neu_box.dart';

void main() {
  runApp(MaterialApp(
      debugShowCheckedModeBanner: false,
      home: SearchResult(
        txtSearch: '',
      )));
}

class Album {
  String name;
  String artistName;
  String imgUrl;

  Album({required this.name, required this.artistName, required this.imgUrl});
}

class SearchResult extends StatelessWidget {
  var _txtSearch = TextEditingController();
  final String txtSearch;
  SearchResult({super.key, required this.txtSearch});
  List<Album> _albumList = [
    Album(name: 'Son Tung 1', artistName: 'ST', imgUrl: 'assets/images/6.webp'),
    Album(name: 'Son Tung 2', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'Son Tung 3', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'Son Tung 2', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'Son Tung 2', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'Son Tung 2', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'Son Tung 2', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
    Album(name: 'Son Tung 2', artistName: 'ST', imgUrl: 'assets/images/8.jpg'),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Container(
            // Gradient container
            decoration: BoxDecoration(
              gradient: LinearGradient(
                begin: Alignment.topLeft,
                end: Alignment.bottomRight,
                colors: [
                  Color(0xFFADDFFF),
                  Color(0xFFFDD7E4),
                ],
              ),
            ),
          ),
          Padding(
            // Padding on top of the gradient
            padding: const EdgeInsets.only(
              top: 50.0,
              left: 25.0,
              right: 25.0,
              bottom: 50.0,
            ),
            child: Column(
              // Your content goes here
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  children: [
                    IconButton(
                        onPressed: () {
                          Navigator.pop(context);
                        },
                        icon: Icon(Icons.arrow_back)),
                    SizedBox(
                      width: 10,
                    ),
                    SizedBox(
                      width: 280, // Adjust the width as needed
                      height: 40, // Adjust the height as needed
                      child: TextField(
                        controller: _txtSearch,
                        decoration: InputDecoration(
                          filled: true, // Sets the background to be filled
                          fillColor: Color(0xFF87AFC7),
                          hintStyle: TextStyle(color: Colors.white),
                          hintText: 'Son Tung',
                          prefixIcon: Icon(
                            Icons.search,
                            color: Colors.white,
                            size: 30,
                          ),
                          enabledBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide: BorderSide(
                              color: Color(0xFF87AFC7), // Enabled border color
                              width: 1.5,
                            ),
                          ),
                          focusedBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide:
                                BorderSide(color: Colors.blue, width: 2.0),
                          ),
                        ),
                        // onChanged: ,
                      ),
                    ),
                  ],
                ),
                SizedBox(
                  height: 30,
                ),
                SizedBox(
                  height: 650,
                  child: GridView.builder(
                    itemCount: _albumList.length,
                    gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: 2,
                      mainAxisSpacing: 0,
                      crossAxisSpacing: 15,
                      childAspectRatio: 1,
                    ),
                    itemBuilder: (context, index) {
                      return _renderItems(context, _albumList[index]);
                    },
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

void _handleSearch(BuildContext context, String val) {
  Navigator.push(context,
      MaterialPageRoute(builder: (context) => SearchResult(txtSearch: val)));
}

Widget _renderItems(BuildContext context, Album album) {
  return Column(
    crossAxisAlignment: CrossAxisAlignment.start,
    children: [
      GestureDetector(
        onTap: () => {},
        child: Container(
          width: double.infinity,
          height: 100,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(10),
            color: Colors.white,
            boxShadow: [
              BoxShadow(
                color: Colors.white.withOpacity(0.5),
                spreadRadius: 5,
                blurRadius: 7,
                offset: Offset(0, 3), // changes position of shadow
              ),
            ],
          ),
          // child: Image.asset(
          //   album.imgUrl,
          //   fit: BoxFit.cover,
          // ),
          child: Container(
              width: 90,
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage(album.imgUrl),
                  fit: BoxFit.cover,
                ),
                borderRadius: BorderRadius.circular(10),
              )),
        ),
      ),
      SizedBox(
        height: 5,
      ),
      Text(
        album.name,
        textAlign: TextAlign.left,
        style: TextStyle(
            fontSize: 18, color: Colors.black54, fontWeight: FontWeight.bold, fontFamily: 'San Francisco'),
      ),
    ],
  );
}
