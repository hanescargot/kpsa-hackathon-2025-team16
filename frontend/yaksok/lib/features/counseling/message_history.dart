import 'package:flutter/material.dart';
import '../../util.dart';

class ConsultationRecordPage extends StatelessWidget {
  const ConsultationRecordPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF9EC9FF),
      body: SafeArea(
        child: Column(
          children: [
            const SizedBox(height: 24),

            /// 상단 로고 + 제목
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Image.asset('assets/logo/logo_rmbg.png', height: 48),
                const SizedBox(width: 8),
                const Text(
                  '상담 기록',
                  style: TextStyle(
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                    fontFamily: 'MapleStory',
                  ),
                ),
              ],
            ),

            const SizedBox(height: 20),

            /// 필터 버튼
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: const [
                  _TabButton(label: '최근기록', isActive: false),
                  SizedBox(width: 8),
                  _TabButton(label: '상담중', isActive: true),
                ],
              ),
            ),

            const SizedBox(height: 16),

            /// 상담 기록 리스트
            Expanded(
              child: ListView(
                padding: const EdgeInsets.all(16),
                children: const [
                  _ConsultCard(
                    date: '2025.07.03',
                    pharmacistName: '김봉봉 약사',
                    question: '약먹을때 음주 어디까지 가능한가요?',
                    answer: '음주하지 마세요.',
                    comment: '싫어요...',
                    imagePath: 'assets/imgs/ph1.png',
                  ),
                  SizedBox(height: 12),
                  _ConsultCard(
                    date: '2025.07.01',
                    pharmacistName: '김봉봉 약사',
                    question: '배탈이 났는데 무슨 약을 먹어야할까요?',
                    answer:
                    '증상에 따라 달라서, 설사인지 복통인지 먼저 알려주시면 딱 맞는 약을 추천드릴게요!',
                    comment: '',
                    imagePath: 'assets/imgs/ph1.png',
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _TabButton extends StatelessWidget {
  final String label;
  final bool isActive;

  const _TabButton({required this.label, this.isActive = false});

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 10),
        decoration: BoxDecoration(
          color: isActive ? Colors.white : const Color(0xFFCCE2FF),
          borderRadius: BorderRadius.circular(12),
        ),
        alignment: Alignment.center,
        child: Text(
          label,
          style: TextStyle(
            fontWeight: FontWeight.bold,
            color: isActive ? kColorPrimary : Colors.black54,
          ),
        ),
      ),
    );
  }
}

class _ConsultCard extends StatelessWidget {
  final String date;
  final String pharmacistName;
  final String question;
  final String answer;
  final String comment;
  final String imagePath;

  const _ConsultCard({
    required this.date,
    required this.pharmacistName,
    required this.question,
    required this.answer,
    required this.comment,
    required this.imagePath,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text('질문 $date', style: const TextStyle(color: Colors.white)),

        const SizedBox(height: 6),

        Container(
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(16),
          ),
          padding: const EdgeInsets.all(16),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              CircleAvatar(
                backgroundImage: AssetImage(imagePath),
                radius: 28,
              ),
              const SizedBox(width: 12),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(question, style: const TextStyle(fontSize: 14)),
                    const SizedBox(height: 4),
                    Text(
                      '답변: $answer',
                      style: const TextStyle(
                        color: Colors.blueAccent,
                        fontSize: 14,
                      ),
                    ),
                    if (comment.isNotEmpty)
                      Padding(
                        padding: const EdgeInsets.only(top: 4.0),
                        child: Text(comment,
                            style: const TextStyle(color: Colors.black45)),
                      ),
                    const SizedBox(height: 8),
                    Text(pharmacistName,
                        style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 13,
                        )),
                  ],
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
