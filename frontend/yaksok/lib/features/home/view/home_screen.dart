import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:yaksok/features/home/view/video_call_screen.dart';
import '../../../widgets/home_bottom_nav_bar.dart';
import '../../my_page/prescription_info.dart';
import '../../my_page/today_medication_info.dart';
import 'package:go_router/go_router.dart';

// 전역 변수 (언제든 변경 가능)
int globalRank = 72;

class HomeScreen extends ConsumerStatefulWidget {
  const HomeScreen({super.key});

  @override
  ConsumerState<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends ConsumerState<HomeScreen> {
  final PageController _pageController = PageController();
  int _currentPage = 0;
  final List<String> _ads = [
    'assets/ad/ad1.png',
    'assets/ad/ad2.png',
    'assets/ad/ad3.png',
  ];
  Timer? _timer;

  @override
  void initState() {
    super.initState();
    _timer = Timer.periodic(const Duration(seconds: 3), (Timer timer) {
      if (_currentPage < _ads.length - 1) {
        _currentPage++;
      } else {
        _currentPage = 0;
      }
      _pageController.animateToPage(
        _currentPage,
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeIn,
      );
    });
  }

  @override
  void dispose() {
    _timer?.cancel();
    _pageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    const backgroundColor = Color(0xFFFF6B6B);

    return Scaffold(
      backgroundColor: backgroundColor,
      body: SafeArea(
        child: Column(
          children: [
            // 🔄 광고 배너 캐러셀
            Container(
              height: 64,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(12),
                color: Colors.white,
              ),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(4),
                child: PageView.builder(
                  controller: _pageController,
                  itemCount: _ads.length,
                  itemBuilder: (context, index) {
                    return Image.asset(_ads[index], fit: BoxFit.cover);
                  },
                ),
              ),
            ),

            // 앱 제목 + 알림
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  GestureDetector(
                    onTap: () =>
                        context.pushNamed(VideoNotificationScreen.routeName),
                    child: const Text(
                      '약속이',
                      style: TextStyle(fontSize: 32, color: Colors.white),
                    ),
                  ),
                  Icon(Icons.notifications, color: Colors.white),
                ],
              ),
            ),

            // 약 정보 카드
            Expanded(
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 8),
                child: Row(
                  children: [
                    Expanded(
                      child: Container(
                        margin: const EdgeInsets.only(right: 8),
                        decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: const Center(child: PrescriptionInfoPage()),
                      ),
                    ),
                    Expanded(
                      child: Container(
                        margin: const EdgeInsets.only(left: 2),
                        decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(12),
                        ),
                        child: Expanded(child: TodayMedicationInfo()),
                      ),
                    ),
                  ],
                ),
              ),
            ),

            // 순위 (72위 강조)
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 12),
              child: RichText(
                text: TextSpan(
                  style: const TextStyle(
                    fontSize: 16,
                    color: Colors.white,
                    fontFamily: 'MapleStory',
                  ),
                  children: [
                    const TextSpan(text: '현재 은서님의 순위는 '),
                    WidgetSpan(
                      child: Container(
                        padding: const EdgeInsets.symmetric(
                          horizontal: 6,
                          vertical: 2,
                        ),
                        decoration: BoxDecoration(
                          color: Colors.white, // 박스 안 배경
                          borderRadius: BorderRadius.circular(4),
                        ),
                        child: Text(
                          '$globalRank위',
                          style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                            color: backgroundColor, // 글자색 배경과 동일
                          ),
                        ),
                      ),
                    ),
                    const TextSpan(text: ' 입니다.'),
                  ],
                ),
              ),
            ),

            // 내비게이션
            const HomeBottomNavBar(),
          ],
        ),
      ),
    );
  }
}
