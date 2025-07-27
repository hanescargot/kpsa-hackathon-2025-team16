// features/ranking/pharmacy_ranking_screen.dart
import 'package:flutter/material.dart';
import 'package:yaksok/util.dart';

class PharmacyRankingScreen extends StatelessWidget {
  const PharmacyRankingScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final rankings = [
      {'rank': 1, 'name': '호바른마음약국', 'location': '서울 강남구', 'score': "숲"},
      {'rank': 2, 'name': '정직한약국', 'location': '서울 마포구', 'score': "나무"},
      {'rank': 3, 'name': '사랑이약국', 'location': '부산 해운대구', 'score': "나무"},
      {'rank': 4, 'name': '튼튼약국', 'location': '대전 유성구', 'score': "새싹"},
      {'rank': 5, 'name': '희망약국', 'location': '광주 동구', 'score': "새싹"},
    ];

    return Scaffold(
      appBar: AppBar(
        title: const Text(
          '🏆 약국 등급 랭킹',
          style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
        ),
        backgroundColor: kColorPrimary,
        centerTitle: true,
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          const SizedBox(height: 12),
          Image.asset("assets/imgs/rank.png", width: 180,),
          const SizedBox(height: 12),

          const Padding(
            padding: EdgeInsets.symmetric(horizontal: 16),
            child: Text(
              '약국 등급은 전월 해당 약국 환자들이 획득한\n총 포인트 합계/참여인원수로 산정됩니다.',
              style: TextStyle(color: Colors.black87),
            ),
          ),
          const SizedBox(height: 12),
          Divider(),
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsets.all(16),
              itemCount: rankings.length,
              itemBuilder: (context, index) {
                final item = rankings[index];
                final String score = item['score'].toString();
                final label = _getLabel(score);

                return Container(
                  margin: const EdgeInsets.only(bottom: 12),
                  padding: const EdgeInsets.all(16),
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(16),
                    boxShadow: const [
                      BoxShadow(color: Colors.black12, blurRadius: 4, offset: Offset(0, 2)),
                    ],
                  ),
                  child: Row(
                    children: [
                      CircleAvatar(
                        radius: 20,
                        backgroundColor: kColorPrimary,
                        child: Text(
                          '${item['rank']}',
                          style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
                        ),
                      ),
                      const SizedBox(width: 16),
                      Expanded(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              item['name'].toString(),
                              style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                            ),
                            Text(
                              item['location'].toString(),
                              style: const TextStyle(color: Colors.grey),
                            ),
                          ],
                        ),
                      ),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.end,
                        children: [
                          // Text.rich(
                          //   TextSpan(
                          //     children: [
                          //       const TextSpan(text: '상위 ', style: TextStyle(fontSize: 14)),
                          //       TextSpan(
                          //         text: '$score%',
                          //         style: TextStyle(
                          //           fontSize: 16,
                          //           fontWeight: FontWeight.bold,
                          //           color: kColorPrimary,
                          //         ),
                          //       ),
                          //     ],
                          //   ),
                          // ),
                          // const SizedBox(height: 4),
                          Row(
                            children: [
                              Text(
                                label['label'].toString(),
                                style: const TextStyle(fontSize: 12),
                              ),
                              const SizedBox(width: 4),
                              Text(
                                label['emoji'].toString(),
                                style: const TextStyle(fontSize: 16),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ],
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  /// 등급별 라벨/이모지 설정
  Map<String, String> _getLabel(String r) {
    if (r == '새싹') {
      return {'label': '🌱 새싹 약국', 'emoji': '🌱'};
    } else if (r == '나무') {
      return {'label': '🌳 나무 약국', 'emoji': '🌳'};
    } else {
      return {'label': '🌲 숲 약국', 'emoji': '🌲'};
    }
  }
}
