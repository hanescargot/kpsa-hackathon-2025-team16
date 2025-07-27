import 'dart:async';
import 'package:flutter/material.dart';

/// 상단(제목 + 알림시간) 위젯이 가로 공간을 넘기면
/// 자동으로 작아지도록 FittedBox(BoxFit.scaleDown)를 씌웠습니다.
/// (외부 패키지 없이 해결)
class TodayMedicationInfo extends StatelessWidget {
  const TodayMedicationInfo({super.key});

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          _buildMedicationRow(
            title: "아침",
            time: "오전 9:00",
            medicines: ["히트코나졸정", "피디정", "동구록시트로마이신", "모사피아정"],
            isChecked: true,
          ),
          const SizedBox(height: 16),
          _buildMedicationRow(
            title: "점심",
            time: "",
            medicines: const [],
            isChecked: false,
          ),
          const SizedBox(height: 16),
          _buildMedicationRow(
            title: "저녁",
            time: "오후 7:00",
            medicines: const ["히트코나졸정", "피디정", "동구록시트로마이신", "모사피아정", "로이탄연질캡슐"],
            isChecked: false,
          ),
        ],
      ),
    );
  }

  Widget _buildMedicationRow({
    required String title,
    required String time,
    required List<String> medicines,
    required bool isChecked,
  }) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        // 아이콘 + 제목/시간 + 메모 카드
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // ✅ 가로가 모자라면 폰트가 자동으로 줄어들도록 처리
              Column(
                children: [
                  Row(
                    children: [
                      const Icon(Icons.medication, size: 24),
                      const SizedBox(width: 4),
                      Text(
                        title,
                        style: const TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ],
                  ),
                  if (time.isNotEmpty) ...[
                    Row(
                      children: [
                        const Icon(Icons.alarm, size: 18, color: Colors.red),
                        const SizedBox(width: 4),
                        Text(time, style: const TextStyle(fontSize: 14)),
                      ],
                    ),
                  ],
                ],
              ),
              const SizedBox(height: 8),
              Container(
                padding: const EdgeInsets.all(12),
                width: 180, // 필요시 MediaQuery 로 계산해서 조정 가능
                decoration: BoxDecoration(
                  color: Colors.grey[100],
                  borderRadius: BorderRadius.circular(8),
                ),
                child: medicines.isEmpty
                    ? const SizedBox(height: 40)
                    : Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: medicines
                            .map(
                              (m) =>
                                  Text(m, style: const TextStyle(fontSize: 14)),
                            )
                            .toList(),
                      ),
              ),
            ],
          ),
        ),

        const SizedBox(width: 12),

        // 체크박스
        Container(
          margin: const EdgeInsets.only(top: 8),
          width: 24,
          height: 24,
          decoration: BoxDecoration(
            border: Border.all(color: Colors.black, width: 2),
            borderRadius: BorderRadius.circular(4),
          ),
          child: isChecked
              ? const Icon(Icons.check, color: Colors.red,)
              : null,
        ),
      ],
    );
  }
}
