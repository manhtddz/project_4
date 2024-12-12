class Keyword {
  int id;
  String word;

  Keyword({required this.id, required this.word});
}

class KeywordService {
  var _keywordList = [
    Keyword(id: 1, word: 'Son Tung'),
    Keyword(id: 2, word: 'SooBin'),
    Keyword(id: 3, word: 'Senorita'),
  ];

  List<Keyword> findAllKeywords() {
    return _keywordList;
  }
}
