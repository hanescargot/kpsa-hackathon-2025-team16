// features/mypage/my_page_screen.dart
import 'package:flutter/material.dart';
import 'package:yaksok/util.dart';

class MyPageScreen extends StatelessWidget {
  const MyPageScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final List<_MyPageItem> menuItems = [
      _MyPageItem(title: '프로필', onTap: () {}),
      _MyPageItem(title: '보호자 정보 관리', onTap: () {}),
      _MyPageItem(title: '포인트 적립 및 사용 내역', onTap: () {}),
      _MyPageItem(title: '포인트 전환', onTap: () {}),
      _MyPageItem(title: '나의 상담 내역', onTap: () {}),
      _MyPageItem(title: '앱 환경설정', onTap: () {}),
    ];

    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 20),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text(
                '마이페이지',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.brown,
                ),
              ),
              const SizedBox(height: 24),
              // 👵 프로필 이미지
              Center(
                child: Image.asset(
                  'assets/logo/logo_rmbg.png', // 이미지 경로 수정 필요
                  height: 80,
                ),
              ),

              const SizedBox(height: 32),

              // 메뉴 항목 리스트
              ...menuItems.map((item) => Padding(
                padding: const EdgeInsets.symmetric(vertical: 16),
                child: GestureDetector(
                  onTap: item.onTap,
                  child: Text(
                    item.title,
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w600,
                      color: Color(0xFF111827), // 다크 네이비 계열
                    ),
                  ),
                ),
              )),
            ],
          ),
        ),
      ),

      // 하단 내비게이션 바 (홈 아이콘만 표시)
      bottomNavigationBar: BottomAppBar(
        color: Colors.transparent,
        elevation: 0,
        child: InkWell(
          onTap: (){
            Navigator.pop(context);
          },
          child: Padding(
            padding: const EdgeInsets.only(top: 8, bottom: 12),
            child: Center(
              child: Icon(
                Icons.home,
                size: 36,
                color: Colors.blueAccent,
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class _MyPageItem {
  final String title;
  final VoidCallback onTap;

  _MyPageItem({required this.title, required this.onTap});
}
