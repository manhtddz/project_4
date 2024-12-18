class Keyword {
  final int id;
  final String word;

  Keyword({required this.id, required this.word});

  factory Keyword.fromMap(Map<String,dynamic> map) {
    return Keyword(id: map['id'], word: map['word']);
  }

  Map<String, dynamic> toMap() {
    return {
      'id' : id,
      'word' : word
    };
  }
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