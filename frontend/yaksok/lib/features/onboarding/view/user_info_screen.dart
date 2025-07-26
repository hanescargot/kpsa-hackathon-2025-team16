import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../providers/user_provider.dart';
import 'package:intl/intl.dart';

class UserInfoScreen extends ConsumerStatefulWidget {
  const UserInfoScreen({super.key});

  @override
  ConsumerState<UserInfoScreen> createState() => _UserInfoScreenState();
}

class _UserInfoScreenState extends ConsumerState<UserInfoScreen> {
  final nameController = TextEditingController();
  final phoneController = TextEditingController();

  DateTime? birthDate;
  int mealCount = 1;
  int mealStartHour = 8;
  int mealEndHour = 20;

  Future<void> pickBirthDate() async {
    final date = await showDatePicker(
      context: context,
      initialDate: DateTime(2000),
      firstDate: DateTime(1900),
      lastDate: DateTime.now(),
    );
    if (date != null) {
      setState(() => birthDate = date);
    }
  }

  Widget buildHourRangeSelector() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text('식사 시간 구간 (1시간 단위)', style: TextStyle(fontWeight: FontWeight.bold)),
        const SizedBox(height: 12),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text('$mealStartHour시'),
            Expanded(
              child: RangeSlider(
                min: 0,
                max: 23,
                divisions: 23,
                values: RangeValues(mealStartHour.toDouble(), mealEndHour.toDouble()),
                labels: RangeLabels('$mealStartHour시', '$mealEndHour시'),
                onChanged: (values) {
                  setState(() {
                    mealStartHour = values.start.round();
                    mealEndHour = values.end.round();
                  });
                },
              ),
            ),
            Text('$mealEndHour시'),
          ],
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('사용자 정보 입력')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: ListView(
          children: [
            TextField(
              controller: nameController,
              decoration: const InputDecoration(labelText: '이름'),
            ),
            const SizedBox(height: 12),
            GestureDetector(
              onTap: pickBirthDate,
              child: InputDecorator(
                decoration: const InputDecoration(labelText: '생년월일'),
                child: Text(
                  birthDate != null
                      ? DateFormat('yyyy-MM-dd').format(birthDate!)
                      : '날짜 선택',
                ),
              ),
            ),
            const SizedBox(height: 12),
            TextField(
              controller: phoneController,
              decoration: const InputDecoration(labelText: '전화번호'),
              keyboardType: TextInputType.phone,
            ),
            const SizedBox(height: 12),
            DropdownButtonFormField<int>(
              value: mealCount,
              decoration: const InputDecoration(labelText: '하루 식사 횟수'),
              items: [1, 2, 3]
                  .map((count) =>
                  DropdownMenuItem(value: count, child: Text('$count회')))
                  .toList(),
              onChanged: (value) {
                if (value != null) {
                  setState(() {
                    mealCount = value;
                  });
                }
              },
            ),
            const SizedBox(height: 24),
            buildHourRangeSelector(),
            const SizedBox(height: 24),
            ElevatedButton(
              onPressed: () {
                final user = UserInfo(
                  name: nameController.text,
                  birth: birthDate ?? DateTime(2000, 1, 1),
                  phone: phoneController.text,
                  mealCount: mealCount,
                  mealTimes: [
                    TimeOfDay(hour: mealStartHour, minute: 0),
                    TimeOfDay(hour: mealEndHour, minute: 0),
                  ],
                );
                ref.read(userInfoProvider.notifier).state = user;
                // ScaffoldMessenger.of(context).showSnackBar(
                //   const SnackBar(content: Text("사용자 정보 저장됨")),
                // );
                // TODO: 다음 화면 이동
                Navigator.of(context).pop();

              },
              child: const Text('저장하고 계속하기'),
            ),
          ],
        ),
      ),
    );
  }
}
