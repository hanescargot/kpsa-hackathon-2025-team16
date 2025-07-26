// features/ocr/ocr_result_screen.dart
import 'package:flutter/material.dart';
import 'package:yaksok/util.dart';
import '../../models/ocr_model.dart';

class OcrResultScreen extends StatelessWidget {
  final OcrResult result;

  const OcrResultScreen({super.key, required this.result});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('약 봉투 AI 분석 결과', style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold),), backgroundColor: kColorPrimary,),
      body: Padding(

        padding: const EdgeInsets.all(16),
        child: ListView(
          children: [
            Text('📍 약국: ${result.pharmacyName}'),
            Text('📅 처방일자: ${result.prescribedDate}'),
            Text('👨‍⚕️ 의사: ${result.doctorName}'),
            Text('👤 환자: ${result.patientName}'),
            Text('🏠 주소: ${result.address}'),
            const SizedBox(height: 16),
            const Text('💊 처방약 목록', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            ...result.medicines.map((m) => Card(
              child: Padding(
                padding: const EdgeInsets.all(8),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text('약명: ${m.name}', style: const TextStyle(fontWeight: FontWeight.bold)),
                    Text('복용법: ${m.usage}'),
                    Text('효능: ${m.effect}'),
                    Text('주의사항: ${m.caution}'),
                  ],
                ),
              ),
            )),
          ],
        ),
      ),
    );
  }
}
