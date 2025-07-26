import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../widgets/home_bottom_nav_bar.dart';

class HomeScreen extends ConsumerWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      backgroundColor: const Color(0xFFFF6B6B), // 빨강 계열 배경
      body: SafeArea(
        child: Column(
          children: [
            // 광고 배너
            Container(
              margin: const EdgeInsets.all(16),
              padding: const EdgeInsets.symmetric(vertical: 12),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(12),
              ),
              alignment: Alignment.center,
              child: const Text('광고배너'),
            ),

            // 앱 제목 + 알림 아이콘
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: const [
                  Text(
                    '약속이',
                    style: TextStyle(fontSize: 32, color: Colors.white),
                  ),
                  Icon(Icons.notifications, color: Colors.white),
                ],
              ),
            ),

            // 약 정보 카드 2개
            Expanded(
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                child: Row(
                  children: [
                    // 처방약 정보
                    Expanded(
                      child: Container(
                        margin: const EdgeInsets.only(right: 8),
                        decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: const Center(
                          child: Text('처방약 정보'),
                        ),
                      ),
                    ),
                    // 오늘 복약 정보
                    Expanded(
                      child: Container(
                        margin: const EdgeInsets.only(left: 8),
                        decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: const Center(
                          child: Text('오늘 복약 정보'),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),

            // 순위 텍스트
            const Padding(
              padding: EdgeInsets.symmetric(vertical: 12),
              child: Text(
                '현재 은서님의 순위는 72위입니다.',
                style: TextStyle(color: Colors.white, fontSize: 16),
              ),
            ),

            // 하단 내비게이션
            const HomeBottomNavBar(),
          ],
        ),
      ),
    );
  }
}
