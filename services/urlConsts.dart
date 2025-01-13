class UrlConsts {
  static String HOST = "localhost:8080";
  static String BASEURL = "/api/public";
  static String REGISTER = "/api/register";
  static String LOGIN = "/api/login";
  static String USERBYID = "/api/public/users";
  static String GENRE = "/api/public/users";
  static String CATEGORYALBUMS = "/api/public/categories/withAlbum";
  static String ALBUMS = "/api/public/albums";
  static String KEYWORDS = "/api/public/keywords";
  static String PLAYLISTS = "/api/public/playlists";
  static String SONGS = "/api/public/songs";
  static String FAVOURITE_SONGS = "/api/public/favourite-songs";
  static String FAVOURITE_ALBUMS = "/api/public/favourite-albums";
  static String FAVORITE_IMAGES = "assets/images/favourite.png";
  static String USERS = "/api/public/users";

  static final List<String> _WHILTE_LIST_PATHS = [REGISTER, LOGIN];

  static bool isInWhilteList(String path) {
    return _WHILTE_LIST_PATHS.where((it) => it == path).isNotEmpty;
  }
}
