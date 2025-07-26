import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class CalendarScreen extends StatelessWidget {
  const CalendarScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final today = DateTime.now();
    final formattedDate = DateFormat('M월 d일', 'ko_KR').format(today);

    return Scaffold(
      backgroundColor: const Color(0xFFFF6B6B),
      appBar: AppBar(
        title: const Text('갤린더'),
        leading: IconButton(
          onPressed: () {
            Navigator.of(context).pop();
          },
          icon: const Icon(Icons.home, color: Colors.white),
        ),
      ),
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // 상단 제목
            Padding(
              padding: const EdgeInsets.fromLTRB(16, 16, 16, 8),
              child: Row(
                children: const [
                  Text('약속이', style: TextStyle(fontSize: 18, color: Colors.white)),
                  SizedBox(width: 12),
                  Icon(Icons.local_hospital, color: Colors.white),
                ],
              ),
            ),

            // 복약캘린더 타이틀
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 16),
              child: Text(
                '복약캘린더',
                style: TextStyle(fontSize: 28, color: Colors.white, fontWeight: FontWeight.bold),
              ),
            ),

            const SizedBox(height: 12),

            // 날짜
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Text(
                formattedDate,
                style: const TextStyle(fontSize: 24, color: Colors.white),
              ),
            ),

            const SizedBox(height: 12),

            // 캘린더 요약 + 상태
            Container(
              margin: const EdgeInsets.symmetric(horizontal: 16),
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(16),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text('2회 완료 · 3회 중',
                      style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
                  const SizedBox(height: 12),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: List.generate(7, (index) {
                      final date = today.subtract(Duration(days: 3 - index));
                      final isToday = date.day == today.day;
                      return Column(
                        children: [
                          Text(DateFormat.E('ko_KR').format(date)),
                          const SizedBox(height: 4),
                          CircleAvatar(
                            backgroundColor:
                            isToday ? Colors.black : Colors.grey.shade300,
                            radius: 16,
                            child: Text(
                              '${date.day}',
                              style: TextStyle(
                                  color: isToday ? Colors.white : Colors.black),
                            ),
                          ),
                          const SizedBox(height: 4),
                          const Icon(Icons.medication, size: 20),
                        ],
                      );
                    }),
                  ),
                ],
              ),
            ),

            const SizedBox(height: 16),

            // 약 리스트
            Expanded(
              child: Container(
                decoration: const BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.vertical(top: Radius.circular(24)),
                ),
                child: ListView(
                  padding: const EdgeInsets.all(16),
                  children: const [
                    _MedicineCard(
                      name: '아스피린',
                      time: '오전 8:00',
                      detail: '이튿 가득!',
                      status: '복용 완료',
                    ),
                    SizedBox(height: 12),
                    _MedicineCard(
                      name: '리시노프릴',
                      time: '오후 12:00',
                      detail: '',
                      status: '복용 완료',
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _MedicineCard extends StatelessWidget {
  final String name;
  final String time;
  final String detail;
  final String status;

  const _MedicineCard({
    required this.name,
    required this.time,
    required this.detail,
    required this.status,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(name,
                style: const TextStyle(
                    fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 6),
            Row(
              children: [
                Text(time, style: const TextStyle(fontSize: 14)),
                const SizedBox(width: 16),
                if (detail.isNotEmpty)
                  Text(detail,
                      style: const TextStyle(
                          fontSize: 14, fontStyle: FontStyle.italic)),
              ],
            ),
            const SizedBox(height: 12),
            Row(
              children: [
                ElevatedButton(
                  onPressed: () {},
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.black,
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8)),
                  ),
                  child: const Text('복용 완료'),
                ),
                const SizedBox(width: 8),
                OutlinedButton(
                  onPressed: () {},
                  child: Text(
                    status == '복용 완료' ? '미복용' : '복용 완료',
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
