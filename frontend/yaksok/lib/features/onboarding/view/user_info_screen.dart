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
  List<TimeOfDay> mealTimes = [TimeOfDay.now()];

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

  Future<void> pickMealTime(int index) async {
    final time = await showTimePicker(
      context: context,
      initialTime: mealTimes[index],
    );
    if (time != null) {
      setState(() => mealTimes[index] = time);
    }
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
                    mealTimes = List.generate(
                      mealCount,
                          (index) => mealTimes.length > index
                          ? mealTimes[index]
                          : TimeOfDay.now(),
                    );
                  });
                }
              },
            ),
            const SizedBox(height: 12),
            ...List.generate(mealCount, (index) {
              final time = mealTimes[index];
              return ListTile(
                title: Text('식사 시간 ${index + 1}'),
                subtitle: Text('${time.hour}시 ${time.minute}분'),
                onTap: () => pickMealTime(index),
              );
            }),
            const SizedBox(height: 24),
            ElevatedButton(
              onPressed: () {
                final user = UserInfo(
                  name: nameController.text,
                  birth: birthDate ?? DateTime(2000, 1, 1),
                  phone: phoneController.text,
                  mealCount: mealCount,
                  mealTimes: mealTimes,
                );
                ref.read(userInfoProvider.notifier).state = user;
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text("사용자 정보 저장됨")),
                );
                // TODO: 다음 화면 이동
              },
              child: const Text('저장하고 계속하기'),
            ),
          ],
        ),
      ),
    );
  }
}
