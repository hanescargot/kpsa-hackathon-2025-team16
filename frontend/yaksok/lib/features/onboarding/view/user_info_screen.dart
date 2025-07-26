import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';
import 'package:yaksok/util.dart';
import '../../../providers/user_provider.dart';

class NewUserInfoScreen extends ConsumerStatefulWidget {
  const NewUserInfoScreen({super.key});

  @override
  ConsumerState<NewUserInfoScreen> createState() => _UserInfoScreenState();
}

class _UserInfoScreenState extends ConsumerState<NewUserInfoScreen> {
  final TextEditingController idController = TextEditingController();
  final TextEditingController pwController = TextEditingController();
  final TextEditingController birthController = TextEditingController();
  final TextEditingController phoneController = TextEditingController();
  final TextEditingController guardianController = TextEditingController();
  final TextEditingController sleepController = TextEditingController();

  String morningTime = '09:30';
  String lunchTime = '12:30';
  String dinnerTime = '19:30';

  DateTime? birthDate;
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
        const Text('평소 수면 스케줄', style: TextStyle(fontWeight: FontWeight.bold)),
        const SizedBox(height: 12),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text('$mealStartHour시'),
            Expanded(
              child: RangeSlider(
                inactiveColor: Colors.white70,
                activeColor: kColorPrimary,
                min: 0,
                max: 23,
                divisions: 23,
                values: RangeValues(
                  mealStartHour.toDouble(),
                  mealEndHour.toDouble(),
                ),
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
      backgroundColor: const Color(0xFF99C2FF),
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Center(
                child: Column(
                  children: [
                    SizedBox(height: 12),
                    Image(
                      image: AssetImage('assets/logo/logo_rmbg.png'),
                      height: 80,
                    ),
                    SizedBox(height: 12),
                    Text(
                      '회원가입',
                      style: TextStyle(
                        fontSize: 28,
                        fontWeight: FontWeight.bold,
                        color: Colors.black87,
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 24),

              _buildLabel('아이디'),
              _buildTextField(idController, hint: '아이디'),

              _buildLabel('비밀번호'),
              _buildTextField(pwController, hint: '비밀번호', obscure: true),

              _buildLabel('생년월일'),
              _buildTextField(birthController, hint: '8자리'),

              _buildLabel('전화번호'),
              _buildTextField(phoneController, hint: '010-xxxx-xxxx'),

              _buildLabel('보호자 전화번호'),
              _buildTextField(guardianController, hint: '010-xxxx-xxxx'),

              const SizedBox(height: 24),
              buildHourRangeSelector(),
              const SizedBox(height: 12),

              Container(color: Colors.white, child: _buildMealTimeRow()),

              const SizedBox(height: 24),
              SizedBox(
                width: double.infinity,
                height: 48,
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.blue.shade800,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(12),
                    ),
                  ),
                  onPressed: () {
                    // ref.read(userInfoProvider.notifier).state = user;
                    context.go('/home'); // 홈 화면으로 이동
                  },
                  child: const Text(
                    '회원가입',
                    style: TextStyle(color: Colors.white, fontSize: 16),
                  ),
                ),
              ),
              const SizedBox(height: 32),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildLabel(String label) {
    return Padding(
      padding: const EdgeInsets.only(top: 16.0, bottom: 6),
      child: Text(
        label,
        style: const TextStyle(
          fontWeight: FontWeight.bold,
          fontSize: 16,
          color: Colors.black87,
        ),
      ),
    );
  }

  Widget _buildTextField(
    TextEditingController controller, {
    String? hint,
    bool obscure = false,
  }) {
    return TextField(
      controller: controller,
      obscureText: obscure,
      decoration: InputDecoration(
        hintText: hint,
        filled: true,
        fillColor: Colors.white,
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: BorderSide.none,
        ),
        contentPadding: const EdgeInsets.symmetric(
          horizontal: 16,
          vertical: 12,
        ),
      ),
    );
  }

  Widget _buildMealTimeRow() {
    return Column(
      children: [
        _buildTimeDropdownRow('아침', morningTime, (val) {
          setState(() => morningTime = val);
        }),
        const SizedBox(height: 8),
        _buildTimeDropdownRow('점심', lunchTime, (val) {
          setState(() => lunchTime = val);
        }),
        const SizedBox(height: 8),
        _buildTimeDropdownRow('저녁', dinnerTime, (val) {
          setState(() => dinnerTime = val);
        }),
      ],
    );
  }

  Widget _buildTimeDropdownRow(
    String label,
    String value,
    void Function(String) onChanged,
  ) {
    final timeOptions = [
      '06:00',
      '07:00',
      '08:00',
      '09:00',
      '09:30',
      '10:00',
      '11:00',
      '12:00',
      '12:30',
      '13:00',
      '14:00',
      '17:00',
      '18:00',
      '19:00',
      '19:30',
      '20:00',
    ];

    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text(label, style: const TextStyle(fontWeight: FontWeight.bold)),
        DropdownButton<String>(
          value: value,
          onChanged: (v) => onChanged(v!),
          items: timeOptions
              .map((e) => DropdownMenuItem(value: e, child: Text(e)))
              .toList(),
        ),
      ],
    );
  }
}
