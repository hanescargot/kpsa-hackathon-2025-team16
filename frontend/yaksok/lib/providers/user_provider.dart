import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter/material.dart';

final userInfoProvider = StateProvider<UserInfo?>((ref) => null);

class UserInfo {
  final String name;
  final DateTime birth;
  final String phone;
  final int mealCount;
  final List<TimeOfDay> mealTimes;

  UserInfo({
    required this.name,
    required this.birth,
    required this.phone,
    required this.mealCount,
    required this.mealTimes,
  });
}
