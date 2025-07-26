// models/ocr_result.dart
class Medicine {
  final String name;
  final String usage;
  final String effect;
  final String caution;

  Medicine({
    required this.name,
    required this.usage,
    required this.effect,
    required this.caution,
  });

  factory Medicine.fromJson(Map<String, dynamic> json) => Medicine(
    name: json['name'],
    usage: json['usage'],
    effect: json['effect'],
    caution: json['caution'],
  );
}

class OcrResult {
  final String pharmacyName;
  final String prescribedDate;
  final String doctorName;
  final String patientName;
  final String address;
  final List<Medicine> medicines;

  OcrResult({
    required this.pharmacyName,
    required this.prescribedDate,
    required this.doctorName,
    required this.patientName,
    required this.address,
    required this.medicines,
  });

  factory OcrResult.fromJson(Map<String, dynamic> json) => OcrResult(
    pharmacyName: json['pharmacyName'],
    prescribedDate: json['prescribedDate'],
    doctorName: json['doctorName'],
    patientName: json['patientName'],
    address: json['address'],
    medicines:
    (json['medicines'] as List).map((e) => Medicine.fromJson(e)).toList(),
  );
}
