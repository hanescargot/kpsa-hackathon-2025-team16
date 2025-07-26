class OcrRequest {
  final String pharmacyName;
  final String prescribedDate;
  final String doctorName;
  final String patientName;
  final String address;
  final List<OcrMedicine> medicines;

  OcrRequest({
    required this.pharmacyName,
    required this.prescribedDate,
    required this.doctorName,
    required this.patientName,
    required this.address,
    required this.medicines,
  });

  Map<String, dynamic> toJson() => {
    'pharmacyName': pharmacyName,
    'prescribedDate': prescribedDate,
    'doctorName': doctorName,
    'patientName': patientName,
    'address': address,
    'medicines': medicines.map((m) => m.toJson()).toList(),
  };
}

class OcrMedicine {
  final String name;
  final String usage;
  final String effect;
  final String caution;

  OcrMedicine({
    required this.name,
    required this.usage,
    required this.effect,
    required this.caution,
  });

  Map<String, dynamic> toJson() => {
    'name': name,
    'usage': usage,
    'effect': effect,
    'caution': caution,
  };
}
