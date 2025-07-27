import 'package:flutter/material.dart';
import 'package:yaksok/util.dart';

class MedicationDetailScreen extends StatelessWidget {
  final String date;
  final String dayOfWeek;
  final int dayCount;

  const MedicationDetailScreen({
    super.key,
    required this.date,
    required this.dayOfWeek,
    required this.dayCount,
  });

  @override
  Widget build(BuildContext context) {
    const primaryColor = Color(0xFF246BFD);

    return Scaffold(
      backgroundColor: const Color(0xFFE1EEFF),
      body: SafeArea(

        child: Column(
          children: [
            // 🔙 뒤로가기 버튼
            Padding(
              padding: const EdgeInsets.only(left: 8, top: 8),
              child: Row(
                children: [
                  IconButton(
                    icon: const Icon(Icons.arrow_back, color: Colors.black),
                    onPressed: () => Navigator.of(context).pop(),
                  ),
                ],
              ),
            ),
            // 상단 헤더
            Padding(
              padding: const EdgeInsets.all(16),
              child: Row(
                children: [
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Text.rich(
                          TextSpan(
                            children: [
                              TextSpan(
                                text: '최순복',
                                style: TextStyle(
                                  fontWeight: FontWeight.bold,
                                  color: primaryColor,
                                ),
                              ),
                              TextSpan(text: '님  주간 복약일지'),
                            ],
                          ),
                          style: TextStyle(fontSize: 16),
                        ),
                        const SizedBox(height: 4),
                        const Text(
                          '처방 2025.07.04.',
                          style: TextStyle(fontSize: 12),
                        ),
                        const SizedBox(height: 8),
                        Text.rich(
                          TextSpan(
                            text: '복약 ',
                            children: [
                              TextSpan(
                                text: '$dayCount일차',
                                style: const TextStyle(
                                  color: Colors.black,
                                  fontSize: 22,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            ],
                          ),
                          style: const TextStyle(fontSize: 20),
                        ),
                        const SizedBox(height: 8),
                        Row(
                          children: [
                            Chip(
                              label: Text(
                                '고혈압약',
                                style: TextStyle(color: Colors.white),
                              ),
                              backgroundColor: kColorPrimary,
                            ),
                            SizedBox(width: 8),
                            Chip(
                              label: Text(
                                '당뇨병약',
                                style: TextStyle(color: Colors.white),
                              ),
                              backgroundColor: kColorPrimary,
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                  // IconButton(
                  //   icon: const Icon(Icons.settings, color: Colors.black),
                  //   onPressed: () {},
                  // ),
                ],
              ),
            ),

            // 날짜 바
            Container(
              padding: const EdgeInsets.symmetric(vertical: 12),
              // color: primaryColor,
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  Icon(Icons.chevron_left, color: kColorPrimary),
                  Text(
                    '7월 2째주',
                    style: TextStyle(
                      color: kColorPrimary,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Icon(Icons.chevron_right, color: kColorPrimary),
                ],
              ),
            ),

            // 요일
            Container(
              color: Colors.white,
              padding: const EdgeInsets.symmetric(vertical: 6),
              child: Row(
                children: List.generate(7, (i) {
                  const days = ['일', '월', '화', '수', '목', '금', '토'];
                  final isToday = days[i] == dayOfWeek;
                  return Expanded(
                    child: Column(
                      children: [
                        Text(
                          days[i],
                          style: TextStyle(
                            color: isToday ? primaryColor : Colors.black,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 6),
                        if (isToday)
                          Container(
                            width: 24,
                            height: 24,
                            decoration: const BoxDecoration(
                              color: primaryColor,
                              shape: BoxShape.circle,
                            ),
                            alignment: Alignment.center,
                            child: Text(
                              '$dayCount',
                              style: const TextStyle(
                                color: Colors.white,
                                fontSize: 12,
                              ),
                            ),
                          ),
                      ],
                    ),
                  );
                }),
              ),
            ),

            const SizedBox(height: 8),

            // 아침 복약 카드
            _TimeSlotCard(
              time: '아침 8:00',
              missed: true,
              pills: ['히트코나졸정', '피디정', '동구록시트로마이신', '동구록시트로마이신'],
            ),

            const SizedBox(height: 12),

            // 저녁 복약 카드
            _TimeSlotCard(
              time: '저녁 19:00',
              missed: true,
              pills: ['로아큐탄', '피디정'],
            ),
          ],
        ),
      ),
    );
  }
}

class _TimeSlotCard extends StatelessWidget {
  final String time;
  final bool missed;
  final List<String> pills;

  const _TimeSlotCard({
    required this.time,
    required this.missed,
    required this.pills,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.symmetric(horizontal: 16),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        border: Border.all(color: missed ? Colors.red.shade100 : Colors.blue),
        borderRadius: BorderRadius.circular(12),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // 시간 + 버튼
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                time,
                style: const TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 16,
                ),
              ),
              Row(
                children: [
                  OutlinedButton(
                    onPressed: () {},
                    style: OutlinedButton.styleFrom(
                      side: BorderSide(color: Colors.red),
                      foregroundColor: Colors.red,
                    ),
                    child: const Text('미복용'),
                  ),
                  const SizedBox(width: 8),
                  ElevatedButton(
                    onPressed: () {},
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.blue,
                      foregroundColor: Colors.white,
                    ),
                    child: const Text('복용완료'),
                  ),
                ],
              ),
            ],
          ),

          const SizedBox(height: 12),

          // 약 리스트
          Wrap(
            spacing: 8,
            runSpacing: 8,
            children: pills
                .map(
                  (pill) => Chip(
                    label: Text(pill),
                    backgroundColor: Colors.blue.shade50,
                    labelStyle: const TextStyle(color: Colors.black),
                  ),
                )
                .toList(),
          ),
        ],
      ),
    );
  }
}
