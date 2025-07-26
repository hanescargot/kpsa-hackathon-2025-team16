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
      'assets/imgs/p4.jpeg'];

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
        description: '부신피질호르몬제; 만성염증, 피부질환, 기침, 알레르기질환 등',
        imagePath: randomImages[random.nextInt(randomImages.length)],
      ),
      PrescriptionDrug(
        name: '동구록시트로마이신',
        subtitle: '(마이크로라이드계 항생제)',
        description: '각종 감염증 치료',
        imagePath: randomImages[random.nextInt(randomImages.length)],
      ),
      PrescriptionDrug(
        name: '모사피아정',
        subtitle: '(위장운동조절 및 진경제)',
        description: '소화관운동을 촉진시켜 오심, 구토, 가슴쓰림 등의 증상 치료',
        imagePath: randomImages[random.nextInt(randomImages.length)],
      ),
      PrescriptionDrug(
        name: '로이탄연질캡슐',
        subtitle: '(여드름 치료제)',
        description: '여드름 치료제',
        imagePath: randomImages[random.nextInt(randomImages.length)],
      ),
    ];

    return Scaffold(
      appBar: AppBar(
        title: const Text('처방약 정보'),
      ),
      body: ListView.separated(
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 20),
        itemCount: drugs.length,
        separatorBuilder: (_, __) => const SizedBox(height: 24),
        itemBuilder: (context, index) {
          final drug = drugs[index];
          return _PrescriptionDrugItem(drug: drug);
        },
      ),
    );
  }
}

class _PrescriptionDrugItem extends StatelessWidget {
  const _PrescriptionDrugItem({required this.drug});

  final PrescriptionDrug drug;

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        // 약 이미지
        ClipRRect(
          borderRadius: BorderRadius.circular(6),
          child: Image.asset(
            drug.imagePath,
            width: 110,
            height: 70,
            fit: BoxFit.cover,
            errorBuilder: (_, __, ___) {
              return Container(
                width: 110,
                height: 70,
                color: Colors.grey.shade200,
                alignment: Alignment.center,
                child: const Icon(Icons.medication, size: 32, color: Colors.grey),
              );
            },
          ),
        ),
        const SizedBox(height: 8),
        // 텍스트 정보
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              drug.name,
              style: theme.textTheme.titleMedium?.copyWith(
                fontWeight: FontWeight.bold,
                fontSize: 20,
              ),
            ),
            const SizedBox(height: 4),
            if (drug.subtitle.isNotEmpty)
              Text(
                drug.subtitle,
                style: theme.textTheme.bodyMedium?.copyWith(
                  color: Colors.black87,
                  fontSize: 14,
                ),
              ),
            const SizedBox(height: 8),
            Text(
              drug.description,
              style: theme.textTheme.bodyMedium?.copyWith(fontSize: 14),
            ),
          ],
        ),
        Divider(),
      ],
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
