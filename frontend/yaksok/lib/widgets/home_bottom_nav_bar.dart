import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:yaksok/features/calendar/calendar_screen.dart' hide MedicationCalendarScreen;
import 'package:yaksok/features/camera/camera_screen.dart';

import '../features/calendar/medication_calendar_screen.dart';
import '../features/counseling/counseling_screen.dart';
import '../features/my_page/my_page_screen.dart';
import '../features/ranking/ranking_screen.dart';

class HomeBottomNavBar extends StatelessWidget {
  const HomeBottomNavBar({super.key});

  @override
  Widget build(BuildContext context) {
    return BottomAppBar(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: const [
          _NavIcon(icon: Icons.calendar_month, label: '캘린더', index: 0),
          _NavIcon(icon: Icons.emoji_events, label: '랭킹', index: 1),
          _NavIcon(icon: Icons.camera_alt, label: '카메라', index: 2),
          _NavIcon(icon: Icons.people, label: '상담', index: 3),
          _NavIcon(icon: Icons.person, label: 'My', index: 4),
        ],
      ),
    );
  }
}

class _NavIcon extends StatelessWidget {
  final IconData icon;
  final String label;
  final int index;

  const _NavIcon({
    required this.icon,
    required this.label,
    required this.index,
  });

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: InkWell(
        onTap: () {
          switch (index) {
            case 0:
              Navigator.of(context).push(
                MaterialPageRoute(
                  builder: (_) => const MedicationCalendarScreen(),
                ),
              );
              break;
            case 1:
              Navigator.of(
                context,
              ).push(MaterialPageRoute(builder: (_) => const RankingScreen()));
              break;
            case 2:
              Navigator.of(context).push(
                MaterialPageRoute(builder: (_) => const CameraGuideScreen()),
              );
              break;
            case 3:
              Navigator.of(context).push(
                MaterialPageRoute(builder: (_) => const ConsultationScreen()),
              );
              break;
            case 4:
              Navigator.of(
                context,
              ).push(MaterialPageRoute(builder: (_) => const MyPageScreen()));
              break;
          }
        },
        child: Column(
          mainAxisSize: MainAxisSize.min,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon, color: Colors.black87),
            const SizedBox(height: 4),
            Text(label, style: const TextStyle(fontSize: 12)),
          ],
        ),
      ),
    );
  }
}
