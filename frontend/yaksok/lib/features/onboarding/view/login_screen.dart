import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart'; // ConsumerWidget을 위한 import
import 'package:go_router/go_router.dart';

import '../../../providers/auth_provider.dart';
import '../../../util.dart';

class LoginScreen extends ConsumerWidget {
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Stack(
          children: [
            // 닫기 버튼 (X)
            // Align(
            //   alignment: Alignment.topRight,
            //   child: Padding(
            //     padding: const EdgeInsets.all(12),
            //     child: IconButton(
            //       icon: const Icon(Icons.close, color: Colors.white),
            //       onPressed: () => Navigator.pop(context),
            //       style: IconButton.styleFrom(
            //         backgroundColor: kColorPrimary,
            //         padding: const EdgeInsets.all(10),
            //       ),
            //     ),
            //   ),
            // ),

            // 메인 로그인 UI
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
                        Text(
                          '약속이',
                          style: TextStyle(
                            fontSize: 32,
                            fontWeight: FontWeight.bold,
                            fontFamily: 'MapleStory',
                            color: kColorPrimary,
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
                          fontWeight: FontWeight.normal,
                          fontFamily: 'MapleStory',
                          color: Colors.black87,
                        ),
                      ),
                    ),
                    const SizedBox(height: 12),

                    const SizedBox(height: 32),

                    TextField(
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

                    TextField(
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
                        onPressed: () {
                          ref.read(authProvider.notifier).state = true;
                          context.go('/home');
                        },
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Text("로그인", style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold)),
                            // SizedBox(width: 16),
                            // const Icon
                            //   (Icons.arrow_forward, color: Colors.white),
                          ],
                        ),
                      ),
                    ),

                    const SizedBox(height: 16),

                    // 소셜 로그인
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
