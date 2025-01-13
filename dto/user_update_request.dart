class UpdateUserWithAttribute {
  final int id;
  final String attribute;
  final String value;

  UpdateUserWithAttribute({required this.id, required this.attribute, required this.value});

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'attribute': attribute,
      'value': value,
    };
  }
}