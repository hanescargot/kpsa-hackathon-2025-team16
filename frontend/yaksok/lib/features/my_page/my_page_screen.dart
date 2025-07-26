import 'package:flutter/material.dart';

class MyPageScreen extends StatelessWidget {
  const MyPageScreen({super.key});

  Widget _sectionLabel(String text) {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(12),
      margin: const EdgeInsets.only(bottom: 4),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
      ),
      child: Text(
        text,
        style: const TextStyle(fontWeight: FontWeight.bold),
      ),
    );
  }

  Widget _subItem(String text, VoidCallback onTap) {
    return Column(
      children: [
        ListTile(
          title: Text(text),
          trailing: const Icon(Icons.arrow_forward_ios, size: 16),
          onTap: onTap,
        ),
        const Divider(height: 1),
      ],
    );
  }

  Widget _singleItem(String text, VoidCallback onTap) {
    return Container(
      width: double.infinity,
      margin: const EdgeInsets.symmetric(vertical: 6),
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          padding: const EdgeInsets.symmetric(vertical: 14),
          backgroundColor: Colors.white,
          foregroundColor: Colors.black,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12),
            side: const BorderSide(color: Colors.black12),
          ),
        ),
        onPressed: onTap,
        child: Text(text, style: const TextStyle(fontWeight: FontWeight.bold)),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFFF6B6B),
      appBar: AppBar(
        title: const Text('마이페이지'),
        backgroundColor: const Color(0xFFFF6B6B),
        foregroundColor: Colors.white,
        centerTitle: true,
        elevation: 0,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: ListView(
          children: [
            _sectionLabel('사용자 프로필'),
            _subItem('1. 개인정보확인 및 수정', () {}),
            _subItem('2. 등록약국 수정 및 추가', () {}),
            _subItem('3. 생활패턴 기록', () {}),

            const SizedBox(height: 16),
            _sectionLabel('보호자 정보 관리'),
            _subItem('1. 보호자 등록 및 수정', () {}),
            _subItem('2. 보호자 앱 연동 여부', () {}),

            const SizedBox(height: 16),
            _sectionLabel('포인트 적립 및 사용 내역'),
            _subItem('1. 포인트 적립 내역', () {}),
            _subItem('2. 포인트 사용 내역', () {}),

            const SizedBox(height: 16),
            _singleItem('포인트 전환', () {}),
            _singleItem('나의 counselling_screen 내역', () {}),
            _singleItem('앱 환경설정', () {}),
          ],
        ),
      ),
    );
  }
}
