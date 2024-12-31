import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../themes/theme_provider.dart';
class NeuBox  extends StatelessWidget{
final Widget? child;
const NeuBox({
  super.key,
  required this.child,
});
  @override
  Widget build(BuildContext context) {
    bool isDarkMode = Provider.of<ThemeProvider>(context).isDarkMode;
    // TODO: implement build
  return Container(
    decoration: BoxDecoration(
      color: Theme.of(context).colorScheme.background,
      borderRadius: BorderRadius.circular(12),
      boxShadow: [
        BoxShadow(
          color: isDarkMode ? Colors.black :  Colors.grey.shade500,
          blurRadius: 15,
          offset: const Offset(4, 4),
        ),

         BoxShadow(
          color:isDarkMode ? Colors.grey.shade800 : Colors.white,
          blurRadius: 15,
          offset: const Offset(4, 4),
        )

      ]
    ),
    padding: EdgeInsets.all(17),
    child: child,
  );
  }

}