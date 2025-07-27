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
      backgroundColor: const Color(0xFFF8F9FB),
      appBar: AppBar(
        automaticallyImplyLeading: false, // 뒤로가기 제거
        title: const Text(
          '약 봉투 AI 분석 결과',
          style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
        ),
        backgroundColor: kColorPrimary,
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Expanded(
              child: ListView(
                children: [
                  Text('📍 약국: ${result.pharmacyName}'),
                  Text('📅 처방일자: ${result.prescribedDate}'),
                  Text('👨‍⚕️ 의사: ${result.doctorName}'),
                  Text('👤 환자: ${result.patientName}'),
                  Text('🏠 주소: ${result.address}'),
                  const SizedBox(height: 16),
                  const Text('💊 처방약 목록',
                      style: TextStyle(fontWeight: FontWeight.bold)),
                  const SizedBox(height: 8),
                  ...result.medicines.map(
                        (m) => Card(
                      elevation: 2,
                      child: Padding(
                        padding: const EdgeInsets.all(12),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text('약명: ${m.name}',
                                style: const TextStyle(
                                    fontWeight: FontWeight.bold)),
                            Text('복용법: ${m.usage}'),
                            Text('효능: ${m.effect}'),
                            Text('주의사항: ${m.caution}'),
                          ],
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),

            /// 하단 버튼
            Row(
              children: [
                Expanded(
                  child: OutlinedButton(
                    onPressed: () {
                      Navigator.of(context).pop(); // 취소 시 뒤로가기
                    },
                    style: OutlinedButton.styleFrom(
                      padding: const EdgeInsets.symmetric(vertical: 16),
                      side: BorderSide(color: kColorPrimary, width: 2),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(12),
                      ),
                    ),
                    child: Text(
                      '취소',
                      style: TextStyle(
                          color: kColorPrimary,
                          fontWeight: FontWeight.bold,
                          fontSize: 16),
                    ),
                  ),
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () {
                      // TODO: 적용 시 처리 로직
                      Navigator.of(context).pop(result); // 필요시 result 반환
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: kColorPrimary,
                      padding: const EdgeInsets.symmetric(vertical: 16),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(12),
                      ),
                    ),
                    child: const Text(
                      '스케줄 등록',
                      style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 16,
                          color: Colors.white),
                    ),
                  ),
                ),
              ],
            ),
            SizedBox(height: 48,)
          ],
        ),
      ),
    );
  }
}
