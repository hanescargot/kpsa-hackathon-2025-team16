import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart'; // ConsumerWidget을 위한 import
import 'package:go_router/go_router.dart';

import '../../../providers/auth_provider.dart';

class LoginScreen extends ConsumerWidget {
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final themeColor = const Color(0xFFFF6B6B);

    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Stack(
          children: [
            // 닫기 버튼 (X)
            Align(
              alignment: Alignment.topRight,
              child: Padding(
                padding: const EdgeInsets.all(12),
                child: IconButton(
                  icon: const Icon(Icons.close, color: Colors.white),
                  onPressed: () => Navigator.pop(context),
                  style: IconButton.styleFrom(
                    backgroundColor: themeColor,
                    padding: const EdgeInsets.all(10),
                  ),
                ),
              ),
            ),

            // 메인 로그인 UI
            Center(
              child: SingleChildScrollView(
                padding: const EdgeInsets.symmetric(horizontal: 32),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    const SizedBox(height: 32),
                    const Text(
                      '로그인',
                      style: TextStyle(fontSize: 32, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 32),

                    Image.asset(
                      'assets/images/pill.png',
                      height: 100,
                    ),

                    const SizedBox(height: 32),

                    TextField(
                      decoration: InputDecoration(
                        prefixIcon: const Icon(Icons.email_outlined),
                        labelText: '아이디',
                        labelStyle: TextStyle(color: themeColor),
                        border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
                      ),
                    ),
                    const SizedBox(height: 16),

                    TextField(
                      obscureText: true,
                      decoration: InputDecoration(
                        prefixIcon: const Icon(Icons.lock_outline),
                        labelText: '비밀번호',
                        labelStyle: TextStyle(color: themeColor),
                        border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
                      ),
                    ),

                    const SizedBox(height: 12),

                    // 로그인 버튼
                    SizedBox(
                      width: double.infinity,
                      height: 48,
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                          backgroundColor: themeColor,
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
                            Text("로그인", style: TextStyle(color: Colors.white),),
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
                      child: const Text('비밀번호를 잊으셨나요?'),
                    ),
                    const SizedBox(height: 8),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        const Text('계정이 없으신가요? '),
                        TextButton(
                          onPressed: () {
                            // 회원가입 이동
                          },
                          child: const Text('회원가입', style: TextStyle(fontWeight: FontWeight.bold)),
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
