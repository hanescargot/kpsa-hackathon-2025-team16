import 'package:flutter/material.dart';

class HomeBottomNavBar extends StatelessWidget {
  const HomeBottomNavBar({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      padding: const EdgeInsets.symmetric(vertical: 8),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: const [
          _NavIcon(icon: Icons.calendar_month),
          _NavIcon(icon: Icons.emoji_events),
          _NavIcon(icon: Icons.camera_alt),
          _NavIcon(icon: Icons.person),
          _NavIcon(icon: Icons.medication),
        ],
      ),
    );
  }
}

class _NavIcon extends StatelessWidget {
  final IconData icon;

  const _NavIcon({required this.icon});

  @override
  Widget build(BuildContext context) {
    return Icon(icon, color: Colors.black87);
  }
}
