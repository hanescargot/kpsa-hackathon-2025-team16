import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

import '../../util.dart';
import 'medication_weekly_screen.dart';

class MedicationCalendarScreen extends StatelessWidget {
  const MedicationCalendarScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final today = DateTime.now();
    final currentMonth = DateFormat('yyyy년 M월').format(today);

    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Column(
          children: [
            // 상단 사용자 이름 + 홈 버튼
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        '최순복',
                        style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      Text('함께하는 복약 캘린더'),
                    ],
                  ),
                  IconButton(
                    icon: Icon(Icons.home, size: 28, color: kColorPrimary),
                    onPressed: () => Navigator.of(context).pop(),
                  ),
                ],
              ),
            ),

            // 월 이동 및 표시
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 8),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  IconButton(
                    onPressed: () {},
                    icon: const Icon(Icons.chevron_left),
                  ),
                  Text(
                    currentMonth,
                    style: const TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  IconButton(
                    onPressed: () {},
                    icon: const Icon(Icons.chevron_right),
                  ),
                ],
              ),
            ),

            // 요일
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: const ['일', '월', '화', '수', '목', '금', '토']
                  .map(
                    (day) => Expanded(
                      child: Center(
                        child: Text(
                          day,
                          style: TextStyle(fontWeight: FontWeight.bold),
                        ),
                      ),
                    ),
                  )
                  .toList(),
            ),

            // 캘린더 (데모: 고정 데이터)
            Expanded(
              child: GridView.builder(
                padding: const EdgeInsets.all(8),
                itemCount: 42, // 6줄 x 7칸
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 7,
                  crossAxisSpacing: 6,
                  mainAxisSpacing: 6,
                ),
                itemBuilder: (context, index) {
                  final day = index - 1; // 임시로 1일부터 시작
                  final isToday = day == 4;
                  final isInMonth = index >= 1 && index <= 31;

                  Widget? status;
                  if (day == 1 || day == 3) {
                    status = const Icon(
                      Icons.check,
                      size: 14,
                      color: Colors.green,
                    );
                  } else if (day == 2) {
                    status = const Icon(
                      Icons.error,
                      size: 14,
                      color: Colors.red,
                    );
                  } else if (day == 4) {
                    status = const Icon(
                      Icons.access_time,
                      size: 14,
                      color: Colors.blue,
                    );
                  }

                  String? percent;
                  if (day == 1 || day == 3) {
                    percent = '100%';
                  } else if (day == 2) {
                    percent = '67%';
                  }

                  return GestureDetector(
                    onTap: isInMonth
                        ? () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (_) => const MedicationDetailScreen(
                            date: '2025-07-04',
                            dayOfWeek: '목',
                            dayCount: 4,
                          ),
                        ),
                      );
                    }
                        : null,
                    child: Container(
                      decoration: BoxDecoration(
                        color: isToday ? Colors.blue.shade100 : Colors.transparent,
                        borderRadius: BorderRadius.circular(8),
                      ),
                      padding: const EdgeInsets.all(4),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: [
                          Text(
                            isInMonth ? '$day' : '',
                            style: const TextStyle(fontSize: 14),
                          ),
                          if (status != null) status,
                          if (percent != null)
                            Text(
                              percent,
                              style: TextStyle(
                                fontSize: 12,
                                color: percent.contains('100')
                                    ? Colors.green
                                    : Colors.red,
                              ),
                            ),
                        ],
                      ),
                    ),
                  );

                },
              ),
            ),
            Row(
              children: [
                SizedBox(width: 24),
                Text("이번달 복약률", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18, color: Colors.black87), ),
              ],
            ),
            // 통계
            Container(
              margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
              padding: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                color: Colors.grey.shade100,
                borderRadius: BorderRadius.circular(12),
              ),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: const [
                  _StatBlock(
                    title: '85%',
                    subtitle: 'Adherence',
                    color: Colors.green,
                  ),
                  _StatBlock(
                    title: '12',
                    subtitle: 'Streak Days',
                    color: Colors.blue,
                  ),
                  _StatBlock(title: '3', subtitle: 'Missed', color: Colors.red),
                ],
              ),
            ),

            const SizedBox(height: 60), // bottom nav 공간
          ],
        ),
      ),
    );
  }
}

class _StatBlock extends StatelessWidget {
  final String title;
  final String subtitle;
  final Color color;

  const _StatBlock({
    required this.title,
    required this.subtitle,
    required this.color,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Text(
          title,
          style: TextStyle(
            color: color,
            fontSize: 20,
            fontWeight: FontWeight.bold,
          ),
        ),
        Text(subtitle, style: const TextStyle(fontSize: 13)),
      ],
    );
  }
}
