import 'package:flutter/material.dart';

class RankingScreen extends StatelessWidget {
  const RankingScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final myRank = 72;
    final myRate = 86;

    final rankingList = List.generate(20, (index) {
      final rank = index + 1;
      return {
        'name': '사용자$rank',
        'rank': rank,
        'rate': 100 - index * 2,
      };
    });

    return Scaffold(
      appBar: AppBar(
        title: const Text('복약 챌린지 순위'),
        centerTitle: true,
      ),
      body: Column(
        children: [
          // 내 순위 카드
          Container(
            margin: const EdgeInsets.all(16),
            padding: const EdgeInsets.all(16),
            decoration: BoxDecoration(
              color: Colors.teal.shade50,
              borderRadius: BorderRadius.circular(16),
              border: Border.all(color: Colors.teal, width: 1.5),
            ),
            child: Row(
              children: [
                const CircleAvatar(
                  radius: 28,
                  backgroundImage: AssetImage('assets/images/avatar.png'), // 없으면 기본으로 null
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text('은서님', style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
                      Text('현재 순위: $myRank위', style: const TextStyle(fontSize: 14)),
                    ],
                  ),
                ),
                Column(
                  children: [
                    const Text('복약률', style: TextStyle(fontSize: 14)),
                    Text('$myRate%', style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
                  ],
                ),
              ],
            ),
          ),

          const Padding(
            padding: EdgeInsets.symmetric(horizontal: 16.0),
            child: Align(
              alignment: Alignment.centerLeft,
              child: Text('전체 랭킹', style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
            ),
          ),
          const SizedBox(height: 8),

          // 랭킹 리스트
          Expanded(
            child: ListView.builder(
              itemCount: rankingList.length,
              itemBuilder: (context, index) {
                final user = rankingList[index];
                return ListTile(
                  leading: CircleAvatar(
                    backgroundColor: Colors.teal,
                    child: Text('${user['rank']}', style: const TextStyle(color: Colors.white)),
                  ),
                  title: Text('${user['rate']}%'),
                  trailing: Text('${user['rate']}%', style: const TextStyle(fontWeight: FontWeight.bold)),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
