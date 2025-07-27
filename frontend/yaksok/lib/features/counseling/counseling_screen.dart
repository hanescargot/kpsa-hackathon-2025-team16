import 'dart:math';

import 'package:flutter/material.dart';
import '../../util.dart';
import 'chatting_screen.dart';
import 'message_history.dart';

class PharmacistConsultRequestPage extends StatelessWidget {
  const PharmacistConsultRequestPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Stack(
          children: [
            /// 메인 콘텐츠
            Column(
              children: [
                const SizedBox(height: 40),

                /// 상단 제목 + 로고
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Image.asset('assets/logo/logo_rmbg.png', height: 48),
                    const SizedBox(width: 8),
                    Text(
                      '약사 상담 요청',
                      style: TextStyle(
                        fontSize: 24,
                        fontWeight: FontWeight.bold,
                        fontFamily: 'MapleStory',
                        color: kColorPrimary,
                      ),
                    ),
                  ],
                ),

                const SizedBox(height: 20),

                /// 필터 설정 박스
                Container(
                  margin: const EdgeInsets.symmetric(horizontal: 16),
                  padding: const EdgeInsets.all(12),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(16),
                    border: Border.all(color: kColorPrimary, width: 1.5),
                    boxShadow: [
                      BoxShadow(
                        color: Colors.black12,
                        blurRadius: 4,
                        offset: Offset(0, 2),
                      ),
                    ],
                  ),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        '필터 설정',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 16,
                          color: Colors.black87,
                        ),
                      ),
                      const SizedBox(height: 12),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: [
                          _OutlinedFilterButton(label: '거리순'),
                          _OutlinedFilterButton(label: '응답률'),
                          _OutlinedFilterButton(label: '별점높은순'),
                        ],
                      ),
                    ],
                  ),
                ),

                const SizedBox(height: 20),

                /// 상담 리스트
                Expanded(
                  child: ListView.builder(
                    padding: const EdgeInsets.symmetric(horizontal: 16),
                    itemCount: 4,
                    itemBuilder: (context, index) {
                      final random = Random();
                      final imageNumber = random.nextInt(3) + 1; // 1 ~ 3
                      return _PharmacistCard(
                        imagePath: 'assets/imgs/ph$imageNumber.png',
                        pharmacyName: 'OO약국',
                        pharmacistName: '홍길동',
                        empathy: '서울시 성동구',
                      );
                    },
                  ),
                ),

                const SizedBox(height: 80),
              ],
            ),

            /// 닫기 버튼
            Positioned(
              top: 12,
              right: 12,
              child: GestureDetector(
                onTap: () => Navigator.of(context).pop(),
                child: Container(
                  decoration: const BoxDecoration(
                    color: Colors.white,
                    shape: BoxShape.circle,
                    boxShadow: [
                      BoxShadow(color: Colors.black26, blurRadius: 4),
                    ],
                  ),
                  padding: const EdgeInsets.all(8),
                  child: const Icon(Icons.close, color: Colors.black),
                ),
              ),
            ),

            /// 하단 상담요청 / 기록보기 버튼
            Positioned(
              bottom: 24,
              left: 16,
              right: 16,
              child: Row(
                children: [
                  // Expanded(
                  //   child: ElevatedButton(
                  //     onPressed: () {
                  //       // TODO: 상담 요청 기능
                  //     },
                  //     style: ElevatedButton.styleFrom(
                  //       backgroundColor: kColorPrimary,
                  //       padding: const EdgeInsets.symmetric(vertical: 16),
                  //       shape: RoundedRectangleBorder(
                  //         borderRadius: BorderRadius.circular(16),
                  //       ),
                  //     ),
                  //     child: const Text(
                  //       '상담 요청',
                  //       style: TextStyle(
                  //         fontSize: 16,
                  //         fontWeight: FontWeight.bold,
                  //         color: Colors.white,
                  //       ),
                  //     ),
                  //   ),
                  // ),
                  const SizedBox(width: 12),
                  Expanded(
                    child: OutlinedButton(
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (_) => const ConsultationRecordPage(),
                          ),
                        );
                      },
                      style: OutlinedButton.styleFrom(
                        side: BorderSide(color: kColorPrimary, width: 2),
                        padding: const EdgeInsets.symmetric(vertical: 16),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(16),
                        ),
                      ),
                      child: Text(
                        '상담 기록 보기',
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                          color: kColorPrimary,
                        ),
                      ),
                    ),
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

class _OutlinedFilterButton extends StatelessWidget {
  final String label;

  const _OutlinedFilterButton({required this.label});

  @override
  Widget build(BuildContext context) {
    return OutlinedButton(
      onPressed: () {},
      style: OutlinedButton.styleFrom(
        side: BorderSide(color: kColorPrimary, width: 1.5),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
        padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
        foregroundColor: kColorPrimary,
      ),
      child: Text(label, style: const TextStyle(fontSize: 14)),
    );
  }
}

class _PharmacistCard extends StatelessWidget {
  final String imagePath;
  final String pharmacyName;
  final String pharmacistName;
  final String empathy;

  const _PharmacistCard({
    required this.imagePath,
    required this.pharmacyName,
    required this.pharmacistName,
    required this.empathy,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(16),
        border: Border.all(color: kColorPrimary, width: 1),
      ),
      child: Row(
        children: [
          CircleAvatar(radius: 28, backgroundImage: AssetImage(imagePath)),
          const SizedBox(width: 16),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  children: [
                    Text(
                      pharmacyName,
                      style: const TextStyle(
                        color: Colors.blue,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    Spacer(),
                    Container(
                      // color: Colors.blue.shade100,
                      padding: EdgeInsets.all(2),
                      decoration: BoxDecoration(
                        border: Border.all(color: Colors.blue, width: 1.5),
                        borderRadius: BorderRadius.circular(4),
                        color: Colors.blue.shade100,
                      ),
                      child: InkWell(
                        onTap: (){
                          Navigator.push(context, MaterialPageRoute(builder: (_) => const PharmacistChatScreen()));
                        },
                        child: Row(
                          children: [
                            Icon(Icons.send, color: Colors.blue, size: 12),
                            Text(" 상담 요청 ", style: TextStyle(color: Colors.blue)),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                Text(pharmacistName, style: const TextStyle(fontSize: 16)),
                const SizedBox(height: 4),
                Text(
                  empathy,
                  style: const TextStyle(
                    fontSize: 12,
                    color: Colors.blueAccent,
                    decoration: TextDecoration.underline,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
