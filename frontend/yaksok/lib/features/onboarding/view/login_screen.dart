import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../../providers/auth_provider.dart';
import '../../../service/app_service.dart';
import '../../../util.dart';

class LoginScreen extends ConsumerStatefulWidget {
  const LoginScreen({super.key});

  @override
  ConsumerState<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends ConsumerState<LoginScreen> {
  final usernameController = TextEditingController();
  final passwordController = TextEditingController();

  @override
  void dispose() {
    usernameController.dispose();
    passwordController.dispose();
    super.dispose();
  }

  Future<void> _handleLogin(BuildContext context) async {
    try {
      final result = await ApiService().login(
        username: usernameController.text.trim(),
        password: passwordController.text,
      );

      ref.read(authProvider.notifier).state = true;
      context.go('/home');
    } catch (e) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('로그인 실패: $e')));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Stack(
          children: [
            Center(
              child: SingleChildScrollView(
                padding: const EdgeInsets.symmetric(horizontal: 32),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    const SizedBox(height: 32),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Image.asset('assets/logo/logo_rmbg.png', height: 140),
                        InkWell(
                          onTap: (){
                            usernameController.text = "abcd@test.com";
                            passwordController.text = "abcd1234";
                            setState(() {});
                          },
                          child: Text(
                            '약속이',
                            style: TextStyle(
                              fontSize: 32,
                              fontWeight: FontWeight.bold,
                              fontFamily: 'MapleStory',
                              color: kColorPrimary,
                            ),
                          ),
                        ),
                      ],
                    ),
                    SizedBox(
                      width: 200,
                      child: Text(
                        '복약, 혼자 하지 마세요. 함께 지키고, 함께 건강해지는 AI 복약 챌린지 플랫폼',
                        style: TextStyle(
                          fontSize: 12,
                          fontFamily: 'MapleStory',
                          color: Colors.black87,
                        ),
                      ),
                    ),
                    const SizedBox(height: 32),

                    // 아이디 입력
                    TextField(
                      controller: usernameController,
                      decoration: InputDecoration(
                        prefixIcon: const Icon(Icons.email_outlined),
                        labelText: '아이디',
                        labelStyle: TextStyle(
                          color: kColorPrimary,
                          fontWeight: FontWeight.bold,
                        ),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(12),
                        ),
                      ),
                    ),
                    const SizedBox(height: 16),

                    // 비밀번호 입력
                    TextField(
                      controller: passwordController,
                      obscureText: true,
                      decoration: InputDecoration(
                        prefixIcon: const Icon(Icons.lock_outline),
                        labelText: '비밀번호',
                        labelStyle: TextStyle(
                          color: kColorPrimary,
                          fontWeight: FontWeight.bold,
                        ),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(12),
                        ),
                      ),
                    ),

                    const SizedBox(height: 12),

                    // 로그인 버튼
                    SizedBox(
                      width: double.infinity,
                      height: 48,
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          backgroundColor: kColorPrimary,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(12),
                          ),
                        ),
                        onPressed: () => _handleLogin(context),
                        child: const Text(
                          "로그인",
                          style: TextStyle(
                            color: Colors.white,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ),

                    const SizedBox(height: 12),

                    // // 🧪 테스트 자동 입력 버튼
                    // InkWell(
                    //   onTap: () {
                    //     usernameController.text = "abcd@test.com";
                    //     passwordController.text = "abcd1234";
                    //     setState(() {});
                    //   },
                    //   child: const Text(
                    //     '🧪 테스트 계정 자동입력',
                    //     style: TextStyle(
                    //       color: Colors.blue,
                    //       fontWeight: FontWeight.bold,
                    //     ),
                    //   ),
                    // ),

                    const SizedBox(height: 16),
                    Image.asset('assets/tr/sns_login.png'),
                    const SizedBox(height: 32),
                    TextButton(
                      onPressed: () {},
                      child: const Text(
                        '비밀번호를 잊으셨나요?',
                        style: TextStyle(color: Colors.black87),
                      ),
                    ),
                    const SizedBox(height: 8),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        const Text(
                          '계정이 없으신가요? ',
                          style: TextStyle(color: Colors.black87),
                        ),
                        TextButton(
                          onPressed: () {
                            // 회원가입 이동
                          },
                          child: Text(
                            '회원가입',
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              color: kColorPrimary,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
