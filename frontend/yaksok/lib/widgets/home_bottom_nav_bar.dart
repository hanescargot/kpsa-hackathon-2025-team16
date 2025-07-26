import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:yaksok/features/calendar/calendar_screen.dart' hide MedicationCalendarScreen;
import 'package:yaksok/features/camera/camera_screen.dart';

import '../features/calendar/medication_calendar_screen.dart';
import '../features/counseling/counseling_screen.dart';
import '../features/my_page/my_page_screen.dart';
import '../features/rank/medication_challenge_square.dart';
import '../features/ranking/ranking_screen.dart';

class HomeBottomNavBar extends StatelessWidget {
  const HomeBottomNavBar({super.key});

  @override
  Widget build(BuildContext context) {
    return BottomAppBar(
      height: 100,
      // color: Color(0x562379FF), // 배경 투명
      color: Colors.transparent, // 배경 투명
      elevation: 0,
      child: SizedBox(
        height: 70, // 충분한 높이 확보
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
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
        ),
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

  void _navigate(BuildContext context) {
    switch (index) {
      case 0:
        Navigator.push(context, MaterialPageRoute(builder: (_) => const MedicationCalendarScreen()));
        break;
      case 1:
        Navigator.push(context, MaterialPageRoute(builder: (_) => const MedicationChallengeSquare()));
        break;
      case 2:
        Navigator.push(context, MaterialPageRoute(builder: (_) => const CameraGuideScreen()));
        break;
      case 3:
        Navigator.push(context, MaterialPageRoute(builder: (_) => const ConsultationScreen()));
        break;
      case 4:
        Navigator.push(context, MaterialPageRoute(builder: (_) => const MyPageScreen()));
        break;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        InkWell(
          onTap: () => _navigate(context),
          borderRadius: BorderRadius.circular(100),
          child: Container(
            width: 52,
            height: 52,
            decoration: BoxDecoration(
              boxShadow: [
                BoxShadow(
                  color: Colors.black26,
                  blurRadius: 4,
                  offset: Offset(0, 2),
                ),
              ],
              color: Colors.white,
              shape: BoxShape.circle,
            ),
            child: Icon(icon, size: 24, color: Colors.black87),
          ),
        ),
        const SizedBox(height: 4),
        // Text(label, style: const TextStyle(fontSize: 11, color: Colors.black87, fontWeight: FontWeight.bold)),
      ],
    );
  }
}
