class UrlConsts {
  static String HOST = "localhost:8080";
  static String REGISTER = "/api/register";
  static String LOGIN = "/api/login";
  static String USERBYID = "/api/public/users";
  static String GENRE = "/api/public/users";
  static String CATEGORYALBUMS = "/api/public/categories/withAlbum";
  static final List<String> _WHILTE_LIST_PATHS = [REGISTER, LOGIN];

  static bool isInWhilteList(String path) {
    return _WHILTE_LIST_PATHS.where((it) => it == path).isNotEmpty;
  }
}
