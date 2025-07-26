import 'package:flutter/material.dart';

class ConsultationScreen extends StatelessWidget {
  const ConsultationScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final pharmacists = [
      {'name': 'OO 약국 OOO 약사', 'image': 'https://via.placeholder.com/150'},
      {
        'name': 'OO 약국 OOO 약사',
        'image': 'https://via.placeholder.com/150/000000/FFFFFF',
      },
    ];

    return Scaffold(
      backgroundColor: const Color(0xFFFF6B6B),
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // 상단 타이틀
            Padding(
              padding: const EdgeInsets.fromLTRB(16, 16, 16, 8),
              child: Row(
                children: const [
                  Text(
                    '약속이',
                    style: TextStyle(fontSize: 18, color: Colors.white),
                  ),
                  SizedBox(width: 12),
                  Icon(Icons.local_hospital, color: Colors.white),
                ],
              ),
            ),
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 16),
              child: Text(
                '약사 상담',
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                  color: Colors.black,
                ),
              ),
            ),
            const SizedBox(height: 12),

            // 필터 버튼
            SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Row(
                children: [
                  _FilterButton(label: '필터 설정'),
                  _FilterButton(label: '거리순'),
                  _FilterButton(label: '응답률순'),
                  _FilterButton(label: '나의 약국'),
                ],
              ),
            ),

            const SizedBox(height: 16),

            // 약사 카드 리스트
            Expanded(
              child: ListView.builder(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                itemCount: pharmacists.length,
                itemBuilder: (context, index) {
                  final pharmacist = pharmacists[index];
                  return Container(
                    margin: const EdgeInsets.only(bottom: 12),
                    padding: const EdgeInsets.all(12),
                    decoration: BoxDecoration(
                      color: Colors.white.withOpacity(0.9),
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Row(
                      children: [
                        CircleAvatar(
                          backgroundImage: NetworkImage(pharmacist['image']!),
                          radius: 30,
                        ),
                        const SizedBox(width: 16),
                        Expanded(
                          child: Text(
                            pharmacist['name']!,
                            style: const TextStyle(fontSize: 16),
                          ),
                        ),
                      ],
                    ),
                  );
                },
              ),
            ),

            const SizedBox(height: 12),

            // 안내 문구
            const Padding(
              padding: EdgeInsets.symmetric(horizontal: 16),
              child: Text(
                '클릭하시면 해당 약사님과의 채팅방이 생성됩니다.',
                style: TextStyle(color: Colors.white, fontSize: 14),
              ),
            ),
            const SizedBox(height: 12),

            // 채팅 바로가기 아이콘들
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: const [
                  _ChatIcon(label: '나의 채팅방'),
                  _ChatIcon(label: '이전 상담내역'),
                ],
              ),
            ),

            const SizedBox(height: 12),

            // 하단 네비게이션
            Container(
              height: 56,
              decoration: const BoxDecoration(color: Colors.white),
              child: IconButton(
                onPressed: () {
                  Navigator.of(context).pop();
                },
                icon: Center(
                  child: Icon(Icons.home, size: 28, color: Colors.black),
                ),
              ),
              // child: const Center(child: Icon(Icons.home, size: 28, color: Colors.black)),
            ),
          ],
        ),
      ),
    );
  }
}

class _FilterButton extends StatelessWidget {
  final String label;

  const _FilterButton({required this.label});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(right: 8),
      child: OutlinedButton(
        style: OutlinedButton.styleFrom(
          foregroundColor: Colors.black,
          backgroundColor: Colors.white,
          side: const BorderSide(color: Colors.black12),
        ),
        onPressed: () {},
        child: Text(label),
      ),
    );
  }
}

class _ChatIcon extends StatelessWidget {
  final String label;

  const _ChatIcon({required this.label});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        const Icon(Icons.chat_bubble_outline, color: Colors.white, size: 32),
        const SizedBox(height: 4),
        Text(label, style: const TextStyle(color: Colors.white)),
      ],
    );
  }
}
