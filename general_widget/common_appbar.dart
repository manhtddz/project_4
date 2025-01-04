import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CommonAppBar extends StatelessWidget {
  final String label1;
  final String label2;
  final String appBarImg;
  final bool isUserFav;
  const CommonAppBar({required this.label1, required this.label2, required this.appBarImg, required this.isUserFav, Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      flexibleSpace: Stack(
        children: [
          Align(
            alignment: Alignment.bottomCenter,
            child: Container(
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage(appBarImg),
                  fit: BoxFit.cover,
                  opacity: 0.7,
                ),
              ),
            ),
          ),
          Align(
            alignment: Alignment.bottomLeft,
            child: Container(
              padding: EdgeInsets.only(
                  left: 16, bottom: 50), // Adjust padding as needed
              child: Text(
                // "${currentUser!.username}'s Favourites",
                "$label1",
                style: TextStyle(
                    color: Colors.white, // Or any desired color
                    fontSize: 26,
                    fontWeight:
                    FontWeight.bold // Adjust font size as needed
                ),
              ),
            ),
          ),
          Align(
            alignment: Alignment.bottomLeft,
            child: Container(
              padding: EdgeInsets.only(
                left: 16,
                bottom: 10,
              ), // Adjust padding as needed
              child: isUserFav == true?
              Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  CircleAvatar(
                    backgroundImage:
                    NetworkImage('assets/images/avatar.png'),
                  ),// Image.asset('${currentUser.image}'),
                  SizedBox(
                    width: 10,
                  ),
                  Text(
                    // '${currentUser.username}',
                    '$label2',
                    style: TextStyle(
                      color: Colors.white, // Or any desired color
                      fontSize: 16, // Adjust font size as needed
                    ),
                  ),
                ],
              ) :
              Text(
                // '${currentUser.username}',
                '$label2',
                style: TextStyle(
                  color: Colors.white, // Or any desired color
                  fontSize: 16, // Adjust font size as needed
                ),
              ),

            ),
          ),
        ],
      ),
    );
  }
}