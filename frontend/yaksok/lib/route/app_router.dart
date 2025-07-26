import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:yaksok/features/home/view/new_home_screen.dart';
import '../features/home/view/video_call_screen.dart';
import '../features/onboarding/view/login_screen.dart';
import '../features/onboarding/view/new_user_info_screen.dart';
import '../features/onboarding/view/onboarding_screen.dart';
import '../providers/auth_provider.dart';
import '../providers/user_provider.dart';
import 'go_router_refresh_stream.dart';

final appRouterProvider = Provider<GoRouter>((ref) {
  final authState = ref.watch(authProvider);
  final userInfoState = ref.watch(userInfoProvider);

  return GoRouter(
    initialLocation: '/login',
    refreshListenable: GoRouterRefreshStream(
      ref.watch(authProvider.notifier).stream,
    ),
    redirect: (context, state) {
      final isLoggedIn = authState;
      final hasUserInfo = userInfoState != null;
      final loc = state.matchedLocation;

      // 1. 로그인 안함
      if (!isLoggedIn && loc != '/login') {
        return '/login';
      }

      // 2. 로그인했지만 사용자 정보 없음 → /userinfo 로
      if (isLoggedIn && !hasUserInfo && loc != '/userinfo') {
        return '/userinfo';
      }

      // 3. 로그인했고 user info 있음인데 /login이나 /userinfo에 있음 → /home 으로
      if (isLoggedIn &&
          hasUserInfo &&
          (loc == '/login' || loc == '/userinfo')) {
        return '/home';
      }

      return null; // 기본 유지
    },
    routes: [
      GoRoute(
        path: '/login',
        name: 'login',
        builder: (context, state) => LoginScreen(),
      ),
      GoRoute(
        path: '/onboarding',
        name: 'onboarding',
        builder: (context, state) => const OnboardingScreen(),
      ),
      GoRoute(
        path: '/userinfo',
        name: 'userinfo',
        builder: (context, state) => const NewUserInfoScreen(),
      ),
      GoRoute(
        path: '/home',
        name: 'home',
        builder: (context, state) => const NewHomeScreen(),
      ),
      GoRoute(
        path: '/video-notification',
        name: VideoNotificationScreen.routeName,
        builder: (context, state) => const VideoNotificationScreen(),
      ),
    ],
  );
});
