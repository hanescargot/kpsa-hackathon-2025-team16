import 'package:flutter/material.dart';
import '../../util.dart';
import '../ranking/ranking_list.dart';

class MedicationChallengeSquare extends StatelessWidget {
  const MedicationChallengeSquare({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF9EC9FF),
      body: SafeArea(
        child: Stack(
          children: [
            /// 메인 콘텐츠
            SingleChildScrollView(
              child: Column(
                children: [
                  const SizedBox(height: 64),

                  /// 상단 제목 + 아이콘
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Image.asset('assets/logo/logo_rmbg.png', height: 48),
                      const SizedBox(width: 10),
                      Text(
                        '복약 챌린지 스퀘어',
                        style: TextStyle(
                          fontSize: 26,
                          fontWeight: FontWeight.bold,
                          fontFamily: 'MapleStory',
                          color: kColorPrimary,
                        ),
                      ),
                    ],
                  ),

                  const SizedBox(height: 24),

                  /// 베팅 포인트 카드
                  Container(
                    margin: const EdgeInsets.symmetric(horizontal: 16),
                    padding: const EdgeInsets.all(16),
                    decoration: BoxDecoration(
                      color: Colors.white.withOpacity(0.95),
                      borderRadius: BorderRadius.circular(16),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black26,
                          blurRadius: 6,
                          offset: const Offset(0, 3),
                        ),
                      ],
                    ),
                    child: Row(
                      children: [
                        Container(
                          padding: const EdgeInsets.all(10),
                          decoration: const BoxDecoration(
                            color: Color(0xFFE6F0FF),
                            shape: BoxShape.circle,
                          ),
                          child: const Icon(
                            Icons.monetization_on,
                            color: Colors.amber,
                            size: 28,
                          ),
                        ),
                        const SizedBox(width: 16),
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              '현재 보유한 베팅 포인트',
                              style: TextStyle(
                                fontSize: 14,
                                color: Colors.black54,
                              ),
                            ),
                            const SizedBox(height: 4),
                            Text(
                              '160만 P',
                              style: TextStyle(
                                fontSize: 22,
                                fontWeight: FontWeight.bold,
                                color: kColorPrimary,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),

                  const SizedBox(height: 24),

                  /// 참가자 카드
                  Container(
                    margin: const EdgeInsets.symmetric(horizontal: 16),
                    padding: const EdgeInsets.symmetric(
                      vertical: 20,
                      horizontal: 16,
                    ),
                    decoration: BoxDecoration(
                      color: Colors.white.withOpacity(0.95),
                      borderRadius: BorderRadius.circular(18),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black12,
                          blurRadius: 6,
                          offset: const Offset(0, 2),
                        ),
                      ],
                    ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.stretch,
                      children: [
                        const Text(
                          '참가자 목록',
                          style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        const SizedBox(height: 12),

                        /// 내 이행률 정보
                        Container(
                          padding: const EdgeInsets.all(12),
                          decoration: BoxDecoration(
                            color: const Color(0xFFF0F5FF),
                            borderRadius: BorderRadius.circular(12),
                          ),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              InkWell(
                                onTap:(){
                                  showDialog(
                                    context: context,
                                    builder: (context) => AlertDialog(
                                      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
                                      backgroundColor: Colors.white,
                                      title: const Row(
                                        children: [
                                          Text('🎉', style: TextStyle(fontSize: 24)),
                                          SizedBox(width: 8),
                                          Text('포인트 축하', style: TextStyle(fontWeight: FontWeight.bold)),
                                        ],
                                      ),
                                      content: const Column(
                                        mainAxisSize: MainAxisSize.min,
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                          Text('어제 받은 포인트: ',
                                              style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500)),
                                          SizedBox(height: 4),
                                          Text('💰 180P',
                                              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: Colors.green)),
                                          SizedBox(height: 16),
                                          Text('누적 포인트: ',
                                              style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500)),
                                          SizedBox(height: 4),
                                          Text('🎯 480P',
                                              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: Colors.blue)),
                                        ],
                                      ),
                                      actions: [
                                        TextButton(
                                          onPressed: () => Navigator.of(context).pop(),
                                          child: const Text('확인', style: TextStyle(color: Colors.blue)),
                                        ),
                                      ],
                                    ),
                                  );
                                },
                                child: Image.asset
                                  (
                                  'assets/icon/blue_gm.png',
                                  height: 48,
                                ),
                              ),
                              const SizedBox(width: 12),
                              const Text.rich(
                                TextSpan(
                                  children: [
                                    TextSpan(
                                      text: '나의 복약 이행률은 ',
                                      style: TextStyle(color: Colors.black54),
                                    ),
                                    TextSpan(
                                      text: '67%',
                                      style: TextStyle(
                                        color: Colors.indigo,
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                    TextSpan(
                                      text: ' 입니다.',
                                      style: TextStyle(color: Colors.black54),
                                    ),
                                  ],
                                ),
                              ),
                            ],
                          ),
                        ),

                        const SizedBox(height: 20),

                        /// 아바타 리스트
                        Wrap(
                          alignment: WrapAlignment.center,
                          spacing: 16,
                          runSpacing: 16,
                          children: List.generate(
                            8,
                            (index) => Column(
                              children: [
                                Container(
                                  padding: const EdgeInsets.all(8),
                                  decoration: BoxDecoration(
                                    color: const Color(0xFFF9FAFB),
                                    borderRadius: BorderRadius.circular(12),
                                    border: Border.all(
                                      color: Colors.grey.shade300,
                                    ),
                                  ),
                                  child: Image.asset(
                                    'assets/icon/blue_gm.png',
                                    height: 64,
                                  ),
                                ),
                                const SizedBox(height: 4),
                                const Text(
                                  '김**',
                                  style: TextStyle(fontSize: 13),
                                ),
                              ],
                            ),
                          ),
                        ),
                        const SizedBox(height: 24),
                      ],
                    ),
                  ),

                  const SizedBox(height: 24),

                  /// 약국 등급 랭킹 버튼
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 16),
                    child: ElevatedButton.icon(
                      icon: const Text('🏆', style: TextStyle(fontSize: 20)),
                      label: const Text(
                        '약국 등급 랭킹',
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      style: ElevatedButton.styleFrom(
                        foregroundColor: Colors.white,
                        backgroundColor: kColorPrimary,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(16),
                        ),
                        padding: const EdgeInsets.symmetric(vertical: 18),
                        minimumSize: const Size.fromHeight(60),
                      ),
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => const PharmacyRankingScreen(),
                          ),
                        );
                      },
                    ),
                  ),
                  const SizedBox(height: 24),
                ],
              ),
            ),

            /// 닫기 버튼
            Positioned(
              top: 16,
              right: 16,
              child: GestureDetector(
                onTap: () => Navigator.of(context).pop(),
                child: Container(
                  decoration: BoxDecoration(
                    color: Colors.white,
                    shape: BoxShape.circle,
                    boxShadow: [
                      BoxShadow(color: Colors.black12, blurRadius: 4),
                    ],
                  ),
                  padding: const EdgeInsets.all(8),
                  child: const Icon(Icons.close, color: Colors.black),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
