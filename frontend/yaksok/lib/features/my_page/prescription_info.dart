import 'dart:math';
import 'package:flutter/material.dart';

class PrescriptionInfoPage extends StatelessWidget {
  const PrescriptionInfoPage({super.key});

  @override
  Widget build(BuildContext context) {
    final random = Random();
    final randomImages = [
      'assets/imgs/p1.jpeg',
      'assets/imgs/p2.jpeg',
      'assets/imgs/p3.jpeg',
      'assets/imgs/p4.jpeg',
    ];

    final drugs = <PrescriptionDrug>[
      PrescriptionDrug(
        name: '히트코나졸정',
        subtitle: '(항진균제)',
        description: '진균(곰팡이균)에 의한 감염증',
        imagePath: randomImages[random.nextInt(randomImages.length)],
      ),
      PrescriptionDrug(
        name: '피디정',
        subtitle: '(부신피질호르몬)',
        description: '만성염증, 피부질환, 알레르기질환 등',
        imagePath: randomImages[random.nextInt(randomImages.length)],
      ),
      PrescriptionDrug(
        name: '동구록시트로마이신',
        subtitle: '(마크롤라이드계 항생제)',
        description: '각종 감염증 치료',
        imagePath: randomImages[random.nextInt(randomImages.length)],
      ),
      PrescriptionDrug(
        name: '모사피아정',
        subtitle: '(위장운동조절)',
        description: '오심, 구토, 가슴쓰림 등 치료',
        imagePath: randomImages[random.nextInt(randomImages.length)],
      ),
      PrescriptionDrug(
        name: '로이탄연질캡슐',
        subtitle: '(여드름 치료제)',
        description: '여드름 치료제',
        imagePath: randomImages[random.nextInt(randomImages.length)],
      ),
    ];

    return SizedBox(
      height: 140,
      child: ListView.builder(
        scrollDirection: Axis.horizontal,
        padding: const EdgeInsets.symmetric(horizontal: 16),
        itemCount: drugs.length,
        itemBuilder: (context, index) {
          final drug = drugs[index];
          return Padding(
            padding: const EdgeInsets.only(right: 12),
            child: _PrescriptionDrugCard(drug: drug),
          );
        },
      ),
    );
  }
}

class _PrescriptionDrugCard extends StatelessWidget {
  final PrescriptionDrug drug;

  const _PrescriptionDrugCard({required this.drug});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    return Container(
      width: 220,
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: Colors.blue.shade50,
        borderRadius: BorderRadius.circular(12),
      ),
      child: Row(
        children: [
          // 이미지
          ClipRRect(
            borderRadius: BorderRadius.circular(8),
            child: Image.asset(
              drug.imagePath,
              width: 60,
              height: 60,
              fit: BoxFit.cover,
              errorBuilder: (_, __, ___) => Container(
                width: 60,
                height: 60,
                color: Colors.grey.shade200,
                alignment: Alignment.center,
                child: const Icon(Icons.medication, color: Colors.grey),
              ),
            ),
          ),
          const SizedBox(width: 12),
          // 텍스트
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  drug.name,
                  style: theme.textTheme.bodyLarge?.copyWith(
                    fontWeight: FontWeight.bold,
                    fontSize: 15,
                  ),
                ),
                if (drug.subtitle.isNotEmpty)
                  Text(
                    drug.subtitle,
                    style: theme.textTheme.bodySmall?.copyWith(
                      color: Colors.black54,
                      fontSize: 12,
                    ),
                  ),
                const SizedBox(height: 4),
                Text(
                  drug.description,
                  style: theme.textTheme.bodySmall?.copyWith(fontSize: 12),
                  maxLines: 2,
                  overflow: TextOverflow.ellipsis,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class PrescriptionDrug {
  final String name;
  final String subtitle;
  final String description;
  final String imagePath;

  PrescriptionDrug({
    required this.name,
    required this.subtitle,
    required this.description,
    required this.imagePath,
  });
}
