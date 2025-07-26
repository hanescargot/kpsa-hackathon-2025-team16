import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../../providers/auth_provider.dart';

class LoginScreen extends ConsumerWidget {
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Scaffold(
      appBar: AppBar(title: const Text("약속이")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
                // 로그인 상태 저장
                ref.read(authProvider.notifier).state = true;
                // 홈으로 이동 (GoRouter가 리다이렉트 자동 처리도 가능하지만 명시적 이동도 OK)
                context.go('/home');
              },
              child: const Text('로그인'),
            ),
          ],
        ),
      ),
    );
  }
}
