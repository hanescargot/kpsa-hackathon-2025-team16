import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

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

  UserInfo copyWith({
    String? name,
    DateTime? birth,
    String? phone,
    int? mealCount,
    List<TimeOfDay>? mealTimes,
  }) {
    return UserInfo(
      name: name ?? this.name,
      birth: birth ?? this.birth,
      phone: phone ?? this.phone,
      mealCount: mealCount ?? this.mealCount,
      mealTimes: mealTimes ?? this.mealTimes,
    );
  }

  @override
  String toString() {
    return 'UserInfo(name: $name, birth: $birth, phone: $phone, '
        'mealCount: $mealCount, mealTimes: $mealTimes)';
  }
}
